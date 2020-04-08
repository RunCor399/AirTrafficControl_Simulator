package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import model.Airport;
import model.AirportImpl;
import model.Position2DImpl;
import model.RadarPositionImpl;
import model.Runway;
import model.RunwayEnd;
import model.RunwayEndImpl;
import model.RunwayImpl;
import model.Vor;
import model.VorImpl;

public class AirportSelectionImpl {
    private final List<Airport> airportList = new ArrayList<>();
    private final Controller controller;

    {
        RunwayEnd r1 = new RunwayEndImpl("33", new RadarPositionImpl(new Position2DImpl(-1212.0, 700.0)));
        RunwayEnd r2 = new RunwayEndImpl("15", new RadarPositionImpl(new Position2DImpl(1212.0, -700.0)));
        Runway run = new RunwayImpl(r1, r2);
        Vor vor = new VorImpl("33", new RadarPositionImpl(new Position2DImpl(-200.0, 0.0)));
        this.airportList.add(new AirportImpl("BO", "Bologna", Set.of(vor), List.of(run),
                new RadarPositionImpl(new Position2DImpl(0.0, 1.0))));

        RunwayEnd r20 = new RunwayEndImpl("20", new RadarPositionImpl(new Position2DImpl(1550.5, 564.3)));
        RunwayEnd r02 = new RunwayEndImpl("02", new RadarPositionImpl(new Position2DImpl(-1550.5, -564.3)));
        Runway runRoma1 = new RunwayImpl(r20, r02);
        RunwayEnd r11R = new RunwayEndImpl("11R", new RadarPositionImpl(new Position2DImpl(1550.5, 564.3)));
        RunwayEnd r29L = new RunwayEndImpl("29L", new RadarPositionImpl(new Position2DImpl(1693.0, 3101.0)));
        Runway runRoma2 = new RunwayImpl(r11R, r29L);
        // correct this
        RunwayEnd r11L = new RunwayEndImpl("11L", new RadarPositionImpl(new Position2DImpl(-1550.5, -564.3)));
        RunwayEnd r29R = new RunwayEndImpl("29R", new RadarPositionImpl(new Position2DImpl(1550.5, 564.3)));
        Runway runRoma3 = new RunwayImpl(r11L, r29R);
        this.airportList.add(new AirportImpl("RO", "Roma", Set.of(vor), List.of(runRoma1, runRoma2, runRoma3),
                new RadarPositionImpl(new Position2DImpl(0.0, 1.0))));
    }

    public AirportSelectionImpl(final Controller controller) {
        this.controller = controller;
    }

    public final Map<String, String> getAllAirports() {
        return this.airportList.stream()
                .collect(Collectors.toMap(airport -> airport.getId(), airport -> airport.getName()));
    }

    private Optional<Airport> getAirportById(final String id) {
        return this.airportList.stream().filter(airport -> airport.getId().equals(id)).findFirst();
    }

    public void setAirportById(final String id) {
        Objects.requireNonNull(id);
        Optional<Airport> found = this.getAirportById(id);
        if (found.isPresent()) {
            this.controller.setActualAirport(found.get());
        }
    }
}
