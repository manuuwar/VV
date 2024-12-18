package main;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;

class IntegrationTests {

    @Test
    void testBoardGameInitIntegration() {
        Board board = new Board();

        assertNotNull(board.getPlayer());
        assertNotNull(board.getShot());
        assertFalse(board.getAliens().isEmpty());
    }

    @Test
    void testPlayerActIntegration() {
        Player player = new Player();
        player.setX(100);

        var rightArrowKey = new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        player.keyPressed(rightArrowKey);
        var dx = player.dx;
        player.act();
        player.keyReleased(rightArrowKey);
        player.act();

        assertEquals(100 + dx, player.getX());

        var leftArrowKey = new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        player.keyPressed(leftArrowKey);
        player.act();
        player.keyReleased(leftArrowKey);
        player.act();

        assertEquals(100, player.getX());
    }

    @Test
    void testBoardUpdateIntegration() {
        Board board = new Board();
        board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY - 1);

        board.update();

        assertTrue(board.isInGame());

        board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY);
        board.update();

        assertFalse(board.isInGame());
        assertEquals("Game won!", board.getMessage());
    }

    @Test
    void testBoardUpdateAliensIntegration() {
        Board board = new Board();
        Alien alien = board.getAliens().get(0);
        int direction = 1;

        board.setDirection(direction);
        int x = alien.getX();

        board.update_aliens();

        assertFalse(board.getAliens().isEmpty());
        assertEquals(x + direction, alien.getX());
    }

    @Test
    void testBoardUpdateShotsIntegration() {
        Board board = new Board();
        Shot shot = board.getShot();

        shot.setX(board.getAliens().get(0).getX());
        shot.setY(board.getAliens().get(0).getY());
        board.update_shots();

        assertFalse(shot.isVisible());
    }

    @Test
    void testBoardUpdateBombIntegration() {
        Board board = new Board();
        Alien alien = board.getAliens().get(0);
        Alien.Bomb bomb = alien.getBomb();

        bomb.setDestroyed(false);
        bomb.setX(board.getPlayer().getX());
        bomb.setY(board.getPlayer().getY());
        board.update_bomb();

        assertTrue(board.getPlayer().isDying());
        assertTrue(bomb.isDestroyed());
    }
}
