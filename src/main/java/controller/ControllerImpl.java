package controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.Airport;
import model.Model;
import model.ModelImpl;
import model.RadarPositionImpl;
import model.Runway;
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

    public ControllerImpl(final View view) {
        this.model = new ModelImpl();
        this.view = view;
        this.selector = new AirportSelectionImpl(this);
        this.agentMgr = new AgentManagerImpl(this.model, this.view, this);
        this.planeController = new PlaneControllerImpl(this.model, this.view);
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
    public void setActualAirport(final Airport airport) {
        Objects.requireNonNull(airport);
        this.model.setAirport(airport);
        this.resetGameContext();
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
    public Airport getActualAirport() {
        return this.model.getAirport();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Double, Double> getRadarDimension() {
        return new Pair<>(RadarPositionImpl.X_BOUND, RadarPositionImpl.Y_BOUND);
    }

    /**
     * {@inheritDoc}
     */
    public Optional<List<Runway>> getAirportRunways() {
        return this.model.getAirport().getRunways();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeRunwayEndStatus(final String runwayEnd) {
        this.model.getAirport().setActiveRunways(runwayEnd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getRunwayEndStatus(final String runwayEnd) {
        for (Runway r : this.model.getAirport().getRunways().get()) {
            if (r.checkRunwayEnd(runwayEnd)) {
                return r.getRunwayEnds().getX().getNumRunwayEnd().equals(runwayEnd)
                        ? r.getRunwayEnds().getX().getStatus()
                        : r.getRunwayEnds().getY().getStatus();
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGameContext() {
        // maybe pause
        this.agentMgr.stopThreads();
        this.model.removeAllPlanes();
        this.getActualAirport().deactivateAllRunways();
    }

}
