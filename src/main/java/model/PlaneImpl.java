package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import model.exceptions.OperationNotAvailableException;

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
     * The speed to reach when the plane takes off.
     */
    private static final Speed TAKEOFF_SPEED = new SpeedImpl(220.0);
    /**
     * The speed to reach when the plane takes off.
     */
    private static final double TAKEOFF_ALTITUDE = 200;
    /**
     * The maximum distance between the plane and the runway end.
     */
    private static final double MAXIMUM_DISTANCE = 5;

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

    private void checkIfTrueAndThrow(final boolean condition, final String message)
            throws OperationNotAvailableException {
        if (condition) {
            throw new OperationNotAvailableException(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void land(final Airport airport) throws OperationNotAvailableException {
        Objects.requireNonNull(airport);
        this.checkIfTrueAndThrow(airport.getActiveRunways().isEmpty(), "No active runway found.");
        this.checkIfTrueAndThrow(!this.isLandingPossible(), "Speed or altitude of the plane are too high.");
        this.checkIfTrueAndThrow(this.getClosestRunway(airport).isEmpty(), "No active runway found.");
        // I check if the direction is correct. (TODO)

        // I stop the plane
        this.resetAllTargets();
        this.setAltitude(0);
        this.setSpeed(new SpeedImpl(0.0));
        this.setPosition(airport.getParkingPosition());

    }

    /**
     * This method checks if speed and altitude allow the plane to land.
     * 
     * @return the result of the check.
     */
    private boolean isLandingPossible() {
        return (this.getSpeed().getAsKnots() <= SPEED_TO_LAND.getAsKnots() && this.getAltitude() <= ALTITUDE_TO_LAND)
                ? true
                : false;
    }

    /**
     * This method gets the closest runway end to the plane that can be used by it to land.
     * 
     * @param airport the target airport.
     * @return the closest runway end.
     */
    private Optional<RunwayEnd> getClosestRunway(final Airport airport) {
        return airport.getActiveRunways().get().stream().map(runway -> runway.getRunwayStatus().get())
                .filter(runwayEnd -> runwayEnd.getPosition().distanceFrom(this.getPosition()) <= MAXIMUM_DISTANCE)
                .sorted((run1, run2) -> {
                    double diff = run1.getPosition().distanceFrom(this.getPosition())
                            - run2.getPosition().distanceFrom(this.getPosition());
                    if (diff > 0) {
                        return 1;
                    } else if (diff < 0) {
                        return -1;
                    }
                    return 0;
                }).findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeOff(final Airport airport) throws OperationNotAvailableException {
        Objects.requireNonNull(airport);
        this.checkIfTrueAndThrow(airport.getActiveRunways().isEmpty(), "No active runway found.");
        Runway activeRunway = airport.getActiveRunways().get().get(0);
        RunwayEnd startRunwayEnd = activeRunway.getRunwayStatus().get();
        // RunwayEnd targetRunwayEnd = activeRunway.
        // DEVO IMPOSTARE LA DIREZIONE!!!
        this.setPosition(startRunwayEnd.getPosition());
        this.setTargetSpeed(TAKEOFF_SPEED);
        this.setTargetAltitude(TAKEOFF_ALTITUDE);
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
