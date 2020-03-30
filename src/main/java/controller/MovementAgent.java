package controller;

import model.Model;
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
            } catch (InterruptedException exception) {
            }
        }
    }

    private void updatePlanesPositionAndView() {
        this.getModel().computeAllPlanePositions();
        this.view.radarUpdate(this.getModel().getAllPlanes());
    }
}
