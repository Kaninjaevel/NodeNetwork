/**
 * Course: 5DV133
 * Written by: Thomas Sarlin thsa0043/id15tsn
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */
import java.io.IOException;

/**
 * Will create and run the program following the specification of the assignment.
 */
public class TestProgram {

    /**
     * The method that will create and run the program
     * @param argv - argument
     * @throws IOException - if anything does wrong in the program
     */
    public static void main(String[] argv) throws IOException{
        Network n= new Network(50,10,15,400,10000);

        while(!n.isEndOfProgram()) {
            n.timeStep();
        }
    }
}
