/**
 * JUnit test program for methods in AgentMessage Class.
 * Course: 5DV133
 * Written by: Thomas Sarlin id15tsn
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for class AgentMessage.
 */
public class AgentMessageTest {

    /**
     * Tests if the constructor works as intended.
     *
     * @throws Exception if agent is not created
     */
    @Test
    public void shouldBeAbleToCreate() throws Exception{
        new AgentMessage(new Node(100,new Position(1,1)));
    }


    /**
     * Tests if the agent is able to move.
     * @throws Exception if peek don't give message a
     */
    @Test
    public void shouldBeAbleToMove() throws Exception{
        Node n1 = new Node(1,new Position(1,1));
        Node n2 = new Node(1,new Position(1,2));
        n1.setNeighbour(n2);

        AgentMessage a = new AgentMessage(n1);
        a.move(n1);

        assertEquals(a,n2.peekAtQueue());
    }

    /**
     * Tests so that the agent stops being added to ques
     * after 50 moves.
     *
     * @throws Exception if nodes queue is not null
     */

    @Test
    public void shouldNotBeAbleToMove()throws Exception{
        Node n1 = new Node(1,new Position(1,1));
        Node n2 = new Node(1,new Position(1,2));
        n1.setNeighbour(n2);
        n2.setNeighbour(n1);

        AgentMessage a = new AgentMessage(n1);
        for(int i=0;i<101;i++) {
            n1.takeNextMessage();
            n2.takeNextMessage();
        }

        assertTrue(n1.peekAtQueue()==null);
        assertTrue(n2.peekAtQueue()==null);
    }
    /**
     * Tests if agent successfully withdraws actions and passes them forward.
     * @throws Exception if nodes table aren't updated
     */
    @Test
    public void testSpreadingMultipleActions() throws Exception{
        Node n1 = new Node(1,new Position(1,1));
        Node n2 = new Node(1,new Position(1,2));
        Node n3 = new Node(1,new Position(1,3));
        n1.setAction(new Action("action",0,1));
        n1.setNeighbour(n2);
        n2.setNeighbour(n3);
        n2.setAction(new Action("action2",0,2));
        n3.setNeighbour(n1);
        new AgentMessage(n1);
        for(int i=0;i<6;i++){
            n1.takeNextMessage();
            n2.takeNextMessage();
            n3.takeNextMessage();
        }
        assertEquals("action",n3.getAction("action").getActionID());
        assertEquals(2,n3.getAction("action").getSteps());

        assertEquals("action2",n3.getAction("action2").getActionID());
        assertEquals(1,n3.getAction("action2").getSteps());
    }

    /**
     * Tests if agent successfully withdraws values from the node if the node
     * has a shorter path to the target action.
     *
     * @throws Exception if steps is not 4
     */
    @Test
    public void testReplacingValuesOnAgent() throws Exception{
        Node n1 = new Node(1,new Position(1,1));
        Node n2 = new Node(1,new Position(1,2));
        Node n3 = new Node(1,new Position(1,3));
        n1.setAction(new Action("action",25,1));
        n1.setNeighbour(n2);
        n2.setNeighbour(n3);
        n3.setNeighbour(n1);
        n2.setAction(new Action("action",2,1));
        AgentMessage a=  new AgentMessage(n1);
        for(int i=0;i<6;i++){
            n1.takeNextMessage();
            n2.takeNextMessage();
            n3.takeNextMessage();
        }
        assertEquals(4,a.getAgentActionTable().get("action").getSteps());
    }

    /**
     * Tests if agent successfully replaces values in the node if the Agent
     * has a shorter path to the target action.
     *
     * @throws Exception if steps is not 1
     */
    @Test
    public void testReplacingValuesOnNode() throws Exception{
        Node n1 = new Node(1,new Position(1,1));
        Node n2 = new Node(1,new Position(1,2));
        Node n3 = new Node(1,new Position(1,3));
        n1.setAction(new Action("action",0,1));
        n1.setNeighbour(n2);
        n2.setNeighbour(n3);
        n3.setNeighbour(n1);
        n2.setAction(new Action("action",25,1));
        AgentMessage a=  new AgentMessage(n1);
        for(int i=0;i<6;i++){
            n1.takeNextMessage();
            n2.takeNextMessage();
            n3.takeNextMessage();
        }
        assertEquals(1,n2.getAction("action").getSteps());
    }

    /**
     * Tests so that the agent changes the next position in the nodes action
     * if the agent has a shorter path in its table.
     * @throws Exception if the position is not (1,1) and (1,2)
     */
    @Test
    public void shouldChangeNextPosition() throws Exception{
        Node n1 = new Node(1,new Position(1,1));
        Node n2 = new Node(1,new Position(1,2));
        Node n3 = new Node(1,new Position(1,3));
        n1.setAction(new Action("action",0,1));
        n1.setNeighbour(n2);
        n2.setNeighbour(n3);
        n3.setNeighbour(n1);
        n2.setAction(new Action("action",25,1));
        AgentMessage a=  new AgentMessage(n1);
        for(int i=0;i<6;i++){
            n1.takeNextMessage();
            n2.takeNextMessage();
            n3.takeNextMessage();
        }
        assertEquals(new Position(1,1),n2.getAction("action").getNextPosition());
        assertEquals(new Position(1,2),n3.getAction("action").getNextPosition());
    }

}
