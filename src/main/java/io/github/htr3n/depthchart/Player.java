package io.github.htr3n.depthchart;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {

  @EqualsAndHashCode.Include private int number;

  private String firstName;
  private String lastName;
  private String note;

  @Override
  public String toString() {
    return String.format(
        "(#%d - %s %s - %s)", number, firstName, lastName, note != null ? note : "");
  }
}
