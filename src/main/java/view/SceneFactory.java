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
     * @return Parent of menu scenery
     */

    Pair<SceneController, Parent> loadMenu();

    /**
     * Loads the fxml of the game scenery.
     * 
     * @return Parent of game scenery. 
     */

    Pair<SceneController, Parent> loadGame();

    /**
     * Loads the fxml of tutorial scenery.
     * 
     * @return Parent of tutorial scenery.
     */

    Pair<SceneController, Parent> loadTutorial();

}
