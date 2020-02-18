package model;

/**
 * 
 * An interface that models the direction (in degrees) of a dynamic element. 0
 * means Right, 90 means North, 180 means Left and 270 means South
 *
 */
public interface Direction {

    /**
     * 
     * This method allows the user to sum a given direction to the actual one.
     * 
     * @param direction the direction to sum.
     */
    void sum(Direction direction);

    /**
     * 
     * This method allows the user to subtract a given direction to the actual one.
     * 
     * @param direction the direction to subtract.
     */
    void subtract(Direction direction);

    /**
     * 
     * This method returns the direction in degrees.
     * 
     * @return the direction in degrees.
     */
    double getDirectionAsDegrees();

    /**
     * 
     * This method returns the direction in degrees.
     * 
     * @param direction the direction to compare.
     * 
     * @return > 0 if the actual direction is greater, 0 they are equal, < 0 if the
     *         given direction is greater.
     */
    int compareTo(Direction direction);

}
