package model;

import java.util.Objects;

/**
 * An implementation of {@link model.Vor}.
 * 
 */

public class VorImpl extends AbstractRadarElement implements Vor {
    // need to modify
    private static final long serialVersionUID = 1234;
    private final String vorId;

    /**
     * Constructor of a VOR.
     * 
     * @param vorPosition
     * 
     * @param vorId
     */
    public VorImpl(final String vorId, final RadarPosition vorPosition) {
        super(vorPosition);
        Objects.requireNonNull(vorId);
        this.vorId = vorId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return this.vorId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.vorId;
    }
}
