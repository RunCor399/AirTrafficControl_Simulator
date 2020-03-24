package model;

/**
 * An interface that models a VOR.
 */

public interface Vor {
    /**
     * this method returns the id of a VOR.
     * 
     * @return id of a VOR
     */
    String getId();

    /**
     * this method returns the position of a VOR.
     * 
     * @return the position of a VOR
     */
    RadarPosition getPosition();

}
