package model;

import java.util.Objects;
import utilities.Pair;

/**
 * An implementation of {@link RadarPosition}.
 * 
 */
public class RadarPositionImpl implements RadarPosition {
    private static final Double X_BOUND = 30000.0;
    private static final Double Y_BOUND = 20000.0;
    private Position2D elementPosition;

    /**
     * 
     * Constructor of the initial {@link Position2D} of an element.
     * 
     * @param initialPosition
     */
    public RadarPositionImpl(final Position2D initialPosition) {
        Objects.requireNonNull(initialPosition);
        this.elementPosition = initialPosition;
    }

    /**
     * Method that returns a pair containing radar bounds.
     * 
     * @return pair of double containing radar bounds
     */
    public static final Pair<Double, Double> getRadarBounds() {
        return new Pair<Double, Double>(X_BOUND, Y_BOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position2D getPosition() {
        return new Position2DImpl(this.elementPosition.getX(), this.elementPosition.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Position2D position) {
        Objects.requireNonNull(position);
        if (!this.isWithinRadar()) {
            throw new IllegalStateException();
        }

        this.elementPosition = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RadarPosition sumPosition(final Position2D offsetPosition) {
        Objects.requireNonNull(offsetPosition);
        Position2D finalPosition = this.getPosition();
        finalPosition.addX(offsetPosition.getX());
        finalPosition.addY(offsetPosition.getY());

        return new RadarPositionImpl(finalPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean isWithinRadar() {
        return ((Math.abs(this.elementPosition.getX()) <= X_BOUND)
                && (Math.abs(this.elementPosition.getY())) <= Y_BOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction computeDirectionToTargetPosition(final RadarPosition targetPosition) {
        final double xRelative = targetPosition.getPosition().getX() - this.elementPosition.getX();
        final double yRelative = targetPosition.getPosition().getY() - this.elementPosition.getY();
        double degrees = Math.toDegrees(Math.atan2(yRelative, xRelative));
        degrees = degrees < 0 ? 360 + degrees : degrees;

        return new DirectionImpl(degrees);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double distanceFrom(final RadarPosition position) {
        Position2D targetPosition = position.getPosition();
        return Math.sqrt(Math.pow(targetPosition.getX() - this.elementPosition.getX(), 2)
                + Math.pow(targetPosition.getY() - this.elementPosition.getY(), 2));
    }
}
