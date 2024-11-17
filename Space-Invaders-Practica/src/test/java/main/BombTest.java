package main;

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Alien.Bomb;

import static org.junit.jupiter.api.Assertions.*;

class BombTest {
  @Test
  void testBombInitializationWithinBounds() {
    Bomb bomb = new Bomb(Commons.BORDER_LEFT + 50, Commons.GROUND - 10);
    assertEquals(Commons.BORDER_LEFT + 50, bomb.getX());
    assertEquals(Commons.GROUND - 10, bomb.getY());
    assertTrue(bomb.isDestroyed());
  }

  @Test
  void testBombInitializationOutOfBoundsRight() {
    Bomb bomb = new Bomb(Commons.BOARD_WIDTH + 10, Commons.GROUND);
    assertEquals(Commons.BOARD_WIDTH, bomb.getX());
    assertEquals(Commons.GROUND, bomb.getY());
  }

  @Test
  void testBombInitializationNegativeCoordinates() {
    Bomb bomb = new Bomb(-10, -20);
    assertEquals(Commons.BORDER_LEFT, bomb.getX());
    assertEquals(0, bomb.getY());
  }
}