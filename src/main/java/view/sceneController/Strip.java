package view.sceneController;

import model.Plane;

/**
 * 
 * Interface that defines a Strip.
 */

public interface Strip {

    /**
     * 
     * Sets the background color of the label.
     */
    void setSelected();

    /**
     * 
     * Resets the background color of the label.
     */
    void setNotSelected();

    /**
     * 
     * Update speed, altitude and direction values.
     */
    void updateShownTargets();

    /**
     * Method that returns the plane associated with this strip.
     * 
     * @return plane relative to this strip
     */
    Plane getPlane();

    /**
     * Method that returns the id of the plane associated with this strip.
     * 
     * @return plane id
     */
    int getPlaneId();

}
