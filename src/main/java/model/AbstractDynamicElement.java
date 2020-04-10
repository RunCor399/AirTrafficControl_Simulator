package model;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractDynamicElement extends AbstractRadarElement implements DynamicElement, Serializable {

    /**
     * This value represents the time considered when updating the actual parameters.
     */
    public static final double TIME_QUANTUM = 0.25;
    private static final long serialVersionUID = 5949982404790725460L;
    private static final double KMH_TO_MS = 3.6;
    private Speed speed;
    private double altitude;
    private Direction direction;

    public AbstractDynamicElement(final RadarPosition position, final Speed speed, final double altitude,
            final Direction direction) {
        super(position);
        Objects.requireNonNull(speed);
        Objects.requireNonNull(direction);
        this.isAltitudeValid(altitude);

        this.speed = speed;
        this.altitude = altitude;
        this.direction = direction;
    }

    private void checkArgumentAndThrow(final boolean condition) {
        if (condition) {
            throw new IllegalArgumentException();
        }
    }

    private void isAltitudeValid(final double altitude) {
        this.checkArgumentAndThrow(altitude < 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAltitude() {
        return this.altitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Speed getSpeed() {
        return this.speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * 
     * Protected method to set the altitude internally.
     * This method intent is to allow the subclasses to directly work with the element parameters.
     * 
     * @param altitude the altitude to set.
     */
    protected void setAltitude(final double altitude) {
        this.altitude = altitude;
    }

    /**
     * 
     * Protected method to set the internally.
     * This method intent is to allow the subclasses to directly work with the element parameters.
     * 
     * @param speed the speed to set.
     */
    protected void setSpeed(final Speed speed) {
        Objects.requireNonNull(speed);
        this.speed = speed;
    }

    /**
     * 
     * Protected method to set the direction internally.
     * This method intent is to allow the subclasses to directly work with the element parameters.
     * 
     * @param direction the direction to set.
     */
    protected void setDirection(final Direction direction) {
        Objects.requireNonNull(direction);
        this.direction = direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void computeNewPosition() {
        this.setPosition(this.getPosition().sumPosition(this.getPositionDelta()));
        /* DEBUG code. Remove the comment to print the element information on the console */
//        System.out.println(this);
//        System.out.println("Position -> x: " + this.getPosition().getPosition().getX());
//        System.out.println("y: " + this.getPosition().getPosition().getY());
    }

    /**
     * 
     * This method computes the movement made by the element in the specified time
     * quantum considering the actual speed and direction.
     * 
     * @return the position delta in the specified time quantum.
     */
    private Position2D getPositionDelta() {
        final double actualSpeed = this.speed.getAsKMH() / KMH_TO_MS;
        final double actualDirection = this.direction.getAsRadians();
        double xMovement = TIME_QUANTUM * actualSpeed * Math.cos(actualDirection);
        double yMovement = TIME_QUANTUM * actualSpeed * Math.sin(actualDirection);
        return new Position2DImpl(xMovement, yMovement);
    }

    /**
     * Method that shows all the info about the element.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder = builder.append("Speed -> ").append(this.speed.getAsKnots()).append(" knots\n");
        builder = builder.append("Direction -> ").append(this.direction).append("\n");
        builder = builder.append("Altitude -> ").append(this.altitude).append(" ft\n");
        return builder.toString();

    }

}
