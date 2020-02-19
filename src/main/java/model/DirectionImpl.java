package model;

import java.io.Serializable;
import java.util.Objects;

public class DirectionImpl implements Serializable, Direction {

    private static final long serialVersionUID = 344363981707757298L;
    private static final double MAX_ANGLE = 360;
    private static final double CONVERSION_VALUE = 180;

    private double direction;

    public DirectionImpl(final double direction) {
        this.checkIfValid(direction);
        this.direction = direction;
    }

    private void checkIfValid(final double direction) {
        if (direction < 0 || direction > MAX_ANGLE) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sum(final Direction direction) {
        Objects.requireNonNull(direction);
        this.direction = (this.direction + direction.getAsDegrees()) % MAX_ANGLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subtract(final Direction direction) {
        Objects.requireNonNull(direction);
        this.direction = (this.direction - direction.getAsDegrees() < 0)
                ? MAX_ANGLE + this.direction - direction.getAsDegrees()
                : this.direction - direction.getAsDegrees();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAsDegrees() {
        return this.direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAsRadians() {
        return Math.toRadians(this.direction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double compareTo(final Direction direction) {
        double rawDiff = (this.direction - direction.getAsDegrees() + CONVERSION_VALUE) % MAX_ANGLE - CONVERSION_VALUE;
        return Math.abs(rawDiff < -CONVERSION_VALUE ? rawDiff + MAX_ANGLE : rawDiff);
    }

}
