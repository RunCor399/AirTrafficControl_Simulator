package view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Plane;

public class StripImpl extends Parent implements Strip {
    private final Plane plane;
    private int width;
    private int height;

    public StripImpl(final int width, final int height, final Plane plane) {
        this.width = width;
        this.height = height;
        this.plane = plane;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initStripElements() {
        Label speed = new Label("speed");
        Button btn = new Button("btn");
        this.getChildren().addAll(speed, btn);
    }

}
