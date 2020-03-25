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

/**
 * 
 * An implementation of the {@link Controller} interface.
 *
 */
public class ControllerImpl implements Controller {
    private Model model;
    private Plane currentSelectedPlane;

    public ControllerImpl() {
        this.model = new ModelImpl();
        this.currentSelectedPlane = null;
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

}
