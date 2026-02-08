export enum UnitType {
  VOLUME = 'VOLUME',
  WEIGHT = 'WEIGHT',
  COUNT = 'COUNT',
}

export enum VolumeUnit {
  MILLILITER = 'ml',
  LITER = 'L',
  FLUID_OUNCE = 'fl_oz',
  CUP = 'cup',
  TABLESPOON = 'tbsp',
  TEASPOON = 'tsp',
  GALLON = 'gal',
  PINT = 'pt',
  QUART = 'qt',
}

export enum WeightUnit {
  MILLIGRAM = 'mg',
  GRAM = 'g',
  KILOGRAM = 'kg',
  OUNCE = 'oz',
  POUND = 'lb',
}

export enum CountUnit {
  PIECE = 'piece',
  DOZEN = 'dozen',
  PACKAGE = 'package',
}

export type Unit = VolumeUnit | WeightUnit | CountUnit;

export const getUnitType = (unit: Unit): UnitType => {
  if (Object.values(VolumeUnit).includes(unit as VolumeUnit)) {
    return UnitType.VOLUME;
  }
  if (Object.values(WeightUnit).includes(unit as WeightUnit)) {
    return UnitType.WEIGHT;
  }
  return UnitType.COUNT;
};
