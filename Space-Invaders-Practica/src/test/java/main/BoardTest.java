package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import space_invaders.sprites.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
  }

  /***********************
   ** BLACK BOX TESTING **
   ***********************/

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
  void testGameWon() throws InterruptedException {
    board.setDeaths(Commons.NUMBER_OF_ALIENS_TO_DESTROY);
    Thread.sleep(Commons.DELAY);
    assertFalse(board.isInGame());
    assertEquals("Game won!", board.getMessage());
  }

  @Test
  void testGameContinues() {
    board.setDeaths(0);
    assertTrue(board.isInGame());
  }

  @Test
  void testShotDestroysAlien() throws InterruptedException {
    board.getTimer().stop();
    var alien = board.getAliens().get(0);
    var shot = new Shot(alien.getX() - 6, alien.getY() + 1);
    board.setShot(shot);
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertEquals(1, board.getDeaths());
  }

  @Test
  void testBombKillsPlayer() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    var bomb = alien.getBomb();
    var player = board.getPlayer();
    bomb.setX(player.getX());
    bomb.setY(player.getY());
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertTrue(player.isDying());
  }

  @Test
  void testGameLostByInvasion() throws InterruptedException {
    board.getTimer().stop();
    var alien = board.getAliens().get(0);
    alien.setY(Commons.GROUND);
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertFalse(board.isInGame());
    assertEquals("Invasion!", board.getMessage());
  }

  /***********************
   ** WHITE BOX TESTING **
   ***********************/

  @Test
  void testUpdateWhenConditionsMet() throws InterruptedException {
    board.getTimer().stop();
    board.setDeaths(Commons.CHANCE);
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertFalse(board.isInGame());
    assertEquals("Game Won!", board.getMessage());
  }

  @Test
  void testUpdateWhenConditionsNotMet() throws InterruptedException {
    board.getTimer().stop();
    board.setDeaths(Commons.CHANCE - 1);
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertTrue(board.isInGame());
    assertNotEquals("Game Won!", board.getMessage());
  }

  @Test
  void testUpdateShotsShotNotVisible() throws InterruptedException {
    board.getTimer().stop();
    Shot shot = new Shot();
    shot.die(); // Shot is not visible
    board.setShot(shot);
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertFalse(shot.isVisible());
  }

  @Test
  void testUpdateShotsVisibleAlienVisibleHit() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    Shot shot = new Shot();
    shot.setX(alien.getX());
    shot.setY(alien.getY());
    board.setShot(shot);
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertTrue(alien.isDying());
    assertFalse(shot.isVisible());
  }

  @Test
  void testUpdateShotsVisibleAlienVisibleMiss() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    Player player = board.getPlayer();
    Shot shot = new Shot();
    alien.setX(Commons.BORDER_LEFT);
    alien.setY(Commons.BOARD_HEIGHT);
    shot.setX(player.getX());
    shot.setY(player.getY());
    board.getTimer().start();
    Thread.sleep(Commons.DELAY);
    assertTrue(shot.isVisible());
    assertFalse(alien.isDying());
  }

  @Test
  void testUpdateAliensMoveDownAtRightBoundary() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    alien.setX(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT); // At right boundary
    alien.setY(50);
    board.setDirection(1); // Moving right

    board.getTimer().start();
    Thread.sleep(Commons.DELAY);

    assertEquals(50 + Commons.GO_DOWN, alien.getY()); // Alien moves downward
    assertEquals(0, board.getDirection()); // Direction reset
  }

  @Test
  void testUpdateAliensMoveDownAtLeftBoundary() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    alien.setX(Commons.BORDER_LEFT);
    alien.setY(50);
    board.setDirection(-1); // Moving left

    board.getTimer().start();
    Thread.sleep(Commons.DELAY);

    assertEquals(50 + Commons.GO_DOWN, alien.getY());
    assertEquals(1, board.getDirection());
  }

  @Test
  void testUpdateAliensGameOver() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    alien.setX(100);
    alien.setY(Commons.GROUND - Commons.ALIEN_HEIGHT);

    board.getTimer().start();
    Thread.sleep(Commons.DELAY);

    assertFalse(board.isInGame()); // Game ends
    assertEquals("Invasion!", board.getMessage());
  }

  @Test
  void testUpdateAliensMoveRight() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    alien.setX(50); // Well within the board
    alien.setY(50);
    board.setDirection(1); // Moving right

    board.getTimer().start();
    Thread.sleep(Commons.DELAY);

    assertEquals(52, alien.getX()); // Alien moves right
    assertEquals(1, board.getDirection()); // Direction remains unchanged
    assertTrue(board.isInGame()); // Game is still ongoing
  }

  @Test
  void testUpdateAliensMoveLeft() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    alien.setX(Commons.BORDER_LEFT + 2);
    alien.setY(50);
    board.setDirection(-1); // Moving left

    board.getTimer().start();
    Thread.sleep(Commons.DELAY);

    assertEquals(Commons.BORDER_LEFT, alien.getX());
    assertEquals(-1, board.getDirection());
    assertTrue(board.isInGame());
  }

  @Test
  void testUpdateBombCreatedForVisibleAlien() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    Alien.Bomb bomb = alien.getBomb();
    bomb.setDestroyed(true);
    board.setPlayer(new Player());

    board.getTimer().start();
    Thread.sleep(Commons.DELAY);

    assertFalse(bomb.isDestroyed());
    assertEquals(alien.getX(), bomb.getX());
    assertEquals(alien.getY(), bomb.getY());
  }

  @Test
  void testUpdateBombHitsPlayer() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    Alien.Bomb bomb = alien.getBomb();
    bomb.setDestroyed(false);
    Player player = board.getPlayer();
    player.setX(50);
    player.setY(50);
    bomb.setX(50);
    bomb.setY(50);

    board.getTimer().start();
    Thread.sleep(Commons.DELAY);

    assertTrue(player.isDying());
    assertTrue(bomb.isDestroyed());
  }

  @Test
  void testUpdateBombReachesGround() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    Alien.Bomb bomb = alien.getBomb();
    bomb.setDestroyed(false);
    bomb.setY(Commons.GROUND - Commons.BOMB_HEIGHT);

    board.getTimer().start();
    Thread.sleep(Commons.DELAY);

    assertTrue(bomb.isDestroyed());
  }

  @Test
  void testUpdateBombContinuesFalling() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    Alien.Bomb bomb = alien.getBomb();
    bomb.setDestroyed(false); // Bomb is active
    bomb.setY(50); // Bomb is mid-air

    board.getTimer().start();
    Thread.sleep(Commons.DELAY);

    assertEquals(51, bomb.getY()); // Bomb moves one step downward
    assertFalse(bomb.isDestroyed()); // Bomb is still active
  }

  @Test
  void testUpdateBombNotCreatedForInvisibleAlien() throws InterruptedException {
    board.getTimer().stop();
    Alien alien = board.getAliens().get(0);
    alien.die();
    Alien.Bomb bomb = alien.getBomb();
    bomb.setDestroyed(true); // Bomb not created initially

    board.getTimer().start();
    Thread.sleep(Commons.DELAY);

    assertTrue(bomb.isDestroyed()); // Bomb remains destroyed
  }
}
