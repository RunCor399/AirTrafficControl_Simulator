package model;

/**
 * 
 * An interface that models a radar element which is able to move.
 *
 */
public interface DynamicElement extends RadarElement {

    /**
     * Method that returns the actual altitude.
     * 
     * @return the actual altitude.
     */
    double getAltitude();

    /**
     * Method that returns the actual speed.
     * 
     * @return the actual altitude.
     */
    Speed getSpeed();

    /**
     * Method that returns the actual direction.
     * 
     * @return the actual direction.
     */
    Direction getDirection();

    /**
     * 
     * Method to set the altitude of the element.
     * 
     * @param altitude the altitude to set.
     */
    void setAltitude(double altitude);

    /**
     * 
     * Method to set the speed of the element.
     * 
     * @param speed the speed to set.
     */
    void setSpeed(Speed speed);

    /**
     * 
     * Method to set the direction of the element.
     * 
     * @param direction the direction to set.
     */
    void setDirection(Direction direction); 

    /**
     * This method computes the new position, speed, altitude and direction of the
     * element after a time quantum.
     * 
     * @param timeDelta the time quantum (in seconds) that is used to compute the new position of the element.
     */
    void computeNewPosition(double timeDelta);

}
