package tests;

import main.Games.Game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGame {
    private Game game1 = new Game();
    private Game game2 = new Game("Medium", "Spring");

    @Test
    public void testConstruction() {
        assertEquals(1, game1.getDay());
        assertEquals("Fall", game1.getSeason());
        assertEquals("Easy", game1.getDifficulty());

        assertEquals(1, game2.getDay());
        assertEquals("Spring", game2.getSeason());
        assertEquals("Medium", game2.getDifficulty());
    }

    @Test
    public void testSetters() {
        game1.setDifficulty("Hard");
        assertEquals("Hard", game1.getDifficulty());

        game2.setSeason("Summer");
        assertEquals("Summer", game2.getSeason());
    }

    @Test
    public void testDayIncrement() {
        game1.incrementDay();
        game1.incrementDay();
        assertEquals(3, game1.getDay());
    }
}
