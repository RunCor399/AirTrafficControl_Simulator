package view.sceneController;

import java.util.Map;
import java.util.stream.Collectors;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import view.View;

public class AirportSelectorController extends AbstractSceneController {

    @FXML
    private ChoiceBox<String> selectionBox;
    @FXML
    private Label lblLog;
    private Map<String, String> airports;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameters(final Controller controller, final View view) {
        super.setParameters(controller, view);
        this.airports = getController().getAirportSelector().getAllAirports();
        this.selectionBox.getItems().addAll(this.airports.entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toSet()));
    }

    /**
     * This method gets from the map of {@link Airport} the id of the one specified by the name passed as parameter.
     * 
     * @param name the name of the {@link Airport}.
     * @return the {@link Airport} id.
     */
    private String getAirportId(final String name) {
        return this.airports.entrySet().stream()
                .filter(entry -> entry.getValue().equals(name))
                .map(entry -> entry.getKey())
                .findFirst()
                .get();
    }

    @FXML
    public final void confirmSelection(final ActionEvent event) {
        getController().getAirportSelector().setAirportById(this.getAirportId(this.selectionBox.getValue()));
        this.lblLog.setText("The selected airport (" + this.selectionBox.getValue() + ") was succesfully loaded.");
    }

    @FXML
    public final void backToMenu(final ActionEvent event) {
        getView().changeScene(getView().getSceneFactory().loadMenu());
    }
}
