package model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * An implementation of {@link Airport}.
 * 
 */

public class AirportImpl implements Airport {
    private final String airportId;
    private final String airportName;
    private final RadarPosition parkingPosition;
    private Set<Vor> vorSet = new HashSet<>();
    private List<Runway> runwayList = new LinkedList<>();

    /**
     * Constructor of a standard airport.
     * 
     * @param airportId
     * 
     * @param airportName
     * 
     * @param vorSet
     * 
     * @param runwayList
     * 
     * @param parkingPosition
     */
    public AirportImpl(final String airportId, final String airportName, final Set<Vor> vorSet,
            final List<Runway> runwayList, final RadarPosition parkingPosition) {
        Objects.requireNonNull(airportId);
        Objects.requireNonNull(airportName);
        Objects.requireNonNull(vorSet);
        Objects.requireNonNull(runwayList);
        Objects.requireNonNull(parkingPosition);
        this.airportId = airportId;
        this.airportName = airportName;
        this.vorSet = vorSet;
        this.runwayList = runwayList;
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
        this.vorSet.add(newVor);
     /*   if (this.isVorAlreadyInList(newVor)) {
            System.out.println("already in");
            throw new IllegalStateException();
        }
        System.out.println("added " + newVor.getId());
        this.vorSet.add(newVor);*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Set<Vor>> getVorList() {
        return this.vorSet.isEmpty() ? Optional.empty() : Optional.of(this.vorSet);
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

    private Optional<Vor> isVorPresent(final String vorId) {
        return this.vorSet.stream().filter(x -> x.getId().equals(vorId)).findAny();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Runway>> getRunways() {
        return this.runwayList.isEmpty() ? Optional.empty() : Optional.of(this.runwayList);
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

        for (Runway runway : this.runwayList) {
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
        if (this.runwayList.contains(newRunway)) {
            throw new IllegalStateException();
        }

        this.runwayList.add(newRunway);
    }

    private Optional<List<Runway>> computeActiveRunways() {
        List<Runway> activeRunwayList = new LinkedList<>();

        for (final Runway runway : this.runwayList) {
            if (runway.getRunwayStatus().isPresent()) {
                activeRunwayList.add(runway);
            }
        }

        if (activeRunwayList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(activeRunwayList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Airport name: " + this.airportName + "\nAirport id: " + this.airportId + "\nRunways list: "
                + this.runwayList + "\nVor list: " + this.vorSet + "\nParking position: "
                + this.parkingPosition.getPosition();

    }
}
