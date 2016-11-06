/**
 * JUnit test program for methods in RequestMessage Class.
 * Course: 5DV133
 * Written by: Thomas Sarlin id15tsn
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for class Time.
 */
public class TimeTest {

    /**
     * Testing if the create method works as intended.
     * @throws Exception if Time can't be created
     */
    @Test
    public void shouldBeAbleToCreate() throws Exception{
        new Time();
    }

    /**
     * Should throw exception if it can't get time
     * @throws Exception if getTime don't return 0
     */
    @Test
    public void shouldBeAbleToGetTime() throws Exception{
        Time t = new Time();
        assertEquals(0,t.getTime());
    }
    /**
     * Testing if the increment works as intended.
     * @throws Exception if getTime don't return 10
     */

    @Test
    public void shouldBeAbleToIncrease() throws Exception{
        Time t = new Time();
        for(int i=0; i<10;i++)
            t.increment();

        assertEquals(10,t.getTime());
    }
}
