package model;

public abstract class RadarElementImpl implements RadarElement {

    private RadarPosition position;

    /**
     * 
     * Constructor of a radar element.
     * 
     * @param position where the element is in the radar.
     */

    public RadarElementImpl(final RadarPosition position) {
        this.position = position;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RadarPosition getPosition() {
        return this.position;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final RadarPosition position) {
           this.position = position;

    }

}
