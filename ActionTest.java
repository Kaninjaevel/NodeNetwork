/**
 * JUnit test program for methods in Action Class.
 * Course: 5DV133
 * Written by: Susanne, Desireé
 *
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for class Action.
 */
public class ActionTest {
    /**
     * Tests if the constructor works as intended
     * @throws Exception if can't create Action
     */
    @Test
    public void shouldCreateAnAction() throws Exception {
        Action a = new Action("Action1", 0, 3);
    }

    /**
     * Tests if the method to get the number of steps works as intended
     * @throws Exception if getSteps don't return 5
     */
    @Test
    public void shouldGetNumberOfSteps() throws Exception {
        assertEquals(5, new Action("happend", 5, 0).getSteps());
    }

    /**
     * Tests if the method to change the number of steps works as intended
     * @throws Exception if getSteps don't return 9
     */
    @Test
    public void shouldChangeNumberOfSteps() throws Exception {
        Action a = new Action("happend", 0, 5);
        a.setSteps(9);
        assertEquals(9, a.getSteps());
    }

    /**
     * Tests if the method to get the action ID works as intended
     * @throws Exception if getActionID don't return "happen"
     */
    @Test
    public void shouldGetActionID() throws Exception {
        assertEquals("happen", new Action("happen", 0, 5).getActionID());
    }

    /**
     * Tests if the method equals works as intended when two objects are equal
     * @throws Exception if a are not equal to b
     */
    @Test
    public void shouldBeEqualForBoth() throws Exception {
        Action a = new Action("happen", 0, 5);
        Action b = new Action("happen", 0, 5);

        assertTrue(a.equals(b));
    }

    /**
     * Tests if the method equals works as intended when two objects are not equal
     * @throws Exception if a are equal to b
     */
    @Test
    public void shouldNotBeEqualBecauseOfDifferentNumberOfSteps() throws Exception {
        Action a = new Action("happen", 2, 5);
        Action b = new Action("happen", 3, 100);
        assertFalse(a.getSteps() == b.getSteps());

    }

    /**
     * Tests if the method equals works as intended when ID is different but the position is equal
     * @throws Exception if a are  equal to b
     */
    @Test
    public void shouldNotBeEqualBecauseOfDifferentActionID() throws Exception {
        Action a = new Action("happen", 0, 5);
        Action b = new Action("happy", 0, 5);

        assertFalse(a.equals(b));
    }

    /**
     * Tests if the method equals works as intended when ID id equal but the position different
     * @throws Exception if a are not equal to b
     */
    @Test
    public void shouldNotBeEqualActionID() throws Exception {
        Action a = new Action("happy", 0, 9);
        Action b = new Action("happy", 0, 10);

        assertTrue(a.equals(b));
    }
}
