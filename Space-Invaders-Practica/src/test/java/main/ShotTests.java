package main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import space_invaders.sprites.Shot;

class ShotTests {

    @Test
    void testInitShotWithinBounds() {
        Shot shot = new Shot(100, 200);
        assertEquals(100 + 6, shot.getX()); // 100 + H_SPACE
        assertEquals(200 - 1, shot.getY()); // 200 - V_SPACE
    }

    @Test
    void testInitShotOutOfBounds() {
        Shot shot = new Shot(Commons.BOARD_WIDTH + 100, Commons.BOARD_HEIGHT + 200);
        assertEquals(Commons.BOARD_WIDTH + 100 + 6, shot.getX());
        assertEquals(Commons.BOARD_HEIGHT + 200 - 1, shot.getY());
    }

    @Test
    void testInitShotNegativeCoordinates() {
        Shot shot = new Shot(-100, -200);
        assertEquals(-100 + 6, shot.getX());
        assertEquals(-200 - 1, shot.getY());
    }
}
