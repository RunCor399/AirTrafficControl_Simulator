package view.sceneController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class TutorialControllerImpl extends AbstractSceneController implements TutorialController {

    @FXML
    private Button menuButton;

    @FXML
    private ImageView screenImage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchToMenu() {
        this.getView().changeScene(this.getView().getSceneFactory().loadMenu());
    }
}
