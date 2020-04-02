package view.sceneController;

import java.awt.Label;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    @FXML
    public final void initialize() {

        this.runwayEnd1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, 
                    final Boolean newValue) {
                changeRunwayEndStatus(runwayNum1.getText());
            }
        });

        this.runwayEnd2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, 
                    final Boolean newValue) {
                changeRunwayEndStatus(runwayNum2.getText());
            }
        });
    }

    /**
     * Method that initializes the current runway view.
     * 
     * @param runway
     */
    public void initializeRunwayLayout(final Runway runway) {
        Objects.nonNull(runway);
        this.runwayNum1.setText(runway.getRunwayEnds().getX().getNumRunwayEnd());
        this.runwayNum2.setText(runway.getRunwayEnds().getY().getNumRunwayEnd());
        this.runwayId.setText(this.runwayNum1.getText() + " - " + this.runwayNum2.getText());
        this.updateRunwayStatus();
    }

    /**
     * Method that changes the status of a runwayEnd.
     * 
     * @param runwayEnd
     */
    public void changeRunwayEndStatus(final String runwayEnd) {
        this.getController().changeRunwayEndStatus(runwayEnd);
        this.updateRunwayStatus();
    }

    /**
     * Method that updates the status of the runway after changed status.
     */
    //TODO: use this method only when initializing
    private void updateRunwayStatus() {
        this.runwayEnd1.setSelected(this.getController().getRunwayEndStatus(this.runwayNum1.getText()));
        this.runwayEnd2.setSelected(this.getController().getRunwayEndStatus(this.runwayNum2.getText()));
        if (!this.runwayEnd1.isSelected() && !this.runwayEnd2.isSelected()) {
            this.statusRunway.setStyle("-fx-background-color: #FF0000"); //RED
        }
        this.statusRunway.setStyle("-fx-background-color: #2EFE2E"); //GREEN
    }
}
