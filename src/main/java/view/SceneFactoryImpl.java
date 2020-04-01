package view;

import java.io.IOException;
import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import utilities.Pair;
import view.sceneController.SceneController;

public class SceneFactoryImpl implements SceneFactory {

    private Controller controller = null;
    private View view = null;

    /**
     * Constructor of the SceneFactoryImpl.
     * @param controller the main controller of the application. 
     * @param view the main view of the application.
     */
    public SceneFactoryImpl(final Controller controller, final View view) {
        this.controller = controller;
        this.view = view;
    }

    /**
     * Return the Parent of the fxml file loaded by getting the scenery chosen.
     * 
     * @param scenery of the type of the fxml file.
     * @return parent of the file fxml loaded.
     * @throws IOException if the fxml file dont exist.
     */

    private Pair<SceneController, Parent> loadScene(final String fileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/" + fileName + ".fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            SceneController sc = fxmlLoader.getController();
            sc.setParameters(this.controller, this.view);
            Pair<SceneController, Parent> p = new Pair<>(sc, parent);
            return p;
        } catch (IOException e) {
            System.out.print("File not found");
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<SceneController, Parent> loadMenu() {
        return loadScene("Menu");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<SceneController, Parent> loadGame() {

        return loadScene("RadarLayout");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<SceneController, Parent> loadTutorial() {
        return loadScene("Tutorial");
    }

}
