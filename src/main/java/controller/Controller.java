package controller;

import java.util.List;
import java.util.Optional;

import model.Runway;
import utilities.Pair;
import model.Airport;

/**
 * 
 * This interface models the main controller of the application.
 *
 */
public interface Controller {

    /**
     * This method allows the switch to a specific{@link Airport}.
     * 
     * @param airport the airport we want to set.
     */
    void setActualAirport(Airport airport);

    /**
     * method that returns current airport.
     * 
     * @return current airport
     */
    Airport getActualAirport();

    /**
     * Method that returns the X and Y bounds of the radar. This method is useful to
     * correctly represent the radar.
     * 
     * @return a {@link Pair} containing the X and Y bounds.
     */
    Pair<Double, Double> getRadarDimension();

    /**
     * Method that returns the list of the runways.
     * 
     * @return List of runways
     */
    Optional<List<Runway>> getAirportRunways();

    /**
     * Method that changes the status of a ruwnayEnd.
     * 
     * @param runwayEnd
     */
    void changeRunwayEndStatus(String runwayEnd);

    /**
     * Method that returns the status of a runwayEnd.
     * 
     * @param runwayEnd
     * @return Boolean status of runwayEnd
     */
    boolean getRunwayEndStatus(String runwayEnd);

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

}
