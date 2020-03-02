package model;

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
    RunwayEnd getRunwayStatus();

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
     * This method returns if a runwayEnd is active.
     * 
     * @param numRunwayEnd Number of the runwayEnd
     * 
     * @return Boolean Active or not active
     */
    boolean isRunwayEndActive(String numRunwayEnd);
}
