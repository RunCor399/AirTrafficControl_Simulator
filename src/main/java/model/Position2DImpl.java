package model;

/** 
 * An implementation of {@link Position2D}
 */

public class Position2DImpl implements Position2D {

    private double x;
    private double y;
    
    /**
     * Constructor of the bidimensional position of an object
     * 
     * @param x Coordinate X
     * 
     * @param y Coordinate Y
     */
    public Position2DImpl(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getX() {
        return this.x;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getY() {
        return this.y;
    }

}
