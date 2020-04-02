package model;

/**
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
  //  void setPosition(Position2D position);

    /**
     * Updates the position of an object inside the radar.
     * 
     * @param positionOffset
     */
    void sumPosition(Position2D positionOffset);

    /**
     * Controls if a plane is inside the radar bounds.
     * 
     * @return true if a plane is inside the radar bounds.
     */
    boolean isWithinRadar();

    /**
     * 
     * Method that returns the direction to follow in order to go towards the target
     * position.
     * 
     * @param targetPosition the target position.
     * 
     * @return the direction to follow.
     */
    Direction computeDirectionToTargetPosition(RadarPosition targetPosition);

    /**
     * 
     * Method that returns the distance between this point and the given one.
     * 
     * @param position the given position.
     * 
     * @return the distance between the two points.
     */
    double distanceFrom(RadarPosition position);
}
