package controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.Airport;
import model.Direction;
import model.Model;
import model.ModelImpl;
import model.Plane;
import model.RadarPositionImpl;
import model.Runway;
import model.Speed;
import model.Vor;
import model.exceptions.OperationNotAvailableException;
import utilities.Pair;
import view.View;

/**
 * 
 * An implementation of the {@link Controller} interface.
 *
 */
public class ControllerImpl implements Controller {
    private Model model;
    private View view;
    private Plane currentSelectedPlane;
    private RandomizerAgent planeRandomizer;
    private MovementAgent movementAgent;
    private AirportSelection selector;

    public ControllerImpl(final View view) {
        this.model = new ModelImpl();
        this.view = view;
        this.currentSelectedPlane = null;
        this.planeRandomizer = new RandomizerAgent(this.model);
        this.movementAgent = new MovementAgent(this.model, this.view, this);
        this.selector = new AirportSelectionImpl(this);
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
    public void selectTargetPlane(final int planeId) {
        Objects.requireNonNull(planeId);
        this.currentSelectedPlane = this.model.getPlaneById(planeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlaneSpeed(final Speed targetSpeed) {
        if (this.currentSelectedPlane != null) {
            Objects.requireNonNull(targetSpeed);
            this.currentSelectedPlane.setTargetSpeed(targetSpeed);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlaneHeading(final Direction targetDirection) {
        if (this.currentSelectedPlane != null) {
            Objects.requireNonNull(targetDirection);
            this.currentSelectedPlane.setTargetDirection(targetDirection);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlaneAltitude(final double targetAltitude) {
        if (this.currentSelectedPlane != null) {
            Objects.requireNonNull(targetAltitude);
            this.currentSelectedPlane.setTargetAltitude(targetAltitude);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToVor(final String vorId) {
        if (this.currentSelectedPlane != null) {
            Objects.requireNonNull(vorId);
            Optional<Vor> vor = this.getActualAirport().getVorById(vorId);
            if (vor.isEmpty()) {
                throw new IllegalStateException();
            }
            this.currentSelectedPlane.setTargetPosition(vor.get().getPosition());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeOff() {
        if (this.currentSelectedPlane != null) {
            try {
                this.currentSelectedPlane.takeOff(this.model.getAirport());
            } catch (OperationNotAvailableException e) {
                this.view.windowAlert("OPERATION NOT POSSIBLE", e.getMessage());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void land() {
        if (this.currentSelectedPlane != null) {
            try {
                this.currentSelectedPlane.land(this.model.getAirport());
            } catch (OperationNotAvailableException e) {
                this.view.windowAlert("OPERATION NOT POSSIBLE", e.getMessage());
            }
        }
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
    public void stopThreads() {
        this.planeRandomizer.stopThread();
        this.movementAgent.stopThread();
        this.planeRandomizer = new RandomizerAgent(this.model);
        this.movementAgent = new MovementAgent(this.model, this.view, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseThreads() {
        this.planeRandomizer.pauseThread();
        this.movementAgent.pauseThread();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSimulationRate(final int rate) {
        this.planeRandomizer.setMultiplier(rate);
        this.movementAgent.setMultiplier(rate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startThreads() {
        if (this.planeRandomizer.isAlive()) {
            this.planeRandomizer.resumeThread();
        } else {
            this.planeRandomizer.start();
        }

        if (this.movementAgent.isAlive()) {
            this.movementAgent.resumeThread();
        } else {
            this.movementAgent.start();
        }
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
                return r.getRunwayEnds().getX().getNumRunwayEnd().equals(runwayEnd) ? r.getRunwayEnds().getX().getStatus() : r.getRunwayEnds().getY().getStatus();
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGameContext() {
        //maybe pause
        this.stopThreads();
        this.model.removeAllPlanes();
        this.getActualAirport().deactivateAllRunways();
    }

}
