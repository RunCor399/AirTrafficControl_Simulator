package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    {
        RunwayEnd r1 = new RunwayEndImpl("33", new RadarPositionImpl(new Position2DImpl(-1212.0, 700.0)));
        RunwayEnd r2 = new RunwayEndImpl("15", new RadarPositionImpl(new Position2DImpl(1212.0, -700.0)));
        Runway run = new RunwayImpl(r1, r2);
        Vor vor = new VorImpl("33", new RadarPositionImpl(new Position2DImpl(-200.0, 0.0)));
        this.airportList.add(new AirportImpl("BO", "Bologna", Set.of(vor), List.of(run),
                new RadarPositionImpl(new Position2DImpl(0.0, 1.0))));
    }

    public final Map<String, String> getAllAirports() {
        //TODO
        return null;
    }
}
