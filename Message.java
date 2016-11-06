/**
 * Course: 5DV133
 * Written by: Susanne
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */

/**
 * Abstract class Message that AgentMessage and RequestMessage inherit from. Needed so that Node can have both request
 * and agent in the same toDoList.
 */
public abstract class Message {

    public Message(){}

    /**
     * abstract class move
     * @param n - node that Message is on
     */
    public abstract void move(Node n);
}
