package view.sceneController;


import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import view.View;

public class AirportManagementControllerImpl extends AbstractSceneController implements SceneController {

    @FXML
    private GridPane gridPane;

    /**
     * Constructor of a AirportManagementController.
     */
    public AirportManagementControllerImpl() {
        super();
        //TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameters(final Controller controller, final View view) {
        super.setParameters(controller, view);
        //TODO
    }

}
