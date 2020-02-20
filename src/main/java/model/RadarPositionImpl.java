package model;

import java.util.Objects;

public class RadarPositionImpl implements RadarPosition {
    private static final Double X_POSITIVE_BOUND = 30000.0;
    private static final Double Y_POSITIVE_BOUND = 20000.0;
    private static final Double X_NEGATIVE_BOUND = 30000.0;
    private static final Double Y_NEGATIVE_BOUND = 20000.0;
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
            // TODO plane is out of bound, needs to be removed
            throw new IllegalStateException();
        }

        this.elementPosition = position;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePosition() {
        // TODO Auto-generated method stub

    }

    private boolean isWithinRadar(final Position2D position) {
        return (((position.getX() <= X_POSITIVE_BOUND) && (position.getX() >= X_NEGATIVE_BOUND))
                && ((position.getY() <= Y_POSITIVE_BOUND) && (position.getY() >= Y_NEGATIVE_BOUND)));
    }

}
