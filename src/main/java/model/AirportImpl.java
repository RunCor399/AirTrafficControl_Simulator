package model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An implementation of {@link Airport}.
 * 
 */

public class AirportImpl implements Airport {
    private final String airportId;
    private final String airportName;
    private List<Vor> vorList = new LinkedList<>();
    private Set<Runway> runwaySet = new HashSet<>();
    private Set<RunwayEnd> activeRunwaysEnds = new HashSet<>();

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
     */
    public AirportImpl(final String airportId, final String airportName, final List<Vor> vorList,
            final Set<Runway> runwaySet) {
        Objects.requireNonNull(airportId);
        Objects.requireNonNull(airportName);
        Objects.requireNonNull(vorList);
        this.airportId = airportId;
        this.airportName = airportName;
        this.vorList = vorList;
        this.computeActiveRunways();
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
    public Optional<Set<Runway>> getRunways() {
        return this.runwaySet.isEmpty() ? Optional.empty() : Optional.of(this.runwaySet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Set<RunwayEnd>> getActiveRunways() {
        this.computeActiveRunways();
        return this.activeRunwaysEnds.isEmpty() ? Optional.empty() : Optional.of(this.activeRunwaysEnds);
    }

    private void computeActiveRunways() {
        this.activeRunwaysEnds = this.runwaySet.stream().map(x -> x.getRunwayStatus()).collect(Collectors.toSet());
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
        this.computeActiveRunways();

    }
}
