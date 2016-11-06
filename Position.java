/**
 * Course: 5DV133
 * Written by: Erik Dahlberg c15edg
 * @Author Group 15 - 5DV133 vt2016
 * @Version 1.0
 */

/**
 * Represents x and y axis to navigate by position in Network
 */
public class Position {
    private int x;
    private int y;

    /**
     * will take to x and y param and update positions x and y values.
     * @param x - represents x-axis
     * @param y - represent y-axis
     */
    public Position (int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Will return the value representing x-axis
     * @return value that represents x-axis
     */
    public int getX(){
        return x;

    }

    /**
     * Will return the value representing y-axis
     * @return value that represents y-axis
     */
    public int getY(){
        return  y;
    }

    /**
     * Convert x and y to String, example string (3,3)
     * @return string with x and y value
     */
    public String toString(){
        return "(" + x + "," + y + ")";

    }


    /**
     * Compare method
     * Auto intellij generated equals method
     * @param o is object to compare with
     * @return boolean true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;

    }

    /**
     * Will return hashcode
     * Auto intellij generated hashcode method
     * @return int of hashcode
     */
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

}