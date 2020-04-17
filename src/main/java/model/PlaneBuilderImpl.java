package model;

import java.util.Objects;
import java.util.Optional;

import model.Plane.Action;

/**
 * 
 * An implementation of a {@link PlaneBuilder} interface.
 *
 */
public class PlaneBuilderImpl implements PlaneBuilder {

    private final int planeId;
    private final String companyName;
    private Optional<RadarPosition> position;
    private Optional<Action> planeAction;
    private Optional<Speed> speed;
    private Optional<Double> altitude;
    private Optional<Direction> direction;
    private boolean built;

    /**
     * Constructor of a {@link PlaneBuilder}. This constructor requires both the id and company name of the
     * {@link Plane} we want to create; that's because they are the main parameters of a plane.
     * 
     * @param planeId
     * @param companyName
     */
    public PlaneBuilderImpl(final int planeId, final String companyName) {
        Objects.requireNonNull(companyName);
        this.planeId = planeId;
        this.companyName = companyName;
        this.position = Optional.empty();
        this.planeAction = Optional.empty();
        this.speed = Optional.empty();
        this.altitude = Optional.empty();
        this.direction = Optional.empty();
        this.built = false;
    }

    /**
     * 
     * Useful method that throws an {@link IllegalArgumentException} when the
     * condition is true.
     * 
     * @param condition the condition to check
     */
    private void checkAndThrow(final boolean condition) {
        if (condition) {
            throw new IllegalStateException();
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public PlaneBuilder position(final RadarPosition position) {
        this.checkAndThrow(this.built);
        this.position = Optional.of(position);
        return this;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public PlaneBuilder planeAction(final Action action) {
        this.checkAndThrow(this.built);
        this.planeAction = Optional.of(action);
        return this;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public PlaneBuilder speed(final Speed speed) {
        this.checkAndThrow(this.built);
        this.speed = Optional.of(speed);
        return this;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public PlaneBuilder altitude(final double altitude) {
        this.checkAndThrow(this.built);
        this.altitude = Optional.of(altitude);
        return this;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public PlaneBuilder direction(final Direction direction) {
        this.checkAndThrow(this.built);
        this.direction = Optional.of(direction);
        return this;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Plane build() {
        this.checkAndThrow(this.built);
        this.checkAndThrow(this.position.isEmpty());
        this.checkAndThrow(this.planeAction.isEmpty());
        this.checkAndThrow(this.speed.isEmpty());
        this.checkAndThrow(this.altitude.isEmpty());
        this.checkAndThrow(this.direction.isEmpty());
        this.built = true;
        return new PlaneImpl(this.planeId, this.companyName, this.planeAction.get(), this.position.get(),
                this.speed.get(), this.altitude.get(), this.direction.get());
    }

}
