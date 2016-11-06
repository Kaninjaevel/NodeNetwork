/**
 * JUnit test program for methods in Network Class.
 * Course: 5DV133
 * Written by: Thomas Sarlin id15tsn
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for class Network.
 */
public class NetworkTest {

    /**
     * Testing constructor with accepted values.
     *
     * @throws Exception if constructor is not working as intended.
     */
    @Test
    public void shouldBeAbleToCreate() throws Exception {
        new Network(40,10,15,10,100);
    }

    /**
     * Testing if constructor throws exception as intended when
     * distance is larger than range.
     *
     * @throws Exception if constructor works as intended.
     */
    @Test (expected = Exception.class)
    public void tryCreatingWithInvalidRange() throws Exception{
        new Network(40,15,10,10,100);
    }

    /**
     * Testing if constructor throws exception as intended when
     * breakpoint is larger than endpoint.
     *
     * @throws Exception if constructor works as intended.
     */
    @Test (expected = Exception.class)
    public void tryCreatingWithInvalidBreakEndPoints() throws Exception{
        new Network(40,10,15,100,10);
    }

    /**
     * Testing if constructor throws exception as intended when
     * using an invalid field size.
     * @throws Exception if constructor works as intended.
     */
    @Test (expected = Exception.class)
    public void tryCreatingWithInvalidFieldSize() throws Exception {
        new Network(-10,10,15,10,100);
    }


    /**
     * Testing so that the network is able to set neighbours.
     * @throws Exception if neighbours are incorrect.
     */
    @Test
    public void shouldBeAbleToSetNeighbours() throws Exception{
        Network n = new Network(50,10,15,10,100);
        Node[][] array = n.getAllNodes();

        assertEquals(3,array[0][0].getNeighbours().size());
        assertEquals(8,array[3][3].getNeighbours().size());

    }

    /**
     * Tests different ranges of the network and how well the network is
     * able to add neighbours using the pythagoras theorem.
     *
     * @throws Exception if range is incorrect.
     */
    @Test
    public void shouldBeAbleToUseDifferentRanges() throws Exception{
        Network n = new Network(50,1,2,10,100);
        Node[][] array = n.getAllNodes();

        assertEquals(12,array[10][10].getNeighbours().size());

        n = new Network(50,1,5,10,100);
        array = n.getAllNodes();

        assertEquals(80,array[10][10].getNeighbours().size());

    }

    /**
     * Testing if the network sets the correct neighbours.
     * @throws Exception if Network was unable to set correct neighbours.
     */

    @Test
    public void shouldAddCorrectNeighbours()throws Exception{
        Network n = new Network(50,10,15,10,100);
        Node[][] array = n.getAllNodes();

        assertEquals(8,array[5][5].getNeighbours().size());
        assertEquals(204,array[5][5].getNeighbours().get(0).getNodeID());
        assertEquals(205,array[5][5].getNeighbours().get(1).getNodeID());
        assertEquals(206,array[5][5].getNeighbours().get(2).getNodeID());
        assertEquals(254,array[5][5].getNeighbours().get(3).getNodeID());
        assertEquals(256,array[5][5].getNeighbours().get(4).getNodeID());
        assertEquals(304,array[5][5].getNeighbours().get(5).getNodeID());
        assertEquals(305,array[5][5].getNeighbours().get(6).getNodeID());
        assertEquals(306,array[5][5].getNeighbours().get(7).getNodeID());

    }


    /**
     * Testing timeSteps is able to end program as intended.
     * @throws Exception if isEndOfProgram returns false.
     */

    @Test
    public void tryEndOfProgram() throws Exception{

        Network n = new Network(10,10,15,2,10);
        for(int i=0;i<10;i++)
            n.timeStep();

        assertTrue(n.isEndOfProgram());
    }
}
