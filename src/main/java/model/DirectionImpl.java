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
        this.direction = (this.direction + direction.getDirectionAsDegrees()) % MAX_ANGLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subtract(final Direction direction) {
        Objects.requireNonNull(direction);
        this.direction = (this.direction - direction.getDirectionAsDegrees() < 0)
                ? MAX_ANGLE + this.direction - direction.getDirectionAsDegrees()
                : this.direction - direction.getDirectionAsDegrees();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDirectionAsDegrees() {
        return this.direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double compareTo(final Direction direction) {
        double rawDiff = (this.direction - direction.getDirectionAsDegrees() + CONVERSION_VALUE) % MAX_ANGLE
                - CONVERSION_VALUE;
        return rawDiff < -CONVERSION_VALUE ? rawDiff + MAX_ANGLE : rawDiff;
    }

}
