package controller;

import model.Model;
import view.View;

public class MovementAgent extends Thread {

    private static final long DELTA_UPDATE = 500;

    private final Model model;
    private final View view;
    private volatile boolean stop;
    private volatile boolean pause;

    public MovementAgent(final Model model, final View view) {
        this.model = model;
        this.view = view;
        this.stop = false;
        this.pause = false;
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
                //compute wait time
              sleep(DELTA_UPDATE);
            } catch (InterruptedException exception) {
            }
        }
    }

    private void updatePlanesPositionAndView() {
        this.model.computeAllPlanePositions();
        this.view.radarUpdate(this.model.getAllPlanes());
    }

    /**
     * method that stops the thread.
     */
    public final void stopThread() {
        this.stop = true;
        interrupt();
    }

    /**
     * method that resumes a thread currently in pause.
     */
    public synchronized void resumeThread() {
        if (this.pause) {
            this.pause = false;
            this.notify();
        }
    }

    /**
     * method that pauses the thread.
     */
    public final void pauseThread() {
        this.pause = true;
        interrupt();
    }
}
