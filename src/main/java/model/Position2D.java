package model;

/**
 * An Interface that define a position of an object in a bidimensional space.
 */

public interface Position2D {

    /**
     * This method return the coordinate X of an object.
     * 
     * @return double
     */
    Double getX();

    /**
     * This method return the coordinate Y of an object.
     * 
     * @return double
     */
    Double getY();

    /**
     * This method adds to the current x coordinate a value.
     * 
     * @param xOffset
     */
    void addX(Double xOffset);

    /**
     * This method add to the current y coordinate a value.
     * 
     * @param yOffset
     */
    void addY(Double yOffset);
}
