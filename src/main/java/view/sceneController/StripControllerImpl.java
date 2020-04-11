package view.sceneController;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Plane;

public class StripControllerImpl extends AbstractSceneController {

    private static final double STRIP_HEIGHT = 200;
    private VBox strips = new VBox();
    private double width;
    private final MovementController movementController;

    public StripControllerImpl(final double width, final MovementController movementController) {
        this.width = width;
        this.movementController = movementController;
        this.strips.setPrefSize(this.width, STRIP_HEIGHT);
        this.strips.setPickOnBounds(false);
    }

    public final void createStrip(final Plane plane) {
        final StripImpl strip = new StripImpl(100, 100, plane);
        strip.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                getController().selectTargetPlane(plane.getAirplaneId());
                disableAllStrips();
                strip.setSelected();
                getMovementController().updateValues(plane);
            }
        });
        this.strips.getChildren().add(strip);
    }

    private void disableAllStrips() {
        for (Node node : this.strips.getChildren()) {
            if (node instanceof StripImpl) {
                ((StripImpl) node).setNotSelected();
            }
        }
    }

    public final void updateStrip(final Set<Plane> planes) {
        Iterator<Node> stripIterator = this.strips.getChildren().stream().filter(node -> node instanceof StripImpl)
                .iterator();
        List<Node> toBeRemoved = new LinkedList<>();
        while (stripIterator.hasNext()) {
            StripImpl strip = (StripImpl) stripIterator.next();
            if (planes.contains(strip.getPlane())) {
                strip.updateShownTargets();
            } else {
                toBeRemoved.add(strip);
            }
        }
        this.strips.getChildren().removeAll(toBeRemoved);
        this.addMissingStrips(planes);
    }

    private void addMissingStrips(final Set<Plane> planes) {
        Set<Plane> containedPlanes = this.strips.getChildren().stream().filter(node -> node instanceof StripImpl)
                .map(strip -> ((StripImpl) strip).getPlane()).collect(Collectors.toSet());
        for (Plane plane : planes) {
            if (!containedPlanes.contains(plane)) {
                this.createStrip(plane);
            }
        }
    }

    public final VBox getStrips() {
        return this.strips;
    }

    /**
     * 
     * @return movementController
     */
    public MovementController getMovementController() {
        return this.movementController;
    }

}
