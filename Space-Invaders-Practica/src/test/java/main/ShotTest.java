package main;

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Shot;

import static org.junit.jupiter.api.Assertions.*;

class ShotTest {

  @Test
  void testShotInitialization() {
    Shot shot = new Shot(100, 200);
    assertEquals(106, shot.getX()); // x + H_SPACE
    assertEquals(199, shot.getY()); // y - V_SPACE
  }
}