package view.sceneController;

import java.util.List;
import utilities.Pair;

public class AirportManagementControllerImpl extends AbstractSceneController implements SceneController {

    private List<Pair<String, Boolean>> runwayEndList;

    /**
     * Constructor of a AirportManagementController.
     */
    public AirportManagementControllerImpl() {
        super();
        this.getAllRunwayaEnds();
    }

    /**
     * Method that refreshes the runwayEndList after a change in the airport.
     */
    public void getAllRunwayaEnds() {
        //TODO
    }

    /**
     * Method that active or disable a runwayEnd in the airport.
     * 
     * @param runwayEnd to change
     */
    public void changeRunwayEndStatus(final String runwayEnd) {
        //TODO
    }

    /**
     * Method that returns the status of a RunwayEnd for the FXML.
     * 
     * @param runwayEnd to check
     * @return boolean that represents the status
     */
    public boolean getRunwayendStatus(final String runwayEnd) {
        //TODO
        return false;
    }
}
