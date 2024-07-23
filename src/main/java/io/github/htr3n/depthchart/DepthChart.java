package io.github.htr3n.depthchart;

import io.github.htr3n.depthchart.utils.InputValidator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Data
@Builder
public class DepthChart {

  private final Map<String, ChartRow> chart = new HashMap<>();

  private String teamName;

  /**
   * Adds a player to the depth chart at the end at the provided position.
   *
   * @param position the position of the to-be-added player
   * @param player the player to be added
   */
  public void addPlayerToDepthChart(String position, Player player) {
    addPlayerToDepthChart(position, player, -1);
  }

  /**
   * Adds a player to the depth chart at a given position. Adding a player without a position depth
   * i.e. negative number, would add them to the end of the depth chart at that position. The added
   * player would get priority. Anyone below that player in the depth chart would get moved down a
   * position depth.
   *
   * @param position the position of the to-be-added player
   * @param player the player to be added
   * @param positionDepth the depth of the position of the to-be-added player
   */
  public void addPlayerToDepthChart(String position, Player player, int positionDepth) {
    InputValidator validator = InputValidator.create();
    validator.validatePosition(position);
    validator.validatePlayer(player);

    ChartRow row;
    if (chart.containsKey(position)) {
      row = chart.get(position);
    } else {
      row = ChartRow.builder().build();
    }
    row.addPlayer(player, positionDepth);

    chart.put(position, row);
  }

  public ChartRow getChartRow(String position) {
    return chart.getOrDefault(position, null);
  }

  /**
   * Removes a player from the depth chart for a given position and returns that player. An empty
   * list should be returned if that player is not listed in the depth chart at that position
   *
   * @param position the position of the to-be-removed player
   * @param player the player to be removed
   * @return the removed player, if successful, otherwise, {@code null}
   */
  public Player removePlayerFromDepthChart(String position, Player player) {
    InputValidator validator = InputValidator.create();
    validator.validatePosition(position);
    validator.validatePlayer(player);

    if (StringUtils.isNotBlank(position) && chart.containsKey(position)) {
      ChartRow row = chart.get(position);
      if (row.getPlayers() != null && row.getPlayers().remove(player)) {
        return player;
      }
    }
    return null;
  }

  /**
   * For a given player and position, we want to see all players that are "Backups", those with a
   * lower position_depth.
   *
   * @param position the position to query for backups
   * @param player the player to be queried for backups
   * @return An empty list should be returned if the given player has no backups. An empty list
   *     should be returned if the given player is not listed in the depth chart at that position.
   */
  public List<Player> getBackups(String position, Player player) {
    InputValidator validator = InputValidator.create();
    validator.validatePosition(position);
    validator.validatePlayer(player);

    if (StringUtils.isNotBlank(position)) {
      ChartRow row = chart.get(position);
      if (row != null) {
        List<Player> players = row.getPlayers();
        if (players != null) {
          int index = players.indexOf(player);
          if (index != -1) {
            return players.subList(index + 1, players.size());
          }
        }
      }
    }
    return Collections.emptyList();
  }

  /**
   * Print out the full depth chart with every position on the team and every player within the
   * Depth Chart.
   */
  public void getFullDepthChart() {
    log.info("Full depth chart");
    for (Entry<String, ChartRow> entry : chart.entrySet()) {
      log.info("{} - {}", entry.getKey(), entry.getValue());
    }
  }
}
