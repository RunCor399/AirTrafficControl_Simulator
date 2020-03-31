package plane;
import org.junit.Assert;

import model.DirectionImpl;
import model.Plane;
import model.Plane.Action;
import model.PlaneBuilder;
import model.PlaneBuilderImpl;
import model.Position2DImpl;
import model.RadarPositionImpl;
import model.SpeedImpl;

/**
 * 
 * Test class used to check if the {@link PlaneBuilder} works as expected.
 *
 */
public class BuilderTest {

    /**
     * Testing the creation of a {@link Plane}.
     * There should be no errors.
     */
    @org.junit.Test
    public void testCreation() {
        PlaneBuilder builder = new PlaneBuilderImpl(20, "ALI");
        builder.position(new RadarPositionImpl(new Position2DImpl(0.0, 0.0)));
        builder.planeAction(Plane.Action.LAND);
        builder.altitude(1000.0);
        builder.direction(new DirectionImpl(40));
        builder.speed(new SpeedImpl(200.0));
        Plane plane = builder.build();
        System.out.println(plane);
        Assert.assertTrue(plane.getAirplaneId() == 20);
        Assert.assertTrue(plane.getCompanyName().equals("ALI"));
        Assert.assertTrue(plane.getPlaneAction().equals(Action.LAND));
        Assert.assertTrue(plane.getPlaneAction().equals(Action.LAND));
        Assert.assertTrue(plane.getAltitude() == 1000.0);
        Assert.assertTrue(plane.getPosition().getPosition().getX() == 0.0);
        Assert.assertTrue(plane.getPosition().getPosition().getY() == 0.0);
        Assert.assertTrue(plane.getSpeed().getAsKnots() == 200.0);
    }

    /**
     * Testing the creation of a {@link Plane} twice with the same builder.
     * {@link IllegalStateException} should be thrown.
     */
    @org.junit.Test(expected = IllegalStateException.class)
    public void testDoubleCreation() {
        PlaneBuilder builder = new PlaneBuilderImpl(15, "RYA");
        builder.position(new RadarPositionImpl(new Position2DImpl(10.0, 5.0)));
        builder.planeAction(Plane.Action.LAND);
        builder.altitude(1000.0);
        builder.direction(new DirectionImpl(40));
        builder.speed(new SpeedImpl(200.0));
        Plane plane = builder.build();
        plane = builder.build();
        System.out.println(plane);
    }

    /**
     * Testing the creation of a {@link Plane} without all the necessary values.
     * {@link IllegalStateException} should be thrown.
     */
    @org.junit.Test(expected = IllegalStateException.class)
    public void testIncompleteCreation() {
        PlaneBuilder builder = new PlaneBuilderImpl(55, "IIA");
        builder.position(new RadarPositionImpl(new Position2DImpl(400.0, 20.0)));
        builder.planeAction(Plane.Action.TAKEOFF);
        builder.direction(new DirectionImpl(40));
        builder.speed(new SpeedImpl(200.0));
        Plane plane = builder.build();
        System.out.println(plane);
    }
}
