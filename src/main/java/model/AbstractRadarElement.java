package model;

import java.util.Objects;

public abstract class AbstractRadarElement implements RadarElement {

    private RadarPosition position;

    /**
     * 
     * Constructor of a radar element.
     * 
     * @param position where the element is in the radar.
     */

    public AbstractRadarElement(final RadarPosition position) {
        this.position = Objects.requireNonNull(position);

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
