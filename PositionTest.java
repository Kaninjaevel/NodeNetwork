/**
 * JUnit test program for methods in Position Class.
 * Course: 5DV133
 * Written by: Susanne, Desireé
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 *
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for Class Position
 */
public class PositionTest {
    /**
     * Test to Create new Position
     * @throws Exception if it can't create Position
     */
    @Test
    public void shouldCreatePosition() throws Exception{
        new Position(0,0);
    }

    /**
     * Test to get X Position
     * @throws Exception if getX don't return 5
     */
    @Test
    public void shouldGetPositionX() throws Exception{
        assertEquals(5, new Position(5,0).getX());
    }

    /**
     * Test to get Y Position
     * @throws Exception if getY don't return 5
     */
    @Test
    public void shouldGetPositionY() throws Exception{
        assertEquals(5, new Position(0,5).getY());
    }

    /**
     * Test that Position returns correct String
     * @throws Exception if toString don't return (3,6)
     */
    @Test
    public void shouldBeAStringOfPosition() throws Exception{
        Position p = new Position(3,6);
        assertEquals("(3,6)", p.toString());
    }

    /**
     * Test equal function, Positions should be equal
     * @throws Exception if p and q is not equal
     */
    @Test
    public void shouldBeEqual() throws Exception{
        Position p = new Position(5,0);
        Position q = new Position(5,0);

        assertTrue(p.equals(q));
    }

    /**
     * Test equal function, Positions should not be equal
     * @throws Exception if p and q  are equal
     */
    @Test
    public void shouldNotBeEqual()throws Exception{
        Position p = new Position (8,5);
        Position q = new Position (9,5);

        assertFalse(p.equals(q));
    }
}
