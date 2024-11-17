package main;

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Shot;

import static org.junit.jupiter.api.Assertions.*;

class ShotTest {

  @Test
  void testShotInitialization() {
    Shot shot = new Shot(100, 200);
    assertEquals(100 + 6, shot.getX()); // 100 + H_SPACE
    assertEquals(200 - 1, shot.getY()); // 200 - V_SPACE
  }
}