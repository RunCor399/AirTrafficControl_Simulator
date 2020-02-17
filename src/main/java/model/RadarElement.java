package model;

/**
 * 
 *An interface that define any element of the radar.
 */
public interface RadarElement {
    /**
     * Get the position of a radar element.
     * 
     * @return RadarPosition.
     */
    RadarPosition getPosition();
    /**
     * Set a new position of a radar element.
     * 
     * @param position where the radar element has moved.
     */
    void setPosition(RadarPosition position);

}
