package model;

import model.exceptions.RunwayNotAvailableException;

/**
 * 
 * An interface that models an airplane, which is a specific dynamic element of
 * a radar.
 *
 */
public interface Plane extends DynamicElement {

    /**
     * 
     * Method that allows the plane to land in the specified Airport.
     * 
     * @throws RunwayNotAvailableException when there isn't an active available runway in the
     *                                     specified airport.
     */
    void land(/* Airport airport */) throws RunwayNotAvailableException;

    /**
     * 
     * Method that allows the plane to take off from the specified airport.
     * 
     * @throws RunwayNotAvailableException when there isn't an active available runway in the
     *                                     specified airport.
     */
    void takeOff(/* Airport airport */) throws RunwayNotAvailableException;
}
