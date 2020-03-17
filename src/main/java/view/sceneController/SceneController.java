package view.sceneController;

/**
 * 
 * This interface models a controlled attached to a specific scene.
 *
 */
public interface SceneController {

    /**
     * Method that gives the controller both the instances of the controller and the
     * view of the application; this enables the scene controller to interact with
     * them.
     * 
     */
    void setParameters(/* final Controller controller, final View view */);

}
