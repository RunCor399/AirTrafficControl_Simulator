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
    double getAsDegrees();

    /**
     * 
     * This method returns the direction in radians.
     * 
     * @return the direction in radians.
     */
    double getAsRadians();

    /**
     * 
     * This method computes the distance between the actual direction and the given
     * one.
     * 
     * @param direction the direction to compare.
     * 
     * @return the distance between the directions
     */
    double compareTo(Direction direction);

    /**
     * 
     * This method is useful to decide if the target direction is easier to reach going counterclockwise or clockwise 
     * (considering the actual direction).
     * 
     * @param targetDirection the target direction to reach.
     * @return true if going counterclockwise is better than clockwise.
     */
    boolean isTurnCounterCW(Direction targetDirection);

}
