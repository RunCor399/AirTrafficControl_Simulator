package view.sceneController;

import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Airport;
import model.Plane;
import model.Position2D;
import model.RadarPosition;
import model.Vor;
import model.Direction;
import model.DirectionImpl;
import utilities.Pair;

public class RadarControllerImpl extends AbstractSceneController {

    private static final int TEXT_DIMENSION = 30;
    private static final int VOR_DIM = 15;
    private static final Direction INVERTED_ANGLE = new DirectionImpl(180);
    private static final double EXTENSION_VALUE = 2000;
    private static final double DASHES_VALUE = 8;

    private double xRatio;
    private double yRatio;
    private Airport actualAirport;

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
                getController().setSimulationRate(newIntValue);
            }
        });
        ChangeListener<? super Number> resizeListener = (obs, oldVal, newVal) -> this.loadRadar();
        this.radarPane.widthProperty().addListener(resizeListener);
        this.radarPane.heightProperty().addListener(resizeListener);
        this.radarContext = this.radarCanvas.getGraphicsContext2D();
        this.airportContext = this.airportCanvas.getGraphicsContext2D();
        this.actualAirport = this.getController().getActualAirport();
    }

    /**
     * Method that draws both the airport elements and the actual planes in the
     * radar with the actual dimension.
     */
    private void loadRadar() {
        double parentWidth = this.radarPane.getWidth();
        double parentHeight = this.radarPane.getHeight();
        this.radarCanvas.setWidth(parentWidth);
        this.radarCanvas.setHeight(parentHeight);
        this.airportCanvas.setWidth(parentWidth);
        this.airportCanvas.setHeight(parentHeight);
        this.drawAirport(this.actualAirport);
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
        this.airportContext.setStroke(Color.WHITE);
        this.airportContext.setFill(Color.WHITE);
        for (Vor vor : airport.getVorList().get()) {
            Position2D position = vor.getPosition().getPosition();
            double xPos = this.computeX(position.getX());
            double yPos = this.computeY(position.getY());
            this.airportContext.strokeOval(xPos, yPos, VOR_DIM, VOR_DIM);
            this.airportContext.fillText(vor.getId(), xPos, yPos, TEXT_DIMENSION);
        }
        this.airportContext.setStroke(Color.FORESTGREEN);
        for (model.Runway runway : airport.getRunways().get()) {
            Pair<RadarPosition, RadarPosition> ends = runway.getPosition();
            Position2D first = ends.getX().getPosition();
            Position2D second = ends.getY().getPosition();
            this.drawRunwayExtension(ends);
            this.airportContext.strokeLine(this.computeX(first.getX()), this.computeY(first.getY()),
                    this.computeX(second.getX()), this.computeY(second.getY()));
        }
    }

    private void drawRunwayExtension(final Pair<RadarPosition, RadarPosition> ends) {
        Direction extensionDir = ends.getX().computeDirectionToTargetPosition(ends.getY());
        double xExt1 = this
                .computeX((Math.cos(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getY().getPosition().getX());
        double yExt1 = this
                .computeY((Math.sin(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getY().getPosition().getY());
        extensionDir.sum(INVERTED_ANGLE);
        double xExt2 = this
                .computeX((Math.cos(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getX().getPosition().getX());
        double yExt2 = this
                .computeY((Math.sin(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getX().getPosition().getY());
        this.airportContext.setLineDashes(DASHES_VALUE);
        this.airportContext.strokeLine(xExt1, yExt1, xExt2, yExt2);
        this.airportContext.setLineDashes(0);
    }

    public void drawPlanes(final Set<Plane> planes) {
        // TODO
    }

    @FXML
    protected void goToMenu(final ActionEvent e) {
        this.getController().pauseThreads();
        this.getView().changeScene(this.getView().getSceneFactory().loadMenu());
    }

    @FXML
    protected void pauseTimeWarp(final ActionEvent e) {
        this.btnResume.setDisable(false);
        this.btnPause.setDisable(true);
        this.getController().pauseThreads();
    }

    @FXML
    protected void resumeTimeWarp(final ActionEvent e) {
        this.btnResume.setDisable(true);
        this.btnPause.setDisable(false);
        this.getController().startThreads();
    }
}
