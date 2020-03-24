package controller;

import java.util.List;
import java.util.Random;

import model.DirectionImpl;
import model.Plane;
import model.PlaneBuilder;
import model.PlaneBuilderImpl;
import model.Position2DImpl;
import model.RadarPosition;
import model.RadarPositionImpl;
import model.Speed;
import model.SpeedImpl;
import model.Plane.Action;

/**
 * 
 * An implementation of a {@link RandomPlaneFactory}.
 *
 */
public class RandomPlaneFactoryImpl {

    private static final Speed STANDARD_SPEED = new SpeedImpl(200.0);
    private static final double STANDARD_ALTITUDE = 300;
    private static final RadarPosition CENTER = new RadarPositionImpl(new Position2DImpl(0.0, 0.0));
    private static final int MAX_VALUE = 999;
    private final List<String> companies = List.of("ALI", "AAL", "ACA", "AZA", "AFR", "ACI", "BAW", "COA", "DAL", "MSR",
            "UAE", "FDX", "IRA");

    private final double xBound;
    private final double yBound;
    private Random random;

    /**
     * Constructor of the factory.
     * 
     * @param xBound the bound for the x coordinate.
     * @param yBound the bound for the y coordinate.
     */
    public RandomPlaneFactoryImpl(final double xBound, final double yBound) {
        this.xBound = xBound;
        this.yBound = yBound;
        this.random = new Random();
    }

    private PlaneBuilder getStandardPlane() {
        int planeId = this.random.nextInt(MAX_VALUE);
        String companyName = this.companies.get(this.random.nextInt(this.companies.size() - 1));
        return new PlaneBuilderImpl(planeId, companyName);
    }

    public Plane randomLandingPlane() {
        double xPosition = this.random.nextBoolean() ? -this.xBound : this.yBound;
        double yPosition = this.random.nextBoolean() ? -this.yBound : this.yBound;
        RadarPosition planePosition = new RadarPositionImpl(new Position2DImpl(xPosition, yPosition));
        PlaneBuilder builder = this.getStandardPlane();
        builder.planeAction(Action.LAND);
        builder.position(planePosition);
        builder.speed(STANDARD_SPEED);
        builder.altitude(STANDARD_ALTITUDE);
        builder.direction(planePosition.computeDirectionToTargetPosition(CENTER));
        return builder.build();
    }

    public Plane randomStillPlane(final RadarPosition planePosition) {
        PlaneBuilder builder = this.getStandardPlane();
        builder.planeAction(Action.TAKEOFF);
        builder.position(planePosition);
        builder.speed(new SpeedImpl(0.0));
        builder.altitude(0);
        builder.direction(new DirectionImpl(this.random.nextInt(360)));
        return builder.build();
    }

}
