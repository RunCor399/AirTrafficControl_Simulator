package model;

import java.util.Objects;

import model.exceptions.RunwayNotAvailableException;

/**
 * 
 * Implementation of a {@link Plane}, which is by definition a
 * {@link DynamicElement}.
 *
 */
public class PlaneImpl extends AbstractDynamicElement implements Plane {

    /**
     * The maximum altitude that allows the plane to land.
     */
    private static final double ALTITUDE_TO_LAND = 300;
    /**
     * The maximum speed that allows the plane to land.
     */
    private static final Speed SPEED_TO_LAND = new SpeedImpl(100.0);
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
    public void land(/* final Airport airport */) throws RunwayNotAvailableException {
        // I take an active runway. From this one, i get the active runwayEnd
        RadarPosition runwayEndPosition = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        // I check if it's close enough and the parameters are correct (altitude, speed,
        // direction)
        if (this.getSpeed().getAsKnots() <= SPEED_TO_LAND.getAsKnots() && this.getAltitude() <= ALTITUDE_TO_LAND) {
            // land
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeOff(/* final Airport airport */) throws RunwayNotAvailableException {
        // I take an active runway. From this one, i get the active runwayEnd
        // The other runwayEnd will be the target of the plane.
        RadarPosition runwayEndPosition = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        this.setPosition(runwayEndPosition);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getPlaneAction() {
        return this.planeAction;
    }

}
