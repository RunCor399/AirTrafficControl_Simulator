package view.sceneController;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import model.Direction;
import model.DirectionImpl;
import model.Plane;
import model.Speed;
import model.SpeedImpl;
import model.Vor;
import view.View;

public class MovementControllerImpl extends AbstractSceneController implements SceneController {
    private static final int MAX_HEADING = 359;
    private static final int MIN_HEADING = 0;
    private static final int MIN_DELTA = 1;
    private static final int MAX_DELTA = 10;

    private StripControllerImpl stripController;
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
    private ChoiceBox<String> vorChoiceBox;

    @FXML
    private Label headingLabel;

    @FXML
    private Button increaseHeadingButton;

    @FXML
    private Button decreaseHeadingButton;

    @FXML
    private CheckBox turnCheckBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public final void initialize() {

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

        this.speedLabel.setText("210");
        this.altitudeLabel.setText("2000");
        this.headingLabel.setText("0");
    }

    /**
     * This method is used to update the strips based on the set of {@link Plane}
     * given as parameter.
     * 
     * @param planes the updated set of {@link Plane}
     */
    public void updateStrips(final Set<Plane> planes) {
        this.stripController.updateStrip(planes);
        this.scrollPane.setContent(this.stripController.getStrips());
    }

    public void updateValues(final Plane p) {
        this.speedLabel.setText(Double.toString(p.getSpeed().getAsKnots().intValue()));
        this.speedSlider.setValue(p.getSpeed().getAsKnots().intValue());

        this.altitudeLabel.setText(Double.toString(Math.round(p.getAltitude())));
        this.altitudeSlider.setValue(Math.round(p.getAltitude()));
        this.headingLabel.setText(Double.toString(Math.round(p.getDirection().getAsDegrees())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameters(final Controller controller, final View view) {
        super.setParameters(controller, view);
        this.initializeVorList();
        this.stripController = new StripControllerImpl(this.scrollPane.getPrefWidth());
        this.stripController.setParameters(controller, view);
    }

    /**
     * method that initializes vor's choice box with all the vor's of an airport.
     */
    private void initializeVorList() {
        Optional<Set<Vor>> vorSetOpt = getController().getActualAirport().getVorList();

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

    /**
     * method that increases heading label value.
     */
    @FXML
    public void increaseHeading() {
        Integer currentHeading = Integer.valueOf(this.headingLabel.getText());

        if (this.turnCheckBox.isSelected()) {
            if ((currentHeading + MAX_DELTA) > MAX_HEADING) {
                this.headingLabel.setText(
                        String.valueOf(MIN_HEADING + (MAX_DELTA - (MAX_HEADING - currentHeading) - MIN_DELTA)));
            } else {
                this.headingLabel.setText(String.valueOf(currentHeading + MAX_DELTA));
            }
        } else {
            if (currentHeading == MAX_HEADING) {
                this.headingLabel.setText(String.valueOf(MIN_HEADING));
            } else {
                this.headingLabel.setText(String.valueOf(currentHeading + MIN_DELTA));
            }
        }

        this.setCurrentHeading(new DirectionImpl(Double.valueOf(this.headingLabel.getText())));
    }

    /**
     * method that decreases heading label value.
     */
    @FXML
    public void decreaseHeading() {
        Integer currentHeading = Integer.valueOf(this.headingLabel.getText());

        if (this.turnCheckBox.isSelected()) {
            if ((currentHeading - MAX_DELTA) < MIN_HEADING) {
                this.headingLabel.setText(String.valueOf(MAX_HEADING - (MAX_DELTA - currentHeading) + 1));
            } else {
                this.headingLabel.setText(String.valueOf(currentHeading - MAX_DELTA));
            }
        } else {
            if ((currentHeading == MIN_HEADING)) {
                this.headingLabel.setText(String.valueOf(MAX_HEADING));
            } else {
                this.headingLabel.setText(String.valueOf(currentHeading - MIN_DELTA));
            }
        }

        this.setCurrentHeading(new DirectionImpl(Double.valueOf(this.headingLabel.getText())));
    }
}
