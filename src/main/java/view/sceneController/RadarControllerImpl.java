package view.sceneController;

import java.util.Set;
import java.util.stream.Collectors;

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
import javafx.scene.text.Font;
import model.Airport;
import model.Plane;
import model.Position2D;
import model.RadarPosition;
import model.Runway;
import model.Vor;
import model.Direction;
import model.DirectionImpl;
import utilities.Pair;
import view.View;

public class RadarControllerImpl extends AbstractSceneController implements RadarController {

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
    @FXML
    private SceneController airportGUIController;
    @FXML
    private SceneController movementGUIController;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameters(final Controller controller, final View view) {
        super.setParameters(controller, view);
        this.airportGUIController.setParameters(controller, view);
        this.movementGUIController.setParameters(controller, view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePlanes(final Set<Plane> planes) {
        Set<Plane> drawablePlanes = planes.stream()
                .filter(plane -> plane.getPlaneAction().equals(Plane.Action.LAND)
                        || (plane.getPlaneAction().equals(Plane.Action.TAKEOFF) && plane.isActionPerformed()))
                .collect(Collectors.toSet());
        Platform.runLater(() -> {
            this.drawer.cachedPlanes = drawablePlanes;
            this.drawer.drawPlanes();
        });
    }

    @FXML
    protected final void goToMenu(final ActionEvent e) {
        this.getController().setSimulationRate(1);
        this.getController().pauseThreads();
        this.getView().changeScene(this.getView().getSceneFactory().loadMenu());
    }

    @FXML
    protected final void pauseTimeWarp(final ActionEvent e) {
        this.btnResume.setDisable(false);
        this.btnPause.setDisable(true);
        this.getController().pauseThreads();
    }

    @FXML
    protected final void resumeTimeWarp(final ActionEvent e) {
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

        private static final int COORD_DIM = 15;
        private static final int AIRPORT_NAME_DIM = 22;
        private static final int AIRPORT_NAME_POS = 40;
        private static final int VOR_DIM = 12;
        private static final double EXTENSION_VALUE = 3000;
        private static final double DASHES_VALUE = 8;
        private static final double LINE_LENGHT = 20;
        private static final double PLANE_DIM = 8;

        private final Direction flatAngle = new DirectionImpl(180);
        private double xRatio;
        private double yRatio;
        private Airport actualAirport;
        private Set<Plane> cachedPlanes = Set.of();
        private Pair<Double, Double> radarDimension;

        /**
         * Method that computes the ratios used to obtain the coordinates of an object.
         */
        private void computeRatios() {
            if (this.radarDimension == null) {
                this.radarDimension = getController().getRadarDimension();
            }
            this.xRatio = radarCanvas.getWidth() / (this.radarDimension.getX() * 2);
            // Inverted the sign because of how the canvas considers the y axis.
            this.yRatio = -radarCanvas.getHeight() / (this.radarDimension.getY() * 2);
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
         * Method that draws the VORs and the runways of the airplane.
         * 
         * @param airport the airport to draw.
         */
        private void drawAirport(final Airport airport) {
            this.clearAirport();
            this.drawAirportName(airport.getName());
            airportContext.setStroke(Color.FORESTGREEN);
            for (Runway runway : airport.getRunways().get()) {
                this.drawRunwayExtension(runway);
                Pair<RadarPosition, RadarPosition> ends = runway.getPosition();
                Position2D first = ends.getX().getPosition();
                Position2D second = ends.getY().getPosition();
                airportContext.setLineWidth(2);
                airportContext.strokeLine(this.computeX(first.getX()), this.computeY(first.getY()),
                        this.computeX(second.getX()), this.computeY(second.getY()));
            }
            if (airport.getVorList().isPresent()) {
                airportContext.setFill(Color.WHITE);
                airportContext.setFont(new Font(VOR_DIM));
                for (Vor vor : airport.getVorList().get()) {
                    Position2D position = vor.getPosition().getPosition();
                    double xPos = this.computeX(position.getX());
                    double yPos = this.computeY(position.getY());
                    airportContext.fillOval(xPos - VOR_DIM / 2, yPos - VOR_DIM / 2, VOR_DIM, VOR_DIM);
                    airportContext.fillText(vor.getId(), xPos + VOR_DIM, yPos + VOR_DIM);
                }
            }
            this.drawCoordinates();
        }

        private void drawAirportName(final String airportName) {
            airportContext.setFont(new Font(AIRPORT_NAME_DIM));
            airportContext.fillText(airportName, AIRPORT_NAME_POS, AIRPORT_NAME_POS);
        }

        /**
         * Method that draws a dashed line that extends the specified runway (to make it more visible).
         */
        private void drawRunwayExtension(final Runway runway) {
            Pair<RadarPosition, RadarPosition> ends = runway.getPosition();
            Direction extensionDir = ends.getY().computeDirectionToTargetPosition(ends.getX());
            airportContext.setLineWidth(1);
            double xExt1 = this.computeX((Math.cos(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getX().getPosition().getX());
            double yExt1 = this.computeY((Math.sin(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getX().getPosition().getY());
            extensionDir.sum(flatAngle);
            double xExt2 = this.computeX((Math.cos(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getY().getPosition().getX());
            double yExt2 = this.computeY((Math.sin(extensionDir.getAsRadians()) * EXTENSION_VALUE) + ends.getY().getPosition().getY());
            airportContext.setLineDashes(DASHES_VALUE);
            airportContext.strokeLine(xExt1, yExt1, xExt2, yExt2);
            airportContext.setFont(new Font(10));
            airportContext.fillText(runway.getRunwayEnds().getX().getNumRunwayEnd(), xExt1, yExt1);
            airportContext.fillText(runway.getRunwayEnds().getY().getNumRunwayEnd(), xExt2, yExt2);
            airportContext.setLineDashes(0);
        }

        /**
         * This method draws the coordinates in the radar in order to make it easier to orientate.
         */
        private void drawCoordinates() {
            airportContext.setFill(Color.WHITE);
            airportContext.setFont(new Font(COORD_DIM));
            airportContext.fillText("90", radarPane.getWidth() / 2, COORD_DIM);
            airportContext.fillText("180", COORD_DIM, radarPane.getHeight() / 2);
            airportContext.fillText("270", radarPane.getWidth() / 2, radarPane.getHeight() - COORD_DIM);
            airportContext.fillText("0", radarPane.getWidth() - COORD_DIM, radarPane.getHeight() / 2);
        }

        /**
         * Method that draws all the cached planes.
         */
        private void drawPlanes() {
            this.clearRadar();
            radarContext.setFill(Color.WHITESMOKE);
            for (Plane plane : this.cachedPlanes) {
                radarContext.setStroke(plane.getPlaneAction().equals(Plane.Action.TAKEOFF) ? Color.CYAN : Color.ORANGE);
                Position2D planePosition = plane.getPosition().getPosition();
                double xPosition = this.computeX(planePosition.getX());
                double yPosition = this.computeY(planePosition.getY());
                radarContext.strokeRect(xPosition - (PLANE_DIM / 2), yPosition - (PLANE_DIM / 2), PLANE_DIM, PLANE_DIM);
                this.drawGuideline(xPosition, yPosition, plane.getDirection());
                radarContext.fillText(plane.getCompanyName() + " " + plane.getAirplaneId() + "  " 
                        + (int) plane.getDirection().getAsDegrees()  + "°\n" + plane.getSpeed().getAsKnots().intValue()
                        + " kt " + (int) plane.getAltitude() + " ft", xPosition + LINE_LENGHT, yPosition + LINE_LENGHT);
            }
        }

        /**
         * Method that draws a line that indicates the direction of a specific element.
         */
        private void drawGuideline(final double x, final double y, final Direction direction) {
            double rads = direction.getAsRadians();
            radarContext.strokeLine(x, y, x + (LINE_LENGHT * Math.cos(rads)), y - (LINE_LENGHT * Math.sin(rads)));
        }
    }
}
