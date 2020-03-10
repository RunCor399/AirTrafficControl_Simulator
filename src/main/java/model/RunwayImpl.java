package model;

import java.util.Objects;

public class RunwayImpl implements Runway {

    private final Pair<RunwayEnd, RunwayEnd> runwayends;

    public RunwayImpl(final String end1, final RadarPosition endPosition1, final String end2,
            final RadarPosition endPosition2) {
        Objects.requireNonNull(end1);
        Objects.requireNonNull(end2);

        this.runwayends = new Pair<>(new RunwayEndImpl(end1, endPosition1), new RunwayEndImpl(end2, endPosition2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RunwayEnd getRunwayStatus() {
        return this.runwayends.getX().getStatus() ? this.runwayends.getX() : this.runwayends.getY();
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

    public void changeActiveRunwayEnd() {
        // TODO
    }

    private void setActiveRunwayEnd(final RunwayEnd runwayEnd) {
        // TODO Auto-generated method stub use RunwayEnd as parameter.
    }

    private void setInactiveRunwayEnd(final RunwayEnd runwayEnd) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<RunwayEnd, RunwayEnd> getRunwayEnds() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAnyEndActive() {
        // NEW!!!!!! TODO Auto-generated method stub
        return false;
    }

}
