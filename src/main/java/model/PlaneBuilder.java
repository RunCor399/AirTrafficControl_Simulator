package model;

import model.Plane.Action;

public interface PlaneBuilder {

    /**
     * 
     * Method that sets the {@link RadarPosition} of the plane.
     * 
     * @param position the position we want to give to the plane.
     * @return the PlaneBuilder object (this).
     */
    PlaneBuilder position(RadarPosition position);

    /**
     * 
     * Method that sets the {@link Action} of the plane.
     * 
     * @param action the action we want to give to the plane.
     * @return the PlaneBuilder object (this).
     */
    PlaneBuilder planeAction(Action action);

    /**
     * 
     * Method that sets the {@link Speed} of the plane.
     * 
     * @param speed the speed of the plane.
     * @return the PlaneBuilder object (this).
     */
    PlaneBuilder speed(Speed speed);

    /**
     * 
     * Method that sets the altitude of the plane.
     * 
     * @param altitude the altitude of the new plane.
     * @return the PlaneBuilder object (this).
     */
    PlaneBuilder altitude(double altitude);

    /**
     * 
     * Method that sets the {@link Direction} of the plane.
     * 
     * @param direction the direction of the plane.
     * @return the PlaneBuilder object (this).
     */
    PlaneBuilder direction(Direction direction);

    /**
     * 
     * Method that creates the wanted plane.
     * 
     * @return the new plane.
     */
    Plane build();

}
