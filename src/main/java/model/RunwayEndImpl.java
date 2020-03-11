package model;

import java.util.Objects;

public class RunwayEndImpl extends AbstractRadarElement implements RunwayEnd {

    private final String numRunwayEnd;
    private boolean isActive;

    /**
     * Constructor of runwayEnd.
     * 
     * @param numRunwayEnd Value of the identifier for runwayEnd
     * 
     * @param runwayEndPosition Position of the runwayEnd
     */
    public RunwayEndImpl(final String numRunwayEnd, final RadarPosition runwayEndPosition) {
        super(runwayEndPosition);
        Objects.requireNonNull(numRunwayEnd);
        Objects.requireNonNull(runwayEndPosition);

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

}
