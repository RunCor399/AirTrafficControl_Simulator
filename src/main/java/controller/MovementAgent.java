package controller;

import javafx.application.Platform;
import model.Model;
import model.Plane;
import view.View;

/**
 * An implementation of {@link MovementAgent}. 
 *
 */

public class MovementAgent extends AbstractAgent {
    private final View view;
    private final Controller controller;

    /**
     * Constructor of the movement agent.
     * 
     * @param model
     * 
     * @param view
     * 
     * @param controller
     */
    public MovementAgent(final Model model, final View view, final Controller controller) {
        super(model);
        this.view = view;
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeAgentAction() {
        this.updatePlanesPositionAndView();
        this.updateUnderControlPlanes();
    }

    /**
     * Method that computes an update {@link RadarPosition} of every {@link Plane}
     * in the {@link Model} and asks {@link View} to render all the planes in their
     * new {@link RadarPosition}.
     * 
     */
    private void updatePlanesPositionAndView() {
        this.getModel().computeAllPlanePositions();
        this.view.radarUpdate(this.getModel().getAllPlanes());
    }

    /**
     * Method that calls for each {@link Plane} methods to remove outbound and
     * inbound planes.
     * 
     */
    private void updateUnderControlPlanes() {
        this.getModel().getAllPlanes().stream().peek(x -> this.removeOutboundPlanes(x))
                .peek(x -> this.removeInboundPlanes(x)).forEach(x -> this.checkNotLandedPlanes(x));
    }

    /**
     * Method that removes {@link Plane} that have taken-off and are out of radar
     * sight.
     * 
     * @param plane
     */
    private void removeOutboundPlanes(final Plane plane) {
        if (plane.getPlaneAction().equals(Plane.Action.TAKEOFF)) {
            if ((!plane.getPosition().isWithinRadar()) && (plane.isActionPerformed())) {
                this.getModel().removePlaneById(plane.getAirplaneId());
            }
        }
    }

    /**
     * Method that removes {@link Plane} that have landed in the current
     * {@link Airport}.
     * 
     * @param plane
     */
    private void removeInboundPlanes(final Plane plane) {
        if ((plane.getPlaneAction().equals(Plane.Action.LAND)) && (plane.isActionPerformed())) {
            this.getModel().removePlaneById(plane.getAirplaneId());
        }
    }

    /**
     * Methods that checks if a {@link Plane} that has to land was sent outside of
     * radar boundaries. In that case user loses and is redirected to Main Menu.
     * 
     * @param plane
     */
    private void checkNotLandedPlanes(final Plane plane) {
        if ((plane.getPlaneAction().equals(Plane.Action.LAND)) && (!plane.getPosition().isWithinRadar())) {
            this.controller.resetGameContext();
            this.view.windowAlert("Hai perso", "Un aereo che doveva atterrare è finito fuori dai limiti del radar");
            Platform.runLater(() -> this.view.changeScene(this.view.getSceneFactory().loadMenu()));
        }
    }
}
