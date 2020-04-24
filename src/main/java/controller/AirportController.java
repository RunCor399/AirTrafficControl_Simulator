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
     * method that returns the current {@link Airport}.
     * 
     * @return current {@link Airport}
     */
    Airport getActualAirport();

    /**
     * Method that returns the list of the {@link Model.Runway}.
     * 
     * @return List of {@link Model.Runway}
     */
    Optional<List<Runway>> getAirportRunways();

    /**
     * Method that changes the status of a {@link RunwayEnd}.
     * 
     * @param runwayEnd
     */
    void changeRunwayEndStatus(String runwayEnd);

    /**
     * Method that returns the status of a {@link RunwayEnd}.
     * 
     * @param runwayEnd
     * @return Boolean status of runwayEnd
     */
    boolean getRunwayEndStatus(String runwayEnd);
}
