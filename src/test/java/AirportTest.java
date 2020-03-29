import java.util.LinkedList;
import java.util.List;

import model.Airport;
import model.AirportImpl;
import model.Position2DImpl;
import model.RadarPosition;
import model.RadarPositionImpl;
import model.Runway;
import model.RunwayEnd;
import model.RunwayEndImpl;
import model.RunwayImpl;
import model.Vor;
import model.VorImpl;

public class AirportTest {
    private Airport airport;

    @org.junit.Before
    public void initializationTest() {
        RadarPosition position18 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition position36 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition position27 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition position9 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition positionVor1 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition positionVor2 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition parkingPosition = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));

        Vor vor1 = new VorImpl("alfa", positionVor1);
        Vor vor2 = new VorImpl("bravo", positionVor2);
        List<Vor> vorList = new LinkedList<>();
        vorList.add(vor1);
        vorList.add(vor2);

        RunwayEnd runwayEnd18 = new RunwayEndImpl("18", position18);
        RunwayEnd runwayEnd36 = new RunwayEndImpl("36", position36);
        RunwayEnd runwayEnd27 = new RunwayEndImpl("27", position27);
        RunwayEnd runwayEnd9 = new RunwayEndImpl("9", position9);

        Runway runway1836 = new RunwayImpl(runwayEnd18, runwayEnd36);
        Runway runway927 = new RunwayImpl(runwayEnd9, runwayEnd27);
        List<Runway> runwayList = new LinkedList<>();
        runwayList.add(runway1836);
        runwayList.add(runway927);

        this.airport = new AirportImpl("LIRF", "Roma Fiumicino", vorList, runwayList, parkingPosition);
        System.out.println(airport.toString());
    }

    @org.junit.Test
    public void addNewRunwayTest() {
        RadarPosition position1 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition position19 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));

        RunwayEnd runwayEnd1 = new RunwayEndImpl("1", position1);
        RunwayEnd runwayEnd19 = new RunwayEndImpl("19", position19);

        Runway runway119 = new RunwayImpl(runwayEnd1, runwayEnd19);
        this.airport.addRunway(runway119);

        System.out.println(airport.toString());
    }
}
