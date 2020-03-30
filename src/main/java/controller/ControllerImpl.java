package controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.Airport;
import model.Direction;
import model.Model;
import model.ModelImpl;
import model.Plane;
import model.Runway;
import model.Speed;
import model.Vor;
import model.exceptions.OperationNotAvailableException;

/**
 * 
 * An implementation of the {@link Controller} interface.
 *
 */
public class ControllerImpl implements Controller {
    private Model model;
    private Plane currentSelectedPlane;
    private RandomizerAgent planeRandomizer;

    public ControllerImpl() {
        this.model = new ModelImpl();
        this.currentSelectedPlane = null;
        this.planeRandomizer = new RandomizerAgent(this.model);
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseThreads() {
        this.planeRandomizer.pauseThread();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSimulationRate(final int rate) {
        this.planeRandomizer.setMultiplier(rate);
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Runway> getAirportRunways() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeRunwayEndStatus(final String runwayEnd) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getRunwayEndStatus(final String runwayEnd) {
        // TODO Auto-generated method stub
        return false;
    }

}
