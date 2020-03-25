package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import model.Direction;
import model.Model;
import model.ModelImpl;
import model.Plane;
import model.Runway;
import model.Speed;
import model.Vor;
import model.exceptions.OperationNotAvailableException;
import utilities.Pair;

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
    public void goToVor(final Vor targetVor) {
        Objects.requireNonNull(targetVor);
        this.currentSelectedPlane.setTargetPosition(targetVor.getPosition());
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
    public List<Pair<String, Boolean>> getListRunwayEnds() {
        List<Pair<String, Boolean>> list = new LinkedList<>();
        Objects.requireNonNull(this.model.getAirport().getRunways().get());
        for (Runway r : this.model.getAirport().getRunways().get()) {
            list.add(new Pair<>(r.getRunwayEnds().getX().toString(), r.getRunwayEnds().getX().getStatus()));
            list.add(new Pair<>(r.getRunwayEnds().getY().toString(), r.getRunwayEnds().getY().getStatus()));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeStatusRunwayEnd(final String runwayEnd) {
        this.model.getAirport().setActiveRunways(runwayEnd);
    }
}
