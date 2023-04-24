package model;

/**
 * Implementation of {@link Speed} that defines the speed of a {@link Plane}. 
 *
 */
public class SpeedImpl implements Speed {
    private static final Double CONVERSION_FACTOR = 1.852;
    private Double knotsSpeed;

    /**
     * 
     * Constructor of the knots speed of an Object.
     * 
     * @param knotsSpeed Knots speed of an Object as Double.
     */
    public SpeedImpl(final Double knotsSpeed) {
        this.knotsSpeed = knotsSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Double getAsKnots() {
        return this.knotsSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Double getAsKMH() {
        return this.knotsSpeed * CONVERSION_FACTOR;
    }

}
