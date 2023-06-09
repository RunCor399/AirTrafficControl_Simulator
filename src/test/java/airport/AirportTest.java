package airport;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;

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

/**
 * Test class to check if operations onto the elements of a {@link Airport} works. 
 * 
 *
 */
public class AirportTest {
    private Airport airport;

    /**
     * Initialization of an {@link Airport} and of two different {@link Vor}.
     * 
     */
    @org.junit.Before
    public void initializationTest() {
        RadarPosition position18 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition position36 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition position27 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition position09 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition positionVor1 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition positionVor2 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition parkingPosition = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));

        Vor vor1 = new VorImpl("alfa", positionVor1);
        Vor vor2 = new VorImpl("bravo", positionVor2);
        Set<Vor> vorSet = new HashSet<>();
        vorSet.add(vor1);
        vorSet.add(vor2);

        RunwayEnd runwayEnd18 = new RunwayEndImpl("18", position18);
        RunwayEnd runwayEnd36 = new RunwayEndImpl("36", position36);
        RunwayEnd runwayEnd27 = new RunwayEndImpl("27", position27);
        RunwayEnd runwayEnd09 = new RunwayEndImpl("09", position09);

        Runway runway1836 = new RunwayImpl(runwayEnd18, runwayEnd36);
        Runway runway0927 = new RunwayImpl(runwayEnd09, runwayEnd27);
        List<Runway> runwayList = new LinkedList<>();
        runwayList.add(runway1836);
        runwayList.add(runway0927);

        this.airport = new AirportImpl("LIRF", "Roma Fiumicino", vorSet, runwayList, parkingPosition);
    }

    /**
     * Test that adds a new {@link Runway} to the current {@link Airport}.
     * 
     */
    @org.junit.Test
    public void addNewRunwayTest() {
        RadarPosition position1 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        RadarPosition position19 = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));

        RunwayEnd runwayEnd01 = new RunwayEndImpl("01", position1);
        RunwayEnd runwayEnd19 = new RunwayEndImpl("19", position19);

        Runway runway0119 = new RunwayImpl(runwayEnd01, runwayEnd19);
        assertEquals(2, this.airport.getRunways().get().size());
        this.airport.addRunway(runway0119);
        assertEquals(3, this.airport.getRunways().get().size());
    }

    /**
     * Test that adds a new {@link Vor} to the current {@link Airport}.
     * 
     */
    @org.junit.Test
    public void addNewVorTest() {
        assertEquals(2, this.airport.getVorList().get().size());
        RadarPosition positionNewVor = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        Vor newVor = new VorImpl("charlie", positionNewVor);

        this.airport.addVor(newVor);
        assertEquals(3, this.airport.getVorList().get().size());
    }

    /**
     * Test that tries to add a {@link Vor} already present in vorSet.
     * 
     */
    @org.junit.Test
    public void alreadyPresentVorTest() {
        RadarPosition positionNewVor = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
        Vor newVor = new VorImpl("alfa", positionNewVor);

        this.airport.addVor(newVor);
        assertEquals(2, this.airport.getVorList().get().size());
    }

    /**
     * Test that gets a {@link Vor} by its id.
     * 
     */
    @org.junit.Test
    public void findVorByIdTest() {
        Optional<Vor> vorById = this.airport.getVorById("bravo");
        Assert.assertTrue(vorById.isPresent());
    }

    /**
     * Test that tries to get a {@link Vor} not in the vorSet.
     * 
     */
    @org.junit.Test
    public void findVorErrorTest() {
        assertEquals(Optional.empty(), this.airport.getVorById("delta"));
    }

    /**
     * Test of activation/deactivation of {@link Runway}.
     * 
     */
    @org.junit.Test
    public void activeRunwaysTest() {
        List<RunwayEnd> activeEndsList = new LinkedList<>();

        assertEquals(Optional.empty(), this.airport.getActiveRunways());
        this.airport.setActiveRunways("18");
        this.airport.setActiveRunways("27");
        assertEquals(2, this.airport.getActiveRunways().get().size());

        for (final Runway runway : this.airport.getActiveRunways().get()) {
            activeEndsList.add(runway.getRunwayStatus().get());
        }
        assertEquals(2, activeEndsList.size());

        activeEndsList.clear();
        this.airport.setActiveRunways("9");
        for (final Runway runway : this.airport.getActiveRunways().get()) {
            activeEndsList.add(runway.getRunwayStatus().get());
        }
        assertEquals(2, activeEndsList.size());
        activeEndsList.clear();

        this.airport.deactivateAllRunways();
        assertEquals(Optional.empty(), this.airport.getActiveRunways());
    }
}
