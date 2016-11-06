/**
 * Course: 5DV133
 * Written by: Susanne, Desireé
 * @author Group 15 - 5DV133 vt2016
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Representing an message that's moves around in the network and asking nodes for
 * information about a certain action. Will move around by getting the node or nodes to move to and putting itself
 * in that nodes toDoList. When request get information about the action it searching for it will move back the same
 * that it came. Class is inherited from Message.
 */
public class RequestMessage extends Message{

    private String actionID;
    private String ID;
    private Stack <Node> backNavigationStack;
    private Action response;
    private Time currentTime;
    private int startTime;
    private boolean hasResponse;
    private static final int AllowedStepsToTake = 45;

    /**
     * Creates a new RequestMessage
     * @param n The node it is starting from
     * @param ID The ID the Message gets from the network
     * @param t The current time the system has
     * @param actionID The action it is looking for
     */
    public RequestMessage(Node n, String ID, Time t, String actionID) {
        this.currentTime = t;
        this.startTime = currentTime.getTime();
        this.hasResponse = false;
        this.actionID = actionID;
        this.ID = ID;
        this.backNavigationStack= new Stack<>();
        this.backNavigationStack.push(n);
        this.response=null;
        n.addToDo(this);
    }

    /**
     * Gets the value saved in actionID
     * @return the actionID that request is searching for
     */
    public String getActionID(){
        return actionID;
    }

    /**
     * String with RequestMessages ID
     * @return RequestMessages ID
     */
    public String getID(){
        return ID;
    }

    /**
     * To get boolean if requestMessage have an response
     * @return the value saved in hasResponse
     */

    public boolean hasResponse(){
        return hasResponse;
    }


    /**
     * While not exceeding the limit for its Life span of 8*parameter number of time steps
     * from when it was sent out, the request will, once every time step, be put in the
     * next node in order's toDoList
     * @param n current node
     */
    public void move(Node n){

            if (hasResponse()) {
                moveBack();
            } else {
                String actionID = getActionID();
                if (n.getActionTable().containsKey(actionID)) {
                    if (n.getActionTable().get(actionID).getSteps() == 0) {
                        setResponse(n.getActionTable().get(actionID));
                        moveBack();
                    } else {
                        moveToTarget(n.NextNode(actionID));
                    }
                } else {
                    ArrayList<Node> neighbours = n.getNeighbours();
                    if((currentTime.getTime()-AllowedStepsToTake)<= startTime) {
                        if ((currentTime.getTime() - (8 * AllowedStepsToTake)) <= startTime) {
                            Random r = new Random();
                            int index = r.nextInt(neighbours.size());
                            backNavigationStack.push(neighbours.get(index));
                            neighbours.get(index).addToDo(this);
                        }
                    }
                }
            }

    }

    /**
     * Method will make Message move back by popping stack.
     * Will check that node don't live longer then 8 times the number of steps it allowed to take
     * without finding information.
     * Method which looks if the message has steps left to go and if so moves back to the start node
     */
    private void moveBack(){
        if ((currentTime.getTime()-(8*AllowedStepsToTake))<=startTime){
            if(!stackIsEmpty()){
                Node n = backNavigationStack.pop();
                n.addToDo(this);
            }
        }
    }

    /**
     * Take node to go to in stack and then put it self in nodes toDoList
     * Will check that node don't live longer then 8 times the number of steps it allowed to take
     * without finding information.
     * @param n - node to move to
     */
    private void moveToTarget(Node n){
        if ((currentTime.getTime()-(8*AllowedStepsToTake))<=startTime){
            backNavigationStack.push(n);
            n.addToDo(this);
        }
    }

    /**
     * Sets new value for Action response
     * @param response takes in the action response
     */
    private void setResponse(Action response){
        this.response = response;
        hasResponse = true;
    }

    /**
     * To see if Requests stack is empty or not
     * @return true if stack is empty else false
     */
    public boolean stackIsEmpty(){
        return backNavigationStack.empty();
    }

    /**
     * Gets the value saved in response
     * @return the response that request is carrying
     */
    public Action getResponse(){
        return response;
    }
}
