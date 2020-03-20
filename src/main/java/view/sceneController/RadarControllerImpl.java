package view.sceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

public class RadarControllerImpl extends AbstractSceneController {

    private GraphicsContext radarContext;
    @FXML
    private Slider timeWarpSlider;
    @FXML
    private Button btnResume;
    @FXML
    private Button btnPause;
    @FXML
    private Canvas radarCanvas;
    @FXML
    private Pane radarPane;

    public void initialize() {
        this.timeWarpSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.setTimeWarpValue((int) newValue);
        });

    }

    public void loadRadar() {
        double parentWidth = this.radarPane.getWidth();
        double parentHeight = this.radarPane.getHeight();
        this.radarCanvas.setWidth(parentWidth);
        this.radarCanvas.setHeight(parentHeight);
        this.radarContext = this.radarCanvas.getGraphicsContext2D();
        this.radarContext.translate(parentWidth / 2, parentHeight / 2);
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
