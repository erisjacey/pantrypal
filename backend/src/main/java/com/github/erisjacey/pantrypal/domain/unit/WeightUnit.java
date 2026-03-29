package com.github.erisjacey.pantrypal.domain.unit;

import java.math.BigDecimal;

/**
 * Units of measurement for mass and weight.
 */
public enum WeightUnit implements Unit {

  G("g", BigDecimal.ONE),
  MG("mg", new BigDecimal("0.001")),
  KG("kg", new BigDecimal("1000")),
  OZ("oz", new BigDecimal("28.349523")),
  LB("lb", new BigDecimal("453.59237"));

  private final String code;
  private final BigDecimal toBaseFactor;

  WeightUnit(String code, BigDecimal toBaseFactor) {
    this.code = code;
    this.toBaseFactor = toBaseFactor;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public BigDecimal getToBaseFactor() {
    return toBaseFactor;
  }

  @Override
  public UnitType getUnitType() {
    return UnitType.WEIGHT;
  }
}
