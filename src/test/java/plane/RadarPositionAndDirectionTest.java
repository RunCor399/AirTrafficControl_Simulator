package plane;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.AbstractDynamicElement;
import model.Direction;
import model.DirectionImpl;
import model.Plane;
import model.Position2DImpl;
import model.RadarPosition;
import model.RadarPositionImpl;

/**
 * 
 *  In this class some important methods implemented in {@link RadarPosition} and {@link Direction} are tested.
 *  These methods are used in {@link Plane} and {@link AbstractDynamicElement}.
 *
 */
public class RadarPositionAndDirectionTest {

    /**
     * This method tests if the distance between two points is well computed.
     */
    @org.junit.Test
    public void testDistanceBetweenPoints() {
        RadarPosition rad1 = new RadarPositionImpl(new Position2DImpl(2.0, -20.0));
        RadarPosition rad2 = new RadarPositionImpl(new Position2DImpl(5.0, 10.0));
        System.out.println(rad1.distanceFrom(rad2));
        assertTrue((int) rad1.distanceFrom(rad2) == (int) Math.sqrt(Math.pow(3.0, 2) + Math.pow(30.0, 2)));
    }

    /**
     * This method tests if the decision to go CounterClockWise is correct.
     */
    @org.junit.Test
    public void testCCWDecision() {
        Direction dir1 = new DirectionImpl(180);
        Direction dir2 = new DirectionImpl(200);
        assertTrue(dir1.isTurnCounterCW(dir2));
        dir2.sum(new DirectionImpl(170));
        assertFalse(dir1.isTurnCounterCW(dir2));
    }

    /**
     * This method tests if the direction to follow to reach a certain point is well computed.
     */
    @org.junit.Test
    public void testDirectionToTarget() {
        RadarPosition rad1 = new RadarPositionImpl(new Position2DImpl(0.0, 20.0));
        RadarPosition rad2 = new RadarPositionImpl(new Position2DImpl(100.0, 20.0));
        assertTrue((int) rad1.computeDirectionToTargetPosition(rad2).getAsDegrees() == 0);
        RadarPosition rad3 = new RadarPositionImpl(new Position2DImpl(0.0, 20.0));
        RadarPosition rad4 = new RadarPositionImpl(new Position2DImpl(0.0, 100.0));
        assertTrue((int) rad3.computeDirectionToTargetPosition(rad4).getAsDegrees() == 90);
        RadarPosition rad5 = new RadarPositionImpl(new Position2DImpl(0.0, 20.0));
        RadarPosition rad6 = new RadarPositionImpl(new Position2DImpl(0.0, -100.0));
        assertTrue((int) rad5.computeDirectionToTargetPosition(rad6).getAsDegrees() == 270);
    }
}
