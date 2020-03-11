package model;

import java.util.Objects;

public class RunwayEndImpl implements RunwayEnd {

    private final String numRunwayEnd;
    private boolean isActive;
    private RadarPosition runwayendPosition;

    /**
     * Constructor of runwayEnd.
     * 
     * @param numRunwayEnd Value of the identifier for runwayEnd
     * 
     * @param runwayEndPosition Position of the runwayEnd
     */
    public RunwayEndImpl(final String numRunwayEnd, final RadarPosition runwayEndPosition) {
        super();
        Objects.requireNonNull(numRunwayEnd);
        Objects.requireNonNull(runwayEndPosition);

        this.runwayendPosition = runwayEndPosition;
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
    public RadarPosition getPosition() {
        return this.runwayendPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final RadarPosition position) {
        Objects.requireNonNull(position);
        this.runwayendPosition = position;
    }

}
