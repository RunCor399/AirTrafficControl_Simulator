package view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utilities.Pair;

public class ViewImpl extends Application implements View {
    private Stage primaryStage;
    // private SceneFactory sceneFactory;
    // private Controller controller;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        // create controller and factory
        // view passes controller and view to factory
        // view calls method switchToMenu
        this.setStageResolution();

        // create scene by calling changeScene

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeScene(final Parent newScene) {
        // TODO Auto-generated method stub

    }

    private Pair<Double, Double> computeScreenResolution() {
        return new Pair<>(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    }

    private void setStageResolution() {
        Pair<Double, Double> resolution = this.computeScreenResolution();
        this.primaryStage.setWidth(resolution.getX());
        this.primaryStage.setHeight(resolution.getY());
    }

}
