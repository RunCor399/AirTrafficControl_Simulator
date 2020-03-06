package model;

import java.util.Objects;
import java.util.Optional;

import model.Plane.Action;

/**
 * 
 * An implementation of a {@link PlaneBuilder} interface.
 *
 */
public class PlaneBuilderImpl {

    private final RadarPosition position;
    private Optional<Action> planeAction;
    private Optional<Speed> speed;
    private Optional<Double> altitude;
    private Optional<Direction> direction;
    private boolean built;

    public PlaneBuilderImpl(final RadarPosition position) {
        Objects.requireNonNull(position);
        this.position = position;
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
     * Method that sets the {@link Action} of the plane.
     * 
     * @param action the action we want to give to the plane.
     * @return the PlaneBuilder object (this).
     */
    public PlaneBuilderImpl planeAction(final Action action) {
        this.checkAndThrow(this.built);
        this.planeAction = Optional.of(action);
        return this;
    }

    /**
     * 
     * Method that sets the {@link Speed} of the plane.
     * 
     * @param speed the speed of the plane.
     * @return the PlaneBuilder object (this).
     */
    public PlaneBuilderImpl speed(final Speed speed) {
        this.checkAndThrow(this.built);
        this.speed = Optional.of(speed);
        return this;
    }

    /**
     * 
     * Method that sets the altitude of the plane.
     * 
     * @param altitude the altitude of the new plane.
     * @return the PlaneBuilder object (this).
     */
    public PlaneBuilderImpl altitude(final double altitude) {
        this.checkAndThrow(this.built);
        this.altitude = Optional.of(altitude);
        return this;
    }

    /**
     * 
     * Method that sets the {@link Direction} of the plane.
     * 
     * @param direction the direction of the plane.
     * @return the PlaneBuilder object (this).
     */
    public PlaneBuilderImpl direction(final Direction direction) {
        this.checkAndThrow(this.built);
        this.direction = Optional.of(direction);
        return this;
    }

    /**
     * 
     * Method that creates the wanted plane.
     * 
     * @return the new plane.
     */
    public Plane build() {
        this.checkAndThrow(this.built);
        this.checkAndThrow(this.planeAction.isEmpty());
        this.checkAndThrow(this.speed.isEmpty());
        this.checkAndThrow(this.altitude.isEmpty());
        this.checkAndThrow(this.direction.isEmpty());
        this.built = true;
        return new PlaneImpl(this.planeAction.get(), this.position, this.speed.get(), this.altitude.get(),
                this.direction.get());
    }

}
