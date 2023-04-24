package controller;

import model.Model;

/**
 * Implementation of the AbstractAgent.
 *
 */
public abstract class AbstractAgent extends Thread {

    /**
     * delta time used in thread's sleep.
     */
    protected static final long DELTA_TIME = 250;
    private static final int INITIAL_MULTIPLIER = 1;

    private final Model model;
    private volatile boolean stop;
    private volatile boolean pause;
    private volatile int multiplier;

    /**
     * Constructor of the AbstractAgent.
     * 
     * @param model
     */
    public AbstractAgent(final Model model) {
        this.model = model;
        this.stop = false;
        this.pause = false;
        this.multiplier = INITIAL_MULTIPLIER;
    }

    /**
     * 
     */
    @Override
    public void run() {
        while (!this.isStopped()) {
            try {
                synchronized (this) {
                    if (this.isPaused()) {
                        this.wait();
                    }
                }
                sleep(DELTA_TIME / this.getMultiplier());
                this.executeAgentAction();
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * 
     */
    protected abstract void executeAgentAction();

    /**
     * method that returns the {@link Model} instance.
     * 
     * @return model to which apply operations
     */
    public Model getModel() {
        return this.model;
    }

    /**
     * method to check whether a thread is running or not.
     * 
     * @return true if thread has been stopped, false otherwise
     */
    public boolean isStopped() {
        return this.stop;
    }

    /**
     * method to check whether a thread is paused or running.
     * 
     * @return true if thread is paused, false otherwise
     */
    public boolean isPaused() {
        return this.pause;
    }

    /**
     * method that gets the current time multiplier.
     * 
     * @return value of time multiplier
     */
    public int getMultiplier() {
        return this.multiplier;
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
