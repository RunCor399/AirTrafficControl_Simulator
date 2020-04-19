package view.sceneController;

import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Runway;

public class RunwayControllerImpl extends AbstractSceneController implements RunwayController {

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeRunwayLayout(final Runway runway) {
        Objects.nonNull(runway);
        this.runwayNum1.setText(runway.getRunwayEnds().getX().getNumRunwayEnd());
        this.runwayNum2.setText(runway.getRunwayEnds().getY().getNumRunwayEnd());
        this.runwayId.setText(this.runwayNum1.getText() + " - " + this.runwayNum2.getText());
        this.updateRunwayStatus();
    }

    /**
     * {@inheritDoc}
     */
    @FXML
    @Override
    public void changeRunwayEndStatus(final ActionEvent event) {
        this.getController().getAirportController().changeRunwayEndStatus(event.getSource().equals(this.runwayEnd1) ? this.runwayNum1.getText() : this.runwayNum2.getText());
        this.updateRunwayStatus();
    }

    /**
     * Method that updates the status of the runway after changed status.
     */
    private void updateRunwayStatus() {
        this.runwayEnd1.setSelected(this.getController().getAirportController().getRunwayEndStatus(this.runwayNum1.getText()));
        this.runwayEnd2.setSelected(this.getController().getAirportController().getRunwayEndStatus(this.runwayNum2.getText()));
        if (!this.runwayEnd1.isSelected() && !this.runwayEnd2.isSelected()) {
            this.statusRunway.setStyle("-fx-background-color: #FF0000;"); //RED
        } else {
            this.statusRunway.setStyle("-fx-background-color: #2EFE2E;"); //GREEN
        }
    }
}
