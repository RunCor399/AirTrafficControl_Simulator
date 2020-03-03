package model;

import java.util.Objects;

public class RunwayImpl implements Runway {

    private final Pair<RunwayEnd, RunwayEnd> runwayends;

    public RunwayImpl(final String end1, final String end2) {
        Objects.requireNonNull(end1);
        Objects.requireNonNull(end2);

        this.runwayends = new Pair<>(new RunwayEndImpl(end1), new RunwayEndImpl(end2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRunwayStatus() {
        return this.runwayends.getX().getStatus() ? this.runwayends.getX().toString() : this.runwayends.getY().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<RadarPosition, RadarPosition> getPosition() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Pair<RadarPosition, RadarPosition> positions) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveRunwayEnd(final String numRunwayEnd) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunwayEndActive(final String numRunwayEnd) {
        // TODO Auto-generated method stub
        return false;
    }

}
