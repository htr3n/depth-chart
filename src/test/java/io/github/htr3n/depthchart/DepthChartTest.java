package io.github.htr3n.depthchart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class DepthChartTest extends TestBase {

  private DepthChart initializeDepthChart() {
    DepthChart depthChart = DepthChart.builder().build();
    depthChart.addPlayerToDepthChart("QB", players[0], 0);
    depthChart.addPlayerToDepthChart("QB", players[1], 1);
    depthChart.addPlayerToDepthChart("QB", players[2], 2);

    depthChart.addPlayerToDepthChart("LWR", players[3], 0);
    depthChart.addPlayerToDepthChart("LWR", players[4], 1);
    depthChart.addPlayerToDepthChart("LWR", players[5], 2);
    depthChart.addPlayerToDepthChart("LWR", players[6], 3);

    assertThat(depthChart.getChart()).hasSize(2);
    return depthChart;
  }

  @Test
  void addPlayerToDepthChart__shouldThrowException__giveInvalidInputs() {
    DepthChart depthChart = DepthChart.builder().build();

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> depthChart.addPlayerToDepthChart(null, players[0], 0));

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> depthChart.addPlayerToDepthChart("QB", null, 0));
  }

  @Test
  void addPlayerToDepthChart__shouldAddThemAtTheGivenPosition__givenValidInputs() {
    DepthChart depthChart = DepthChart.builder().build();
    depthChart.addPlayerToDepthChart("LWR", players[3], 0);
    depthChart.addPlayerToDepthChart("LWR", players[4], 1);
    depthChart.addPlayerToDepthChart("LWR", players[5], 2);
    depthChart.addPlayerToDepthChart("LWR", players[6], 3);

    depthChart.getFullDepthChart();

    ChartRow lwr = depthChart.getChartRow("LWR");
    assertThat(lwr).isNotNull();

    List<Player> lwrPlayers = lwr.getPlayers();
    assertThat(lwrPlayers).hasSize(4);

    assertThat(lwrPlayers.get(0)).isEqualTo(players[3]);
    assertThat(lwrPlayers.get(1)).isEqualTo(players[4]);
    assertThat(lwrPlayers.get(2)).isEqualTo(players[5]);
    assertThat(lwrPlayers.get(3)).isEqualTo(players[6]);
  }

  @Test
  void addPlayerToDepthChart__shouldAddThemAtTheEnd__whenPositionDepthIsNotProvided() {
    DepthChart depthChart = DepthChart.builder().build();
    depthChart.addPlayerToDepthChart("QB", players[2]);
    depthChart.addPlayerToDepthChart("QB", players[1]);
    depthChart.addPlayerToDepthChart("QB", players[0]);

    depthChart.getFullDepthChart();

    ChartRow qb = depthChart.getChartRow("QB");
    assertThat(qb).isNotNull();

    List<Player> qbPlayers = qb.getPlayers();
    assertThat(qbPlayers).hasSize(3);

    assertThat(qbPlayers.get(0)).isEqualTo(players[2]);
    assertThat(qbPlayers.get(1)).isEqualTo(players[1]);
    assertThat(qbPlayers.get(2)).isEqualTo(players[0]);
  }

  @Test
  void removePlayerFromDepthChart__shouldThrowException__giveInvalidInputs() {
    DepthChart depthChart = DepthChart.builder().build();

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> depthChart.removePlayerFromDepthChart(null, players[0]));

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> depthChart.removePlayerFromDepthChart("QB", null));
  }

  @Test
  void
      removePlayerFromDepthChart__shouldReturnNull__whenThePlayerIsNotListedAtTheProvidedPosition() {
    DepthChart depthChart = initializeDepthChart();
    depthChart.getFullDepthChart();

    Player removed = depthChart.removePlayerFromDepthChart("LWR", players[0]);
    assertThat(removed).isNull();
    assertThat(depthChart.getChartRow("LWR").getPlayers()).hasSize(4);

    removed = depthChart.removePlayerFromDepthChart("QB", players[3]);
    assertThat(removed).isNull();
    assertThat(depthChart.getChartRow("QB").getPlayers()).hasSize(3);
  }

  @Test
  void removePlayerFromDepthChart__shouldSucceed__givenValidInputs() {
    DepthChart depthChart = initializeDepthChart();
    depthChart.getFullDepthChart();

    Player removed = depthChart.removePlayerFromDepthChart("QB", players[0]);
    assertThat(removed).isEqualTo(players[0]);

    List<Player> qbPlayers = depthChart.getChartRow("QB").getPlayers();
    assertThat(qbPlayers).hasSize(2);

    assertThat(qbPlayers.get(0)).isEqualTo(players[1]);
    assertThat(qbPlayers.get(1)).isEqualTo(players[2]);

    removed = depthChart.removePlayerFromDepthChart("QB", players[2]);
    assertThat(removed).isEqualTo(players[2]);

    assertThat(qbPlayers.get(0)).isEqualTo(players[1]);
  }

  @Test
  void getBackups__shouldThrowException__giveInvalidInputs() {
    DepthChart depthChart = DepthChart.builder().build();

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> depthChart.getBackups(null, players[0]));

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> depthChart.getBackups("QB", null));
  }

  @Test
  void getBackups__shouldReturnEmptyList__whenThePlayerIsNotListedAtTheProvidedPosition() {
    DepthChart depthChart = initializeDepthChart();
    depthChart.getFullDepthChart();

    log.info("LWR Backups of {} are {}", players[0], depthChart.getBackups("LWR", players[0]));
    assertThat(depthChart.getBackups("LWR", players[0])).isEmpty();
  }

  @Test
  void getBackups__shouldReturnEmptyList__whenThereAreNoAssignedPlayersAtTheProvidedPosition() {
    DepthChart depthChart = DepthChart.builder().build();
    depthChart.getFullDepthChart();

    log.info("QB Backups of {} are {}", players[0], depthChart.getBackups("QB", players[0]));
    assertThat(depthChart.getBackups("QB", players[0])).isEmpty();
  }

  @Test
  void getBackups__shouldSucceed__givenValidInputs() {
    DepthChart depthChart = initializeDepthChart();
    depthChart.getFullDepthChart();

    log.info("QB backups of {} are {}", players[0], depthChart.getBackups("QB", players[0]));
    assertThat(depthChart.getBackups("QB", players[0]))
        .hasSize(2)
        .usingRecursiveComparison()
        .ignoringCollectionOrder()
        .isEqualTo(List.of(players[1], players[2]));

    log.info("QB backups of {} are {}", players[1], depthChart.getBackups("QB", players[1]));
    assertThat(depthChart.getBackups("QB", players[1]))
        .hasSize(1)
        .usingRecursiveComparison()
        .ignoringCollectionOrder()
        .isEqualTo(List.of(players[2]));

    log.info("QB backups of {} are {}", players[2], depthChart.getBackups("QB", players[2]));
    assertThat(depthChart.getBackups("QB", players[2])).isEmpty();
  }
}
