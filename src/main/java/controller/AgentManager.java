package controller;

public interface AgentManager {

    /**
     * Method that stops all the threads of the application.
     * It also initializes them in order to be able to start threads again.
     */
    void stopThreads();

    /**
     * Method that pauses all the threads of the application.
     */
    void pauseThreads();

    /**
     * Method that sets the simulation rate of all the threads of the application.
     * 
     * @param rate the integer value that represents the rate of update of the
     *             threads.
     */
    void setSimulationRate(int rate);

    /**
     * Method that resumes all the threads of the application. If a thread hasn't
     * started yet, it will start.
     */
    void startThreads();

}
