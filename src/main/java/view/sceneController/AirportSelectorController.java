package view.sceneController;

import java.util.Map;
import java.util.stream.Collectors;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import view.View;

public class AirportSelectorController extends AbstractSceneController{

    @FXML
    private ChoiceBox<String> selectionBox;
    private Map<String, String> airports;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameters(Controller controller, View view) {
        super.setParameters(controller, view);
        this.airports = getController().getAirportSelector().getAllAirports();
        this.selectionBox.getItems().addAll(this.airports.entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toSet()));
    }

    @FXML
    public void confirmSelection(final ActionEvent event) {
        getController().getAirportSelector().setAirportById(this.selectionBox.getValue());
    }

    @FXML
    public void backToMenu(final ActionEvent event) {
        getView().changeScene(getView().getSceneFactory().loadMenu());
    }
}
