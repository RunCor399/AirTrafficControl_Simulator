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
        return this.planes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Plane getPlaneById(final int id) {
        Plane p = null;
        for (Plane tmp : this.planes) {
            if (tmp.getAirplaneId() == id) {
                 return p;
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
    public synchronized void computeAllPlanePositions() {
        for (Plane tmp : this.planes) {
            tmp.computeNewPosition();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllPlanes() {
        this.planes.clear();
    }

}
