package controller;

import utilities.Pair;

/**
 * 
 * This interface models the main controller of the application.
 *
 */
public interface Controller {

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

    /**
     * Method that returns the {@link AirportSelectionImpl}, which is used to manage
     * the actual {@link Airport} in the model.
     * 
     * @return the {@link AirportSelectionImpl} of the application.
     */
    AirportSelection getAirportSelector();

    /**
     * Method that returns the {@link AgentManager}, which is used to manage all the
     * agents of the application.
     * 
     * @return the {@link AgentManager} of the application.
     */
    AgentManager getAgentManager();

    /**
     * Method that returns a {@link PlaneController}, which is used to set the
     * parameters of the current selected {@link Plane}.
     * 
     * @return {@link PlaneControllerImpl} of the application.
     */
    PlaneController getPlaneController();

    /**
     * Method that returns a {@link AirportController}, which is used to modify the
     * status of an {@link Airport}.
     * 
     * @return {@link AirportControllerImpl} of the application.
     */
    AirportController getAirportController();

}
