package model;

import java.util.Optional;

import utilities.Pair;

/**
 * 
 * An interface that models a runway in an airport.
 *
 */
public interface Runway {

    /**
     * This method returns the Optional of the active runwayEnd.
     * 
     * @return RunwayEnd
     */
    Optional<RunwayEnd> getRunwayStatus();

    /**
     * This method returns the positions of the 2 ranwayEnds.
     * 
     * @return Pair of 2 runwayEnds positions
     */
    Pair<RadarPosition, RadarPosition> getPosition();

    /**
     * This method sets the positions of the runwayEnds.
     * 
     * @param positions Pair of 2 positions for runwayEnds
     */
    void setPosition(Pair<RadarPosition, RadarPosition> positions);

    /**
     * This method sets the active runwayEnd.
     * 
     * @param numRunwayEnd Number of the runwayEnd
     */
    void setActiveRunwayEnd(String numRunwayEnd);

    /**
     * This method checks if the runwayEnd exists in this runway.
     * 
     * @param numRunwayEnd Number of runway
     * 
     * @return boolean
     */
    boolean checkRunwayEnd(String numRunwayEnd);

    /**
     * Method that returns a pair of runwayEnds.
     * 
     * @return pair of runwayEnds relative to a runway
     */
    Pair<RunwayEnd, RunwayEnd> getRunwayEnds();

}
