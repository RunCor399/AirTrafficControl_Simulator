package model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import model.exceptions.OperationNotAvailableException;
import utilities.Pair;

/**
 * 
 * Implementation of a {@link Plane}, which is by definition a
 * {@link DynamicElement}.
 *
 */
public class PlaneImpl extends AbstractCommandableElement implements Plane, Serializable {

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
    private static final double TAKEOFF_ALTITUDE = 2000;
    /**
     * The maximum distance between the plane and the runway end.
     */
    private static final double MAXIMUM_DISTANCE = 5000;
    /**
     * The maximum difference between the direction of the plane and the runway
     * direction.
     */
    private static final double MAXIMUM_DIRECTION_DIFF = 5;

    /**
     * The specifics of an airplane.
     */
    private static final Direction DIRECTION_DELTA = new DirectionImpl(1.8);
    private static final Speed SPEED_DELTA = new SpeedImpl(10.0);
    private static final double ALTITUDE_DELTA = 80;

    private final int planeId;
    private final String companyName;
    private final Action planeAction;
    private boolean actionWasPerformed;
    private final Comparator<? super RunwayEnd> sortByDistance = (run1, run2) -> {
        double diff = run1.getPosition().distanceFrom(this.getPosition())
                - run2.getPosition().distanceFrom(this.getPosition());
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        }
        return 0;
    };

    public PlaneImpl(final int planeId, final String companyName, final Action planeAction,
            final RadarPosition position, final Speed speed, final double altitude, final Direction direction) {
        super(position, speed, altitude, direction);
        Objects.requireNonNull(planeAction);
        Objects.requireNonNull(companyName);
        this.planeId = planeId;
        this.companyName = companyName;
        this.planeAction = planeAction;
        this.actionWasPerformed = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Direction getDirectionDelta() {
        return this.canMove() ? DIRECTION_DELTA : new DirectionImpl(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Speed getSpeedDelta() {
        return this.canMove() ? SPEED_DELTA : new SpeedImpl(0.0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double getAltitudeDelta() {
        return this.canMove() ? ALTITUDE_DELTA : 0;
    }

    /**
     * This method determines whether the {@link Plane} can or cannot actually move.
     * 
     * @return true if it can move, false otherwise.
     */
    private boolean canMove() {
        return this.planeAction.equals(Action.TAKEOFF) && !this.actionWasPerformed ? false : true;
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
        this.checkIfTrueAndThrow(this.actionWasPerformed, "The plane has already performed the action.");
        this.checkIfTrueAndThrow(!this.planeAction.equals(Action.LAND), "The plane is not supposed to land.");
        this.checkIfTrueAndThrow(airport.getActiveRunways().isEmpty(), "No active runway found.");
        this.checkIfTrueAndThrow(!this.isLandingPossible(), "Speed or altitude of the plane are too high.");
        this.checkIfTrueAndThrow(this.getClosestRunway(airport).isEmpty(),
                "No nearby active runway found.\nCheck if your direction is compatible with the active runways.");
        //I stop the airplane
        this.resetAllTargets();
        this.setAltitude(0);
        this.setSpeed(new SpeedImpl(0.0));
        this.setPosition(airport.getParkingPosition());
        this.actionWasPerformed = true;
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
     * This method gets the closest runway end that can be used by it to land. The
     * direction of the runway must be at max 5° different from the plane direction,
     * in order to be used to land. Moreover, the plane heading must be at max 5°
     * different from the heading to follow in order to reach the runway end
     * (considering the actual plane position).
     * 
     * @param airport the target airport.
     * @return the closest runway end.
     */
    private Optional<RunwayEnd> getClosestRunway(final Airport airport) {
        return airport.getActiveRunways().get().stream()
                .filter(runway -> runway.getRunwayStatus().get().getPosition().distanceFrom(this.getPosition()) <= MAXIMUM_DISTANCE)
                .filter(runway -> {
                    RunwayEnd active = runway.getRunwayStatus().get();
                    RunwayEnd inactive = runway.getRunwayEnds().getX().getStatus() ? runway.getRunwayEnds().getY()
                            : runway.getRunwayEnds().getX();
                    return active.getPosition().computeDirectionToTargetPosition(inactive.getPosition())
                            .compareTo(this.getDirection()) <= MAXIMUM_DIRECTION_DIFF;
                })
                .filter(runway -> this.directionDiffToRunwayEnd(runway.getRunwayStatus().get()) <= MAXIMUM_DIRECTION_DIFF)
                .map(runway -> runway.getRunwayStatus().get())
                .sorted(this.sortByDistance).findFirst();
    }

    /**
     * This method returns the difference (in degrees) between the actual heading
     * and the heading to follow in order to arrive at the target runway end.
     * 
     * @param runwayEnd the target runway end.
     * @return the difference in terms of degrees.
     */
    private double directionDiffToRunwayEnd(final RunwayEnd runwayEnd) {
        return this.getDirection()
                .compareTo(this.getPosition().computeDirectionToTargetPosition(runwayEnd.getPosition()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeOff(final Airport airport) throws OperationNotAvailableException {
        //I check if there are any runways available.
        Objects.requireNonNull(airport);
        this.checkIfTrueAndThrow(this.actionWasPerformed, "The plane has already performed the action.");
        this.checkIfTrueAndThrow(!this.planeAction.equals(Action.TAKEOFF), "The plane is not supposed to take off.");
        this.checkIfTrueAndThrow(airport.getActiveRunways().isEmpty(), "No active runway found.");
        //I set up the plane in order to let it take off.
        Runway activeRunway = airport.getActiveRunways().get().get(0);
        Pair<RunwayEnd, RunwayEnd> ends = activeRunway.getRunwayEnds();
        RunwayEnd startRunwayEnd = activeRunway.getRunwayStatus().get();
        RunwayEnd targetRunwayEnd = ends.getX().getStatus() ? ends.getY() : ends.getX();
        this.setPosition(startRunwayEnd.getPosition());
        this.setDirection(this.getPosition().computeDirectionToTargetPosition(targetRunwayEnd.getPosition()));
        this.setTargetSpeed(TAKEOFF_SPEED);
        this.setTargetAltitude(TAKEOFF_ALTITUDE);
        this.actionWasPerformed = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getPlaneAction() {
        return this.planeAction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActionPerformed() {
        return this.actionWasPerformed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + planeId;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PlaneImpl other = (PlaneImpl) obj;
        if (this.planeId != other.getAirplaneId()) {
            return false;
        }
        return true;
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
