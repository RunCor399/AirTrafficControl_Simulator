package controller;

import model.Airport;
import model.Direction;
import model.Speed;
import utilities.Pair;

/**
 * 
 * This interface models the main controller of the application.
 *
 */
public interface Controller {

    /**
     * method that selects a plane as target.
     * 
     * @param planeId
     */
    void selectTargetPlane(int planeId);

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
     * method that heads a plane to a specific vor retrieving it from id.
     * 
     * @param vorId
     */
    void goToVor(String vorId);

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

    /**
     * method that returns current airport.
     * 
     * @return current airport
     */
    Airport getActualAirport();

    /**
     * Method that stops all the threads of the application.
     * It also initializes them in order to be able to start threads again.
     */
    void stopThreads();

    /**
     * Method that pauses all the threads of the application.
     */
    void pauseThreads();

    /**
     * Method that sets the simulation rate of all the threads of the application.
     * 
     * @param rate the integer value that represents the rate of update of the
     *             threads.
     */
    void setSimulationRate(int rate);

    /**
     * Method that resumes all the threads of the application. If a thread hasn't
     * started yet, it will start.
     */
    void startThreads();

    /**
     * Method that returns the X and Y bounds of the radar. This method is useful to
     * correctly represent the radar.
     * 
     * @return a {@link Pair} containing the X and Y bounds.
     */
    Pair<Double, Double> getRadarDimension();

    /**
     * Method that resets the game deleting all current planes and deactivating
     * every runway.
     */
    void resetGameContext();

}
