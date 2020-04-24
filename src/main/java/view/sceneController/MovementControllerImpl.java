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

public class MovementControllerImpl extends AbstractSceneController implements MovementController {
    private static final int MAX_HEADING = 359;
    private static final int ZERO = 0;
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
            }
        });

        this.altitudeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> obs, final Number oldValue,
                    final Number newValue) {
                altitudeLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStrips(final Set<Plane> planes) {
        this.stripController.updateStrip(planes);
        this.scrollPane.setContent(this.stripController.getStrips());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateValues(final Plane plane) {
        this.speedLabel.setText(String.valueOf(plane.getSpeed().getAsKnots().intValue()));
        this.speedSlider.setValue(plane.getSpeed().getAsKnots().intValue());

        this.headingLabel.setText(String.valueOf((int) plane.getDirection().getAsDegrees()));

        this.altitudeLabel.setText(String.valueOf((int) plane.getAltitude()));
        this.altitudeSlider.setValue((int) plane.getAltitude());

        this.vorChoiceBox.setValue("none");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameters(final Controller controller, final View view) {
        super.setParameters(controller, view);
        this.initializeVorList();
        this.stripController = new StripControllerImpl(this.scrollPane.getPrefWidth(), this);
        this.stripController.setParameters(controller, view);
    }

    /**
     * method that initializes {@link Model.Vor} choice box with all the vor's of an airport.
     */
    private void initializeVorList() {
        Optional<Set<Vor>> vorSetOpt = getController().getAirportController().getActualAirport().getVorList();
        this.vorChoiceBox.getItems().add("none");

        if (vorSetOpt.isPresent()) {
            vorSetOpt.get().stream().forEach(x -> {
                this.vorChoiceBox.getItems().add(x.getId());
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargetAirplane(final int planeId) {
        Objects.requireNonNull(planeId);
        this.getController().getPlaneController().selectTargetPlane(planeId);
    }

    /**
     * Method that calls the {@link Controller} in order to set a new {@link Speed} value for the
     * current selected {@link Plane}.
     * 
     */
    @FXML
    private void setSpeedValue() {
        Speed targetSpeed = new SpeedImpl(this.speedSlider.getValue());
        this.getController().getPlaneController().setPlaneSpeed(targetSpeed);
    }

    /**
     * Method that calls the {@link Controller} in order to set a new Altitude value for the
     * current selected {@link Plane}.
     * 
     */
    @FXML
    private void setAltitudeValue() {
        double targetAltitude = this.altitudeSlider.getValue();
        this.getController().getPlaneController().setPlaneAltitude(targetAltitude);
    }

    /**
     * method that passes to the {@link Controller} the airplane's direction to be set.
     * 
     * @param targetDirection
     */
    private void setCurrentHeading(final Direction targetDirection) {
        Objects.requireNonNull(targetDirection);
        this.getController().getPlaneController().setPlaneHeading(targetDirection);
    }

    /**
     * method that passes to {@link Controller} the {@link Model.Vor} to which the
     * {@link Plane} will be directed.
     * 
     * @param vorId
     */
    private void headToVor(final String vorId) {
        Objects.requireNonNull(vorId);
        this.getController().getPlaneController().goToVor(vorId);
    }

    /**
     * method that makes a {@link Plane} takeoff from the {@link Airport}.
     */
    @FXML
    public void takeoffPressed() {
        this.getController().getPlaneController().takeOff();
    }

    /**
     * method that makes a {@link Plane} land in a specific {@link Model.Runway} of
     * the {@link Airport}.
     */
    @FXML
    public void landPressed() {
        this.getController().getPlaneController().land();
    }

    /**
     * method that increases heading label value.
     */
    @FXML
    public void increaseHeading() {
        Integer currentHeading = Integer.valueOf(this.headingLabel.getText());

        if (this.turnCheckBox.isSelected()) {
            if ((currentHeading + MAX_DELTA) > MAX_HEADING) {
                this.headingLabel
                        .setText(String.valueOf(ZERO + (MAX_DELTA - (MAX_HEADING - currentHeading) - MIN_DELTA)));
            } else {
                this.headingLabel.setText(String.valueOf(currentHeading + MAX_DELTA));
            }
        } else {
            if (currentHeading == MAX_HEADING) {
                this.headingLabel.setText(String.valueOf(ZERO));
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
            if ((currentHeading - MAX_DELTA) < ZERO) {
                this.headingLabel.setText(String.valueOf(MAX_HEADING - (MAX_DELTA - currentHeading) + 1));
            } else {
                this.headingLabel.setText(String.valueOf(currentHeading - MAX_DELTA));
            }
        } else {
            if ((currentHeading == ZERO)) {
                this.headingLabel.setText(String.valueOf(MAX_HEADING));
            } else {
                this.headingLabel.setText(String.valueOf(currentHeading - MIN_DELTA));
            }
        }

        this.setCurrentHeading(new DirectionImpl(Double.valueOf(this.headingLabel.getText())));
    }
}
