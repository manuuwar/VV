package main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import space_invaders.sprites.Alien;

class AlienTests {

    @Test
    void testInitAlienWithinBounds() {
        Alien alien = new Alien(50, 100);
        assertEquals(50, alien.getX());
        assertEquals(100, alien.getY());
    }

    @Test
    void testInitAlienOutOfBounds() {
        Alien alien = new Alien(Commons.BOARD_WIDTH + 10, Commons.BOARD_HEIGHT + 10);
        assertEquals(Commons.BOARD_WIDTH, alien.getX());
        assertEquals(Commons.BOARD_HEIGHT, alien.getY());
    }

    @Test
    void testInitAlienNegativeCoordinates() {
        Alien alien = new Alien(-20, -50);
        assertEquals(0, alien.getX());
        assertEquals(0, alien.getY());
    }

    @Test
    void testActAlienLeft() {
        Alien alien = new Alien(50, 50);
        int direction = -1; // Left.
        alien.act(direction);
        assertEquals(50 + direction, alien.getX());
    }

    @Test
    void testActAlienRight() {
        Alien alien = new Alien(50, 50);
        int direction = 1; // Right.
        alien.act(direction);
        assertEquals(50 + direction, alien.getX());
    }

    @Test
    void testActAlienDown() {
        Alien alien = new Alien(50, 50);
        int direction = 0; // Down.
        alien.act(direction);
        assertEquals(50 + direction, alien.getX());
    }
}
