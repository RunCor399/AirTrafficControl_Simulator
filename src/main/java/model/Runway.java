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
     * NEW!!!!!! method that returns pair of runway ends of the current runway.
     * 
     * @return pair of runway ends
     */
    Pair<RunwayEnd, RunwayEnd> getRunwayEnds();

    /**
     * This method sets the positions of the runwayEnds.
     * 
     * @param positions Pair of 2 positions for runwayEnds
     */
    void setPosition(Pair<RadarPosition, RadarPosition> positions);

    /**
     * method that checks if any of the runway ends is active.
     * 
     * @return true if a runway end is active, false otherwise
     */
    boolean isAnyEndActive();

    /**
     * method that changes the active runway end.
     */
    void changeActiveRunwayEnd();
}
