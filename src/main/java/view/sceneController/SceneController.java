package view.sceneController;

import controller.Controller;

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
     * @param controller the main controller of the application.
     * 
     */
    void setParameters(Controller controller /* , final View view */);

    /**
     * 
     * Controller getter.
     * 
     * @return the main controller of the application.
     */
    Controller getController();

    /**
     * 
     * Controller setter.
     * 
     * @param controller the main controller of the application.
     */
    void setController(Controller controller);

//    /**
//     * 
//     * View getter.
//     * 
//     * @return the main view of the application.
//     */
//    View getView();
//
//    /**
//     * 
//     * View setter.
//     * 
//     * @param view the main view of the application.
//     */
//    void setView(View view);

}
