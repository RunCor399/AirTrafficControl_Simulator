package view.sceneController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class TutorialController extends AbstractSceneController implements SceneController {

    @FXML
    private Button menuButton;

    @FXML
    private ImageView screenImage;

    @FXML
    public final void inizialize() {
        //TODO
    }

    /**
     * Method that switches into menu Scenery.
     */
    public void switchToMenu() {
        this.getView().changeScene(this.getView().getSceneFactory().loadMenu());
    }
}
