package view.sceneController;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import model.Plane;

public class StripControllerImpl extends AbstractSceneController {

    private VBox strips = new VBox();
    private double width;
    static final double STRIP_HEIGHT = 200;

    public StripControllerImpl(final double width) {
        this.width = width;
        this.strips.setPrefSize(this.width, STRIP_HEIGHT);
        this.strips.setPickOnBounds(false);
    }

    public final void createStrip(final Plane p) {
        Button selectButton = this.createButton(p.getAirplaneId());
        StripImpl strip = new StripImpl(100, 100, p, selectButton);
        this.strips.getChildren().add(strip);
    }

    public final void updateStrip(final Set<Plane> planes) {
        Iterator<Node> stripIterator = this.strips.getChildren().stream()
                .filter(node -> node instanceof StripImpl)
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
