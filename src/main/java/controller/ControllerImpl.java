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
        RunwayEnd r1 = new RunwayEndImpl("20", new RadarPositionImpl(new Position2DImpl(0.0, 0.0)));
        RunwayEnd r2 = new RunwayEndImpl("02", new RadarPositionImpl(new Position2DImpl(60.0, 0.0)));
        RunwayEnd r3 = new RunwayEndImpl("20", new RadarPositionImpl(new Position2DImpl(0.0, 60.0)));
        RunwayEnd r4 = new RunwayEndImpl("02", new RadarPositionImpl(new Position2DImpl(200.0, 60.0)));
        Vor vor = new VorImpl("1", new RadarPositionImpl(new Position2DImpl(0.0, 0.0)));
        Runway run = new RunwayImpl(r1, r2);
        Runway run2 = new RunwayImpl(r3, r4);
        this.model.setAirport(new AirportImpl("1", "airportName", Set.of(vor), Arrays.asList(run, run2),
                new RadarPositionImpl(new Position2DImpl(0.0, 1.0))));
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
        //TODO
        //ADD METHOD THAT CHECKS IF THE PLANE IS STILL IN THE SET OF PLANES, HAS TO BE CALLED IN EACH MODIFIER, takeoff and land too
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
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
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
