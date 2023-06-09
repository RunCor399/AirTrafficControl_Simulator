package model;

/**
 * 
 * An interface that defines the direction of use of a Runway.
 *
 */
public interface RunwayEnd extends RadarElement {

    /**
     * This method changes the status of a runwayEnd.
     * 
     * @param isActive
     */
    void changeStatus(boolean isActive);

    /**
     * This method returns the status of the runwayEnd.
     * 
     * @return boolean Status of runwayEnd
     */
    boolean getStatus();

    /**
     * This method returns the number of the runwayEnd.
     * 
     * @return String Number of runwayEnd
     */
    String getNumRunwayEnd();

    /**
     * Method that returns runwayEnd heading.
     * 
     * @return runwayEnd heading
     */
    Direction getRunwayEndHeading();
}
