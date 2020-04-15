package model;

/**
 * 
 * An interface that models a radar element which is able to move.
 *
 */
public interface DynamicElement extends RadarElement {

    /**
     * Method that returns the actual altitude.
     * 
     * @return the actual altitude.
     */
    double getAltitude();

    /**
     * Method that returns the actual speed.
     * 
     * @return the actual altitude.
     */
    Speed getSpeed();

    /**
     * Method that returns the actual direction.
     * 
     * @return the actual direction.
     */
    Direction getDirection();

    /**
     * This method computes the new position, speed, altitude and direction of the
     * element after a time quantum.
     */
    void computeNewPosition();

}
