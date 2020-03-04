package model;

import java.util.Objects;

import model.exceptions.RunwayNotAvailableException;

/**
 * 
 * Implementation of a {@link Plane}, which is by definition a {@link DynamicElement}. 
 *
 */
public class PlaneImpl extends AbstractDynamicElement implements Plane {

    private static final Direction DIRECTION_DELTA = new DirectionImpl(1.8);
    private static final Speed SPEED_DELTA = new SpeedImpl(20.0);
    private static final double ALTITUDE_DELTA = 10;

    private final Action planeAction;

    public PlaneImpl(final Action planeAction, final RadarPosition position, final Speed speed, final double altitude,
            final Direction direction) {
        super(position, speed, altitude, direction);
        Objects.requireNonNull(planeAction);
        this.planeAction = planeAction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Direction getDirectionDelta() {
        return DIRECTION_DELTA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Speed getSpeedDelta() {
        return SPEED_DELTA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double getAltitudeDelta() {
        return ALTITUDE_DELTA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void land(/*final Airport airport*/) throws RunwayNotAvailableException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeOff(/*final Airport airport*/) throws RunwayNotAvailableException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getPlaneAction() {
        return this.planeAction;
    }

}
