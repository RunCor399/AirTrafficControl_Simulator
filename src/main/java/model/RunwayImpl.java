package model;

import java.util.Objects;
import java.util.Optional;

import utilities.Pair;

public class RunwayImpl implements Runway {

    private final Pair<RunwayEnd, RunwayEnd> runwayends;

    public RunwayImpl(final RunwayEnd end1, final RunwayEnd end2) {
        Objects.requireNonNull(end1);
        Objects.requireNonNull(end2);

        this.runwayends = new Pair<>(end1, end2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<RunwayEnd> getRunwayStatus() {
        if (!this.runwayends.getX().getStatus() && !this.runwayends.getY().getStatus()) {
            return Optional.empty();
        }
        return this.runwayends.getX().getStatus() ? Optional.of(this.runwayends.getX())
                : Optional.of(this.runwayends.getY());
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

        if (numRunwayEnd.equals(this.runwayends.getX().getNumRunwayEnd())) {
            if (this.runwayends.getX().getStatus()) {
                this.runwayends.getX().changeStatus(false);
            } else {
                this.runwayends.getX().changeStatus(true);
                this.runwayends.getY().changeStatus(false);
            }
        } else {
            if (this.runwayends.getY().getStatus()) {
                this.runwayends.getY().changeStatus(false);
            } else {
                this.runwayends.getX().changeStatus(false);
                this.runwayends.getY().changeStatus(true);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkRunwayEnd(final String numRunwayEnd) {
        Objects.requireNonNull(numRunwayEnd);

        return (this.runwayends.getX().getNumRunwayEnd().equals(numRunwayEnd)
                || this.runwayends.getY().getNumRunwayEnd().equals(numRunwayEnd));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<RunwayEnd, RunwayEnd> getRunwayEnds() {
        return this.runwayends;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.runwayends.getX().getNumRunwayEnd() + " " + this.runwayends.getY().getNumRunwayEnd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivateBothRunwayEnds() {
        this.runwayends.getX().changeStatus(false);
        this.runwayends.getY().changeStatus(false);
    }
}
