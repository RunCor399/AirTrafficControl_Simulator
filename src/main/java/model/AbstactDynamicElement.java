package model;

import java.util.Objects;
import java.util.Optional;

public class AbstactDynamicElement implements DynamicElement/* extends AbstractRadarElement */ {

    private static final double NO_VALUE = -1;

    private Speed speed;
    private double altitude;
    private double direction;
    private Speed targetSpeed;
    private double targetAltitude;
    private double targetDirection;

    public AbstactDynamicElement(final Speed speed, final double altitude, final double direction) {
        Objects.requireNonNull(speed);
        Objects.requireNonNull(altitude);
        Objects.requireNonNull(direction);
        this.isDirectionValid(direction);
        this.isAltitudeValid(altitude);
        this.speed = speed;
        this.altitude = altitude;
        this.direction = direction;
        this.targetSpeed = null;
        this.targetAltitude = NO_VALUE;
        this.targetDirection = NO_VALUE;
    }

    private void checkArgumentAndThrow(final boolean condition) {
        if (condition) {
            throw new IllegalArgumentException();
        }
    }

    private void isDirectionValid(final double direction) {
        this.checkArgumentAndThrow(direction < 0);
        this.checkArgumentAndThrow(direction > 360);
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
    public double getDirection() {
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
    public double getTargetDirection() {
        return this.targetDirection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void computeNewPosition() {
        // TODO Auto-generated method stub

    }

}
