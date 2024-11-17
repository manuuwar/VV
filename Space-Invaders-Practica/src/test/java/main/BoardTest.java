package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import space_invaders.sprites.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
  }

  @Test
  void testGameInit() {
    assertNotNull(board);
    assertTrue(board.isInGame());
    assertNotNull(board.getAliens());
    assertNotNull(board.getPlayer());
    assertNotNull(board.getShot());
    assertEquals(board.getAliens().size(), Commons.NUMBER_OF_ALIENS_TO_DESTROY);
  }

  @Test
  void testGameWon() {
    board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY);
    assertFalse(board.isInGame());
    assertEquals(board.getMessage(), "Game won!");
  }

  @Test
  void testGameContinues() {
    board.setDeaths(0);
    assertTrue(board.isInGame());
  }

  @Test
  void testShotDestroysAlien() {
    var alien = board.getAliens().get(0);
    board.setShot(new Shot(alien.getX(), alien.getY()));
    assertEquals(board.getAliens().size(), Commons.NUMBER_OF_ALIENS_TO_DESTROY - 1);
  }

  @Test
  void testBombKillsPlayer() {
    Alien alien = board.getAliens().get(0);
    var bomb = alien.getBomb();
    var player = board.getPlayer();
    bomb.setX(player.getX());
    bomb.setY(player.getY());
    assertTrue(player.isDying());
  }
}
