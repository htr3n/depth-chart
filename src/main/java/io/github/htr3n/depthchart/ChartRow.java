package io.github.htr3n.depthchart;

import io.github.htr3n.depthchart.utils.InputValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class ChartRow {

  @Builder.Default private List<Player> players = new ArrayList<>();

  /**
   * Adds a player to the current chart row at the end. Throws an {@link IllegalArgumentException}
   * if the inputs are invalid.
   *
   * @param player the player to be added
   */
  public void addPlayer(Player player) {
    addPlayer(player, -1);
  }

  /**
   * Adds a player to the current chart row at a given position if the position depth is greater
   * than zero. If the position is negative, add the player to the end. Throws an {@link
   * IllegalArgumentException} if the inputs are invalid.
   *
   * @param player the player to be added
   * @param positionDepth the position depth to add the player to
   */
  public void addPlayer(Player player, int positionDepth) {
    InputValidator validator = InputValidator.create();
    validator.validatePlayer(player);

    if (positionDepth >= 0) {
      players.add(positionDepth, player);
    } else {
      players.add(player);
    }
  }

  @Override
  public String toString() {
    return players.stream().map(Object::toString).collect(Collectors.joining(", "));
  }
}
