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
    private List<Vor> vorList = new LinkedList<>();

    /**
     * Constructor of a standard airport.
     * 
     * @param airportId
     * 
     * @param airportName
     * 
     * @param vorList
     */
    public AirportImpl(final String airportId, final String airportName, final List<Vor> vorList) {
        Objects.requireNonNull(airportId);
        Objects.requireNonNull(airportName);
        Objects.requireNonNull(vorList);
        this.airportId = airportId;
        this.airportName = airportName;
        this.vorList = vorList;
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
    public List<Vor> getVorList() {
        return this.vorList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vor getVorById(final String vorId) {
        Optional<Vor> vor = this.isVorPresent(vorId);
        if (vor.isPresent()) {
            return vor.get();
        }

        throw new IllegalStateException();
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
}
