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
public class RandomizerAgent extends Thread {

    private static final long DELTA_TIME = 500;
    private static final int MILLIS_TO_SEC = 1000;
    private static final int MAX_WAIT = 30;
    private static final int MIN_WAIT = 16;
    private static final double NO_VALUE = -1;
    private static final int INITIAL_MULTIPLIER = 1;

    private final Random random;
    private final Model model;
    private volatile boolean stop;
    private volatile boolean pause;
    private volatile int multiplier;
    private double actualWaitTime;
    private double timeWaited;
    private final RandomPlaneFactory planeFactory;

    public RandomizerAgent(final Model model) {
        this.model = model;
        this.random = new Random();
        this.pause = false;
        this.stop = false;
        this.multiplier = INITIAL_MULTIPLIER;
        this.actualWaitTime = NO_VALUE;
        this.timeWaited = NO_VALUE;
        this.planeFactory = new RandomPlaneFactoryImpl(RadarPositionImpl.X_BOUND, RadarPositionImpl.Y_BOUND);
        this.setDaemon(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        while (!this.stop) {
            try {
                synchronized (this) {
                    if (this.pause) {
                        this.wait();
                    }
                }
                sleep(DELTA_TIME / this.multiplier);
                this.timeWaited = this.timeWaited + ((double) DELTA_TIME / MILLIS_TO_SEC);
                if (this.timeWaited >= this.actualWaitTime) {
                    this.computeNewWaitTime();
                    this.createNewPlane();
                }
            } catch (InterruptedException e) {
            }

        }
    }

    /**
     * Method that computes the next wait time before the creation of a new
     * {@link Plane}.
     */
    private void computeNewWaitTime() {
        this.actualWaitTime = this.random.nextInt(MAX_WAIT - MIN_WAIT) + MIN_WAIT;
        this.timeWaited = 0;
    }

    /**
     * The method that creates the new random airplane.
     */
    private void createNewPlane() {
        Plane newPlane = this.random.nextBoolean() ? this.planeFactory.randomLandingPlane()
                : this.planeFactory.randomStillPlane(this.model.getAirport().getParkingPosition());
        this.model.addPlane(newPlane);
//DEBUG CODE
//        System.out.println(newPlane);
//        System.out.println("Position -> x: " + newPlane.getPosition().getPosition().getX());
//        System.out.println("y: " + newPlane.getPosition().getPosition().getY());
//        System.out.println(this.model.getAllPlanes().size());
    }

    /**
     * 
     * Method to set the multiplier of the update frequency.
     * 
     * @param multiplier the frequency multiplier.
     */
    public void setMultiplier(final int multiplier) {
        if (multiplier < 1) {
            throw new IllegalArgumentException();
        }
        this.multiplier = multiplier;
    }

    /**
     * Method that stops the thread.
     */
    public void stopThread() {
        this.stop = true;
        interrupt();
    }

    /**
     * Method that resumes the thread.
     */
    public synchronized void resumeThread() {
        if (this.pause) {
            this.pause = false;
            this.notify();
        }
    }

    /**
     * Method that temporarily pauses the current thread.
     */
    public void pauseThread() {
        this.pause = true;
        interrupt();
    }

}
