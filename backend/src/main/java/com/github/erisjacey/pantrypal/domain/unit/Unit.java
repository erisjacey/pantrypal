package com.github.erisjacey.pantrypal.domain.unit;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A unit of measurement. Sealed to {@link VolumeUnit}, {@link WeightUnit}, and {@link CountUnit}.
 */
public sealed interface Unit permits VolumeUnit, WeightUnit, CountUnit {

  Map<String, Unit> CODE_INDEX = Stream.of(VolumeUnit.values(), WeightUnit.values(),
          CountUnit.values()).flatMap(Arrays::stream)
      .collect(Collectors.toMap(Unit::getCode, Function.identity()));

  /**
   * Factory method.
   * <p>
   * Retrieves the corresponding {@link Unit} from the input code.
   *
   * @param code The input code.
   * @return The unit.
   * @throws IllegalArgumentException If no valid unit is found.
   */
  static Unit fromCode(String code) {
    return Optional.ofNullable(CODE_INDEX.get(code))
        .orElseThrow(() -> new IllegalArgumentException("Unknown unit code: " + code));
  }

  /**
   * Returns the lowercase string code used for persistence and display (e.g. {@code "ml"},
   * {@code "kg"}).
   *
   * @return The unit code.
   */
  String getCode();

  /**
   * Returns the multiplication factor to convert one of this unit to the base unit of its type
   * (e.g. ml for volume, g for weight, piece for count).
   *
   * @return The base factor.
   */
  BigDecimal getToBaseFactor();

  /**
   * Gets the corresponding type of unit.
   *
   * @return The type of the unit.
   */
  UnitType getUnitType();
}
