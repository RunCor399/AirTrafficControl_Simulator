package model;

import java.util.List;
import java.util.Optional;

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
     * this method returns a list of all VOR's.
     * 
     * @return list of all VOR's
     */
    Optional<List<Vor>> getVorList();

    /**
     * this method returns a VOR with id vorId if present.
     * 
     * @param vorId
     * @return VOR with id vorId
     */
    Optional<Vor> getVorById(String vorId);
}
