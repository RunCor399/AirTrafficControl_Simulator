package view.sceneController;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Direction;
import model.Plane;
import model.Plane.Action;
import model.Speed;

public class StripImpl extends StackPane {
    private static final int FONT_SIZE = 18;
    private static final double PADDING_SIZE = 15;
    private static final double TOP_BORDER = 1.5;
    private static final double LEFT_BORDER = 3;
    private static final int ZERO = 0;
    private static final int NO_VALUE = -1;

    private int width;
    private int height;
    private final Plane plane;
    private final Label speedLabel;
    private final Label altitudeLabel;
    private final Label headingLabel;
    private final Label idLabel;

    public StripImpl(final int width, final int height, final Plane p) {
        this.width = width;
        this.height = height;
        Insets pad = new Insets(PADDING_SIZE);
        Font font1 = new Font("Impact", FONT_SIZE);
        Font font2 = new Font("Comic Sans MS", FONT_SIZE);
        this.idLabel = new Label(p.getCompanyName() + Integer.toString(p.getAirplaneId()));
        this.idLabel.setFont(font1);
        this.idLabel.setPadding(pad);
        this.plane = p;
        this.speedLabel = new Label();
        this.speedLabel.setPadding(pad);
        this.speedLabel.setFont(font2);
        this.altitudeLabel = new Label();
        this.altitudeLabel.setPadding(pad);
        this.altitudeLabel.setFont(font2);
        this.headingLabel = new Label();
        headingLabel.setPadding(pad);
        headingLabel.setFont(font2);

        StackPane.setAlignment(idLabel, Pos.TOP_LEFT);
        StackPane.setAlignment(speedLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(altitudeLabel, Pos.TOP_RIGHT);
        StackPane.setAlignment(headingLabel, Pos.BOTTOM_RIGHT);

        this.getChildren().addAll(idLabel, speedLabel, altitudeLabel, headingLabel);
        this.setPrefWidth(this.width);
        this.setMinHeight(this.height);
        if (p.getPlaneAction().equals(Action.LAND)) {
            this.setStyle("-fx-background-color: #ffc24f;");
        } else {
            this.setStyle("-fx-background-color: #67fffb;");
        }
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(TOP_BORDER, LEFT_BORDER, TOP_BORDER, LEFT_BORDER))));
        this.setInitialValues();
    }

    private void setShownTargetSpeed() {
        Optional<Speed> optTargetSpeed = this.plane.getTargetSpeed();
        if (optTargetSpeed.isPresent()) {
            this.speedLabel.setText(String.valueOf((optTargetSpeed.get().getAsKnots().intValue())) + " kt");
        }
    }

    private void setShownTargetAltitude() {
        Double targetAltitude = this.plane.getTargetAltitude();
        if (targetAltitude != NO_VALUE) {
            this.altitudeLabel.setText(String.valueOf(((int) this.plane.getTargetAltitude())) + " ft");
        }
    }

    private void setShownTargetDirection() {
        Optional<Direction> optTargetDirection = this.plane.getTargetDirection();
        if (optTargetDirection.isPresent()) {
            this.headingLabel.setText(String.valueOf(((int) optTargetDirection.get().getAsDegrees())) + "°");
        }
    }

    /**
     * 
     * Sets the background color of the label.
     */
    public void setSelected() {
        this.idLabel.setStyle("-fx-background-color: #FF0000;");
    }

    /**
     * Resets the background color of the label.
     * 
     */
    public void setNotSelected() {
        this.idLabel.setStyle("-fx-background-color: none;");
    }

    /**
     * 
     * Update speed, altitude and direction values.
     */
    public void updateShownTargets() {
        this.setShownTargetAltitude();
        this.setShownTargetSpeed();
        this.setShownTargetDirection();
    }

    public final Plane getPlane() {
        return this.plane;
    }

    public final int getPlaneId() {
        return this.getPlane().getAirplaneId();
    }

    private void setInitialValues() {
        this.headingLabel.setText(String.valueOf(((int) this.plane.getDirection().getAsDegrees())) + "°");
        this.speedLabel.setText(String.valueOf(this.plane.getSpeed().getAsKnots().intValue()) + " kt");

        if (this.plane.getPlaneAction().equals(Action.TAKEOFF)) {
            this.altitudeLabel.setText(String.valueOf(ZERO) + " ft");
        } else {
            this.altitudeLabel.setText(String.valueOf((int) this.plane.getAltitude()) + " ft");
        }

    }
}
