package main;

import main.Commons;
import org.junit.jupiter.api.Test;
import space_invaders.sprites.Player;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testPlayerMoveWithinBounds() {
        Player player = new Player();
        player.setX(Commons.BORDER_LEFT + 50);
        player.dx = 10;
        player.act();
        assertEquals(Commons.BORDER_LEFT + 60, player.getX());
    }

    @Test
    void testPlayerMoveLeftBoundary() {
        Player player = new Player();
        player.setX(Commons.BORDER_LEFT);
        player.keyPressed(KeyEvent.VK_LEFT);
        player.act();
        assertEquals(Commons.BORDER_LEFT, player.getX());
    }

    @Test
    void testPlayerMoveRightBoundary() {
        Player player = new Player();
        player.setX(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT - Commons.PLAYER_WIDTH);
        player.dx = 20;
        player.act();
        assertEquals(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT - Commons.PLAYER_WIDTH, player.getX());
    }
}
