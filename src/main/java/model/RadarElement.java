package model;

/**
 * 
 *An interface that define any element of the radar.
 */
public interface RadarElement {
    /**
     * Gets the position of a radar elements.
     * 
     * @return RadarPosition of an object.
     */
    RadarPosition getPosition();
    /**
     * Sets a new position of a radar elements.
     * 
     * @param position where the radar element has moved.
     */
    void setPosition(RadarPosition position);

}
