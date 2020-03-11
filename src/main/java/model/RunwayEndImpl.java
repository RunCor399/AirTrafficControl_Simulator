package model;

import java.util.Objects;

public class RunwayEndImpl implements RunwayEnd {

    private static final int MINVALUERUNWAY = 1;
    private static final int MAXVALUERUNWAY = 36;
    private final String numRunwayEnd;
    private boolean isActive;
    private RadarPosition runwayendPosition;

    /**
     * Constructor of runwayEnd.
     * 
     * @param numRunwayEnd Value of the identifier for runwayEnd
     * 
     * @param runwayendPosition Position of the runwayEnd
     */
    public RunwayEndImpl(final String numRunwayEnd, final RadarPosition runwayendPosition) {
        super();
        Objects.requireNonNull(runwayendPosition);
        if ((Integer.parseInt(numRunwayEnd) < MINVALUERUNWAY) || (Integer.parseInt(numRunwayEnd) > MAXVALUERUNWAY)) {
            throw new IllegalArgumentException("Out of range 1-36");
        }

        this.runwayendPosition = runwayendPosition;
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
