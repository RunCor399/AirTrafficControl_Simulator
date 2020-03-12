package model;

import java.util.Objects;

/**
 * An implementation of {@link model.Vor}.
 * 
 */

public class VorImpl implements Vor {
    private final String vorId;
    private final Position2D vorPosition;

    /**
     * Constructor of a VOR.
     * 
     * @param vorId
     * 
     * @param vorPosition
     */
    public VorImpl(final String vorId, final Position2D vorPosition) {
        Objects.requireNonNull(vorId);
        Objects.requireNonNull(vorPosition);
        this.vorId = vorId;
        this.vorPosition = vorPosition;
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
    public Position2D getPosition() {
        return this.vorPosition;
    }

}
