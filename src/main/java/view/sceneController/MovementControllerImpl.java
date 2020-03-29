package view.sceneController;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import model.Direction;
import model.DirectionImpl;
import model.Speed;
import model.SpeedImpl;
import model.Vor;

public class MovementControllerImpl extends AbstractSceneController implements SceneController {

    @FXML
    private Slider speedSlider;

    @FXML
    private Slider altitudeSlider;

    @FXML
    private Button takeoffButton;

    @FXML
    private Button landButton;

    @FXML
    private Label speedLabel;

    @FXML
    private Label altitudeLabel;

    @FXML
    private Spinner<Double> directionSpinner;

    @FXML
    private ChoiceBox<String> vorChoiceBox;

    @FXML
    public final void initialize() {

        this.directionSpinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> obs, final Number oldValue,
                    final Number newValue) {
                setCurrentHeading(new DirectionImpl(newValue.doubleValue()));
            }
        });

        this.speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> obs, final Number oldValue,
                    final Number newValue) {
                speedLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
                setCurrentSpeed(new SpeedImpl((double) newValue.intValue()));
            }
        });

        this.altitudeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> obs, final Number oldValue,
                    final Number newValue) {
                altitudeLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
                setCurrentAltitude((double) newValue.intValue());
            }
        });

        this.vorChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> observable, final Number oldValue,
                    final Number newValue) {
                if (!vorChoiceBox.getItems().get((int) newValue).equals("none")) {
                    headToVor(vorChoiceBox.getItems().get((Integer) newValue));
                }
            }
        });

        this.initializeVorList();
        this.speedLabel.setText("210");
        this.altitudeLabel.setText("7000");
    }

    /**
     * method that initializes vor's choice box with all the vor's of an airport.
     */
    private void initializeVorList() {
<<<<<<< HEAD
        Optional<Set<Vor>> vorListOpt = getController().getActualAirport().getVorList();
=======
        Optional<Set<Vor>> vorSetOpt = getController().getActualAirport().getVorList();
>>>>>>> 67e5b9435889ce1bf60e0dc28d694fceebf377fa
        this.vorChoiceBox.getItems().add("none");

        if (vorSetOpt.isPresent()) {
            for (Vor elem : vorSetOpt.get()) {
                this.vorChoiceBox.getItems().add(elem.getId());
            }
        }
    }

    /**
     * method that passes planeId of the plane to be selected.
     * 
     * @param planeId
     */
    @FXML
    public void setTargetAirplane(final int planeId) {
        Objects.requireNonNull(planeId);
        this.getController().selectTargetPlane(planeId);
    }

    /**
     * method that passes to controller the airplane's speed to be set.
     * 
     * @param targetSpeed
     */
    private void setCurrentSpeed(final Speed targetSpeed) {
        Objects.requireNonNull(targetSpeed);
        this.getController().setPlaneSpeed(targetSpeed);
    }

    /**
     * method that passes to controller the airplane's direction to be set.
     * 
     * @param targetDirection
     */
    private void setCurrentHeading(final Direction targetDirection) {
        Objects.requireNonNull(targetDirection);
        this.getController().setPlaneHeading(targetDirection);
    }

    /**
     * method that passes to controller the airplane's altitude to be set.
     * 
     * @param targetAltitude
     */
    private void setCurrentAltitude(final double targetAltitude) {
        Objects.requireNonNull(targetAltitude);
        this.getController().setPlaneAltitude(targetAltitude);
    }

    /**
     * method that passes to controller the vor to which the plane will be directed.
     * 
     * @param vorId
     */
    private void headToVor(final String vorId) {
        Objects.requireNonNull(vorId);
        this.getController().goToVor(vorId);
    }

    /**
     * method that makes a plane takeoff from the airport.
     */
    @FXML
    public void takeoffPressed() {
        this.getController().takeOff();
    }

    /**
     * method that makes a plane land in a specific runway of the airport.
     */
    @FXML
    public void landPressed() {
        this.getController().land();
    }

}
