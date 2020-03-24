package controller;

import model.Plane;
import model.RadarPosition;

/**
 * 
 * Interface that models a factory of random {@link Plane} objects.
 *
 */
public interface RandomPlaneFactory {

    /**
     * Method that creates a new {@link Plane} with random attributes.
     * The generated plane will be in a border of the radar.
     * Its Action will be Land.
     * 
     * @return the new random {@link Plane}
     */
    Plane randomLandingPlane();

    /**
     * Method that creates a new {@link Plane} with random attributes.
     * The generated plane will be still in the {@link RadarPosition} passed as parameter.
     * Its Action will be Take Off
     * 
     * @param planePosition the position of the new {@link Plane}
     * @return the new random {@link Plane}
     */
    Plane randomStillPlane(RadarPosition planePosition);

}
