package view.sceneController;

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
import model.Plane;
import model.Plane.Action;

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

    public StripImpl(final int width, final int height, final Plane p, final Button selectButton) {
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
        Label companyLabel = new Label(p.getCompanyName());
        companyLabel.setPadding(pad);
        companyLabel.setFont(font1);
        if (p.isSelected()) {
            companyLabel.setStyle("-fx-background-color: #FF0000;");
        }

        selectButton.setPadding(pad);

        StackPane.setAlignment(idLabel, Pos.TOP_LEFT);
        StackPane.setAlignment(companyLabel, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(speedLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(altitudeLabel, Pos.TOP_RIGHT);
        StackPane.setAlignment(headingLabel, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(selectButton, Pos.BOTTOM_CENTER);

        this.getChildren().addAll(idLabel, speedLabel, altitudeLabel, headingLabel, companyLabel, selectButton);
        this.setPrefWidth(this.width);
        this.setMinHeight(this.height);
        if (p.getPlaneAction().equals(Action.LAND)) {
            this.setStyle("-fx-background-color: #ffc24f;");
        } else {
            this.setStyle("-fx-background-color: #67fffb;");
        }
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(TOP_BORDER, LEFT_BORDER, TOP_BORDER, LEFT_BORDER))));
        this.updateShownTargets();
    }

    private void setShownTargetSpeed() {
        String targetSpeed = this.plane.getTargetSpeed().isPresent()
                ? this.plane.getTargetSpeed().get().getAsKnots().toString()
                : "None";
        this.speedLabel.setText(targetSpeed);
    }

    private void setShownTargetAltitude() {
        this.altitudeLabel.setText(Double.toString(this.plane.getTargetAltitute()));
    }

    private void setShownTargetDirection() {
        String targetDirection = this.plane.getTargetDirection().isPresent()
                ? Double.toString(this.plane.getTargetDirection().get().getAsDegrees())
                : "None";
        this.headingLabel.setText(targetDirection);
    }

    public void updateShownTargets() {
        this.setShownTargetAltitude();
        this.setShownTargetSpeed();
        this.setShownTargetDirection();
    }

    public final int getPlaneId() {
        return this.id;
    }
}
