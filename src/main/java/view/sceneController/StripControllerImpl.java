package view.sceneController;

import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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
        StripImpl strip = new StripImpl(100, 100, p);
        strip.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent event) {
                getController().selectTargetPlane(strip.getPlaneId());
                System.out.print(strip.getPlaneId());
            }
        });
        strip.setPickOnBounds(true);
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

    public final VBox getStrips() {
        return this.strips;
    }

}
