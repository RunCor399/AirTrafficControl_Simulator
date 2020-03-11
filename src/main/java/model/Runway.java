package model;

import java.util.Optional;

/**
 * 
 * An interface that models runway in airport. 
 *
 */
public interface Runway {

    /**
     * This method returns the active runwayEnd.
     * 
     * @return String of the number of RunwayEnd
     */
    Optional<String> getRunwayStatus();

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
     */
    void checkRunwayEnd(String numRunwayEnd);

}
