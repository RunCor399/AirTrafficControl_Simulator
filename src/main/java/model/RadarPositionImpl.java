package model;

import java.util.Objects;

public class RadarPositionImpl implements RadarPosition {
    private static final Double X_BOUND = 30000.0;
    private static final Double Y_BOUND = 20000.0;
    private Position2D elementPosition;

    /**
     * 
     * Constructor of the initial position of an element.
     * 
     * @param initialPosition
     */
    public RadarPositionImpl(final Position2D initialPosition) {
        Objects.requireNonNull(initialPosition);
        this.elementPosition = initialPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position2D getPosition() {
        return this.elementPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Position2D position) {
        Objects.requireNonNull(position);
        if (!isWithinRadar(position)) {
            /* TODO modify exception */
            throw new IllegalStateException();
        }

        this.elementPosition = position;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sumPosition(final Position2D offsetPosition) {
        Objects.requireNonNull(offsetPosition);
        this.elementPosition.addX(offsetPosition.getX());
        this.elementPosition.addY(offsetPosition.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWithinRadar(final Position2D position) {
        return ((Math.abs(position.getX()) <= X_BOUND) && (Math.abs(position.getY())) <= Y_BOUND);
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

}
