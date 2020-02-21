package model;

/**
 * An implementation of {@link Position2D}.
 * 
 */

public class Position2DImpl implements Position2D {

    private Double x;
    private Double y;

    /**
     * 
     * Constructor of the bidimensional position of an object.
     * 
     * @param x Coordinate X
     * 
     * @param y Coordinate Y
     */
    public Position2DImpl(final Double x, final Double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getX() {
        return this.x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getY() {
        return this.y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addX(final Double xOffset) {
        this.x = this.x + xOffset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addY(final Double yOffset) {
        this.y = this.y + yOffset;
    }

}
