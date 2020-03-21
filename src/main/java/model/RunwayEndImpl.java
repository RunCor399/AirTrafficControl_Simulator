package model;

import java.util.Objects;

public class RunwayEndImpl extends AbstractRadarElement implements RunwayEnd {
    private static final long serialVersionUID = 1L;
    private static final int MAX_RUNWAY_ID = 36;
    private static final int MIN_RUNWAY_ID = 1;
    private final String numRunwayEnd;
    private boolean isActive;

    /**
     * Constructor of runwayEnd.
     * 
     * @param numRunwayEnd      Value of the identifier for runwayEnd
     * 
     * @param runwayEndPosition Position of the runwayEnd
     */
    public RunwayEndImpl(final String numRunwayEnd, final RadarPosition runwayEndPosition) {
        super(runwayEndPosition);
        Objects.requireNonNull(numRunwayEnd);
        Objects.requireNonNull(runwayEndPosition);

        if ((Integer.parseInt(numRunwayEnd) > MAX_RUNWAY_ID) || (Integer.parseInt(numRunwayEnd) < MIN_RUNWAY_ID)) {
            throw new IllegalStateException("illegal runway");
        }

        this.numRunwayEnd = numRunwayEnd;
        this.isActive = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeStatus(final boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getStatus() {
        return this.isActive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNumRunwayEnd() {
        return this.numRunwayEnd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getRunwayEndHeading() {
        return new DirectionImpl(Double.parseDouble(this.getNumRunwayEnd()) * 10);
    }

}
