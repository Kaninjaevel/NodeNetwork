/**
 * Course: 5DV133
 * Written by: Thomas Sarlin thsa0043/id15tsn
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */

/**
 * Class to create and handle time
 * takes in two arguments: after how many time steps requests sends out
 *                         how many time steps the system has
 */
public class Time {
    private int noTimeSteps;


    public Time() {
        noTimeSteps = 0;
    }

    /**
     * To get the current time in the system
     * @return The current value of number of steps which has got in the system
     */
    public int getTime() {
        return this.noTimeSteps;

    }

    /**
     * Increases the value of number of steps by 1
     * If number of time steps modulus value of breakpoint is 0 isBreakPoint becomes true
     * If number of time steps is equal to value of endpoint isEndPoint becomes true
     */
    public void increment() {
        this.noTimeSteps++;
    }
}
