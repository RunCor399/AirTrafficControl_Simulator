package controller;

import java.util.Objects;
import java.util.Optional;

import model.Airport;
import model.Direction;
import model.Model;
import model.ModelImpl;
import model.Plane;
import model.Speed;
import model.Vor;
import model.exceptions.OperationNotAvailableException;
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
        this.movementAgent = new MovementAgent(this.model, this.view);
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
    @Override
    public Airport getActualAirport() {
        return this.model.getAirport();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopThreads() {
        this.planeRandomizer.stopThread();
        this.movementAgent.stopThread();
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
            this.movementAgent.resumeThread();
        }
    }

}
