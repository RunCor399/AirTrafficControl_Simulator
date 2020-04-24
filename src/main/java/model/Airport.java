package model;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * An interface that models an {@link Airport}.
 */

public interface Airport {
    /**
     * method that returns the id of an {@link Airport}.
     * 
     * @return id of an {@link Airport}
     */
    String getId();

    /**
     * method that gets the name of an {@link Airport}.
     * 
     * @return name of an {@link Airport}
     */
    String getName();

    /**
     * method that returns the parking position of an {@link Airport}.
     * 
     * @return parking position
     */
    RadarPosition getParkingPosition();

    /**
     * method that adds a new {@link Model.Vor} if not already present.
     * 
     * @param newVor
     */
    void addVor(Vor newVor);

    /**
     * method that returns a list of all {@link Model.Vor} if any exists.
     * 
     * @return list of all {@link Model.Vor}
     */
    Optional<Set<Vor>> getVorList();

    /**
     * method that returns a {@link Model.Vor} with id vorId if any exists.
     * 
     * @param vorId
     * @return {@link Model.Vor} with id vorId
     */
    Optional<Vor> getVorById(String vorId);

    /**
     * method that returns a list of all the {@link Model.Runway} of a specific {@link Airport} if any
     * exists.
     * 
     * @return {@link Model.Runway} list of the specific {@link Airport}
     */
    Optional<List<Runway>> getRunways();

    /**
     * method that returns the active {@link RunwayEnd} of an {@link Airport}.
     * 
     * @return list of the active {@link RunwayEnd} for the specific {@link Airport}
     */
    Optional<List<Runway>> getActiveRunways();

    /**
     * method that sets the given {@link RunwayEnd} relative to his {@link Model.Runway} active if it is
     * not already.
     * 
     * @param runwayEnd
     */
    void setActiveRunways(String runwayEnd);

    /**
     * method that adds to an {@link Airport} a {@link Model.Runway}.
     * 
     * @param newRunway to be added
     */
    void addRunway(Runway newRunway);

    /**
     * method that deactivates all the {@link Model.Runway} of the airport.
     * 
     */
    void deactivateAllRunways();

}
