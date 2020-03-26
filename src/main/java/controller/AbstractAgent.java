package controller;

import model.Model;

public abstract class AbstractAgent extends Thread {

    private static final long DELTA_TIME = 500;
    private static final int INITIAL_MULTIPLIER = 1;

    private final Model model;
    private volatile boolean stop;
    private volatile boolean pause;
    private volatile int multiplier;

    public AbstractAgent(final Model model) {
        this.model = model;
        this.stop = false;
        this.pause = false;
        this.multiplier = INITIAL_MULTIPLIER;
    }

    /**
     * method that takes a time multiplier as argument and sets it.
     * 
     * @param multiplier
     */
    public void setMultiplier(final int multiplier) {
        if (multiplier < 1) {
            throw new IllegalStateException();
        }
        this.multiplier = multiplier;
    }

    /**
     * method that stops the thread.
     */
    public void stopThread() {
        this.stop = true;
        interrupt();
    }

    /**
     * method that pauses the thread.
     */
    public void pauseThread() {
        this.pause = true;
        interrupt();
    }

    /**
     * method that resume's a paused thread.
     */
    public synchronized void resumeThread() {
        if (this.pause) {
            this.pause = false;
            this.notify();
        }
    }
}
