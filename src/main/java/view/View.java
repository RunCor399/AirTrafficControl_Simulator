package view;


/**
 * 
 * An interface that defines the View of the application.
 */
public interface View {

    /**
     * methods that changes the current scene.
     * 
     *
     */
    void changeScene(/* Pair<SceneController, Parent> sceneContext */);

    /**
     * method that update position of all airplanes.
     * 
     * @param planes
     */
    // void radarUpdate(List<Plane> planes);

}
