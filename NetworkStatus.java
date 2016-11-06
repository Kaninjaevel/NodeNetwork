/**
 * Small class to keep track of information on the network.
 */
public class NetworkStatus {
    private int agents = 0;
    private int requests = 0;
    private int failedRequests = 0;
    private int actions = 0;
    private int resentRequests = 0;
    private int responses = 0;
    private int responseTime = 0;
    private int activeResponses;

    public NetworkStatus(int activeResponses){
        this.activeResponses=activeResponses;
    }

    /**
     * increases specified counter by one.
     */
    public void incrementAgents(){agents++;}
    public void incrementRequests(){requests++;}
    public void incrementFailedRequests(){failedRequests++;}
    public void incrementActions(){actions++;}
    public void incrementResentRequests(){resentRequests++;}
    public void incrementResponse(){responses++;}
    public void incrementTotalResponseTime(int time){
        responseTime=responseTime+time;
    }

    public void printNetworkStatus(){
        System.out.println("\n\n---------------NETWORK SUMMARY---------------");
        System.out.println("Actions sent: " + actions);
        System.out.println("Agents sent: " + agents);
        System.out.println("Requests sent: " + requests);
        System.out.println("Requests resent: " + resentRequests);
        System.out.println("Requests failed: " + failedRequests);
        System.out.println("Requests active at end of program: "
                +activeResponses);
        System.out.println("Responses received: " + responses);
        if(responses!=0)
        System.out.println("Average Response time: "
                + (responseTime/responses));
        System.out.println("---------------------------------------------");
    }

}
