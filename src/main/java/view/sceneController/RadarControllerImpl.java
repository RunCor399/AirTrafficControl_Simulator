package view.sceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

public class RadarControllerImpl extends AbstractSceneController {

    private double xRatio;
    private double yRatio;
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
            int oldIntValue = oldValue.intValue();
            int newIntValue = newValue.intValue();
            if (oldIntValue != newIntValue) {
                this.setTimeWarpValue(newValue.intValue());
            }
        });

    }

    public void loadRadar() {
        double parentWidth = this.radarPane.getWidth();
        double parentHeight = this.radarPane.getHeight();
        this.radarCanvas.setWidth(parentWidth);
        this.radarCanvas.setHeight(parentHeight);
        this.radarContext = this.radarCanvas.getGraphicsContext2D();
        this.radarContext.translate(parentWidth / 2, parentHeight / 2);
        // TODO cambia in valore del model
        this.xRatio = parentWidth / (30000 * 2);
        // Inverted the sign because of how the canvas considers the y axis.
        this.yRatio = -parentHeight / (20000 * 2);
        this.radarContext.fillText("TEST", 30000 * this.xRatio, 30000 * this.yRatio, 15);
    }

    public void drawPlanes(/* final List<Plane> planes */) {
        this.radarContext.restore();
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
