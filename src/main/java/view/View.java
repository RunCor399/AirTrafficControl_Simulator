package view;


import java.util.Set;

import javafx.scene.Parent;
import model.Plane;
import utilities.Pair;
import view.sceneController.SceneController;

/**
 * 
 * An interface that defines the View of the application.
 */
public interface View {

    /**
     * method that changes the current scene.
     * 
     * @param sceneContext
     */
    void changeScene(Pair<SceneController, Parent> sceneContext);

    /**
     * method that updates position of all airplanes.
     * 
     * @param planes
     */
     void radarUpdate(Set<Plane> planes);

    /**
     * method that returns SceneFactory.
     * 
     * @return SceneFactory
     */
    SceneFactory getSceneFactory();
}
