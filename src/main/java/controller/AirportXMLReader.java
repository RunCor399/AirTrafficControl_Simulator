package controller;

import java.util.List;

import model.Airport;

/**
 * 
 * This interface models an XML parser capable of getting an {@link Airport} list given a correct xml file.
 * 
 */
public interface AirportXMLReader {

    /**
     * This method reads from the given xml file all the {@link Airport} contained in it.
     * Then, it returns the {@link List} of the found airports.
     * 
     * @return the {@link List} of the found {@link Airport} objects.
     */
    List<Airport> getAirportListFromXML();

}
