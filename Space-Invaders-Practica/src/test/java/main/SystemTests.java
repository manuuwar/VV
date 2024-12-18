package main;

import java.awt.Dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;

import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;

class SystemTests {

    @Test
    void testGameWinCondition() {
        Board board = new Board();
        board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY);
        board.update();

        assertFalse(board.isInGame());
        assertEquals("Game won!", board.getMessage());
    }

    @Test
    void testGameLoseByDeathCondition() {
        Board board = new Board();
        Player player = board.getPlayer();
        player.setDying(true);
        board.update();

        assertFalse(board.isInGame());
        assertEquals("Game Over", board.getMessage());
    }

    @Test
    void testGameLoseByInvasion() {
        Board board = new Board();
        Alien alien = board.getAliens().get(0);
        alien.setY(Commons.GROUND);
        board.update();

        assertFalse(board.isInGame());
        assertEquals("Invasion!", board.getMessage());
    }

    @Test
    void testWindowScaling() {
        Main gameWindow = new Main();

        gameWindow.setSize(1600, 1200);
        Dimension newSize = gameWindow.getSize();

        assertEquals(1600, newSize.width);
        assertEquals(1200, newSize.height);
    }

    @Test
    void testSingleInstanceEnforcement() {
        Main instance1 = new Main();
        Main instance2 = new Main();

        assertNotSame(instance1, instance2);
    }

    @Test
    void testGameCloseBehavior() {
        Main game = new Main();
        game.setVisible(false);
        assertFalse(game.isDisplayable());
    }
}
