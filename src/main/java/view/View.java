package view;


import javafx.scene.Parent;

/**
 * 
 * An interface that defines the View of the application.
 */
public interface View {

    /**
     * methods that changes the current scene.
     * 
     * @param newScene
     */
    void changeScene(Parent newScene);

    /**
     * method that update position of all airplanes.
     * 
     * @param planes
     */
    //void radarUpdate(List<Plane> planes);

}
