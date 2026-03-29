package com.github.erisjacey.pantrypal.domain.unit;

import java.math.BigDecimal;

/**
 * Units of measurement for liquid/fluid volume.
 */
public enum VolumeUnit implements Unit {

  ML("ml", BigDecimal.ONE),
  L("l", new BigDecimal("1000")),
  FL_OZ("fl_oz", new BigDecimal("29.573530")),
  CUP("cup", new BigDecimal("236.588237")),
  TBSP("tbsp", new BigDecimal("14.786765")),
  TSP("tsp", new BigDecimal("4.928922")),
  GAL("gal", new BigDecimal("3785.411784")),
  PT("pt", new BigDecimal("473.176473")),
  QT("qt", new BigDecimal("946.352946"));

  private final String code;
  private final BigDecimal toBaseFactor;

  VolumeUnit(String code, BigDecimal toBaseFactor) {
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
    return UnitType.VOLUME;
  }
}
