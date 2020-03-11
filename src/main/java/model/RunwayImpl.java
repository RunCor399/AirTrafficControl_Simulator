package model;

import java.util.Objects;

public class RunwayImpl implements Runway {

    private final Pair<RunwayEnd, RunwayEnd> runwayends;

    public RunwayImpl(final String end1, final RadarPosition endPosition1, final String end2, final RadarPosition endPosition2) {
        //TODO add requireNonNull for positions
        Objects.requireNonNull(end1);
        Objects.requireNonNull(end2);

        this.runwayends = new Pair<>(new RunwayEndImpl(end1, endPosition1), new RunwayEndImpl(end2, endPosition2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRunwayStatus() {
        //TODO 2 runwayend can be false, return Optional
        return this.runwayends.getX().getStatus() ? this.runwayends.getX().getNumRunwayEnd() : this.runwayends.getY().getNumRunwayEnd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<RadarPosition, RadarPosition> getPosition() {
        return new Pair<>(this.runwayends.getX().getPosition(), this.runwayends.getY().getPosition());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Pair<RadarPosition, RadarPosition> positions) {
        //TODO add requireNonNull positions
        this.runwayends.getX().setPosition(positions.getX());
        this.runwayends.getY().setPosition(positions.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveRunwayEnd(final String numRunwayEnd) {
        checkRunwayEnd(numRunwayEnd);
        if (Integer.parseInt(numRunwayEnd) == Integer.parseInt(this.runwayends.getX().getNumRunwayEnd())) {
            this.runwayends.getX().changeStatus(true);
            this.runwayends.getY().changeStatus(false);
        }
        this.runwayends.getX().changeStatus(false);
        this.runwayends.getY().changeStatus(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunwayEndActive(final String numRunwayEnd) {
        //TODO delete this method
        checkRunwayEnd(numRunwayEnd);
        return Integer.parseInt(numRunwayEnd) == Integer.parseInt(this.runwayends.getX().getNumRunwayEnd()) ? this.runwayends.getX().getStatus() : this.runwayends.getY().getStatus();
    }

    /**
     * {@inheritDoc}
     */
    public void checkRunwayEnd(final String numRunwayEnd) {
        Objects.requireNonNull(numRunwayEnd);
        int num = Integer.parseInt(numRunwayEnd);
        if ((num != Integer.parseInt(this.runwayends.getX().getNumRunwayEnd())) && num != Integer.parseInt(this.runwayends.getY().getNumRunwayEnd())) {
            throw new IllegalArgumentException("Not a runwayEnd of this Runway");
        }
    }

}
