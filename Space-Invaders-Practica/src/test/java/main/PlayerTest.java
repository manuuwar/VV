package main;

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Player;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testPlayerMoveWithinBounds() {
        var mockPanel = new JPanel();
        Player player = new Player();
        player.setX(Commons.BORDER_LEFT + 50);
        KeyEvent keyEventRight = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        player.keyPressed(keyEventRight);
        player.act();
        player.keyReleased(keyEventRight);
        assertEquals(Commons.BORDER_LEFT + 52, player.getX());
    }

    @Test
    void testPlayerMoveLeftBoundary() {
        var mockPanel = new JPanel();
        Player player = new Player();
        player.setX(Commons.BORDER_LEFT);
        KeyEvent keyEventLeft = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        player.keyPressed(keyEventLeft);
        player.act();
        player.keyReleased(keyEventLeft);
        assertEquals(Commons.BORDER_LEFT, player.getX());
    }

    @Test
    void testPlayerMoveRightBoundary() {
        var mockPanel = new JPanel();
        Player player = new Player();
        player.setX(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT - Commons.PLAYER_WIDTH);
        KeyEvent keyEventRight = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        player.keyPressed(keyEventRight);
        player.act();
        player.keyReleased(keyEventRight);
        assertEquals(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT - Commons.PLAYER_WIDTH, player.getX());
    }
}
