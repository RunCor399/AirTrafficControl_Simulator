package controller;

import java.util.Map;

/**
 * 
 * This interface models an {@link Airport} selection manager.
 * It allows to get the map containing all the airports of the game coupled with their id.
 *
 */
public interface AirportSelection {

    /**
     * This method returns the map containing the name of all the airports of the game coupled with their id.
     * 
     * @return the map containing id-name of all airports.
     */
    Map<String, String> getAllAirports();

    /**
     * This method allows to set the actual {@link Airport} of the game by passing the airport id.
     * 
     * @param id the id of the {@link Airport} we want to set.
     */
    void setAirportById(String id);

}
