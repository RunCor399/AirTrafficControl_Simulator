package view.sceneController;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import model.Airport;
import model.Vor;

public class RadarControllerImpl extends AbstractSceneController {

    private double xRatio;
    private double yRatio;

    @FXML
    private Slider timeWarpSlider;
    @FXML
    private Button btnResume;
    @FXML
    private Button btnPause;
    @FXML
    private Canvas radarCanvas;
    private GraphicsContext radarContext;
    @FXML
    private Canvas airportCanvas;
    private GraphicsContext airportContext;
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
        ChangeListener<? super Number> resizeListener = (obs, oldVal, newVal) -> this.loadRadar();
        this.radarPane.widthProperty().addListener(resizeListener);
        this.radarPane.heightProperty().addListener(resizeListener);
        this.radarContext = this.radarCanvas.getGraphicsContext2D();
        this.airportContext = this.airportCanvas.getGraphicsContext2D();
    }

    public void loadRadar() {
        double parentWidth = this.radarPane.getWidth();
        double parentHeight = this.radarPane.getHeight();
        this.radarCanvas.setWidth(parentWidth);
        this.radarCanvas.setHeight(parentHeight);
        this.airportCanvas.setWidth(parentWidth);
        this.airportCanvas.setHeight(parentHeight);
    }

    /**
     * Method that computes the ratios used to obtain the coordinates of an object.
     */
    private void computeRatios() {
        // TODO cambia in valore del model
        this.xRatio = this.radarCanvas.getWidth() / (30000 * 2);
        // Inverted the sign because of how the canvas considers the y axis.
        this.yRatio = -this.radarCanvas.getHeight() / (20000 * 2);
    }

    /**
     * Method that computes actual x coordinate of an object.
     * 
     * @param initX the x value of the object.
     */
    private double computeX(final double initX) {
        return this.xRatio * initX + this.radarCanvas.getWidth() / 2;
    }

    /**
     * Method that computes actual y coordinate of an object.
     * 
     * @param initY the y value of the object.
     */
    private double computeY(final double initY) {
        return this.yRatio * initY + this.radarCanvas.getHeight() / 2;
    }

    /**
     * Removes the planes of the radar.
     */
    private void clearRadar() {
        this.radarContext.clearRect(0, 0, this.radarCanvas.getWidth(), this.radarCanvas.getHeight());
    }

    /**
     * Method that draws the VORs and the Runways of the airplane.
     * 
     * @param airport the airport to draw.
     */
    private void drawAirport(final Airport airport) {
        for (Vor vor : airport.getVorList().get()) {

        }
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
