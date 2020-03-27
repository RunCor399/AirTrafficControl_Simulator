package view.sceneController;

import java.util.Set;

import controller.Controller;
import javafx.application.Platform;
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
import model.PlaneImpl;
import model.Position2D;
import model.Position2DImpl;
import model.RadarPosition;
import model.RadarPositionImpl;
import model.Runway;
import model.SpeedImpl;
import model.Vor;
import model.Plane.Action;
import model.Direction;
import model.DirectionImpl;
import utilities.Pair;
import view.View;

public class RadarControllerImpl extends AbstractSceneController {

    private AirportDrawer drawer = new AirportDrawer();

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

    public final void initialize() {
        this.timeWarpSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int oldIntValue = oldValue.intValue();
            int newIntValue = newValue.intValue();
            if (oldIntValue != newIntValue) {
                getController().setSimulationRate(newIntValue);
            }
        });
        ChangeListener<? super Number> resizeListener = (obs, oldVal, newVal) -> this.drawer.loadRadar();
        this.radarPane.widthProperty().addListener(resizeListener);
        this.radarPane.heightProperty().addListener(resizeListener);
        this.radarContext = this.radarCanvas.getGraphicsContext2D();
        this.airportContext = this.airportCanvas.getGraphicsContext2D();
    }

    @Override
    public void setParameters(final Controller controller, final View view) {
        super.setParameters(controller, view);
        // TODO passa i parametri agli altri
    }

    public void updatePlanes(final Set<Plane> planes) {
        Platform.runLater(() -> {
            this.drawer.cachedPlanes = planes;
            this.drawer.drawPlanes();
        });
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

    /**
     * 
     * This private inner class responsibility is to draw both the airport elements
     * and the planes in the radar.
     *
     */
    private class AirportDrawer {

        private static final int TEXT_DIMENSION = 30;
        private static final int VOR_DIM = 15;
        private static final double EXTENSION_VALUE = 2000;
        private static final double DASHES_VALUE = 8;
        private static final double LINE_LENGHT = 20;
        private static final double PLANE_DIM = 8;

        private final Direction flatAngle = new DirectionImpl(180);
        private double xRatio;
        private double yRatio;
        private Airport actualAirport;
        private Set<Plane> cachedPlanes = Set.of(
                new PlaneImpl(324, "ALI", Action.LAND, new RadarPositionImpl(new Position2DImpl(20000.0, 10000.0)),
                        new SpeedImpl(100.0), 200, new DirectionImpl(90)),
                new PlaneImpl(535, "EMU", Action.TAKEOFF, new RadarPositionImpl(new Position2DImpl(100.0, 100.0)),
                        new SpeedImpl(0.0), 0, new DirectionImpl(200)));

        /**
         * Method that computes the ratios used to obtain the coordinates of an object.
         */
        private void computeRatios() {
            // TODO cambia in valore del model
            this.xRatio = radarCanvas.getWidth() / (30000 * 2);
            // Inverted the sign because of how the canvas considers the y axis.
            this.yRatio = -radarCanvas.getHeight() / (20000 * 2);
        }

        /**
         * Method that computes actual x coordinate of an object.
         * 
         * @param initX the x value of the object.
         */
        private double computeX(final double initX) {
            return this.xRatio * initX + radarCanvas.getWidth() / 2;
        }

        /**
         * Method that computes actual y coordinate of an object.
         * 
         * @param initY the y value of the object.
         */
        private double computeY(final double initY) {
            return this.yRatio * initY + radarCanvas.getHeight() / 2;
        }

        /**
         * Removes the planes of the radar.
         */
        private void clearRadar() {
            radarContext.clearRect(0, 0, radarCanvas.getWidth(), radarCanvas.getHeight());
        }

        /**
         * Removes all the drawings related to the airport.
         */
        private void clearAirport() {
            airportContext.clearRect(0, 0, airportCanvas.getWidth(), airportCanvas.getHeight());
        }

        /**
         * Method that draws both the airport elements and the actual planes in the
         * radar with the actual dimension.
         */
        private void loadRadar() {
            this.actualAirport = getController().getActualAirport();
            double parentWidth = radarPane.getWidth();
            double parentHeight = radarPane.getHeight();
            radarCanvas.setWidth(parentWidth);
            radarCanvas.setHeight(parentHeight);
            airportCanvas.setWidth(parentWidth);
            airportCanvas.setHeight(parentHeight);
            this.computeRatios();
            this.drawAirport(this.actualAirport);
            this.drawPlanes();
        }

        /**
         * Method that draws the VORs and the Runways of the airplane.
         * 
         * @param airport the airport to draw.
         */
        private void drawAirport(final Airport airport) {
            this.clearAirport();
            airportContext.setStroke(Color.WHITE);
            airportContext.setFill(Color.WHITE);
            for (Vor vor : airport.getVorList().get()) {
                Position2D position = vor.getPosition().getPosition();
                double xPos = this.computeX(position.getX());
                double yPos = this.computeY(position.getY());
                airportContext.strokeOval(xPos, yPos, VOR_DIM, VOR_DIM);
                airportContext.fillText(vor.getId(), xPos, yPos, TEXT_DIMENSION);
            }
            airportContext.setStroke(Color.FORESTGREEN);
            for (Runway runway : airport.getRunways().get()) {
                Pair<RadarPosition, RadarPosition> ends = runway.getPosition();
                Position2D first = ends.getX().getPosition();
                Position2D second = ends.getY().getPosition();
                this.drawRunwayExtension(ends);
                airportContext.strokeLine(this.computeX(first.getX()), this.computeY(first.getY()),
                        this.computeX(second.getX()), this.computeY(second.getY()));
            }
        }

        private void drawRunwayExtension(final Pair<RadarPosition, RadarPosition> ends) {
            Direction extensionDir = ends.getX().computeDirectionToTargetPosition(ends.getY());
            double xExt1 = this.computeX(
                    (Math.cos(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getY().getPosition().getX());
            double yExt1 = this.computeY(
                    (Math.sin(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getY().getPosition().getY());
            extensionDir.sum(flatAngle);
            double xExt2 = this.computeX(
                    (Math.cos(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getX().getPosition().getX());
            double yExt2 = this.computeY(
                    (Math.sin(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getX().getPosition().getY());
            airportContext.setLineDashes(DASHES_VALUE);
            airportContext.strokeLine(xExt1, yExt1, xExt2, yExt2);
            airportContext.setLineDashes(0);
            System.out.println("FATOO2");
        }

        /**
         * Method that draws all the cached planes.
         */
        private void drawPlanes() {
            this.clearRadar();
            radarContext.setStroke(Color.ORANGE);
            radarContext.setFill(Color.WHITESMOKE);
            for (Plane plane : this.cachedPlanes) {
                Position2D planePosition = plane.getPosition().getPosition();
                double xPosition = this.computeX(planePosition.getX());
                double yPosition = this.computeY(planePosition.getY());
                radarContext.strokeRect(xPosition - (PLANE_DIM / 2), yPosition - (PLANE_DIM / 2), PLANE_DIM, PLANE_DIM);
                this.drawGuideline(xPosition, yPosition, plane.getDirection());
                radarContext.fillText(plane.getCompanyName() + " " + plane.getAirplaneId(), xPosition + LINE_LENGHT,
                        yPosition + LINE_LENGHT);
            }
        }

        private void drawGuideline(final double x, final double y, final Direction direction) {
            double rads = direction.getAsRadians();
            radarContext.strokeLine(x, y, x + (LINE_LENGHT * Math.cos(rads)), y - (LINE_LENGHT * Math.sin(rads)));
        }
    }
}
