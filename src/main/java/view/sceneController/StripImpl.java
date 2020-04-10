package view.sceneController;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    private int width;
    private int height;
    static final int FONT_SIZE = 18;
    static final double PADDING_SIZE = 15;
    static final double TOP_BORDER = 1.5;
    static final double LEFT_BORDER = 3;
    private int id;
    private final Plane plane;
    private final Label speedLabel;
    private final Label altitudeLabel;
    private final Label headingLabel;
    private final Label companyLabel;

    public StripImpl(final int width, final int height, final Plane p) {
        this.width = width;
        this.height = height;
        this.id = p.getAirplaneId();
        Insets pad = new Insets(PADDING_SIZE);
        Font font1 = new Font("Impact", FONT_SIZE);
        Font font2 = new Font("Comic Sans MS", FONT_SIZE);
        Label idLabel = new Label(Integer.toString(p.getAirplaneId()));
        idLabel.setFont(font1);
        idLabel.setPadding(pad);
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
        this.companyLabel = new Label(p.getCompanyName());
        this.companyLabel.setPadding(pad);
        this.companyLabel.setFont(font1);

        StackPane.setAlignment(idLabel, Pos.TOP_LEFT);
        StackPane.setAlignment(companyLabel, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(speedLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(altitudeLabel, Pos.TOP_RIGHT);
        StackPane.setAlignment(headingLabel, Pos.BOTTOM_RIGHT);

        this.getChildren().addAll(idLabel, speedLabel, altitudeLabel, headingLabel, companyLabel);
        this.setPrefWidth(this.width);
        this.setMinHeight(this.height);
        if (p.getPlaneAction().equals(Action.LAND)) {
            this.setStyle("-fx-background-color: #ffc24f;");
        } else {
            this.setStyle("-fx-background-color: #67fffb;");
        }
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(TOP_BORDER, LEFT_BORDER, TOP_BORDER, LEFT_BORDER))));
        //this.updateShownTargets();
        this.setInitialValues();
    }

    private void setShownTargetSpeed() {
        Optional<Speed> optTargetSpeed = this.plane.getTargetSpeed();
        if (optTargetSpeed.isPresent()) {
            this.speedLabel.setText(Double.toString(optTargetSpeed.get().getAsKnots()));
        }
    }

    private void setShownTargetAltitude() {
        Double targetAltitude = this.plane.getTargetAltitute();
        if (targetAltitude != -1) {
            this.altitudeLabel.setText(Double.toString(this.plane.getTargetAltitute()));
        }
    }

    private void setShownTargetDirection() {
        Optional<Direction> optTargetDirection = this.plane.getTargetDirection();
        if (optTargetDirection.isPresent()) {
            this.headingLabel.setText(Double.toString(optTargetDirection.get().getAsDegrees()));
        }
    }

    /**
     * 
     * Sets the background color of the label.
     */
    public void setSelected() {
        this.companyLabel.setStyle("-fx-background-color: #FF0000;");
    }

    /**
     * Resets the background color of the label.
     * 
     */
    public void setNotSelected() {
        this.companyLabel.setStyle("-fx-background-color: none;");
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
        this.headingLabel.setText(Double.toString(this.plane.getDirection().getAsDegrees()));
        this.speedLabel.setText(Double.toString(this.plane.getSpeed().getAsKnots()));
        this.altitudeLabel.setText(Double.toString(this.plane.getAltitude()));
    }
}
