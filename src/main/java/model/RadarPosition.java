package model;

/**
 * 
 * An interface that defines the position of an element in the radar.
 */
public interface RadarPosition {

    /**
     * Gets the position of an object inside the radar.
     * 
     * @return the position of the object inside the radar
     */
    Position2D getPosition();

    /**
     * Sets the position of an object inside the radar.
     * 
     * @param position
     */
    void setPosition(Position2D position);

    /**
     * Updates the position of an object inside the radar.
     * 
     */
    void updatePosition();
}
