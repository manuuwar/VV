package main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;

class BoardTests {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.getTimer().stop();
    }

    /**
     * *********************
     ** BLACK BOX TESTING ** *********************
     */
    @Test
    void testGameInit() {
        assertNotNull(board);
        assertTrue(board.isInGame());
        assertNotNull(board.getAliens());
        assertNotNull(board.getPlayer());
        assertNotNull(board.getShot());
        assertEquals(board.getAliens().size(), Commons.NUMBER_OF_ALIENS_TO_DESTROY);
    }

    @Test
    void testGameWon() {
        board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY);
        board.update();
        assertFalse(board.isInGame());
        assertEquals("Game won!", board.getMessage());
    }

    @Test
    void testGameContinues() {
        board.setDeaths(0);
        board.update();
        assertTrue(board.isInGame());
    }

    @Test
    void testShotDestroysAlien() {
        var alien = board.getAliens().get(0);
        var shot = new Shot(alien.getX() - 6, alien.getY() + 1);
        board.setShot(shot);
        board.update_shots();
        assertEquals(1, board.getDeaths());
    }

    @Test
    void testBombKillsPlayer() {
        Alien alien = board.getAliens().get(0);
        var bomb = alien.getBomb();
        var player = board.getPlayer();
        bomb.setX(player.getX());
        bomb.setY(player.getY());
        bomb.setDestroyed(false);
        board.update_bomb();
        assertTrue(player.isDying());
    }

    @Test
    void testGameLostByInvasion() {
        var alien = board.getAliens().get(0);
        alien.setY(Commons.GROUND);
        board.update_aliens();
        assertFalse(board.isInGame());
        assertEquals("Invasion!", board.getMessage());
    }

    @Test
    void testUpdateAliensMoveDownAtLeftBoundary() {
        Alien alien = board.getAliens().get(0);
        alien.setX(Commons.BORDER_LEFT);
        alien.setY(50);
        board.setDirection(-1); // Moving left
        board.update_aliens();
        assertEquals(50 + Commons.GO_DOWN, alien.getY());
        assertEquals(1, board.getDirection());
    }

    @Test
    void testUpdateAliensMoveDownAtRightBoundary() {
        Alien alien = board.getAliens().get(0);
        alien.setX(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT); // At right boundary
        alien.setY(50);
        board.setDirection(1); // Moving right
        board.update_aliens();
        assertEquals(50 + Commons.GO_DOWN, alien.getY()); // Alien moves downward
        assertEquals(-1, board.getDirection());
    }

    /**
     * *********************
     ** WHITE BOX TESTING ** *********************
     */
    @Test
    void testUpdateWhenConditionsMet() {
        board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY);
        board.update();
        assertFalse(board.isInGame());
        assertEquals("Game won!", board.getMessage());
    }

    @Test
    void testUpdateWhenConditionsNotMet() {
        board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY - 1);
        board.update();
        assertTrue(board.isInGame());
        assertNotEquals("Game won!", board.getMessage());
    }

    @Test
    void testUpdateShotsShotNotVisible() {
        Shot shot = new Shot();
        shot.die(); // Shot is not visible
        board.setShot(shot);
        board.update_shots();
        assertFalse(shot.isVisible());
    }

    @Test
    void testUpdateShotsVisibleAlienVisibleHit() {
        Alien alien = board.getAliens().get(0);
        Shot shot = new Shot();
        shot.setX(alien.getX());
        shot.setY(alien.getY());
        board.setShot(shot);
        board.update_shots();
        assertTrue(alien.isDying());
        assertFalse(shot.isVisible());
    }

    @Test
    void testUpdateShotsVisibleAlienVisibleMiss() {
        Alien alien = board.getAliens().get(0);
        Player player = board.getPlayer();
        Shot shot = new Shot();
        alien.setX(Commons.BORDER_LEFT);
        alien.setY(Commons.BOARD_HEIGHT);
        shot.setX(player.getX());
        shot.setY(player.getY());
        board.update_shots();
        assertTrue(shot.isVisible());
        assertFalse(alien.isDying());
    }

    @Test
    void testUpdateAliensGameOver() {
        Alien alien = board.getAliens().get(0);
        alien.setX(100);
        alien.setY(Commons.GROUND - Commons.ALIEN_HEIGHT);
        board.update_aliens();
        assertFalse(board.isInGame()); // Game ends
        assertEquals("Invasion!", board.getMessage());
    }

    @Test
    void testUpdateAliensMoveRight() {
        Alien alien = board.getAliens().get(0);
        alien.setX(50); // Well within the board
        alien.setY(50);
        int direction = 1;
        board.setDirection(direction); // Moving right
        board.update_aliens();
        assertEquals(50 + direction, alien.getX()); // Alien moves right
        assertEquals(1, board.getDirection()); // Direction remains unchanged
        assertTrue(board.isInGame()); // Game is still ongoing
    }

    @Test
    void testUpdateAliensMoveLeft() {
        Alien alien = board.getAliens().get(0);
        // We remove all aliens but the first, to avoid having to place them all
        // manually in the board.
        board.getAliens().clear();
        board.getAliens().add(alien);
        alien.setX(Commons.BORDER_LEFT + 1);
        alien.setY(50);
        int direction = -1;
        board.setDirection(direction); // Moving left
        board.update_aliens();
        assertEquals(Commons.BORDER_LEFT, alien.getX());
        assertEquals(-1, board.getDirection());
        assertTrue(board.isInGame());
    }

    // Because this method is based on chance it is necessary to change the nextInt
    // bound to be Commons.CHANCE to verify it.
    @Test
    void testUpdateBombCreatedForVisibleAlien() {
        Alien alien = board.getAliens().get(0);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true);
        board.setPlayer(new Player());
        board.update_bomb();
        assertFalse(bomb.isDestroyed());
        assertEquals(alien.getX(), bomb.getX());
        assertEquals(alien.getY() + 1, bomb.getY()); // Bomb goes down by 1.
    }

    @Test
    void testUpdateBombHitsPlayer() {
        Alien alien = board.getAliens().get(0);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(false);
        Player player = board.getPlayer();
        player.setX(50);
        player.setY(50);
        bomb.setX(50);
        bomb.setY(50);
        board.update_bomb();
        assertTrue(player.isDying());
        assertTrue(bomb.isDestroyed());
    }

    @Test
    void testUpdateBombReachesGround() {
        Alien alien = board.getAliens().get(0);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(false);
        bomb.setY(Commons.GROUND - Commons.BOMB_HEIGHT);
        board.update_bomb();
        assertTrue(bomb.isDestroyed());
    }

    @Test
    void testUpdateBombContinuesFalling() {
        Alien alien = board.getAliens().get(0);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(false); // Bomb is active
        bomb.setY(50); // Bomb is mid-air
        board.update_bomb();
        assertEquals(51, bomb.getY()); // Bomb moves one step downward
        assertFalse(bomb.isDestroyed()); // Bomb is still active
    }

    @Test
    void testUpdateBombNotCreatedForInvisibleAlien() {
        Alien alien = board.getAliens().get(0);
        alien.die();
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true); // Bomb not created initially
        board.update_bomb();
        assertTrue(bomb.isDestroyed()); // Bomb remains destroyed
    }
}
