package main;

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Player;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testPlayerKeyPressedLeft() {
        var mockPanel = new JPanel();
        Player player = new Player();
        KeyEvent keyEventLeft = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        player.keyPressed(keyEventLeft);
        assertTrue(player.dx < 0);
        player.keyReleased(keyEventLeft);
        assertTrue(player.dx == 0);
    }

    @Test
    void testPlayerKeyPressedRight() {
        var mockPanel = new JPanel();
        Player player = new Player();
        KeyEvent keyEventRight = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        player.keyPressed(keyEventRight);
        assertTrue(player.dx > 0);
        player.keyReleased(keyEventRight);
        assertTrue(player.dx == 0);
    }

    @Test
    void testPlayerMoveLeftWithinBounds() {
        Player player = new Player();
        player.setX(Commons.BOARD_WIDTH - 50);
        player.dx = -2;
        player.act();
        assertEquals(Commons.BOARD_WIDTH - 50 + player.dx, player.getX());
    }

    @Test
    void testPlayerMoveRightWithinBounds() {
        Player player = new Player();
        player.setX(Commons.BORDER_LEFT + 50);
        player.dx = 2;
        player.act();
        assertEquals(Commons.BORDER_LEFT + 50 + player.dx, player.getX());
    }

    @Test
    void testPlayerMoveLeftBoundary() {
        Player player = new Player();
        player.setX(Commons.BORDER_LEFT);
        player.dx = -2;
        player.act();
        assertEquals(Commons.BORDER_LEFT, player.getX());
    }

    @Test
    void testPlayerMoveRightBoundary() {
        Player player = new Player();
        player.setX(Commons.BOARD_WIDTH - Commons.PLAYER_WIDTH);
        player.dx = 2;
        player.act();
        assertEquals(Commons.BOARD_WIDTH - Commons.PLAYER_WIDTH, player.getX());
    }
}
