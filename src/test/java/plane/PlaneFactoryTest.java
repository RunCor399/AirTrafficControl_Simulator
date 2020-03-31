package plane;

import static org.junit.Assert.assertTrue;

import controller.RandomPlaneFactory;
import controller.RandomPlaneFactoryImpl;
import model.Plane;
import model.Plane.Action;
import model.Position2DImpl;
import model.RadarPositionImpl;


/**
 * 
 * Test class which tests if the {@link RandomPlaneFactory} works as expected.
 *
 */
public class PlaneFactoryTest {

    private RandomPlaneFactory factory;

    /**
     * Initializing the PlaneFactory.
     */
    @org.junit.Before
    public void initialize() {
        this.factory = new RandomPlaneFactoryImpl(30000, 20000);
    }

    /**
     * Testing if the factory creates a new {@link Plane} that has to land.
     */
    @org.junit.Test
    public void testLandingPlane() {
        Plane plane = this.factory.randomLandingPlane();
        System.out.println(plane);
        assertTrue(plane != null);
        assertTrue(plane.getPlaneAction().equals(Action.LAND));
    }

    /**
     * Testing if the factory creates a new {@link Plane} that has to take off and it's actually still.
     */
    @org.junit.Test
    public void testTakeOffPlane() {
        Plane plane = this.factory.randomStillPlane(new RadarPositionImpl(new Position2DImpl(10.0, 20.0)));
        System.out.println(plane);
        assertTrue(plane != null);
        assertTrue(plane.getPlaneAction().equals(Action.TAKEOFF));
        assertTrue(plane.getSpeed().getAsKMH() == 0.0);
    }
}
