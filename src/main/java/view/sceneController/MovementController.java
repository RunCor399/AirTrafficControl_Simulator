package view.sceneController;

import java.util.Set;

import model.Plane;

public interface MovementController extends SceneController {

    /**
     * This method is used to update the strips based on the set of {@link Plane}
     * given as parameter.
     * 
     * @param planes the updated set of {@link Plane}
     */
    void updateStrips(Set<Plane> planes);

    /**
     * Method to dinamically update gui values relative to the current selected
     * strip.
     * 
     * @param plane
     */
    void updateValues(Plane plane);

    /**
     * method that passes planeId of the plane to be selected.
     * 
     * @param planeId
     */
    void setTargetAirplane(int planeId);
}
