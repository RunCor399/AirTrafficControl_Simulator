package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class RadarControllerImpl /*extends AbstractSceneController*/{

    @FXML
    private Slider timeWarpSlider;
    @FXML
    private Button btnResume;
    @FXML
    private Button btnPause;

    public void initialize() {
        this.timeWarpSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.setTimeWarpValue((int) newValue);
        });

    }

    private void setTimeWarpValue(final int value) {
        // calls the controller to set the value.
    }

    private void pauseTimeThread() {
        // TODO
    }

    @FXML
    protected void goToMenu(final ActionEvent e) {
        // pause the time warp, change scene
        this.pauseTimeThread();
    }

    @FXML
    protected void pauseTimeWarp(final ActionEvent e) {
        this.btnResume.setDisable(false);
        this.btnPause.setDisable(true);
        this.pauseTimeThread();
    }

    @FXML
    protected void resumeTimeWarp(final ActionEvent e) {
        this.btnResume.setDisable(true);
        this.btnPause.setDisable(false);
        // pause the time warp
    }
}
