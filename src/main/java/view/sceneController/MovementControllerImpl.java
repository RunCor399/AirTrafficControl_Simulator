package view.sceneController;

import java.util.Objects;

import controller.Controller;
import controller.ControllerImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import model.Direction;
import model.Speed;
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
                System.out.println(newValue);
            }
        });

        this.speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> obs, final Number oldValue,
                    final Number newValue) {
                speedLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
            }
        });

        this.altitudeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> obs, final Number oldValue,
                    final Number newValue) {
                altitudeLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
            }
        });

        this.speedLabel.setText("210");
        this.altitudeLabel.setText("7000");
    }

    private final Controller controller = new ControllerImpl();

    /**
     * method that decides which plane is to be set as current.
     * 
     * @param planeId
     */
    public void setTargetAirplane(final int planeId) {
        Objects.requireNonNull(planeId);
        this.controller.selectTargetPlane(planeId);
    }

    /**
     * method that passes to controller the airplane's speed to be set.
     * 
     * @param targetSpeed
     */
    public void setCurrentSpeed(final Speed targetSpeed) {
        Objects.requireNonNull(targetSpeed);
        this.controller.setPlaneSpeed(targetSpeed);
    }

    /**
     * method that passes to controller the airplane's direction to be set.
     * 
     * @param targetDirection
     */
    public void setCurrentHeading(final Direction targetDirection) {
        Objects.requireNonNull(targetDirection);
        this.controller.setPlaneHeading(targetDirection);
    }

    /**
     * method that passes to controller the airplane's altitude to be set.
     * 
     * @param targetAltitude
     */
    public void setCurrentAltitude(final double targetAltitude) {
        Objects.requireNonNull(targetAltitude);
        this.controller.setPlaneAltitude(targetAltitude);
    }

    /**
     * method that directs a plane to a specific vor.
     * 
     * @param targetVor
     */
    public void headToVor(final Vor targetVor) {
        Objects.requireNonNull(targetVor);
        this.controller.goToVor(targetVor);
    }

    /**
     * method that makes a plane takeoff from the airport.
     */
    @FXML
    public void takeoffPressed() {
        this.controller.takeOff();
    }

    /**
     * method that makes a plane land in a specific runway of the airport.
     */
    @FXML
    public void landPressed() {
        this.controller.land();
    }

}
