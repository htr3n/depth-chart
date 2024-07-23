package io.github.htr3n.depthchart.utils;

import io.github.htr3n.depthchart.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * A reusable input validator with fluent API to check all possible input values and generate
 * corresponding error messages.
 */
@Getter
public final class InputValidator {

  private final List<String> errors = new ArrayList<>();

  private InputValidator() {}

  public static InputValidator create() {
    return new InputValidator();
  }

  public String getErrorsAsString() {
    if (!errors.isEmpty()) {
      return String.join(", ", errors);
    }
    return "";
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  public InputValidator nonNullNorEmpty(String input, String label) {
    if (StringUtils.isBlank(input)) {
      String errorMsg = "'" + label + "' must not be null nor empty.";
      errors.add(errorMsg);
    }
    return this;
  }

  public InputValidator nonNullNorEmpty(Collection<?> input, final String label) {
    if (input == null || input.isEmpty()) {
      String errorMsg = "'" + label + "' must not be null nor empty.";
      errors.add(errorMsg);
    }
    return this;
  }

  public InputValidator between(int value, int min, int max, String label) {
    if (value < min || value > max) {
      String errorMsg =
          " The '"
              + label
              + "' is expected to be between "
              + min
              + " and "
              + max
              + " but actually is "
              + value;
      errors.add(errorMsg);
    }
    return this;
  }

  public InputValidator nonNullNorEmpty(Map<?, ?> input, final String label) {
    if (input == null || input.isEmpty()) {
      String errorMsg = "'" + label + "' must not be null nor empty.";
      errors.add(errorMsg);
    }
    return this;
  }

  public InputValidator nonNull(Object input, final String label) {
    if (Objects.isNull(input)) {
      String errorMsg = "'" + label + "' must not be null.";
      errors.add(errorMsg);
    }
    return this;
  }

  public void clearErrors() {
    this.errors.clear();
  }

  public InputValidator greaterThanOrEqual(int value, int pivot, String label) {
    if (value < pivot) {
      String errorMsg =
          " The '"
              + label
              + "' is expected to be greater than or equal to "
              + pivot
              + " but actually is "
              + value;
      errors.add(errorMsg);
    }
    return this;
  }

  public void validatePosition(String position) {
    this.nonNullNorEmpty(position, "Position");

    // TODO: more logic to validate position

    if (this.hasErrors()) {
      throw new IllegalArgumentException(this.getErrorsAsString());
    }
  }

  public void validatePlayer(Player player) {
    this.nonNull(player, "Player");
    if (player != null) {
      this.between(player.getNumber(), 1, 99, "Player's number")
          .nonNullNorEmpty(player.getFirstName(), "Player's first name")
          .nonNullNorEmpty(player.getLastName(), "Player's last name");
    }
    // TODO: more logic to validate player

    if (this.hasErrors()) {
      throw new IllegalArgumentException(this.getErrorsAsString());
    }
  }
}
