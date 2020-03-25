package model;

import java.util.Set;

/**
 * 
 * An interface that defines the model.
 */
public interface Model {

    /**
     * 
     * Get the active airport.
     * 
     * @return airport.
     */
    Airport getAirport();

    /**
     * 
     * Sets the active airport.
     * 
     * @param airport that is active.
     */
    void setAirport(Airport airport);

    /**
     * 
     * Gets a list of all airplanes active in this moment.
     * 
     * @return set.
     */
    Set<Plane> getAllPlanes();

    /**
     * 
     * Sets a list of all active airplanes.
     * 
     * @param planes that are active.
     */
    void setAllPlanes(Set<Plane> planes);

    /**
     * 
     * Gets a Plane by his Id.
     * 
     * @param id of the plane that we have to get.
     * 
     * @return Plane.
     */
    Plane getPlaneById(int id);

    /**
     * 
     * Adds a Plane in the Set.
     * 
     * @param plane to add in the list.
     * 
     */
    void addPlane(Plane plane);

    /**
     * 
     * Remove a Plane by his id.
     * 
     * @param id of the plane to remove.
     * 
     */
    void removePlaneById(int id);

    /**
     * Compute position of all planes.
     */
    void computeAllPlanePositions();


}
