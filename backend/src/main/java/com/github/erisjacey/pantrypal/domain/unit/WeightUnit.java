package com.github.erisjacey.pantrypal.domain.unit;

/**
 * Units of measurement for mass and weight.
 */
public enum WeightUnit implements Unit {

  MG("mg"), G("g"), KG("kg"), OZ("oz"), LB("lb");

  private final String code;

  WeightUnit(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public UnitType getUnitType() {
    return UnitType.WEIGHT;
  }
}
