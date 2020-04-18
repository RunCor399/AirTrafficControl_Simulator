package controller;

import java.util.List;
import java.util.Optional;

import model.Airport;
import model.Runway;

/**
*
* An interface that models a {@link AirportController}.
*
*/
public interface AirportController {
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
}
