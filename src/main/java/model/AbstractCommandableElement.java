package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractCommandableElement extends AbstractDynamicElement implements CommandableElement, Serializable {

    private static final long serialVersionUID = -5883282891035535834L;
    private static final double NO_VALUE = -1;
    private static final double MAX_DIFFERENCE = 0.01;

    private Speed targetSpeed;
    private double targetAltitude;
    private Direction targetDirection;
    private RadarPosition targetPosition;

// flags useful to avoid checking where to go every time new parameters are computed
    private boolean goUp;
    private boolean goLeft;
    private boolean accelerate;

    private double directionDifference;

    public AbstractCommandableElement(final RadarPosition position, final Speed speed, final double altitude,
            final Direction direction) {
        super(position, speed, altitude, direction);
        this.resetAllTargets();

        this.goUp = false;
        this.goLeft = false;
        this.accelerate = false;

        this.directionDifference = NO_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTargetAltitude() {
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
        this.targetAltitude = targetAltitude;
        this.goUp = (this.targetAltitude - this.getAltitude() > 0) ? true : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargetSpeed(final Speed targetSpeed) {
        Objects.requireNonNull(targetSpeed);
        this.targetSpeed = targetSpeed;
        this.accelerate = (this.targetSpeed.getAsKnots() - this.getSpeed().getAsKnots() > 0) ? true : false;
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
        this.goLeft = this.getDirection().isTurnCounterCW(this.targetDirection);
        this.directionDifference = this.getDirection().compareTo(this.targetDirection);
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
     * {@inheritDoc}
     */
    @Override
    public void resetAllTargets() {
        this.targetAltitude = NO_VALUE;
        this.targetDirection = null;
        this.targetPosition = null;
        this.targetSpeed = null;
    }

    /**
     * This private method computes the actual altitude based on the target altitude
     * and the altitude delta.
     */
    private void computeActualAltitude(final double timeDelta) {
        if (this.targetAltitude != NO_VALUE) {
            double altitudeDelta = this.getAltitudeDelta() * timeDelta;
            if (this.goUp) {
                if (this.getAltitude() + altitudeDelta >= this.targetAltitude) {
                    this.setAltitude(this.targetAltitude);
                    this.targetAltitude = NO_VALUE;
                } else {
                    this.setAltitude(this.getAltitude() + altitudeDelta);
                }
            } else {
                if (this.getAltitude() - altitudeDelta <= this.targetAltitude) {
                    this.setAltitude(this.targetAltitude);
                    this.targetAltitude = NO_VALUE;
                } else {
                    this.setAltitude(this.getAltitude() - altitudeDelta);
                }
            }
        }
    }

    /**
     * This private method computes the actual direction based on the target
     * direction and the direction delta.
     */
    private void computeActualDirection(final double timeDelta) {
        if (this.targetDirection != null) {
            Direction directionDelta = new DirectionImpl(this.getDirectionDelta().getAsDegrees() * timeDelta);
            if (this.goLeft) {
                this.getDirection().sum(directionDelta);
            } else {
                this.getDirection().subtract(directionDelta);
            }
            this.directionDifference = this.directionDifference - directionDelta.getAsDegrees();
            if (this.directionDifference <= 0) {
                this.directionDifference = NO_VALUE;
                this.setDirection(this.targetDirection);
                this.targetDirection = null;
            }
        }
    }

    /**
     * This private method computes the actual speed based on the target speed and
     * the speed delta.
     */
    private void computeActualSpeed(final double timeDelta) {
        if (this.targetSpeed != null) {
            double speedDelta = this.getSpeedDelta().getAsKnots() * timeDelta;
            if (this.accelerate) {
                if (this.getSpeed().getAsKnots() + speedDelta >= this.targetSpeed.getAsKnots()) {
                    this.setSpeed(this.targetSpeed);
                    this.targetSpeed = null;
                } else {
                    this.setSpeed(new SpeedImpl(this.getSpeed().getAsKnots() + speedDelta));
                }
            } else {
                if (this.getSpeed().getAsKnots() - speedDelta <= this.targetSpeed.getAsKnots()) {
                    this.setSpeed(this.targetSpeed);
                    this.targetSpeed = null;
                } else {
                    this.setSpeed(new SpeedImpl(this.getSpeed().getAsKnots() - speedDelta));
                }
            }
        }
    }

    /**
     * 
     * Method to get the direction delta per second (expressed in degrees per
     * second).
     * 
     * @return the direction delta per second.
     */
    protected abstract Direction getDirectionDelta();

    /**
     * 
     * Method to get the speed delta per second (expressed in knots per second).
     * 
     * @return the speed delta per second.
     */
    protected abstract Speed getSpeedDelta();

    /**
     * 
     * Method to get the altitude delta per second (expressed in feet per second).
     * 
     * @return the altitude delta per second.
     */
    protected abstract double getAltitudeDelta();

    /**
     * {@inheritDoc}
     */
    @Override
    public void computeNewPosition(final double timeDelta) {
        if (this.targetPosition != null) {
            Direction newTargetDirection = this.getPosition().computeDirectionToTargetPosition(targetPosition);
            if (this.getDirection().compareTo(newTargetDirection) > MAX_DIFFERENCE) {
                this.setOnlyTargetDirection(newTargetDirection);
            } else {
                this.targetPosition = null;
            }
        }
        this.computeActualSpeed(timeDelta);
        this.computeActualDirection(timeDelta);
        this.computeActualAltitude(timeDelta);
        super.computeNewPosition(timeDelta);
    }

    /**
     * Method that shows all the info about the element.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append(super.toString());
        builder = builder.append("Target speed -> ")
                .append(this.targetSpeed == null ? "NONE" : this.targetSpeed.getAsKnots() + " knots").append("\n");
        builder = builder.append("Target direction -> ")
                .append(this.targetDirection == null ? "NONE" : this.targetDirection).append("\n");
        builder = builder.append("Target altitude -> ")
                .append(this.targetAltitude == NO_VALUE ? "NONE" : this.targetAltitude + " ft").append("\n");
        return builder.toString();
    }
}
