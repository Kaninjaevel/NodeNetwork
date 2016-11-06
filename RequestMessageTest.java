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
 * Test for class RequestMessage
 */
public class RequestMessageTest {
    /**
     * Testing if constructor works as intended
     * @throws Exception if request is not created
     */
    @Test
    public void shouldBeAbleToCreate() throws Exception{
        String id="name";
        Node n = new Node(0,new Position(0,0));
        Time t = new Time();
        new RequestMessage(n,id,t,"action");
    }


    /**
     * Tests if the Request Message class is able to move.
     * @throws Exception if request can't move
     */
    @Test
    public void shouldBeAbleToMove() throws Exception{
        String id="name";
        Node n = new Node(0,new Position(0,0));
        Time t = new Time();
        RequestMessage r = new RequestMessage(n,id,t,"action");

        Node n1 = new Node(0,new Position(0,0));
        Node n2 = new Node(1,new Position(0,1));
        n1.setNeighbour(n2);
        r.move(n1);
        assertEquals(RequestMessage.class,n2.peekAtQueue().getClass());
    }
    /**
     * Tests if the Request message class is able to get a response
     * @throws Exception if response in Request is not "action"
     */
    @Test
    public void shouldBeAbleToGetResponse() throws Exception{
        String id="name";
        Node n = new Node(0,new Position(0,0));
        Time t = new Time();
        RequestMessage r = new RequestMessage(n,id,t,"action");

        Node n1 = new Node(0,new Position(0,0));
        Node n2 = new Node(1,new Position(0,1));
        n2.setAction(new Action("action",0,1));
        n1.setNeighbour(n2);
        n1.addToDo(r);

        for(int i=0;i<4;i++){
            n1.takeNextMessage();
            n2.takeNextMessage();
        }

        assertEquals("action",r.getResponse().getActionID());
    }

    /**
     * Tests if the message is able to move to an action, get a response and
     * return it to the start-node.
     * @throws Exception if request is not returned
     */
    @Test
    public void shouldBeAbleToReturnResponse() throws Exception{
        Node n1 = new Node(1,new Position(1,1));
        Node n2 = new Node(1,new Position(1,2));
        Node n3 = new Node(1,new Position(1,3));
        n1.setNeighbour(n2);
        n2.setNeighbour(n3);

        n3.setAction(new Action("action",0,1));
        RequestMessage r = new RequestMessage(n1,"HEJ",new Time(),"action");
        for(int i=0;i<10;i++){
            n1.takeNextMessage();
            n2.takeNextMessage();
            n3.takeNextMessage();
        }

        assertEquals("action",r.getResponse().getActionID());
    }


    /**
     * /**
     * Tests so that the agent stops being added to ques
     * after 45 time steps.
     *
     * @throws Exception if peek don't return null
     */
    @Test
    public void shouldNotBeAbleToMove() throws Exception{
        Node n1 = new Node(1,new Position(1,1));
        Node n2 = new Node(1,new Position(1,2));
        n1.setNeighbour(n2);
        n2.setNeighbour(n1);
        Time t = new Time();
        new RequestMessage(n1,"test",t,"action");
        for(int i=0;i<47;i++) {
            n1.takeNextMessage();
            n2.takeNextMessage();
            t.increment();
        }

        assertTrue(n1.peekAtQueue()==null);
        assertTrue(n2.peekAtQueue()==null);
    }

}
