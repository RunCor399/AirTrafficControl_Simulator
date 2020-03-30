package view.sceneController;

import java.awt.Label;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import model.Runway;

public class RunwayController extends AbstractSceneController implements SceneController {

    @FXML
    private Label runwayId;

    @FXML
    private Label runwayNum1;

    @FXML
    private Label runwayNum2;

    @FXML
    private CheckBox runwayEnd1;

    @FXML
    private CheckBox runwayEnd2;

    @FXML
    private Pane statusRunway;

    public final void initialize() {
        //TODO
    }

    /**
     * Constructor of the runwayController that initialize the runwayEnd.
     * 
     * @param runway
     */
    public RunwayController(final Runway runway) {
        super();
        //TODO
    }

    /**
     * Method that changes the status of a runwayEnd.
     * 
     * @param runwayEnd
     */
    public void changeRunwayEndStatus(final String runwayEnd) {
        //TODO
    }

    /**
     * Method that updates the status of the runway after changed status.
     */
    private void updateRunwayStatus() {
        //TODO
        runwayEnd1.setSelected(true);
    }
}
