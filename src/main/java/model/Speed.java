package model;

/**
 * 
 * An interface that defines the speed of an object both as knots and km/h.
 */
public interface Speed {
    /**
     * Gets the speed as knots.
     * 
     * @return Speed as knots.
     */
    Double getAsKnots();

    /**
     * Gets the speed as km/h.
     * 
     * @return Speed as km/h.
     */
    Double getAsKMH();

}
