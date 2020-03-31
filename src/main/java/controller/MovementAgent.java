package controller;

import java.util.Iterator;

import model.Model;
import model.Plane;
import view.View;

public class MovementAgent extends AbstractAgent {
    private final View view;

    public MovementAgent(final Model model, final View view) {
        super(model);
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        while (!this.isStopped()) {
            try {
                synchronized (this) {
                    if (this.isPaused()) {
                        this.wait();
                    }
                }
                sleep(DELTA_TIME / this.getMultiplier());
                this.updatePlanesPositionAndView();
                this.updateUnderControlPlanes();
            } catch (InterruptedException exception) {
            }
        }
    }

    /**
     * Method that computes an update position of every plane in the model and asks
     * view to render all the planes in teir new position.
     * 
     */
    private void updatePlanesPositionAndView() {
        this.getModel().computeAllPlanePositions();
        this.view.radarUpdate(this.getModel().getAllPlanes());
    }

    /**
     * Method that creates an iterator over all the planes and calls
     * removeOutboundPlanes and removeInboundPlanes passing to each the current
     * plane.
     * 
     */
    private void updateUnderControlPlanes() {
        Iterator<Plane> planeIt = this.getModel().getAllPlanes().iterator();

        while (planeIt.hasNext()) {
            Plane currentPlane = planeIt.next();
            this.removeOutboundPlanes(currentPlane);
            this.removeInboundPlanes(currentPlane);
            this.checkNotLandedPlanes(currentPlane);
        }
    }

    /**
     * Method that removes planes that have taken-off and are out of radar sight.
     * 
     * @param plane
     */
    private void removeOutboundPlanes(final Plane plane) {
        if (plane.getPlaneAction().equals(Plane.Action.TAKEOFF)) {
            if ((!plane.getPosition().isWithinRadar()) && (plane.isActionPerformed())) {
                // Removes plane that has taken-off and is out of radar
                this.getModel().removePlaneById(plane.getAirplaneId());
            }
        }
    }

    /**
     * Method that removes planes that have landed in the current airport.
     * 
     * @param plane
     */
    private void removeInboundPlanes(final Plane plane) {
        if ((plane.getPlaneAction().equals(Plane.Action.LAND)) && (plane.isActionPerformed())) {
            this.getModel().removePlaneById(plane.getAirplaneId());
        }
    }

    /**
     * Methods that checks if a plane that has two land was sent outside of radar
     * boundaries.
     * In that case user loses and is redirected to Main Menu.
     * 
     * @param plane
     */
    private void checkNotLandedPlanes(final Plane plane) {
        if ((plane.getPlaneAction().equals(Plane.Action.LAND)) && (!plane.getPosition().isWithinRadar())) {
            this.view.resetGame("Un aereo che doveva atterrare è finito fuori dai limiti del radar");
        }
    }
}
