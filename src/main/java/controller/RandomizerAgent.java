package controller;

import java.util.Random;

/**
 * 
 * The purpose of this agent is to randomly generate a {@link Plane}, either on
 * the border of the radar or close to the airport.
 *
 */
public class RandomizerAgent extends Thread {

    private static final long DELTA_TIME = 500;
    private static final int MAX_WAIT = 10;
    private static final double NO_VALUE = -1;
    private static final int INITIAL_MULTIPLIER = 1;

    private final Random random;
    private volatile boolean stop;
    private volatile boolean pause;
    private volatile int multiplier;
    private double actualWaitTime;
    private double timeWaited;

    public RandomizerAgent() {
        this.random = new Random();
        this.pause = false;
        this.stop = false;
        this.multiplier = INITIAL_MULTIPLIER;
        this.actualWaitTime = NO_VALUE;
        this.timeWaited = NO_VALUE;
        this.setDaemon(true);
    }

    /**
     * 
     */
    @Override
    public void run() {
        while (!stop) {
            try {
                synchronized (this) {
                    if (pause) {
                        this.wait();
                    }
                    this.pause = false;
                }
            } catch (InterruptedException e) {
            }
            try {
                if (this.actualWaitTime == NO_VALUE) {
                    this.computeNewWaitTime();
                }
                sleep(DELTA_TIME / this.multiplier);
                this.timeWaited = this.timeWaited + DELTA_TIME;
                if (this.timeWaited >= this.actualWaitTime) {
                    this.actualWaitTime = NO_VALUE;
                    this.createNewPlane();
                }
            } catch (InterruptedException e) {
            }

        }
    }

    /**
     * Method that computes the next wait time before the creation of a new {@link Plane}. 
     */
    private void computeNewWaitTime() {
        this.actualWaitTime = this.random.nextInt(MAX_WAIT);
        System.out.println(this.actualWaitTime);
        this.timeWaited = 0;
    }

    /**
     * The method that creates the new random airplane.
     */
    private void createNewPlane() {
        System.out.println("NUOVO");
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
     * 
     */
    public void stopCount() {
        this.stop = true;
        interrupt();
    }

    /**
     * 
     */
    public void pauseThread() {
        this.pause = true;
        interrupt();
    }

}
