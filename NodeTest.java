/**
 * JUnit test program for methods in Node Class.
 * Course: 5DV133
 * Written by: Susanne, Desireé, Erik
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */
import org.junit.Test;
import java.util.Hashtable;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Test for class Node.
 */
public class NodeTest {
    @Test
    public void shouldCreateANode() throws Exception {
        Position p = new Position(3, 9);
        new Node(4, p);
    }

    /**
     * testing that getNodeID will return Nodes ID
     *
     * @throws Exception if new node don't have id 4
     */
    @Test
    public void shouldGetNodeID() throws Exception {
        assertEquals(4, new Node(4, new Position(4, 6)).getNodeID());
    }

    /**
     * testing that setNodeID will set new ID to Node
     *
     * @throws Exception if nodeID is not 9 after used setNode
     */
    @Test
    public void shouldSetNewNodeID() throws Exception {
        Node n = new Node(4, new Position(4, 7));
        n.setNodeID(9);
        assertEquals(9, n.getNodeID());
    }

    /**
     * testing that getPosition will return Nodes Position
     *
     * @throws Exception if position it not (4,6)
     */
    @Test
    public void shouldGetNodePosition() throws Exception {
        assertEquals(new Position(4, 6), new Node(4, new Position(4, 6)).getPosition());
    }

    /**
     * testing that setPosition will set new Position on Node
     *
     * @throws Exception if position is not (8,9)
     */
    @Test
    public void shouldSetNewNodePosition() throws Exception {
        Position p = new Position(4, 6);
        Node n = new Node(4, p);
        n.setPosition(new Position(8, 9));
        assertEquals(new Position(8, 9), n.getPosition());
    }

    /**
     * testing that setAction will insert Action to nodes ActionTable
     *
     * @throws Exception if actionTable dose not contains key "Key1"
     */
    @Test
    public void shouldGetNodeActionFromTable() throws Exception {
        Node n = new Node(5, new Position(4, 6));
        Action action1 = new Action("Key1", 1, 01);
        n.setAction(action1);

        assertTrue(n.getActionTable().containsKey("Key1"));

    }

    /**
     * Test that equal method works, nodes should be Equal
     * Written by: Erik Dahlberg erda0043/c15edg
     *
     * @throws Exception if n and n2 is not equal
     */
    @Test
    public void shouldBeEqual() throws Exception {
        Node n = new Node(5, new Position(4, 6));
        Node n2 = new Node(5, new Position(4, 6));
        assertTrue(n.equals(n2));
    }

    /**
     * Test that equal method works, nodes should not be Equal
     * Written by: Erik Dahlberg erda0043/c15edg
     *
     * @throws Exception if n and n2 are equal
     */
    @Test
    public void shouldNotBeEqual() throws Exception {
        Node n = new Node(5, new Position(4, 6));
        Node n2 = new Node(5, new Position(4, 7));
        assertFalse(n.equals(n2));

    }


    /**
     * Testing that agent will update nodes table.
     * Key3 with 5 steps in Node should be updated to Agent's key3 with 2 steps.
     * @throws Exception  if tables steps are not updated.
     */
    @Test
    public void ShouldUpdateNodesTableWithNewSteps() throws Exception {
        Node n = new Node(5, new Position(4, 6));
        Node n3 = new Node(5, new Position(4, 9));
        n.setNeighbour(n3);
        Action action1 = new Action("Key1", 1, 01);
        Action action2 = new Action("Key2", 2, 02);
        Action action3 = new Action("Key3", 5, 05);
        n.setAction(action1);
        n.setAction(action2);
        n.setAction(action3);

        Node n2 = new Node(6, new Position(4, 7));
        AgentMessage agent = new AgentMessage(n2);
        Hashtable<String, Action> actionTable2 = new Hashtable<>();
        Action action4 = new Action("Key3", 2, 03);
        agent.getAgentActionTable().put("Key3", action4);
        actionTable2.put("Key3", action4);
        n.addToDo(agent);
        n.takeNextMessage();
        n.takeNextMessage();

        assertEquals(2, n.getActionTable().get("Key3").getSteps());
    }


    /**
     * Testing that agentMessage will updatge nodes table.
     * Node's Table should have size 4 after added values from AgentsTable
     * @throws Exception if table size is not 4
     */
    @Test
    public void ShouldUpdateNodesTableWithMoreValues() throws Exception {
        Node n = new Node(5, new Position(4, 6));
        Node n3 = new Node(5, new Position(4, 9));
        n.setNeighbour(n3);
        Action action1 = new Action("Key1", 1, 01);
        Action action2 = new Action("Key2", 2, 02);
        Action action3 = new Action("Key3", 5, 05);
        n.setAction(action1);
        n.setAction(action2);
        n.setAction(action3);

        Node n2 = new Node(6, new Position(4, 7));
        AgentMessage agent = new AgentMessage(n2);
        Hashtable<String, Action> actionTable2 = new Hashtable<>();
        Action action4 = new Action("Key3", 2, 03);
        Action action5 = new Action("Key4", 4, 04);
        agent.getAgentActionTable().put("Key3", action4);
        agent.getAgentActionTable().put("Key5", action5);
        agent.updateTables(actionTable2,n.getActionTable());
        n.addToDo(agent);
        n.takeNextMessage();
        n.takeNextMessage();

        assertEquals(4, n.getActionTable().size());
    }



    /**
     * If Node have ActionID it should move Request to neighbour with Position, NextPosition in action.
     * If Test works it should  move request from Node n2 toDoList to Node n toDoList.
     * @throws Exception if node can't send requestMessage to move
     */
    @Test
    public void ShouldSendRequestToMoveToNode() throws Exception{
        Node n2 = new Node(2, new Position(4, 9));
        Position p = new Position(1, 3);
        Node n = new Node(1, new Position(1, 3));

        n2.setNeighbour(n);
        Action action1 = new Action("Key1", 3, 04);
        n2.setAction(action1);
        action1.setNextPosition(new Position(1, 3));

        Time t = new Time();
        Node n3 = new Node(3, new Position(4, 8));
        RequestMessage request = new RequestMessage(n3, "A", t, "Key1");
        n2.addToDo(request);
        n2.takeNextMessage();
        n2.takeNextMessage();

        assertEquals("Key1", ((RequestMessage) n.peekAtQueue()).getActionID());
    }

    /**
     * Node should handel Agent With empty ActionTable without problem.
     * @throws Exception if node cant handle empty actionTable
     */
    @Test
    public void NodeShouldHandelAgentWithEmptyActionTable() throws Exception {
        Node n = new Node(5, new Position(4, 6));
        Node n3 = new Node(5, new Position(4, 9));
        n.setNeighbour(n3);
        Action action1 = new Action("Key1", 1, 01);
        n.setAction(action1);


        Node n2 = new Node(6, new Position(4, 7));
        AgentMessage agent = new AgentMessage(n2);
        n.addToDo(agent);
        n.takeNextMessage();
        n.takeNextMessage();
    }

}
