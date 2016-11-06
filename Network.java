/**
 * Course: 5DV133
 * Written by: Thomas Sarlin id15tsn
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The main class for the Network, creates a field of nodes with a
 * specified distance between the nodes, range of the nodes to specify
 * their neighbours. Uses a time to be able to set the limit of the program,
 * and how often the program should send out requests for actions. The actions
 * are randomized onto the field with a chance of 0,01% and for each action
 * there's a 50% chance that an agent is created on the specified node.
 */
public class Network {
    private Time time;
    private ArrayList<Node> requestNodes;
    private Node[][] nodes;
    private ArrayList<String> actions;
    private ArrayList<Request> requests;
    private NetworkStatus status;
    private int breakPoint,endPoint;

    /**
     * Constructor for Network, creates a network with a specified size,
     * range of nodes and time specifications.
     *
     * @param fieldSize  -used to create a field in size of fieldSize x fieldSize
     * @param nodeRange  - used to determine the range of the nodes, to set
     *                   neighbors.
     * @param endPoint   - determines how many timesteps the program
     *                   should run.
     * @param breakPoint - determines the interval of the requests sent.
     * @param nodeDistance - distance between nodes.
     */

    public Network(int fieldSize, int nodeDistance, int nodeRange
            , int breakPoint, int endPoint)
            throws IllegalArgumentException {

        startTest(fieldSize,nodeDistance,nodeRange,breakPoint,endPoint);
        this.nodes = new Node[fieldSize][fieldSize];
        this.requestNodes = new ArrayList<>();
        this.actions = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.status= new NetworkStatus(requests.size());
        this.addNodes(fieldSize);
        this.setNeighbours(calculateNeighbours(nodeDistance, nodeRange)
                , fieldSize);
        this.setRequestNodes(fieldSize);
        this.breakPoint=breakPoint;
        this.endPoint=endPoint;
        this.time = new Time();
    }

    /**
     * Checks if there are any illegal arguments inserted into the constructor.
     *
     * @param fieldSize - size of field
     * @param nodeDistance - distance between nodes
     * @param nodeRange - distance between Range.
     * @param endPoint - end of program
     * @param breakPoint - breakpoint of program
     * @throws IllegalArgumentException if starting parameters are incorrect.
     */

    private void startTest(int fieldSize, int nodeDistance, int nodeRange
            , int breakPoint, int endPoint){
        if(nodeRange<nodeDistance){
            throw new IllegalArgumentException("nodeRange is smaller than, " +
                    "node distance.");
        }

        if(fieldSize<=0){
            throw new IllegalArgumentException("field Size is less than or " +
                    "equal to zero.");
        }
        if(breakPoint>endPoint){
            throw new IllegalArgumentException("Breakpoint is larger than" +
                    "the endpoint.");
        }

    }
    /**
     * private class to keep track of when requests were sent, so that the
     * program knows when to stop waiting for these requests and where to
     * resend the request if the response is too slow.
     */
    private class Request {
        String id;
        int time;
        boolean resent;
        Node startNode;

        public Request(String id, int time, Node n) {
            setID(id);
            setTime(time);
            this.resent = false;
            this.startNode = n;

        }
        public void setID(String s) {this.id = s;}
        public String getID() {return id;}
        public void setTime(int time){this.time=time;}
        public int getTime() {return time;}

        /**
         * Used to indicate that a request has been resent.
         */
        public void setResent(boolean status) {resent = status;}

        /**
         * Method to se if request has been resent or not.
         * @return boolean representing if request has been resent.
         */
        public boolean resentStatus() {return resent;}

        /**
         * To get the node that request was sent from.
         * @return the node that the request was sent from.
         */
        public Node getStartNode() {return startNode;}

        public boolean equals(Object o) {
            Request r = (Request) o;
            return this.getID() == r.getID();
        }
    }

    /**
     * Used only for testing purposes, to see so that all the neighbours are
     * set correctly.
     * no actual use in the program.
     * @return all the node that was created.
     */
    public Node[][] getAllNodes(){
        return nodes;
    }


    /**
     * Gives all nodes a 0,01% chance to create an Action.
     * 50% that an Agent is created on current node if Actions is created.
     *
     * After the random occurrences the nodes method to activate messages will
     * be called.
     */

    private void activateNodes() {
        for (int i = 0; i < nodes.length; i++){
            for (int j = 0; j < nodes.length; j++) {
                if (new Random().nextInt(100000) < 10) {
                    nodes[i][j].setAction(
                            new Action(nodes[i][j].getNodeID() + ":"
                                    + time.getTime()
                                    , 0, time.getTime()));
                    actions.add(nodes[i][j].getNodeID() + ":" + time.getTime());
                    status.incrementActions();

                    if (new Random().nextInt(100) < 50) {
                        new AgentMessage(nodes[i][j]);
                        status.incrementAgents();
                    }

                }
                nodes[i][j].takeNextMessage();
            }
        }
    }


    /**
     * Sends four requests to randomized activated Actions
     * from the four active requestNodes.
     */
    private void sendRequestMessages() {
        Random r = new Random();
        if(!actions.isEmpty()) {
            for (int i = 0; i < requestNodes.size(); i++) {
                int r1 = r.nextInt(actions.size());
                new RequestMessage(requestNodes.get(i)
                        , actions.get(r1), time, actions.get(r1));
                requests.add(new Request(actions.get(r1), time.getTime()
                        , requestNodes.get(i)));
                status.incrementRequests();
            }
        }
    }

    /**
     * Checks all requests if there are any responses that should be resent
     * due to slow response, if the request has already been resent once. The
     * request is removed.
     */
    private void updateRequests() {
        if(!requests.isEmpty()) {
            for (int i = 0; i < requests.size(); i++){
                if(requests.get(i).getTime()<=(time.getTime()-(45*8))) {
                    if (requests.get(i).resentStatus()) {
                        requests.remove(i);
                        status.incrementFailedRequests();
                    } else {
                        requests.get(i).setResent(true);
                        String s = requests.get(i).getID();
                        requests.get(i).setTime(time.getTime());
                        requests.get(i).setID(s + "resent");
                        new RequestMessage(requests.get(i).getStartNode()
                                , requests.get(i).getID(), time
                                , s);
                        status.incrementResentRequests();
                    }
                }
            }
        }
    }

    /**
     * Checks if the program has reached it's endpoint.
     * @return - boolean representing if endpoint is reached.
     */
    public boolean isEndOfProgram(){return time.getTime()==endPoint;}

    /**
     * Method to make the program increment by one time-step.
     * Initializes all random occurrences and prints occasionally if
     * a response has reached one of the request nodes.
     *
     * If the program has reached its endpoint the program terminates
     * after input from user.
     * @throws IOException
     */
    public void timeStep() throws IOException {

        if(!isEndOfProgram()) {
            updateRequests();
            getResponse();
            activateNodes();
            if (time.getTime()%breakPoint==0&&time.getTime()!=0) {
                sendRequestMessages();
            }
            time.increment();
        }

        if(isEndOfProgram()) {
            status.printNetworkStatus();
            System.out.println("End of program");
        }

    }


    /**
     * Checks if any responses have been returned to the requestNodes
     * if responses exist, they will be printed.
     */
    private void getResponse(){

        Message m;
        Action a;

        for(int i=0;i<requestNodes.size();i++) {
            m = requestNodes.get(i).peekAtQueue();
            if(m instanceof RequestMessage){
                if(((RequestMessage) m).stackIsEmpty()
                        && ((RequestMessage) m).getResponse()!=null) {
                    a = ((RequestMessage) m).getResponse();
                    if(requestsContain(((RequestMessage) m).getID())) {
                        System.out.println("Action ID: " + a.getActionID()
                                + " Position:" + a.getNextPosition().toString()
                                + " Action created at: " + a.getActionTime());
                    }
                }
            }
        }
    }

    /**
     * Function to see if the returned RequestMessage is still valid.
     * @param ID - string representing ID of RequestMessage.
     * @return boolean.
     */
    private boolean requestsContain(String ID){
        for(int i=0;i<requests.size();i++){
            if(requests.get(i).getID().equals(ID)){
                System.out.println("\nResponse sent at: "
                        + requests.get(i).getTime()
                        + " Response received at: "
                        + time.getTime()
                        + " Response time: "
                        + (time.getTime()-requests.get(i).getTime())
                        + " resent:" + requests.get(i).resentStatus());

                status.incrementResponse();
                status.incrementTotalResponseTime(time.getTime()
                        -requests.get(i).getTime());
                requests.remove(i);
                return true;
            }
        }
        return false;
    }
    /**
     * Adds all nodes to the network.
     *
     * @param fieldSize - size of the field (fieldSize x fieldSize)
     */
    private void addNodes(int fieldSize) {
        int ID = 0;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                nodes[i][j] = (new Node(ID, new Position(i, j)));
                ID++;
            }
        }
    }

    /**
     * By using pythagoras theorem this method calculates the structure of the
     * surrounding neighbours for later use in the add neighbours method.
     *
     * @param nodeDistance - distance between nodes
     * @param nodeRange    - range of the nodes
     * @return List of positions representing instructions on how to get
     * to neighbours.
     */
    private ArrayList<Position> calculateNeighbours(int nodeDistance
            , int nodeRange) {

        ArrayList<Position> NeighbourStructures = new ArrayList<>();

        //Radius to be controlled for neighbours.
        int controlRadius = (nodeRange/nodeDistance)*2;

        for (int i = -controlRadius; i < controlRadius; i++) {
            for (int j = -controlRadius; j < controlRadius; j++) {
                if ((Math.pow(nodeDistance * i, 2)
                        + (Math.pow(nodeDistance * j, 2)))
                        <= Math.pow(nodeRange, 2)) {
                    if (!(i == 0 && j == 0))
                        NeighbourStructures.add(new Position(i, j));
                }
            }
        }
        return NeighbourStructures;
    }


    /**
     * Using the neighbour structure list this method adds all neighbours to
     * all nodes.
     *
     * @param structure - instructions on how to add neighbours.
     * @param fieldSize - size of field.
     */
    private void setNeighbours(ArrayList<Position> structure
            , int fieldSize) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                for (int g = 0; g < structure.size(); g++) {
                    try {
                        nodes[i][j].setNeighbour(nodes[i + structure.get(g).getX()]
                                [j + structure.get(g).getY()]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }
        }
    }

    /**
     * Randomization of four nodes that should be allowed to send requests.
     *
     * @param fieldSize - size of network fieldSize x fieldSize
     */
    private void setRequestNodes(int fieldSize) {
        Random random = new Random();
        int r1;
        int r2;
        for (int i = 0; i < 4; i++) {
            r1 = random.nextInt(fieldSize);
            r2 = random.nextInt(fieldSize);
            requestNodes.add(nodes[r1][r2]);
        }
    }
}