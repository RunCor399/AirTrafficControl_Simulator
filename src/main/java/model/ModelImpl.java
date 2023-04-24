package model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 */
public class ModelImpl implements Model {

    private Airport airport = null;
    private Set<Plane> planes = new HashSet<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public Airport getAirport() {
        return this.airport;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAirport(final Airport airport) {
        this.airport = airport;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Set<Plane> getAllPlanes() {
        //defensive copy
        return Set.copyOf(this.planes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Plane getPlaneById(final int id) {
        for (Plane plane: this.planes) {
            if (plane.getAirplaneId() == id) {
                 return plane;
            }
        }
        throw new  IllegalStateException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void addPlane(final Plane plane) {
        this.planes.add(plane);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAllPlanes(final Set<Plane> planes) {
        this.planes = planes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void removePlaneById(final int id) {
        Plane p = getPlaneById(id);
        planes.remove(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void computeAllPlanePositions(final double timeDelta) {
        for (Plane tmp : this.planes) {
            tmp.computeNewPosition(timeDelta);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void removeAllPlanes() {
        this.planes.clear();
    }

}
