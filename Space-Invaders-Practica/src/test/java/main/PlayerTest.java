package main;

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Player;
import java.awt.event.KeyEvent;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testPlayerMoveWithinBounds() {
        Player player = new Player();
        player.setX(Commons.BORDER_LEFT + 50);
        KeyEvent keyEventRight = new KeyEvent(null, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        player.keyPressed(keyEventRight);
        player.act();
        assertEquals(Commons.BORDER_LEFT + 52, player.getX());
    }

    @Test
    void testPlayerMoveLeftBoundary() {
        Player player = new Player();
        player.setX(Commons.BORDER_LEFT);
        KeyEvent keyEventLeft = new KeyEvent(null, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        player.keyPressed(keyEventLeft);
        player.act();
        assertEquals(Commons.BORDER_LEFT, player.getX());
    }

    @Test
    void testPlayerMoveRightBoundary() {
        Player player = new Player();
        player.setX(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT - Commons.PLAYER_WIDTH);
        KeyEvent keyEventRight = new KeyEvent(null, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        player.keyPressed(keyEventRight);
        player.act();
        assertEquals(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT - Commons.PLAYER_WIDTH, player.getX());
    }
}
