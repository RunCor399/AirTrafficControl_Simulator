package plane;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controller.RandomPlaneFactoryImpl;
import model.Airport;
import model.AirportImpl;
import model.DirectionImpl;
import model.Plane;
import model.Plane.Action;
import model.PlaneImpl;
import model.Position2DImpl;
import model.RadarPositionImpl;
import model.Runway;
import model.RunwayEnd;
import model.RunwayEndImpl;
import model.RunwayImpl;
import model.SpeedImpl;
import model.Vor;
import model.VorImpl;
import model.exceptions.OperationNotAvailableException;


/**
 * 
 * In this test class, the main functions of a {@link Plane} are tested.
 *
 */
public class PlaneTest {
    private Airport airport;
    private Plane plane;
    private Plane plane2;
    private Set<Plane> set;

    /**
     * Initializing an airport and two planes.
     */
    @org.junit.Before
    public void init() {
        RunwayEnd r1 = new RunwayEndImpl("20", new RadarPositionImpl(new Position2DImpl(0.0, 0.0)));
        RunwayEnd r2 = new RunwayEndImpl("02", new RadarPositionImpl(new Position2DImpl(60.0, 0.0)));
        RunwayEnd r3 = new RunwayEndImpl("20", new RadarPositionImpl(new Position2DImpl(0.0, 60.0)));
        RunwayEnd r4 = new RunwayEndImpl("02", new RadarPositionImpl(new Position2DImpl(200.0, 60.0)));
        Vor vor = new VorImpl("1", new RadarPositionImpl(new Position2DImpl(0.0, 0.0)));
        Runway run = new RunwayImpl(r1, r2);
        Runway run2 = new RunwayImpl(r3, r4);
        this.airport = new AirportImpl("1", "airportName", List.of(vor), Arrays.asList(run, run2),
                new RadarPositionImpl(new Position2DImpl(0.0, 1.0)));
        this.plane = new PlaneImpl(3, "ALI", Action.LAND, new RadarPositionImpl(new Position2DImpl(-1000.0, 0.0)),
                new SpeedImpl(100.0), 200, new DirectionImpl(0));
        this.plane2 = new PlaneImpl(5, "EMU", Action.TAKEOFF, airport.getParkingPosition(), new SpeedImpl(0.0), 0,
                new DirectionImpl(200));
        this.airport.setActiveRunways("20");
        set = new HashSet<>();
        set.add(plane);
        set.add(plane2);
        assertTrue(run.getRunwayStatus().isPresent());
        assertTrue(this.airport.getActiveRunways().isPresent());
        assertEquals(run.getRunwayStatus().get().getNumRunwayEnd(), "20");
    }

    /**
     * Testing if plane can take off.
     * In this case the test should fail because the plane has to land.
     * 
     * @throws OperationNotAvailableException
     */
    @org.junit.Test(expected = OperationNotAvailableException.class)
    public void testWrongTakeOff() throws OperationNotAvailableException {
        assertTrue(this.airport.getActiveRunways().isPresent());
        this.plane.takeOff(airport);
    }

    /**
     * Testing if plane can take off.
     * In this case the plane should take off.
     * 
     * @throws OperationNotAvailableException
     */
    @org.junit.Test
    public void testTakeOff() throws OperationNotAvailableException {
        assertTrue(this.airport.getActiveRunways().isPresent());
        this.plane2.takeOff(airport);
    }

    /**
     * Testing if plane can land.
     * In this case the plane should land because is correctly aligned.
     * 
     * @throws OperationNotAvailableException
     */
    @org.junit.Test
    public void testLand() {
        try {
            this.plane.land(this.airport);
        } catch (OperationNotAvailableException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(this.plane.getSpeed().getAsKMH() == 0.0);
        assertTrue(this.plane.isActionPerformed());
    }

    /**
     * Testing if plane can land twice (which is not possible).
     */
    @org.junit.Test
    public void testLandTwice() {
        try {
            this.plane.land(this.airport);
        } catch (OperationNotAvailableException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(this.plane.getSpeed().getAsKMH() == 0.0);
        assertThrows(OperationNotAvailableException.class, () -> this.plane.land(airport));
    }
}
