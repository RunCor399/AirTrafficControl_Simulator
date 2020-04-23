package model;


/**
 * An interface that defines the position of an element in the radar.
 */
public interface RadarPosition {

    /**
     * Method that gets the {@link Position2D} of an object inside the radar.
     * 
     * @return the {@link Position2D} of the object inside the radar
     */
    Position2D getPosition();

    /**
     * Updates the of an object inside the radar.
     * 
     * @param positionOffset
     * @return the new {@link RadarPosition}
     */
    RadarPosition sumPosition(Position2D positionOffset);

    /**
     * Controls if a {@link Plane} is inside the radar bounds.
     * 
     * @return true if a {@link Plane} is inside the radar bounds.
     */
    boolean isWithinRadar();

    /**
     * 
     * Method that returns the {@link Direction} to follow in order to go towards the target
     * {@link RadarPosition}.
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

    /**
     * Method that sets the {@link Position2D} of an object.
     * 
     * @param position
     */
    void setPosition(Position2D position);
}
