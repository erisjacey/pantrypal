package com.github.erisjacey.pantrypal.domain.unit;

/**
 * Units of measurement for liquid/fluid volume.
 */
public enum VolumeUnit implements Unit {

  ML("ml"), L("l"), FL_OZ("fl_oz"), CUP("cup"), TBSP("tbsp"), TSP("tsp"), GAL("gal"), PT("pt"), QT(
      "qt");

  private final String code;

  VolumeUnit(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public UnitType getUnitType() {
    return UnitType.VOLUME;
  }
}
