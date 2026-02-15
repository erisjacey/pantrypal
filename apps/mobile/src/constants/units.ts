export type UnitType = 'volume' | 'weight' | 'count'

export interface UnitOption {
  value: string
  label: string
  type: UnitType
}

export const UNIT_OPTIONS: UnitOption[] = [
  // Volume (alphabetical)
  { value: 'cup', label: 'Cups', type: 'volume' },
  { value: 'fl_oz', label: 'Fluid Ounces (fl oz)', type: 'volume' },
  { value: 'gal', label: 'Gallons (gal)', type: 'volume' },
  { value: 'L', label: 'Liters (L)', type: 'volume' },
  { value: 'ml', label: 'Milliliters (ml)', type: 'volume' },
  { value: 'pt', label: 'Pints (pt)', type: 'volume' },
  { value: 'qt', label: 'Quarts (qt)', type: 'volume' },
  { value: 'tbsp', label: 'Tablespoons (tbsp)', type: 'volume' },
  { value: 'tsp', label: 'Teaspoons (tsp)', type: 'volume' },

  // Weight (alphabetical)
  { value: 'g', label: 'Grams (g)', type: 'weight' },
  { value: 'kg', label: 'Kilograms (kg)', type: 'weight' },
  { value: 'mg', label: 'Milligrams (mg)', type: 'weight' },
  { value: 'oz', label: 'Ounces (oz)', type: 'weight' },
  { value: 'lb', label: 'Pounds (lb)', type: 'weight' },

  // Count (alphabetical)
  { value: 'dozen', label: 'Dozen', type: 'count' },
  { value: 'package', label: 'Packages', type: 'count' },
  { value: 'piece', label: 'Pieces', type: 'count' },
]

export const UNIT_SECTIONS = [
  { title: 'Volume', data: UNIT_OPTIONS.filter((u) => u.type === 'volume') },
  { title: 'Weight', data: UNIT_OPTIONS.filter((u) => u.type === 'weight') },
  { title: 'Count', data: UNIT_OPTIONS.filter((u) => u.type === 'count') },
]

export const getUnitLabel = (value: string): string => {
  return UNIT_OPTIONS.find((u) => u.value === value)?.label ?? value
}
