package controller;

import java.util.Objects;
import java.util.Optional;

import model.Direction;
import model.Model;
import model.Plane;
import model.Speed;
import model.Vor;
import model.exceptions.OperationNotAvailableException;
import view.View;

public class PlaneControllerImpl implements PlaneController {
    private final Model model;
    private final View view;
    private Plane currentSelectedPlane;

    public PlaneControllerImpl(final Model model, final View view) {
        this.model = model;
        this.view = view;
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
            Optional<Vor> vor = this.model.getAirport().getVorById(vorId);
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
}
