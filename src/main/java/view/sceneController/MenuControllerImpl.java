package view.sceneController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuControllerImpl extends AbstractSceneController implements SceneController {

    @FXML
    private Button gameButton;

    @FXML
    private Button tutorialButton;

    @FXML
    private Button quitButton;

    /**
     * Method that switches into game Scenery.
     */
    public void switchToGameScenery() {
        this.getView().changeScene(this.getView().getSceneFactory().loadGame());
        this.getController().getAgentManager().startThreads();
    }

    /**
     * Method that switches into game Scenery.
     */
    public void switchToAirportSelection() {
        this.getView().changeScene(this.getView().getSceneFactory().loadAirportSelection());
    }

    /**
     * Method that switches into tutorial Scenery.
     */
    public void switchToTutorialScenery() {
        this.getView().changeScene(this.getView().getSceneFactory().loadTutorial());
    }

    /**
     * Method that leaves the game.
     */
    public void quitGame() {
        System.exit(0);
    }
}
