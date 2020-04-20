package view.sceneController;

import javafx.event.ActionEvent;
import model.Runway;

public interface RunwayController extends SceneController {

    /**
     * Method that initializes the current runway view.
     * 
     * @param runway
     */
    void initializeRunwayLayout(Runway runway);

    /**
     * Method that changes the status of a runwayEnd.
     * 
     * @param event
     */
    void changeRunwayEndStatus(ActionEvent event);

}
