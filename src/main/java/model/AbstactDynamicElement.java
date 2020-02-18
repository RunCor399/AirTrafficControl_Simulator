package model;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstactDynamicElement implements DynamicElement/* extends AbstractRadarElement */ {

    private static final double NO_VALUE = -1;

    private Speed speed;
    private double altitude;
    private Direction direction;

    private Speed targetSpeed;
    private double targetAltitude;
    private Direction targetDirection;

    private boolean goUp;
    private boolean goLeft;
    private boolean accelerate;

    public AbstactDynamicElement(final Speed speed, final double altitude, final Direction direction) {
        Objects.requireNonNull(speed);
        Objects.requireNonNull(altitude);
        Objects.requireNonNull(direction);
        this.isAltitudeValid(altitude);

        this.speed = speed;
        this.altitude = altitude;
        this.direction = direction;

        this.targetSpeed = null;
        this.targetAltitude = NO_VALUE;
        this.targetDirection = null;

        // flags useful to avoid checking where to go every time new position is
        // computed
        this.goUp = false;
        this.goLeft = false;
        this.accelerate = false;
    }

    private void checkArgumentAndThrow(final boolean condition) {
        if (condition) {
            throw new IllegalArgumentException();
        }
    }

    /*
     * private void isDirectionValid(final double direction) {
     * this.checkArgumentAndThrow(direction < 0);
     * this.checkArgumentAndThrow(direction > 360); }
     */

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
        Objects.requireNonNull(targetDirection);
        this.targetDirection = targetDirection;
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void computeNewPosition() {
        this.computeActualSpeed();
        this.computeActualDirection();
        this.computeActualAltitude();
        System.out.println("Speed :" + this.speed.getAsKnots());
        System.out.println("Altitude :" + this.altitude);
        // TODO
    }

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

    private void computeActualDirection() {
        if (this.targetDirection != null) {
            Direction directionDelta = this.getDirectionDelta();
            if (this.goLeft) {
                //TODO
            }
        }
    }

    protected abstract Direction getDirectionDelta();

    protected abstract Speed getSpeedDelta();

    protected abstract double getAltitudeDelta();

}
