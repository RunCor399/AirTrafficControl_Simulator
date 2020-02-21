package model;

/**
 * 
 * An interface that define the direction of use of a Runway.
 *
 */
public interface RunwayEnd {

    /**
     * This method change the status of a runwayEnd.
     * 
     * @param isActive Status of runwayEnd
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
}
