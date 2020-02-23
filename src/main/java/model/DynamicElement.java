package model;

import java.util.Optional;

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
     * Method that returns the target altitude.
     * 
     * @return the target altitude.
     */
    double getTargetAltitute();

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
    Optional<Direction> getTargetDirection();

    /**
     * Method that returns the target position.
     * 
     * @return the target position.
     */
    Optional<RadarPosition> getTargetPosition();

    /**
     * Method that sets the target altitude.
     * 
     * @param targetAltitude the target altitude.
     */
    void setTargetAltitude(double targetAltitude);

    /**
     * Method that sets the target speed.
     * 
     * @param targetSpeed the target speed.
     */
    void setTargetSpeed(Speed targetSpeed);

    /**
     * Method that sets the target direction.
     * 
     * @param targetDirection the target direction.
     */
    void setTargetDirection(Direction targetDirection);

    /**
     * Method that sets the target position.
     * 
     * @param targetPosition the target position.
     */
    void setTargetPosition(RadarPosition targetPosition);

    /**
     * This method computes the new position, speed, altitude and direction of the
     * element after a time quantum.
     */
    void computeNewPosition();

}
