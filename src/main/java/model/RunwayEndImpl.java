package model;

public class RunwayEndImpl implements RunwayEnd {

    private static final int MINVALUERUNWAY = 1;
    private static final int MAXVALUERUNWAY = 36;
    private final String numRunwayEnd;
    private boolean isActive;

    /**
     * Constructor of runwayEnd.
     * 
     * @param numRunwayEnd Value of the identifier for runwayEnd
     */
    public RunwayEndImpl(final String numRunwayEnd) {
        if ((Integer.parseInt(numRunwayEnd) < MINVALUERUNWAY) || (Integer.parseInt(numRunwayEnd) > MAXVALUERUNWAY)) {
            throw new IllegalArgumentException("Out of range 1-36");
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

}
