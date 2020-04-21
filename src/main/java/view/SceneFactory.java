package view;

import javafx.scene.Parent;
import utilities.Pair;
import view.sceneController.SceneController;

/**
 * An interface that define which scenery to load.
 */

public interface SceneFactory {

    /**
     * Loads the fxml of menu scenery.
     * 
     * @return a {@link Pair} containing {@link Parent} and relative {@link SceneController} of menu scenery
     */

    Pair<SceneController, Parent> loadMenu();

    /**
     * Loads the fxml of airport selection scenery.
     * 
     * @return a {@link Pair} containing {@link Parent} and relative {@link SceneController} of menu scenery.
     */

    Pair<SceneController, Parent> loadAirportSelection();

    /**
     * Loads the fxml of the game scenery.
     * 
     * @return a {@link Pair} containing {@link Parent} and relative {@link SceneController} of game scenery. 
     */

    Pair<SceneController, Parent> loadGame();

    /**
     * Loads the fxml of tutorial scenery.
     * 
     * @return a {@link Pair} containing {@link Parent} and relative {@link SceneController} of tutorial scenery.
     */

    Pair<SceneController, Parent> loadTutorial();
}
