package controller;

import java.util.Random;

import model.Model;
import model.Plane;
import model.RadarPositionImpl;

/**
 * 
 * The purpose of this agent is to randomly generate a {@link Plane}, either on
 * the border of the radar or close to the airport.
 *
 */
public class RandomizerAgent extends AbstractAgent {

    private static final int MILLIS_TO_SEC = 1000;
    private static final int MAX_WAIT = 150;
    private static final int MIN_WAIT = 30;
    private static final double NO_VALUE = 0;

    private final Random random;
    private double actualWaitTime;
    private double timeWaited;
    private final RandomPlaneFactory planeFactory;

    public RandomizerAgent(final Model model) {
        super(model);
        this.random = new Random();
        this.actualWaitTime = NO_VALUE;
        this.timeWaited = NO_VALUE;
        this.planeFactory = new RandomPlaneFactoryImpl(RadarPositionImpl.X_BOUND, RadarPositionImpl.Y_BOUND);
        this.setDaemon(true);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeAgentAction() {
        this.timeWaited = this.timeWaited + ((double) DELTA_TIME / MILLIS_TO_SEC);
        if (this.timeWaited >= this.actualWaitTime) {
            this.computeNewWaitTime();
            this.createNewPlane();
        }
    }

    /**
     * Method that computes the next wait time before the creation of a new
     * {@link Plane}.
     */
    private void computeNewWaitTime() {
        this.actualWaitTime = this.random.nextInt(MAX_WAIT - MIN_WAIT) + MIN_WAIT;
        this.timeWaited = NO_VALUE;
    }

    /**
     * The method that creates the new random airplane.
     */
    private void createNewPlane() {
        Plane newPlane = this.random.nextBoolean() ? this.planeFactory.randomLandingPlane()
                : this.planeFactory.randomStillPlane(this.getModel().getAirport().getParkingPosition());
        this.getModel().addPlane(newPlane);
//DEBUG CODE
//        System.out.println(newPlane);
//        System.out.println("Position -> x: " + newPlane.getPosition().getPosition().getX());
//        System.out.println("y: " + newPlane.getPosition().getPosition().getY());
//        System.out.println(this.getModel().getAllPlanes().size());
    }

}
