package controller;

import model.Direction;
import model.Speed;

/**
 * 
 * An interface that models a {@link PlaneControllerImpl}. 
 * 
 *
 */
public interface PlaneController {

    /**
     * method that selects a {@link Plane} as target.
     * 
     * @param planeId
     */
    void selectTargetPlane(int planeId);

    /**
     * method that sets the speed of a {@link Plane}.
     * 
     * @param targetSpeed
     */
    void setPlaneSpeed(Speed targetSpeed);

    /**
     * method that sets the heading of a {@link Plane}.
     * 
     * @param targetDirection
     */
    void setPlaneHeading(Direction targetDirection);

    /**
     * method that sets the altitude of a {@link Plane}.
     * 
     * @param targetAltitude
     */
    void setPlaneAltitude(double targetAltitude);

    /**
     * method that heads a {@link Plane} to a specific {@link Model.Vor} retrieving it from it's id.
     * 
     * @param vorId
     */
    void goToVor(String vorId);

    /**
     * method that allows the selected {@link Plane} to takeoff.
     * 
     */
    void takeOff();

    /**
     * 
     * method that allows the selected {@link Plane} to land.
     */
    void land();
}
