package model;

import java.time.temporal.ValueRange;
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

        if (!ValueRange.of(MIN_RUNWAY_ID, MAX_RUNWAY_ID).isValidIntValue(Integer.parseInt(numRunwayEnd.substring(0, 2)))) {
            throw new IllegalStateException();
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
        return new DirectionImpl(Double.parseDouble(this.getNumRunwayEnd().substring(0, 2)) * 10);
    }

}
