package view.sceneController;

import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import model.Plane;

public class StripControllerImpl extends AbstractSceneController {

    private VBox strips = new VBox();
    private double width;
    static final double STRIP_HEIGHT = 200;

    public StripControllerImpl(final double width) {
        this.width = width;
    }

    public final void createStrip(final Plane p) {
        Button selectButton = this.createButton(p.getAirplaneId());
        StripImpl strip = new StripImpl(100, 100, p, selectButton);

        this.strips.getChildren().add(strip);
    }

    public final VBox updateStrip(final Set<Plane> planes) {
        this.strips.getChildren().clear();
        for (Plane p : planes) {
            this.createStrip(p);
        }
        this.strips.setPrefSize(this.width, STRIP_HEIGHT);
        this.strips.setPickOnBounds(false);
        return this.strips;
    }

    /**
     * 
     * @param planeId
     * @return
     */
    private Button createButton(final int planeId) {
        Button selectButton = new Button("Seleziona");
        selectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                getController().selectTargetPlane(planeId);
            }
        });

        return selectButton;
    }

    public final VBox getStrips() {
        return this.strips;
    }

}
