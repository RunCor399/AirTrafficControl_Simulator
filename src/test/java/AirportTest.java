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

    @org.junit.Test
    public void initializationTest() {
        RadarPosition position18 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition position36 = new RadarPositionImpl(new Position2DImpl(0.0, 10.0));
        RadarPosition position27 = new RadarPositionImpl(new Position2DImpl(1.0, 2.0));
        RadarPosition position9 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition positionVor1 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition positionVor2 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition parkingPosition = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));

        Vor vor1 = new VorImpl("alfa", positionVor1);
        Vor vor2 = new VorImpl("bravo", positionVor2);
        List<Vor> vorList = new LinkedList<>();
        vorList.add(vor1);
        vorList.add(vor2);

        RunwayEnd runway18 = new RunwayEndImpl("18", position18);
        RunwayEnd runway36 = new RunwayEndImpl("36", position36);
        RunwayEnd runway27 = new RunwayEndImpl("27", position27);
        RunwayEnd runway9 = new RunwayEndImpl("9", position9);

        Runway runway1836 = new RunwayImpl(runway18, runway36);
        Runway runway927 = new RunwayImpl(runway9, runway27);
        List<Runway> runwayList = new LinkedList<>();
        runwayList.add(runway1836);
        runwayList.add(runway927);

        Airport airport = new AirportImpl("LIRF", "Roma Fiumicino", vorList, runwayList, parkingPosition);

    }
}
