package view;

import java.util.Set;

import javafx.scene.Parent;
import model.Plane;
import utilities.Pair;
import view.sceneController.SceneController;

/**
 * 
 * An interface that defines the {@link View} of the application.
 */
public interface View {

    /**
     * method that changes the current scene.
     * 
     * @param sceneContext
     */
    void changeScene(Pair<SceneController, Parent> sceneContext);

    /**
     * method that updates position of all {@link Plane}.
     * 
     * @param planes
     */
    void radarUpdate(Set<Plane> planes);

    /**
     * method that returns the {@link SceneFactory}.
     * 
     * @return SceneFactory
     */
    SceneFactory getSceneFactory();

    /**
     * method that creates a window alert with the specified header and text.
     * 
     * @param header
     * @param text
     */
    void windowAlert(String header, String text);
}
