package model;

import java.util.Objects;

public class AirportImpl implements Airport {
    private final String airportId;
    private final String airportName;

    public AirportImpl(final String airportId, final String airportName) {
        Objects.requireNonNull(airportId);
        Objects.requireNonNull(airportName);
        this.airportId = airportId;
        this.airportName = airportName;
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
}
