package main;

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Alien.Bomb;

import static org.junit.jupiter.api.Assertions.*;

class BombTest {
  @Test
  void testBombInitializationWithinBounds() {
    Alien alien = new Alien(Commons.BORDER_LEFT + 50, Commons.GROUND - 10);
    Bomb bomb = alien.getBomb();
    assertEquals(Commons.BORDER_LEFT + 50, bomb.getX());
    assertEquals(Commons.GROUND - 10, bomb.getY());
    assertTrue(bomb.isDestroyed());
  }

  @Test
  void testBombInitializationOutOfBoundsRight() {
    Alien alien = new Alien(Commons.BOARD_WIDTH + 10, Commons.GROUND - 10);
    Bomb bomb = alien.getBomb();
    assertEquals(Commons.BOARD_WIDTH, bomb.getX());
    assertEquals(Commons.GROUND - 10, bomb.getY());
  }

  @Test
  void testBombInitializationNegativeCoordinates() {
    Alien alien = new Alien(-10, -20);
    Bomb bomb = alien.getBomb();
    assertEquals(0, bomb.getX());
    assertEquals(0, bomb.getY());
  }
}