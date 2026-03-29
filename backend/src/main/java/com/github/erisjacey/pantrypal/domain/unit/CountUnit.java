package com.github.erisjacey.pantrypal.domain.unit;

/**
 * Units of measurement for discrete countable items.
 */
public enum CountUnit implements Unit {

  PIECE("piece"), DOZEN("dozen"), PACKAGE("package");

  private final String code;

  CountUnit(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public UnitType getUnitType() {
    return UnitType.COUNT;
  }
}
