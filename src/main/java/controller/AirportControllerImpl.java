package controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.Airport;
import model.Model;
import model.Runway;

/**
 *
 * An implementation of {@link AirportController}.
 *
 */
public class AirportControllerImpl implements AirportController {
    private final Model model;
    private final Controller controller;

    public AirportControllerImpl(final Model model, final Controller controller) {
        this.model = model;
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActualAirport(final Airport airport) {
        Objects.requireNonNull(airport);
        this.model.setAirport(airport);
        this.controller.resetGameContext();
    }

    /**
     * {@inheritDoc}
     */
    public Airport getActualAirport() {
        return this.model.getAirport();
    }

    /**
     * {@inheritDoc}
     */
    public Optional<List<Runway>> getAirportRunways() {
        return this.model.getAirport().getRunways();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeRunwayEndStatus(final String runwayEnd) {
        this.model.getAirport().setActiveRunways(runwayEnd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getRunwayEndStatus(final String runwayEnd) {
        for (Runway r : this.model.getAirport().getRunways().get()) {
            if (r.checkRunwayEnd(runwayEnd)) {
                return r.getRunwayEnds().getX().getNumRunwayEnd().equals(runwayEnd)
                        ? r.getRunwayEnds().getX().getStatus()
                        : r.getRunwayEnds().getY().getStatus();
            }
        }
        throw new IllegalArgumentException();
    }
}
