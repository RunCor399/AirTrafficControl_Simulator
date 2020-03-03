package model;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * An interface that models an airport.
 */

public interface Airport {
    /**
     * this method return the id of an airport.
     * 
     * @return id of an airport
     */
    String getId();

    /**
     * this method gets the name of an airport.
     * 
     * @return name of an airport
     */
    String getName();

    /**
     * this method adds a new VOR if not already present.
     * 
     * @param newVor
     */
    void addVor(Vor newVor);

    /**
     * this method returns a list of all VOR's if any exists.
     * 
     * @return list of all VOR's
     */
    Optional<List<Vor>> getVorList();

    /**
     * this method returns a VOR with id vorId if any exists.
     * 
     * @param vorId
     * @return VOR with id vorId
     */
    Optional<Vor> getVorById(String vorId);

    /**
     * method that returns a set of all the runways of a specific airport if any
     * exists.
     * 
     * @return runways set of the specific airport
     */
    Optional<Set<Runway>> getRunways();

    /**
     * method that returns the active runways ends of an airport.
     * 
     * @return set of the active runways ends for the specific airport
     */
    Optional<Set<Runway>> getActiveRunways();

    /**
     * method that sets the given runwayEnd relative to his runway active if it is not already.
     * 
     * @param newActiveRunway
     * @param newActiveEnd
     */
    void setActiveRunways(Runway newActiveRunway, RunwayEnd newActiveEnd);

    /**
     * method that adds to an airport a runway.
     * 
     * @param newRunway to be added
     */
    void addRunway(Runway newRunway);

}
