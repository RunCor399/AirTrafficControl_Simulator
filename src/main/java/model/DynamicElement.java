package model;

import java.util.Optional;

/**
 * 
 * An interface that models a radar element which is able to move.
 *
 */
public interface DynamicElement /* implements RadarElememt */ {

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
    double getDirection();

    /**
     * Method that returns the target altitude.
     * 
     * @return the target altitude.
     */
    Optional<Double> getTargetAltitute();

    /**
     * Method that returns the target speed.
     * 
     * @return the target speed.
     */
    Optional<Speed> getTargetSpeed();

    /**
     * Method that returns the target direction.
     * 
     * @return the target direction.
     */
    Optional<Double> getTargetDirection();

    /**
     * This method computes the new position, speed, altitude and direction of the
     * element after a time quantum.
     */
    void computeNewPosition();

}
