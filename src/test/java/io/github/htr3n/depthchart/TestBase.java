package io.github.htr3n.depthchart;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;

public abstract class TestBase {

  protected static final Faker FAKER = new Faker();
  protected static final int NO_TEST_PLAYERS = 7;
  protected final Player[] players = new Player[NO_TEST_PLAYERS];

  @BeforeEach
  void setUp() {
    int base = FAKER.number().numberBetween(10, 60);
    for (int i = 0; i < NO_TEST_PLAYERS; i++) {
      players[i] =
          Player.builder()
              .firstName(FAKER.name().firstName())
              .lastName(FAKER.name().lastName())
              .number(base + i)
              .build();
    }
  }
}
