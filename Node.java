/**
 * Course: 5DV133
 * Written by: Erik Dahlberg c15edg
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */

import java.util.*;

/**
 * Class Node represent the nodes in the Network, that hold information
 * about Actions and how to find an Action.
 * Will only read or send a Message every time the takeNextMessage is called,
 * this is the method that will make the Message Move around
 * from one node to another to spread information in the NetWork.
 *
 */
public class  Node {
    private Position nodePosition;
    private int nodeID;
    private LinkedList<Message> toDoList;
    private ArrayList<Node> neighbours;

    private Hashtable<String, Action> actionTable;
    private boolean waitOnTurn = true;
    private Message newMessage;

    /**
     * Create new Node
     * @param ID The ID that Node will have
     * @param p The Position that Node will have
     */
    public Node(int ID, Position p) {
        this.nodeID = ID;
        this.nodePosition = p;
        neighbours = new ArrayList<>();
        toDoList = new LinkedList<>();
        actionTable = new Hashtable<>();

    }

    /**
     * Used to look at first Message in toDoList
     * without removing the Message, will return null if list is Empty
     * @return First Message in toDoList without, null if list is Empty
     */
    public Message peekAtQueue() {
        return toDoList.peekFirst();
    }

    /**
     * Will set ID of the node.
     * @param ID Updates NodeID to inserted int
     */
    public void setNodeID(int ID) {
        nodeID = ID;
    }

    /**
     * Used to get current ID
     * @return Nodes ID as an int
     */
    public int getNodeID() {
        return nodeID;
    }

    /**
     * Will ad Node's Position to Action then insert it to Nodes actionTable
     * @param a Action that Node will insert to actionTable
     */
    public void setAction(Action a) {
        a.setNextPosition(nodePosition);
        actionTable.put(a.getActionID(), a);
    }

    /**
     * Will return specified Action from Nodes actionTable
     * @param actionID String for the action to return
     * @return Action from Nodes actionTable
     */
    public Action getAction(String actionID) {
        return actionTable.get(actionID);
    }

    /**
     * Will return Nodes actionTable
     * @return Node's actonTable ((HashTable)
     */
    public Hashtable<String, Action> getActionTable() {

        return actionTable;
    }



    /**
     * Method is created to only read or send a Message every time
     * the method is called.
     *
     * It prioritizes sending message over reading. First step is to
     * check the first message of the queue, and next timeStep it forwards
     * the message by using its move method.
     */
    public void takeNextMessage() {

        if (waitOnTurn) {
            if (!toDoList.isEmpty()) {
                newMessage = toDoList.pollFirst();
            }
            else{
                newMessage=null;
            }
        }
        if (!waitOnTurn) {
            if (newMessage != null) {
                newMessage.move(this);
            }
        }
        if(waitOnTurn){
            waitOnTurn = false;
        }else{
            waitOnTurn = true;
        }

    }


    /**
     * Will take in param actionID for the Node it is searching for.
     * Locates the the Position for the param Node and returns
     * the Node in ActionTable that has the same Position.
     * @param actionID The actionID to search for.
     * @return neighbor Node that RequestMessage should go to
     */
    public Node NextNode(String actionID) {
        for (int i = 0; i < neighbours.size(); i++) {
            if (neighbours.get(i).getPosition().equals(actionTable.get(actionID).getNextPosition())) {
                return neighbours.get(i);
            }
        }
        System.err.println("NextNode could not find that Node in AgentTable");
        return null;
    }

    /**
     * Will set Nodes position to inserted param
     * @param p -Position to set as Node's Position
     */
    public void setPosition(Position p) {
        nodePosition = p;
    }

    /**
     * Will return the Position of the Node
     * @return - Nodes Position
     */
    public Position getPosition() {
        return nodePosition ;
    }


    /**
     * Will add AgentMassage to last position and
     * RequestMessage to first position in queue toDoList.
     * @param e Massage that will be added to queue addToDo
     */
    public void addToDo(Message e) {
        if (e instanceof RequestMessage) {
                toDoList.addFirst(e);
        } else {
            toDoList.add(e);
        }

    }

    /**
     * Will add Node to neighbour list
     * @param n Node that will be added to the current nodes neighbour-list.
     */
    public void setNeighbour(Node n) {
        neighbours.add(n);
    }

    /**
     * Will return list with all the current nodes neighbours.
     * @return list of the current nodes neighbours.
     */
    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    /**
     * Method to se if nodes x and y position is equal to param
     * @param o object
     * @return boolean if objects are equal
     */
    public boolean equals(Object o) {
        Node n = (Node) o;
        return this.nodePosition.getX() == n.nodePosition.getX()
                && this.nodePosition.getY() == n.nodePosition.getY();
    }


}