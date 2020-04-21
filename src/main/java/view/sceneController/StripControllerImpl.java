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
    
    /**
     * Is the constructor of the StripControllerImpl that need the width, chosen in perspective of the monitor,
     * and the MovementController.
     * @param width of the strips.
     * @param movementController.
     */
    public StripControllerImpl(final double width, final MovementController movementController) {
        this.width = width;
        this.movementController = movementController;
        this.strips.setPrefSize(this.width, STRIP_HEIGHT);
        this.strips.setPickOnBounds(false);
    }

    /**
     * Method that creates a strip for the received plane.
     * 
     * @param plane the plane that need a strip.
     */

    public final void createStrip(final Plane plane) {
        final StripImpl strip = new StripImpl(100, 100, plane);
        strip.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                getController().getPlaneController().selectTargetPlane(plane.getAirplaneId());
                disableAllStrips();
                strip.setSelected();
                getMovementController().updateValues(plane);
            }
        });
        this.strips.getChildren().add(strip);
    }

    /**
     * Method that disables the strips.
     */
    private void disableAllStrips() {
        for (Node node : this.strips.getChildren()) {
            if (node instanceof StripImpl) {
                ((StripImpl) node).setNotSelected();
            }
        }
    }

    /**
     * Method that updates all the strips.
     * @param planes is the Set with updated planes. 
     */
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

    /**
     * Method that adds all the new strips in the VBox.
     * @param planes that we want to add.
     */
    private void addMissingStrips(final Set<Plane> planes) {
        Set<Plane> containedPlanes = this.strips.getChildren().stream().filter(node -> node instanceof StripImpl)
                .map(strip -> ((StripImpl) strip).getPlane()).collect(Collectors.toSet());
        for (Plane plane : planes) {
            if (!containedPlanes.contains(plane)) {
                this.createStrip(plane);
            }
        }
    }

    /**
     * Method that returns the VBox that contain all the strips.
     * @return strips.
     */
    public final VBox getStrips() {
        return this.strips;
    }

    /**
     * Method that returns the movement controller.
     * @return movementController.
     */
    public MovementController getMovementController() {
        return this.movementController;
    }

}
