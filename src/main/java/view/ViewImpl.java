package view;

import java.util.Set;

import controller.Controller;
import controller.ControllerImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Plane;
import utilities.Pair;
import view.sceneController.RadarController;
import view.sceneController.SceneController;

public class ViewImpl extends Application implements View {
    private static final double MINWIDTH_FACTOR = 1.15;
    private static final double HEIGHT_FACTOR = 1.1;
    private boolean isResized;
    private Stage primaryStage;
    private SceneFactory sceneFactory;
    private Controller controller;
    private SceneController sceneController;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.isResized = false;
        this.primaryStage = primaryStage;
        this.primaryStage.getIcons().add(new Image("images/windowIcon.png"));
        this.primaryStage.setTitle("Simple ATC Simulator");

        this.controller = new ControllerImpl(this);
        this.sceneFactory = new SceneFactoryImpl(controller, this);

        this.changeScene(sceneFactory.loadMenu());
        this.setStageResolution();

        ChangeListener<? super Number> resizeListener = (obs, oldVal, newVal) -> this.computeUnmaximizedScreen();
        this.primaryStage.widthProperty().addListener(resizeListener);

        this.primaryStage.setOnCloseRequest((event) -> this.controller.getAgentManager().stopThreads());
        this.primaryStage.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeScene(final Pair<SceneController, Parent> sceneContext) {
        this.sceneController = sceneContext.getX();
        this.primaryStage
                .setScene(new Scene(sceneContext.getY(), this.primaryStage.getWidth(), this.primaryStage.getHeight()));

        this.setStageResolution();
        this.isResized = false;
    }

    /**
     * Method that computes the actual screen resolution.
     * 
     * @return pair of width and height of the screen
     */
    private Pair<Double, Double> computeScreenResolution() {
        return new Pair<>(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    }

    /**
     * Method that sets the screen resolution.
     * 
     */
    private void setStageResolution() {
        Pair<Double, Double> resolution = this.computeScreenResolution();
        this.primaryStage.setWidth(resolution.getX());
        this.primaryStage.setHeight(resolution.getY());
        this.primaryStage.setMaximized(true);
        this.primaryStage.setMinWidth(this.primaryStage.getWidth() / MINWIDTH_FACTOR);
    }

    /**
     * Method that reduces height of the screen when window is resized for the first
     * time.
     * 
     */
    private void computeUnmaximizedScreen() {
        if (!(this.primaryStage.isMaximized()) && (!this.isResized)) {
            this.isResized = true;
            this.primaryStage.setHeight(this.primaryStage.getHeight() / HEIGHT_FACTOR);
        } 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SceneFactory getSceneFactory() {
        return this.sceneFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void radarUpdate(final Set<Plane> planes) {
        if (this.sceneController instanceof RadarController) {
            ((RadarController) this.sceneController).updatePlanes(planes);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void windowAlert(final String header, final String text) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Simple ATC");
            alert.setHeaderText(header);
            alert.setContentText(text);
            alert.initOwner(this.primaryStage);
            alert.showAndWait();
        });
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
