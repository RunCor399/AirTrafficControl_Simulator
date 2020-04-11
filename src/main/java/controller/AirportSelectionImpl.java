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

public class AirportSelectionImpl implements AirportSelection {
    private final List<Airport> airportList = new ArrayList<>();
    private final Controller controller;

    {
        //Bologna
        RunwayEnd r1 = new RunwayEndImpl("33", new RadarPositionImpl(new Position2DImpl(-1212.0, 700.0)));
        RunwayEnd r2 = new RunwayEndImpl("15", new RadarPositionImpl(new Position2DImpl(1212.0, -700.0)));
        Runway run = new RunwayImpl(r1, r2);
        Vor vorBO33 = new VorImpl("33", new RadarPositionImpl(new Position2DImpl(-5542.1, 3200.0)));
        Vor vorBO15 = new VorImpl("15", new RadarPositionImpl(new Position2DImpl(5542.3, -3200.0)));
        Vor vorUPR = new VorImpl("UPR", new RadarPositionImpl(new Position2DImpl(15000.0, 13000.0)));
        Vor vorDWR = new VorImpl("DWR", new RadarPositionImpl(new Position2DImpl(-15000.0, -13000.0)));
        this.airportList.add(new AirportImpl("BO", "Bologna", Set.of(vorBO33, vorBO15, vorUPR, vorDWR), List.of(run),
                new RadarPositionImpl(new Position2DImpl(0.0, -100.0))));
        //Fiumicino
        RunwayEnd r20 = new RunwayEndImpl("20", new RadarPositionImpl(new Position2DImpl(1550.5, 564.3)));
        RunwayEnd r02 = new RunwayEndImpl("02", new RadarPositionImpl(new Position2DImpl(-1550.5, -564.3)));
        Runway runRoma1 = new RunwayImpl(r20, r02);
        RunwayEnd r11R = new RunwayEndImpl("11R", new RadarPositionImpl(new Position2DImpl(1550.5, 564.3)));
        RunwayEnd r29L = new RunwayEndImpl("29L", new RadarPositionImpl(new Position2DImpl(216.6, 4229.1)));
        Runway runRoma2 = new RunwayImpl(r11R, r29L);
        RunwayEnd r11L = new RunwayEndImpl("11L", new RadarPositionImpl(new Position2DImpl(-883.6, -2396.7)));
        RunwayEnd r29R = new RunwayEndImpl("29R", new RadarPositionImpl(new Position2DImpl(-2217.4, 1268.1)));
        Runway runRoma3 = new RunwayImpl(r11L, r29R);
        Vor vorRO20 = new VorImpl("20", new RadarPositionImpl(new Position2DImpl(6248.9, 2274.1)));
        Vor vorRO02 = new VorImpl("02", new RadarPositionImpl(new Position2DImpl(-6248.3, -2274.0)));
        Vor vorRO11R = new VorImpl("11R", new RadarPositionImpl(new Position2DImpl(3260.6, -4134.1)));
        Vor vorRO29L = new VorImpl("29L", new RadarPositionImpl(new Position2DImpl(-1493.5, 8927.6)));
        Vor vorRO11L = new VorImpl("11L", new RadarPositionImpl(new Position2DImpl(826.5, -7095.1)));
        Vor vorRO29R = new VorImpl("29R", new RadarPositionImpl(new Position2DImpl(-3927.5, 5966.5)));
        this.airportList.add(new AirportImpl("RO", "Fiumicino", 
                Set.of(vorRO20, vorRO02, vorRO11R, vorRO29L, vorRO11L, vorRO29R, vorUPR, vorDWR),
                List.of(runRoma1, runRoma2, runRoma3), new RadarPositionImpl(new Position2DImpl(0.0, -100.0))));
    }

    public AirportSelectionImpl(final Controller controller) {
        this.controller = controller;
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
