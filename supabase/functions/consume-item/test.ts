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

Deno.test('ItemAggregate: FIFO consumption', () => {
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
  assertEquals(batches[0].getCurrentQuantity().amount, 0.5)
  assertEquals(batches[1].getCurrentQuantity().amount, 1)
})
