package controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import model.Airport;
import model.AirportImpl;
import model.Direction;
import model.Model;
import model.ModelImpl;
import model.Plane;
import model.Position2DImpl;
import model.RadarPositionImpl;
import model.Runway;
import model.RunwayEnd;
import model.RunwayEndImpl;
import model.RunwayImpl;
import model.Speed;
import model.Vor;
import model.VorImpl;
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

    public ControllerImpl(final View view) {
        this.model = new ModelImpl();
        this.view = view;
        this.currentSelectedPlane = null;
        this.planeRandomizer = new RandomizerAgent(this.model);
        this.movementAgent = new MovementAgent(this.model, this.view, this);
        //DEBUG AIRPORT
        RunwayEnd r1 = new RunwayEndImpl("18L", new RadarPositionImpl(new Position2DImpl(0.0, 0.0)));
        RunwayEnd r2 = new RunwayEndImpl("36L", new RadarPositionImpl(new Position2DImpl(3000.0, 0.0)));
        RunwayEnd r3 = new RunwayEndImpl("18C", new RadarPositionImpl(new Position2DImpl(0.0, 200.0)));
        RunwayEnd r4 = new RunwayEndImpl("36C", new RadarPositionImpl(new Position2DImpl(3000.0, 200.0)));
        RunwayEnd r5 = new RunwayEndImpl("18R", new RadarPositionImpl(new Position2DImpl(0.0, 400.0)));
        RunwayEnd r6 = new RunwayEndImpl("36R", new RadarPositionImpl(new Position2DImpl(3000.0, 400.0)));
        Vor vor = new VorImpl("1", new RadarPositionImpl(new Position2DImpl(0.0, 0.0)));
        Runway run = new RunwayImpl(r1, r2);
        Runway run2 = new RunwayImpl(r3, r4);
        Runway run3 = new RunwayImpl(r5, r6);
        this.model.setAirport(new AirportImpl("1", "airportName", Set.of(vor), Arrays.asList(run, run2, run3),
                new RadarPositionImpl(new Position2DImpl(0.0, 1.0))));
        Plane plane = new RandomPlaneFactoryImpl(30000, 20000).randomLandingPlane();
        this.model.addPlane(plane);
        this.currentSelectedPlane = plane;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectTargetPlane(final int planeId) {
        Objects.requireNonNull(planeId);
        if (this.currentSelectedPlane != null) {
            this.currentSelectedPlane.changeSelect();
        }
        this.currentSelectedPlane = this.model.getPlaneById(planeId);
        this.currentSelectedPlane.changeSelect();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlaneSpeed(final Speed targetSpeed) {
        Objects.requireNonNull(targetSpeed);
        this.currentSelectedPlane.setTargetSpeed(targetSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlaneHeading(final Direction targetDirection) {
        Objects.requireNonNull(targetDirection);
        this.currentSelectedPlane.setTargetDirection(targetDirection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlaneAltitude(final double targetAltitude) {
        Objects.requireNonNull(targetAltitude);
        this.currentSelectedPlane.setTargetAltitude(targetAltitude);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToVor(final String vorId) {
        Objects.requireNonNull(vorId);
        Optional<Vor> vor = this.getActualAirport().getVorById(vorId);
        if (vor.isEmpty()) {
            throw new IllegalStateException();
        }

        this.currentSelectedPlane.setTargetPosition(vor.get().getPosition());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeOff() {
        try {
            this.currentSelectedPlane.takeOff(this.model.getAirport());
        } catch (OperationNotAvailableException e) {
            this.view.windowAlert("OPERATION NOT POSSIBLE", e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void land() {
        try {
            this.currentSelectedPlane.land(this.model.getAirport());
        } catch (OperationNotAvailableException e) {
            this.view.windowAlert("OPERATION NOT POSSIBLE", e.getMessage());
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
