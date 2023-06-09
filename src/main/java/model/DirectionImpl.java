package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * An implementation of the {@link Direction} interface.
 * 
 */
public class DirectionImpl implements Direction, Serializable {

    private static final long serialVersionUID = 344363981707757298L;
    private static final double MAX_ANGLE = 360;
    private static final double MAX_DIFF = 180;

    private double direction;

    /**
     * 
     * Constructor of the direction.
     * 
     * @param direction the direction in degrees.
     */
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
        double rawDiff = (this.direction - direction.getAsDegrees() + MAX_DIFF) % MAX_ANGLE - MAX_DIFF;
        return Math.abs(rawDiff < -MAX_DIFF ? rawDiff + MAX_ANGLE : rawDiff);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTurnCounterCW(final Direction targetDirection) {
        double diff = targetDirection.getAsDegrees() - this.direction;
        return diff > 0 ? diff <= MAX_DIFF : diff < -MAX_DIFF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(direction);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        DirectionImpl other = (DirectionImpl) obj;
        if (Double.doubleToLongBits(direction) != Double.doubleToLongBits(other.getAsDegrees())) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return this.getAsDegrees() + "°, " + this.getAsRadians() + " rads.";
    }

}
