import { Unit, VolumeUnit, WeightUnit, getUnitType, UnitType } from './Unit.ts';

const VOLUME_TO_ML: Record<VolumeUnit, number> = {
  [VolumeUnit.MILLILITER]: 1,
  [VolumeUnit.LITER]: 1000,
  [VolumeUnit.FLUID_OUNCE]: 29.5735,
  [VolumeUnit.CUP]: 236.588,
  [VolumeUnit.TABLESPOON]: 14.7868,
  [VolumeUnit.TEASPOON]: 4.92892,
  [VolumeUnit.GALLON]: 3785.41,
  [VolumeUnit.PINT]: 473.176,
  [VolumeUnit.QUART]: 946.353,
};

const WEIGHT_TO_GRAMS: Record<WeightUnit, number> = {
  [WeightUnit.MILLIGRAM]: 0.001,
  [WeightUnit.GRAM]: 1,
  [WeightUnit.KILOGRAM]: 1000,
  [WeightUnit.OUNCE]: 28.3495,
  [WeightUnit.POUND]: 453.592,
};

export class IncompatibleUnitsError extends Error {
  constructor(unit1: Unit, unit2: Unit) {
    super(`Cannot compare ${unit1} with ${unit2} - incompatible unit types`);
    this.name = 'IncompatibleUnitsError';
  }
}

export class Quantity {
  private constructor(
    public readonly amount: number,
    public readonly unit: Unit
  ) {
    if (amount < 0) {
      throw new Error('Quantity amount cannot be negative');
    }
  }

  static create = (amount: number, unit: Unit): Quantity => {
    return new Quantity(amount, unit);
  };

  toBaseUnit = (): number => {
    const unitType = getUnitType(this.unit);
    switch (unitType) {
      case UnitType.VOLUME:
        return this.amount * VOLUME_TO_ML[this.unit as VolumeUnit];
      case UnitType.WEIGHT:
        return this.amount * WEIGHT_TO_GRAMS[this.unit as WeightUnit];
      case UnitType.COUNT:
        return this.unit === 'dozen' ? this.amount * 12 : this.amount;
      default:
        throw new Error(`Unknown unit type: ${this.unit}`);
    }
  };

  convertTo = (targetUnit: Unit): Quantity => {
    if (getUnitType(this.unit) !== getUnitType(targetUnit)) {
      throw new IncompatibleUnitsError(this.unit, targetUnit);
    }
    const baseAmount = this.toBaseUnit();
    const unitType = getUnitType(targetUnit);
    let targetAmount: number;
    switch (unitType) {
      case UnitType.VOLUME:
        targetAmount = baseAmount / VOLUME_TO_ML[targetUnit as VolumeUnit];
        break;
      case UnitType.WEIGHT:
        targetAmount = baseAmount / WEIGHT_TO_GRAMS[targetUnit as WeightUnit];
        break;
      case UnitType.COUNT:
        targetAmount = targetUnit === 'dozen' ? baseAmount / 12 : baseAmount;
        break;
      default:
        throw new Error(`Unknown unit type: ${targetUnit}`);
    }
    return Quantity.create(targetAmount, targetUnit);
  };

  add = (other: Quantity): Quantity => {
    if (getUnitType(this.unit) !== getUnitType(other.unit)) {
      throw new IncompatibleUnitsError(this.unit, other.unit);
    }
    const otherConverted = other.convertTo(this.unit);
    return Quantity.create(this.amount + otherConverted.amount, this.unit);
  };

  subtract = (other: Quantity): Quantity => {
    if (getUnitType(this.unit) !== getUnitType(other.unit)) {
      throw new IncompatibleUnitsError(this.unit, other.unit);
    }
    const otherConverted = other.convertTo(this.unit);
    const result = this.amount - otherConverted.amount;
    if (result < 0) {
      throw new Error('Cannot subtract: result would be negative');
    }
    return Quantity.create(result, this.unit);
  };

  isGreaterThanOrEqual = (other: Quantity): boolean => {
    return this.toBaseUnit() >= other.toBaseUnit();
  };

  canSubtract = (other: Quantity): boolean => {
    try {
      return this.isGreaterThanOrEqual(other);
    } catch {
      return false;
    }
  };

  equals = (other: Quantity): boolean => {
    return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < 0.001;
  };

  toString = (): string => {
    return `${this.amount.toFixed(2)} ${this.unit}`;
  };
}
