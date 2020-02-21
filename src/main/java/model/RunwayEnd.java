package model;

/**
 * 
 * An interface that define the direction of use of a runway.
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
     * this method return the status of the runwayEnd.
     * 
     * @return boolean Status of runwayEnd
     */
    boolean getStatus();

    /**
     * this method return the number of the runwayEnd.
     * 
     * @return String Number of runwayEnd
     */
    String getNumRunwayEnd();
}
