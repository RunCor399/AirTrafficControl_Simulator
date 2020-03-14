package model;

import java.io.Serializable;
import java.util.Objects;

import model.exceptions.RunwayNotAvailableException;

/**
 * 
 * Implementation of a {@link Plane}, which is by definition a
 * {@link DynamicElement}.
 *
 */
public class PlaneImpl extends AbstractDynamicElement implements Plane, Serializable {

    private static final long serialVersionUID = 5423657003954572219L;
    /**
     * The maximum altitude that allows the plane to land.
     */
    private static final double ALTITUDE_TO_LAND = 2000;
    /**
     * The maximum speed that allows the plane to land.
     */
    private static final Speed SPEED_TO_LAND = new SpeedImpl(140.0);
    /**
     * The maximum speed that allows the plane to land.
     */
    private static final Speed SPEED = new SpeedImpl(220.0);
    /**
     * The specifics of an airplane.
     */
    private static final Direction DIRECTION_DELTA = new DirectionImpl(1.8);
    private static final Speed SPEED_DELTA = new SpeedImpl(20.0);
    private static final double ALTITUDE_DELTA = 10;

    private final int planeId;
    private final String companyName;
    private final Action planeAction;

    public PlaneImpl(final int planeId, final String companyName, final Action planeAction,
            final RadarPosition position, final Speed speed, final double altitude, final Direction direction) {
        super(position, speed, altitude, direction);
        Objects.requireNonNull(planeAction);
        Objects.requireNonNull(companyName);
        this.planeId = planeId;
        this.companyName = companyName;
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
    public int getAirplaneId() {
        return this.planeId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void land(final Airport airport) throws RunwayNotAvailableException {
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
    public void takeOff(final Airport airport) throws RunwayNotAvailableException {
        // I take an active runway. From this one, i get the active runwayEnd
        // The other runwayEnd will be the target of the plane.
        if (airport.getActiveRunways().isEmpty()) {
            throw new RunwayNotAvailableException();
        }
        Runway activeRunway = airport.getActiveRunways().get().get(0);
        //RadarPosition runwayEndPosition = airport.getActiveRunways().get().get(0).getRunwayStatus().get();
        this.setPosition(activeRunway.getRunwayStatus().get().getPosition());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getPlaneAction() {
        return this.planeAction;
    }

    /**
     * 
     * To string of a {@link Plane}.
     * 
     * @return the final string.
     * 
     */
    public String toString() {
        String result = "Plane id -> " + this.planeId + "\n";
        result += "Company name -> " + this.companyName + "\n";
        result += "Action -> " + this.planeAction + "\n";
        return result + super.toString();
    }

}
