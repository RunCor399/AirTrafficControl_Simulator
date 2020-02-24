package model;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractDynamicElement extends AbstractRadarElement
        implements DynamicElement/* , Serializable */ {

    private static final double NO_VALUE = -1;
    private static final double TIME_QUANTUM = 0.5;
    private static final int SEC_TO_HOURS = 3600;

    private Speed speed;
    private double altitude;
    private Direction direction;

    private Speed targetSpeed;
    private double targetAltitude;
    private Direction targetDirection;
    private RadarPosition targetPosition;

    private boolean goUp;
    private boolean goLeft;
    private boolean accelerate;

    private double directionDifference;

    public AbstractDynamicElement(final RadarPosition position, final Speed speed, final double altitude,
            final Direction direction) {
        super(position);
        Objects.requireNonNull(speed);
        Objects.requireNonNull(direction);
        this.isAltitudeValid(altitude);

        this.speed = speed;
        this.altitude = altitude;
        this.direction = direction;

        this.targetSpeed = null;
        this.targetAltitude = NO_VALUE;
        this.targetDirection = null;
        this.targetPosition = null;

        // flags useful to avoid checking where to go every time new position is
        // computed
        this.goUp = false;
        this.goLeft = false;
        this.accelerate = false;

        this.directionDifference = NO_VALUE;
    }

    private void checkArgumentAndThrow(final boolean condition) {
        if (condition) {
            throw new IllegalArgumentException();
        }
    }

    private void isAltitudeValid(final double altitude) {
        this.checkArgumentAndThrow(altitude < 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAltitude() {
        return this.altitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Speed getSpeed() {
        return this.speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTargetAltitute() {
        return this.targetAltitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Speed> getTargetSpeed() {
        return Optional.ofNullable(this.targetSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Direction> getTargetDirection() {
        return Optional.ofNullable(this.targetDirection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<RadarPosition> getTargetPosition() {
        return Optional.ofNullable(this.targetPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargetAltitude(final double targetAltitude) {
        Objects.requireNonNull(altitude);
        this.targetAltitude = targetAltitude;
        this.goUp = (this.targetAltitude - this.altitude > 0) ? true : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargetSpeed(final Speed targetSpeed) {
        Objects.requireNonNull(targetSpeed);
        this.targetSpeed = targetSpeed;
        this.accelerate = (this.targetSpeed.getAsKnots() - this.speed.getAsKnots() > 0) ? true : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargetDirection(final Direction targetDirection) {
        this.setOnlyTargetDirection(targetDirection);
        this.targetPosition = null;
    }

    /**
     * 
     * Method that sets the target direction without removing the target position.
     * 
     * @param targetDirection the target direction.
     */
    private void setOnlyTargetDirection(final Direction targetDirection) {
        Objects.requireNonNull(targetDirection);
        this.targetDirection = targetDirection;
        this.goLeft = this.direction.isTurnCounterCW(this.targetDirection);
        this.directionDifference = this.direction.compareTo(this.targetDirection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargetPosition(final RadarPosition targetPosition) {
        Objects.requireNonNull(targetPosition);
        this.targetPosition = targetPosition;
    }

    /**
     * 
     * Method that returns the direction to follow in order to go towards the target position.
     * 
     * @return the direction to follow.
     */
    private Direction computeDirectionToTargetPosition() {
        final double xRelative = this.targetPosition.getPosition().getX() - this.getPosition().getPosition().getX();
        final double yRelative = this.targetPosition.getPosition().getY() - this.getPosition().getPosition().getY();
        double degrees = Math.toDegrees(Math.atan2(yRelative, xRelative));
        degrees = degrees < 0 ? 360 + degrees : degrees;
        return new DirectionImpl(degrees);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void computeNewPosition() {
        if (this.targetPosition != null) {
            this.setOnlyTargetDirection(this.computeDirectionToTargetPosition());
            System.out.println("Target" + this.targetDirection.toString());
        }
        this.computeActualSpeed();
        this.computeActualDirection();
        this.computeActualAltitude();
        this.getPosition().sumPosition(this.getPositionDelta());
        /* DEBUG !!! */
        System.out.println("Speed :" + this.speed.getAsKnots());
        System.out.println("Altitude :" + this.altitude);
        System.out.println("Direction :" + this.direction.getAsDegrees());
        System.out.println("Position->  X: " + this.getPosition().getPosition().getX() + " Y: "
                + this.getPosition().getPosition().getY());
    }

    /**
     * This private method computes the actual speed based on the target speed and
     * the speed delta.
     */
    private void computeActualSpeed() {
        if (this.targetSpeed != null) {
            double speedDelta = this.getSpeedDelta().getAsKnots();
            if (this.accelerate) {
                if (this.speed.getAsKnots() + speedDelta >= this.targetSpeed.getAsKnots()) {
                    this.speed = this.targetSpeed;
                    this.targetSpeed = null;
                } else {
                    this.speed = new SpeedImpl(this.speed.getAsKnots() + speedDelta);
                }
            } else {
                if (this.speed.getAsKnots() - speedDelta <= this.targetSpeed.getAsKnots()) {
                    this.speed = this.targetSpeed;
                    this.targetSpeed = null;
                } else {
                    this.speed = new SpeedImpl(this.speed.getAsKnots() - speedDelta);
                }
            }
        }
    }

    /**
     * This private method computes the actual altitude based on the target altitude
     * and the altitude delta.
     */
    private void computeActualAltitude() {
        if (this.targetAltitude != NO_VALUE) {
            double altitudeDelta = this.getAltitudeDelta();
            if (this.goUp) {
                if (this.altitude + altitudeDelta >= this.targetAltitude) {
                    this.altitude = this.targetAltitude;
                    this.targetAltitude = NO_VALUE;
                } else {
                    this.altitude = this.altitude + altitudeDelta;
                }
            } else {
                if (this.altitude - altitudeDelta <= this.targetAltitude) {
                    this.altitude = targetAltitude;
                    this.targetAltitude = NO_VALUE;
                } else {
                    this.altitude = this.altitude - altitudeDelta;
                }
            }
        }
    }

    /**
     * This private method computes the actual direction based on the target
     * direction and the direction delta.
     */
    private void computeActualDirection() {
        if (this.targetDirection != null) {
            Direction directionDelta = this.getDirectionDelta();
            if (this.goLeft) {
                this.direction.sum(directionDelta);
            } else {
                this.direction.subtract(directionDelta);
            }
            this.directionDifference = this.directionDifference - directionDelta.getAsDegrees();
            if (this.directionDifference <= 0) {
                this.directionDifference = NO_VALUE;
                this.direction = this.targetDirection;
                this.targetDirection = null;
            }
        }
    }

    protected abstract Direction getDirectionDelta();

    protected abstract Speed getSpeedDelta();

    protected abstract double getAltitudeDelta();

    private Position2D getPositionDelta() {
        double xMovement = (TIME_QUANTUM / SEC_TO_HOURS) * this.speed.getAsKMH()
                * Math.cos(this.direction.getAsRadians());
        double yMovement = (TIME_QUANTUM / SEC_TO_HOURS) * this.speed.getAsKMH()
                * Math.sin(this.direction.getAsRadians());
        return new Position2DImpl(xMovement, yMovement);
    }

}
