package model;

import java.util.Objects;
import java.util.Optional;

public class RunwayImpl implements Runway {

    private final Pair<RunwayEnd, RunwayEnd> runwayends;

    /**
     * Constructor of a Runway.
     * 
     * @param end1 RunwayEnd 1
     * @param end2 RunwayEnd 2
     */
    public RunwayImpl(final RunwayEnd end1, final RunwayEnd end2) {
        Objects.requireNonNull(end1);
        Objects.requireNonNull(end2);

        this.runwayends = new Pair<>(end1, end2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getRunwayStatus() {
        if (!this.runwayends.getX().getStatus() && !this.runwayends.getY().getStatus()) {
            return Optional.empty();
        }
        return this.runwayends.getX().getStatus() ? Optional.of(this.runwayends.getX().getNumRunwayEnd()) : Optional.of(this.runwayends.getY().getNumRunwayEnd());
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
        Objects.requireNonNull(positions);
        this.runwayends.getX().setPosition(positions.getX());
        this.runwayends.getY().setPosition(positions.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveRunwayEnd(final String numRunwayEnd) {
        if (!checkRunwayEnd(numRunwayEnd)) {
            throw new IllegalArgumentException("Not a runwayEnd of this Runway");
        }

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
    public boolean checkRunwayEnd(final String numRunwayEnd) {
        Objects.requireNonNull(numRunwayEnd);
        int num = Integer.parseInt(numRunwayEnd);

        return (num != Integer.parseInt(this.runwayends.getX().getNumRunwayEnd())) && num != Integer.parseInt(this.runwayends.getY().getNumRunwayEnd());
    }

}
