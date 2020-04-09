package model;

import java.util.Optional;

/**
 * 
 * This interface models a {@link DynamicElement} which can receive commands about the {@link Speed}, {@link Direction},
 * altitude and {@link RadarPosition} to reach.
 *
 */
public interface CommandableElement extends DynamicElement {

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
     * Method that sets the target direction. This method removes the target
     * position of the element (if present).
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
     * Method that resets all the targets of the element.
     * 
     */
    void resetAllTargets();

}
