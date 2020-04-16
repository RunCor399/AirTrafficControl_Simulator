package controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Airport;

public class AirportSelectionImpl implements AirportSelection {
    private final List<Airport> airportList;
    private final Controller controller;
    private final AirportXMLReader dataReader;

    /**
     * The constructor of the {@link AirportSelectionImpl} class.
     * This constructor initializes the list of {@link Airport} read from the specific file.
     * 
     * @param controller the main controller, owner of the AirportManager.
     */
    public AirportSelectionImpl(final Controller controller) {
        this.controller = controller;
        this.dataReader = new AirportXMLReaderImpl("data/airports.xml");
        this.airportList = this.dataReader.getAirportListFromXML();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Map<String, String> getAllAirports() {
        return this.airportList.stream()
                .collect(Collectors.toMap(airport -> airport.getId(), airport -> airport.getName()));
    }

    private Optional<Airport> getAirportById(final String id) {
        return this.airportList.stream().filter(airport -> airport.getId().equals(id)).findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAirportById(final String id) {
        Objects.requireNonNull(id);
        Optional<Airport> found = this.getAirportById(id);
        if (found.isPresent()) {
            this.controller.setActualAirport(found.get());
        }
    }
}
