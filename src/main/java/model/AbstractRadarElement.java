package model;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractRadarElement implements RadarElement, Serializable  {

    /**
     * 
     */
    private static final long serialVersionUID = -7658350082008425580L;
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
        return new RadarPositionImpl(this.position.getPosition());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final RadarPosition position) {
           this.position = position;

    }

}
