package model;

import model.exceptions.OperationNotAvailableException;

/**
 * 
 * An interface that models an airplane, which is a specific dynamic element of
 * a radar.
 *
 */
public interface Plane extends DynamicElement {

    /**
     * 
     * {@link Action} indicates what the plane has to do next.
     *
     */
    enum Action {
        LAND, TAKEOFF;
    }

    /**
     * 
     * Getter of the plane's id.
     * 
     * @return the plane's id.
     */
    int getAirplaneId();

    /**
     * 
     * Getter of the plane's company name.
     * 
     * @return the company name.
     */
    String getCompanyName();

    /**
     * 
     * Method that allows the plane to land in the specified Airport.
     * 
     * @param airport the airport in which the plane has to land.
     * 
     * @throws OperationNotAvailableException when there isn't an active available
     *                                     runway in the specified airport.
     */
    void land(Airport airport) throws OperationNotAvailableException;

    /**
     * 
     * Method that allows the plane to take off from the specified airport.
     * 
     * @param airport the airport from where the plane has to take off.
     * 
     * @throws OperationNotAvailableException when there isn't an active available
     *                                     runway in the specified airport.
     */
    void takeOff(Airport airport) throws OperationNotAvailableException;

    /**
     * 
     * Method that returns the specific {@link Action} that the plane has to
     * perform.
     * 
     * @return the action to perform.
     */
    Action getPlaneAction();

    /**
     * 
     * Method that returns whether the {@link Action} of the plane was already performed or not 
     * (i.e. if a {@link Plane} already landed, the method will return true)
     * 
     * @return if the main action of the plane has already been performed.
     */
    boolean isActionPerformed();
}
