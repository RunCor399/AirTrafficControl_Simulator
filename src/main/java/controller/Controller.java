package controller;

import model.Direction;
import model.Speed;
import model.Vor;

/**
 * 
 * This interface models the main controller of the application.
 *
 */
public interface Controller {

    /**
     * method that selects a plane as target.
     * 
     * 
     */
    void selectTargetPlane(/* Plane targetPlane */);

    /**
     * method that sets a speed of an airplane.
     * 
     * @param targetSpeed
     */
    void setPlaneSpeed(Speed targetSpeed);

    /**
     * method that sets the heading of a plane.
     * 
     * @param targetDirection
     */
    void setPlaneHeading(Direction targetDirection);

    /**
     * method that sets the altitude of a plane.
     * 
     * @param targetAltitude
     */
    void setPlaneAltitude(double targetAltitude);

    /**
     * method that heads a plane to a specific vor.
     * 
     * @param targetVor
     */
    void goToVor(Vor targetVor);

    /**
     * method that allows the selected plane to takeoff.
     * 
     */
    void takeOff();

    /**
     * 
     * method that allows the selected plane to land.
     */
    void land();
}
