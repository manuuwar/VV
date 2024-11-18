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
  void testGameWon() throws InterruptedException {
    board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY);
    Thread.sleep(Commons.DELAY);
    assertFalse(board.isInGame());
    assertEquals("Game won!", board.getMessage());
  }

  @Test
  void testGameContinues() {
    board.setDeaths(0);
    assertTrue(board.isInGame());
  }

  @Test
  void testShotDestroysAlien() throws InterruptedException {
    board.getTimer().stop();
    var alien = board.getAliens().get(0);
    var shot = new Shot(alien.getX() - 6, alien.getY() + 1);
    board.setShot(shot);
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertEquals(1, board.getDeaths());
  }

  @Test
  void testBombKillsPlayer() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    var bomb = alien.getBomb();
    var player = board.getPlayer();
    bomb.setX(player.getX());
    bomb.setY(player.getY());
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertTrue(player.isDying());
  }

  @Test
  void testGameLostByInvasion() throws InterruptedException {
    board.getTimer().stop();
    var alien = board.getAliens().get(0);
    alien.setY(Commons.GROUND);
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertFalse(board.isInGame());
    assertEquals("Invasion!", board.getMessage());
  }
}
