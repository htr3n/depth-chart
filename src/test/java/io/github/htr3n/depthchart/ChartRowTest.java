package io.github.htr3n.depthchart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ChartRowTest extends TestBase {

  private static final Logger log = LoggerFactory.getLogger(ChartRowTest.class);

  @Test
  void addPlayer__shouldThrowException__giveInvalidInputs() {
    ChartRow row = ChartRow.builder().build();

    assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> row.addPlayer(null));

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> row.addPlayer(null, FAKER.number().randomDigit()));
  }

  @Test
  void addPlayer__shouldAddThemAtTheEnd__whenPositionDepthIsNotProvided() {
    ChartRow row = ChartRow.builder().build();

    // add to the end
    row.addPlayer(players[0]);

    assertThat(row.getPlayers()).hasSize(1);
    assertThat(row.getPlayers().get(0)).isEqualTo(players[0]);

    // add to the end
    row.addPlayer(players[1]);

    assertThat(row.getPlayers()).hasSize(2);
    assertThat(row.getPlayers().get(0)).isEqualTo(players[0]);
    assertThat(row.getPlayers().get(1)).isEqualTo(players[1]);
  }

  @Test
  void addPlayer__shouldAddThemAtTheGivenPosition__givenValidInputs() {
    ChartRow row = ChartRow.builder().build();

    // add to a given position
    row.addPlayer(players[0], 0);

    assertThat(row.getPlayers()).hasSize(1);
    assertThat(row.getPlayers().get(0)).isEqualTo(players[0]);

    // add to a given position
    row.addPlayer(players[1], 0);

    assertThat(row.getPlayers()).hasSize(2);
    assertThat(row.getPlayers().get(0)).isEqualTo(players[1]);
    assertThat(row.getPlayers().get(1)).isEqualTo(players[0]);

    // add to the end
    row.addPlayer(players[2], -1);
    assertThat(row.getPlayers()).hasSize(3);
    assertThat(row.getPlayers().get(0)).isEqualTo(players[1]);
    assertThat(row.getPlayers().get(1)).isEqualTo(players[0]);
    assertThat(row.getPlayers().get(2)).isEqualTo(players[2]);
  }
}
