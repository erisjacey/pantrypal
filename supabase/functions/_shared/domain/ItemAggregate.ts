import { Quantity } from './Quantity.ts'
import { InsufficientQuantityError, Item } from './Item.ts'
import type { Unit } from './Unit.ts'

export class ItemAggregate {
  private consumedBatches: Item[] = []

  constructor(
    public readonly barcode: string,
    public readonly name: string,
    private batches: Item[],
  ) {
    // Sort by expiry date (FIFO - oldest first)
    this.batches.sort((a, b) => {
      if (!a.expiryDate) {
        return 1
      }
      if (!b.expiryDate) {
        return -1
      }
      return a.expiryDate.getTime() - b.expiryDate.getTime()
    })
  }

  getTotalQuantity = (targetUnit: string): Quantity => {
    if (this.batches.length === 0) {
      return Quantity.create(0, targetUnit as Unit)
    }
    let total = Quantity.create(0, targetUnit as Unit)
    for (const batch of this.batches) {
      const converted = batch.getCurrentQuantity().convertTo(targetUnit as Unit)
      total = total.add(converted)
    }
    return total
  }

  hasEnough = (required: Quantity): boolean => {
    if (this.batches.length === 0) {
      return false
    }
    const total = this.getTotalQuantity(required.unit)
    return total.canSubtract(required)
  }

  consume = (amount: Quantity): void => {
    if (!this.hasEnough(amount)) {
      const available = this.getTotalQuantity(amount.unit)
      throw new InsufficientQuantityError(available, amount)
    }
    let remaining = amount
    for (const batch of this.batches) {
      if (remaining.amount === 0) {
        break
      }
      const batchQty = batch.getCurrentQuantity()
      if (batchQty.canSubtract(remaining)) {
        batch.consume(remaining)
        remaining = Quantity.create(0, remaining.unit)
      } else {
        const consumed = batchQty.convertTo(remaining.unit)
        batch.consume(batchQty)
        remaining = remaining.subtract(consumed)
      }
    }
    const fullyConsumed = this.batches.filter((b) => b.isFullyConsumed())
    this.consumedBatches.push(...fullyConsumed)
    this.batches = this.batches.filter((b) => !b.isFullyConsumed())
  }

  getBatches = (): Item[] => {
    return [...this.batches]
  }

  getConsumedBatches = (): Item[] => {
    return [...this.consumedBatches]
  }

  getBatchCount = (): number => {
    return this.batches.length
  }
}
