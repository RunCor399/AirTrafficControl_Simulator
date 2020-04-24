package model;

/**
 * An interface that models a VOR.
 */

public interface Vor extends RadarElement {
    /**
     * method that returns the id of a {@link Model.Vor}.
     * 
     * @return id of a {@link Model.Vor}
     */
    String getId();

    /**
     * this method returns the position of a {@link Model.Vor}.
     * 
     * @return the position of a {@link Model.Vor}
     */
    RadarPosition getPosition();

}
