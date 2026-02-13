import { assertEquals, assertThrows } from 'jsr:@std/assert@1'
import { Quantity } from '../_shared/domain/Quantity.ts'
import { VolumeUnit, WeightUnit } from '../_shared/domain/Unit.ts'
import { Item } from '../_shared/domain/Item.ts'
import { ItemAggregate } from '../_shared/domain/ItemAggregate.ts'

Deno.test('Quantity: converts liters to milliliters', () => {
  const qty = Quantity.create(1, VolumeUnit.LITER)
  const converted = qty.convertTo(VolumeUnit.MILLILITER)
  assertEquals(converted.amount, 1000)
})

Deno.test('Quantity: adds different volume units', () => {
  const qty1 = Quantity.create(1, VolumeUnit.LITER)
  const qty2 = Quantity.create(500, VolumeUnit.MILLILITER)
  const result = qty1.add(qty2)
  assertEquals(result.amount, 1.5)
})

Deno.test('Quantity: throws on incompatible units', () => {
  const volume = Quantity.create(1, VolumeUnit.LITER)
  assertThrows(() => volume.convertTo(WeightUnit.GRAM))
})

Deno.test('ItemAggregate: FIFO consumption (partial)', () => {
  const batch1 = Item.create({
    id: 'b1',
    name: 'Milk',
    currentQuantity: Quantity.create(1, VolumeUnit.LITER),
    initialQuantity: Quantity.create(1, VolumeUnit.LITER),
    expiryDate: new Date('2026-01-15'),
    purchaseDate: new Date(),
  })
  const batch2 = Item.create({
    id: 'b2',
    name: 'Milk',
    currentQuantity: Quantity.create(1, VolumeUnit.LITER),
    initialQuantity: Quantity.create(1, VolumeUnit.LITER),
    expiryDate: new Date('2026-01-20'),
    purchaseDate: new Date(),
  })

  const aggregate = new ItemAggregate('barcode', 'Milk', [batch1, batch2])
  aggregate.consume(Quantity.create(0.5, VolumeUnit.LITER))

  const batches = aggregate.getBatches()
  assertEquals(batches.length, 2)
  assertEquals(batches[0].getCurrentQuantity().amount, 0.5)
  assertEquals(batches[1].getCurrentQuantity().amount, 1)
  assertEquals(aggregate.getConsumedBatches().length, 0)
})

Deno.test('ItemAggregate: fully consumed batches tracked separately', () => {
  const batch1 = Item.create({
    id: 'b1',
    name: 'Milk',
    currentQuantity: Quantity.create(1, VolumeUnit.LITER),
    initialQuantity: Quantity.create(1, VolumeUnit.LITER),
    expiryDate: new Date('2026-01-15'),
    purchaseDate: new Date(),
  })
  const batch2 = Item.create({
    id: 'b2',
    name: 'Milk',
    currentQuantity: Quantity.create(1, VolumeUnit.LITER),
    initialQuantity: Quantity.create(1, VolumeUnit.LITER),
    expiryDate: new Date('2026-01-20'),
    purchaseDate: new Date(),
  })

  const aggregate = new ItemAggregate('barcode', 'Milk', [batch1, batch2])
  // Consume 1.5L: fully consumes batch1 (1L), partially consumes batch2 (0.5L)
  aggregate.consume(Quantity.create(1.5, VolumeUnit.LITER))

  const remaining = aggregate.getBatches()
  assertEquals(remaining.length, 1)
  assertEquals(remaining[0].id, 'b2')
  assertEquals(remaining[0].getCurrentQuantity().amount, 0.5)

  const consumed = aggregate.getConsumedBatches()
  assertEquals(consumed.length, 1)
  assertEquals(consumed[0].id, 'b1')
  assertEquals(consumed[0].isFullyConsumed(), true)
})

Deno.test('ItemAggregate: all batches consumed moves all to consumedBatches', () => {
  const batch1 = Item.create({
    id: 'b1',
    name: 'Milk',
    currentQuantity: Quantity.create(1, VolumeUnit.LITER),
    initialQuantity: Quantity.create(1, VolumeUnit.LITER),
    expiryDate: new Date('2026-01-15'),
    purchaseDate: new Date(),
  })

  const aggregate = new ItemAggregate('barcode', 'Milk', [batch1])
  aggregate.consume(Quantity.create(1, VolumeUnit.LITER))

  assertEquals(aggregate.getBatches().length, 0)
  assertEquals(aggregate.getBatchCount(), 0)
  assertEquals(aggregate.getConsumedBatches().length, 1)
  assertEquals(aggregate.getConsumedBatches()[0].id, 'b1')
})
