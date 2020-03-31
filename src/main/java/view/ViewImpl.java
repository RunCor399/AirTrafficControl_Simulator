package view;

import java.util.Set;

import controller.Controller;
import controller.ControllerImpl;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Plane;
import utilities.Pair;
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
        primaryStage.show();
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
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGame(final String reason) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("HAI PERSO!");
        alert.setContentText(reason);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            this.controller.resetGameContext();
            this.changeScene(sceneFactory.loadMenu());
        }
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
