package view.sceneController;


import java.io.IOException;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import model.Runway;
import view.View;

public class AirportManagementControllerImpl extends AbstractSceneController {

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameters(final Controller controller, final View view) {
        super.setParameters(controller, view);
        int i = 0;
        for (Runway runway : this.getController().getAirportRunways().get()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/RunwayGUI.fxml"));
                RunwayControllerImpl runwayController = new RunwayControllerImpl();
                runwayController.setParameters(controller, view);
                fxmlLoader.setController(runwayController);
                Parent parent = fxmlLoader.load();
                runwayController.initializeRunwayLayout(runway);
                this.gridPane.add(parent, 0, i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

}
