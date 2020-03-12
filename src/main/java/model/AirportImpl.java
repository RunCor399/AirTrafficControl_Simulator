package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * An implementation of {@link Airport}.
 * 
 */

public class AirportImpl implements Airport {
    private final String airportId;
    private final String airportName;
    private final RadarPosition parkingPosition;
    private List<Vor> vorList = new LinkedList<>();
    private List<Runway> runwaySet = new LinkedList<>();

    /**
     * Constructor of a standard airport.
     * 
     * @param airportId
     * 
     * @param airportName
     * 
     * @param vorList
     * 
     * @param runwaySet
     * 
     * @param parkingPosition
     */
    public AirportImpl(final String airportId, final String airportName, final List<Vor> vorList,
            final List<Runway> runwaySet, final RadarPosition parkingPosition) {
        Objects.requireNonNull(airportId);
        Objects.requireNonNull(airportName);
        Objects.requireNonNull(vorList);
        this.airportId = airportId;
        this.airportName = airportName;
        this.vorList = vorList;
        this.parkingPosition = parkingPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return this.airportId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.airportName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RadarPosition getParkingPosition() {
        return this.parkingPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVor(final Vor newVor) {
        Objects.requireNonNull(newVor);
        if (this.isVorAlreadyInList(newVor)) {
            throw new IllegalStateException();
        }

        this.vorList.add(newVor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Vor>> getVorList() {
        return this.vorList.isEmpty() ? Optional.empty() : Optional.of(this.vorList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Vor> getVorById(final String vorId) {
        Optional<Vor> vor = this.isVorPresent(vorId);
        if (vor.isPresent()) {
            return vor;
        }

        return Optional.empty();
    }

    private boolean isVorAlreadyInList(final Vor newVor) {
        return this.vorList.contains(newVor);
    }

    private Optional<Vor> isVorPresent(final String vorId) {
        for (Vor vor : this.vorList) {
            if (vor.getId().equals(vorId)) {
                return Optional.of(vor);
            }
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Runway>> getRunways() {
        return this.runwaySet.isEmpty() ? Optional.empty() : Optional.of(this.runwaySet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Runway>> getActiveRunways() {
        return this.computeActiveRunways();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveRunways(final String runwayEnd) {
        Objects.requireNonNull(runwayEnd);

        for (Runway runway : this.runwaySet) {
            if (runway.checkRunwayEnd(runwayEnd)) {
                runway.setActiveRunwayEnd(runwayEnd);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRunway(final Runway newRunway) {
        Objects.requireNonNull(newRunway);
        if (this.runwaySet.contains(newRunway)) {
            throw new IllegalStateException();
        }

        this.runwaySet.add(newRunway);
    }

    private Optional<List<Runway>> computeActiveRunways() {
        List<Runway> activeRunwayList = new LinkedList<>();

        for (Runway runway : this.runwaySet) {
            if (runway.getRunwayStatus().isPresent()) {
                activeRunwayList.add(runway);
            }
        }

        if (activeRunwayList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(activeRunwayList);
    }
}
