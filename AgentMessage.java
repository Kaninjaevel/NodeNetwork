/**
 * Course: 5DV133
 * Written by: Susanne, Desireé
 * @author Group 15 - 5DV133 vt2016
 * @version 1.0
 */

import java.util.*;

/**
 * Representing an agent that moves around in the network and spread information about actions.
 * Will move around in network by getting nodes neighbours and randomly choose one to
 * go to, if possible one not yet visited. Will put it self in that nodes toDOList. Class is inherited from Message.
 */
public class AgentMessage extends Message {

    private Hashtable<String, Action> agentActionTable = new Hashtable<>();
    private ArrayList <Node> visitedNodes = new ArrayList<>();
    private int noSteps;
    private Position previousPosition;
    private static final int AllowedStepToTake = 50;


    /**
     *Create new AgentMessage
     * @param n - Node to ad to visitedList
     */
    public AgentMessage (Node n){
        visitedNodes.add(n);
        previousPosition = n.getPosition();
        n.addToDo(this);

}

    /**
     * Will return agent's ActionTable
     * @return agentActionTable - Agent's ActionTable
     */
    public Hashtable<String, Action> getAgentActionTable(){
        return agentActionTable;
    }

    /**
     * Will compare the first hashtable to the second and then copy any actions
     * from the latter if it does not exist in the first table. If an action
     * exists in both tables, the one with the shortest path will be saved in
     * table 1.
     *
     * @param table1 a hashTable that needs update
     * @param table2 a hashTable to be compared.
     */
     public void  updateTables(Hashtable<String, Action> table1
             , Hashtable<String, Action> table2){

         Enumeration NodeEnuActionID;
         NodeEnuActionID = table2.keys();

         while (NodeEnuActionID.hasMoreElements()) {
             String AgentIDStr = (String) NodeEnuActionID.nextElement();
             if (table1.containsKey(AgentIDStr)) {
                 if (table1.get(AgentIDStr).getSteps()
                         > table2.get(AgentIDStr).getSteps()) {
                     table1.get(AgentIDStr).setSteps(table2
                             .get(AgentIDStr).getSteps());
                     table1.get(AgentIDStr)
                             .setNextPosition(table2
                                     .get(AgentIDStr).getNextPosition());
                 }
             }
         }
         NodeEnuActionID= table2.keys();
         while (NodeEnuActionID.hasMoreElements()) {
             String AgentIDStr = (String) NodeEnuActionID.nextElement();
             if (!table1.containsKey(AgentIDStr)) {

                 Action newAction = new Action(table2
                         .get(AgentIDStr).getActionID()
                         ,table2.get(AgentIDStr).getSteps()
                         ,table2.get(AgentIDStr).getActionTime());

                 newAction.setNextPosition(table2
                         .get(AgentIDStr).getNextPosition());
                 table1.put(AgentIDStr,newAction);
             }
         }


     }
    /**
     * Will take Node's neighbours and random one node to move to that it have not been to.
     * If Agent have been to all neighbours it will random one node from all neighbours. Is always going to check
     * that it does not take more then allowed steps.
     * Will also update steps and position in AgentActionTable
     *
     * @param n - Node that Agent is on
     */
    public void move(Node n){
        ArrayList <Node> shuffleList;

        if(noSteps<AllowedStepToTake) {
            updateTables(n.getActionTable(),agentActionTable);
            updateTables(agentActionTable,n.getActionTable());
            noSteps++;
            previousPosition = n.getPosition();
            updateTable();
            visitedNodes.add(n);
            boolean allVisited = true;


            shuffleList= n.getNeighbours();
            Collections.shuffle(shuffleList);

            for (int i = 0; i < shuffleList.size(); i++) {

                Node a = shuffleList.get(i);
                if (visitedNodes.contains(a)) {

                } else {
                    a.addToDo(this);
                    allVisited = false;
                    break;
                }
            }
            if(allVisited){
                Random r = new Random();
                int index = r.nextInt(shuffleList.size());
                shuffleList.get(index).addToDo(this);
            }

        }

    }

    /**
     * Will update agentTable, by adding 1 to steps on all action in table.
     * Will also change NextPosition in table to previous position for agent.
     */
    private void updateTable(){
        Enumeration sizeAction;
        sizeAction = agentActionTable.keys();
        while(sizeAction.hasMoreElements()){
            String str = (String) sizeAction.nextElement();
            agentActionTable.get(str).addOneStep();
            agentActionTable.get(str).setNextPosition(previousPosition);
        }
    }
}
