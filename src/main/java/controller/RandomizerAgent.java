package controller;

/**
 * 
 * The purpose of this agent is to randomly generate planes, either on the
 * border of the radar or close to the airport.
 *
 */
public class RandomizerAgent extends Thread {

    private static final long DELTA_TIME = 500;

    private volatile boolean stop = false;
    private volatile boolean pause = false;

    public RandomizerAgent() {
        // TODO Auto-generated constructor stub
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
        }
        try {
            sleep(DELTA_TIME);
            //DOSTUFF
        } catch (InterruptedException e) {

        }
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
