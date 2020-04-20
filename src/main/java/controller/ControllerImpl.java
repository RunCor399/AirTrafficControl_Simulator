package controller;

import model.Model;
import model.ModelImpl;
import model.RadarPositionImpl;
import utilities.Pair;
import view.View;

/**
 * 
 * An implementation of the {@link Controller} interface.
 *
 */
public class ControllerImpl implements Controller {
    private final Model model;
    private final View view;

    private final AirportSelection selector;
    private final AgentManager agentMgr;
    private final PlaneController planeController;
    private final AirportController airportController;

    public ControllerImpl(final View view) {
        this.model = new ModelImpl();
        this.view = view;
        this.selector = new AirportSelectionImpl(this);
        this.agentMgr = new AgentManagerImpl(this.model, this.view, this);
        this.planeController = new PlaneControllerImpl(this.model, this.view);
        this.airportController = new AirportControllerImpl(this.model, this);
        this.selector.setAirportById("BO");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AirportSelection getAirportSelector() {
        return this.selector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AgentManager getAgentManager() {
        return this.agentMgr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlaneController getPlaneController() {
        return this.planeController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AirportController getAirportController() {
        return this.airportController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Double, Double> getRadarDimension() {
        return RadarPositionImpl.getRadarBounds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGameContext() {
        this.agentMgr.stopThreads();
        this.model.removeAllPlanes();
        this.getAirportController().getActualAirport().deactivateAllRunways();
    }

}
