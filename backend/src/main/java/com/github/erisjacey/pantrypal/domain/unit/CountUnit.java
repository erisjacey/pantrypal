package com.github.erisjacey.pantrypal.domain.unit;

import java.math.BigDecimal;

/**
 * Units of measurement for discrete countable items.
 */
public enum CountUnit implements Unit {

  PIECE("piece", BigDecimal.ONE),
  DOZEN("dozen", new BigDecimal("12"));

  private final String code;
  private final BigDecimal toBaseFactor;

  CountUnit(String code, BigDecimal toBaseFactor) {
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
    return UnitType.COUNT;
  }
}
