package model;

import java.util.Objects;

/**
 * An implementation of {@link model.Vor}.
 * 
 */

public class VorImpl extends AbstractRadarElement implements Vor {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((vorId == null) ? 0 : vorId.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        VorImpl vor = (VorImpl) object;
        if (!this.vorId.equals(vor.getId())) {
            return false;
        }
        return true;
    }
}
