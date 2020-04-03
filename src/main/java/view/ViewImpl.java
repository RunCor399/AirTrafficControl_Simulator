package view;

import java.util.Set;

import controller.Controller;
import controller.ControllerImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Plane;
import utilities.Pair;
import view.sceneController.RadarController;
import view.sceneController.SceneController;

public class ViewImpl extends Application implements View {
    private Stage primaryStage;
    private SceneFactory sceneFactory;
    private Controller controller;
    private SceneController sceneController;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.controller = new ControllerImpl(this);
        this.sceneFactory = new SceneFactoryImpl(controller, this);

        this.changeScene(sceneFactory.loadMenu());
        this.setStageResolution();
        this.primaryStage.setOnCloseRequest((event) -> this.controller.stopThreads());
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
    }

    private Pair<Double, Double> computeScreenResolution() {
        return new Pair<>(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    }

    private void setStageResolution() {
        Pair<Double, Double> resolution = this.computeScreenResolution();
        this.primaryStage.setWidth(resolution.getX());
        this.primaryStage.setHeight(resolution.getY());
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
            alert.setTitle("");
            alert.setHeaderText(header);
            alert.setContentText(text);
            alert.showAndWait();
        });
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
