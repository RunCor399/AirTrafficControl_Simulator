package view.sceneController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuControllerImpl extends AbstractSceneController implements MenuController {

    @FXML
    private Button gameButton;

    @FXML
    private Button tutorialButton;

    @FXML
    private Button quitButton;

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchToGameScenery() {
        this.getView().changeScene(this.getView().getSceneFactory().loadGame());
        this.getController().getAgentManager().startThreads();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchToAirportSelection() {
        this.getView().changeScene(this.getView().getSceneFactory().loadAirportSelection());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchToTutorialScenery() {
        this.getView().changeScene(this.getView().getSceneFactory().loadTutorial());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void quitGame() {
        System.exit(0);
    }
}
