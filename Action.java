/**
 * Course: 5DV133
 * Written by: Erik Dahlberg c15edg
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */

/**
 * Class that represents Actions that is created around in the Network.
 * Will have information about ID, steps to Action, the new position to get to Action and the Time it was created.
 * If action have 0 Steps it means this is the Node that Action was created on.
 */
public class Action {
    private String actionID;
    private int steps;
    private Position nextPosition;
    private int actionTime;

    /**
     * Create new Action
     * @param actionID the ID of Action
     * @param steps how many steps it is to current Action
     * @param actionTime The time the Action was created
     */
    public Action (String actionID, int steps, int actionTime){
        this.actionID = actionID;
        this.steps = steps;
        this.actionTime = actionTime;
    }

    /**
     * Will return the steps to action
     * @return the number of steps to action
     */
    public int getSteps(){
        return steps;
    }

    /**
     * Will add the position to next node in the path to the current action.
     * @param p Position to the nextNode
     */
    public void setNextPosition(Position p){
        nextPosition = p;
    }

    /**
     * Set steps of Action
     * @param steps set the current steps to action.
     */
    public void setSteps(int steps){
        this.steps = steps;
    }

    /**
     * Increment the step attribute by one.
     */
    public void addOneStep(){
        steps++;
    }

    /**
     * will return ActionID
     * @return will return actions ID
     */
    public String getActionID(){
        return actionID;

    }

    /**
     * Will return the next position to the action.
     * @return the next Position to find the Action.
     */
    public Position getNextPosition(){
        return nextPosition;
    }

    /**
     * Get the time when the action was created.
     * @return the time when the action was created
     */
    public int getActionTime(){
        return actionTime;
    }


    /**
     *
     * Equal Method for actionID
     * @param o the object to compare
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        return actionID != null ? actionID.equals(action.actionID) : action.actionID == null;

    }

    /**
     * Check Hashcode for actionID
     * @return int the hashcode
     */
    @Override
    public int hashCode() {
        int result = actionID != null ? actionID.hashCode() : 0;
        result = 31 * result + steps;
        return result;
    }
}
