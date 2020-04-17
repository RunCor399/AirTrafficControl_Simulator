package controller;

import model.Model;
import view.View;

public class AgentManagerImpl implements AgentManager {
    private RandomizerAgent planeRandomizer;
    private MovementAgent movementAgent;
    private CollisionAgent collisionAgent;
    private final Model model;
    private final Controller controller;
    private final View mainView;

    public AgentManagerImpl(final Model model, final View view, final Controller controller) {
        this.model = model;
        this.mainView = view;
        this.controller = controller;
        this.setUpAgents();
    }

    private void setUpAgents() {
        this.planeRandomizer = new RandomizerAgent(this.model);
        this.movementAgent = new MovementAgent(this.model, this.mainView, this.controller);
        this.collisionAgent = new CollisionAgent(this.model, this.mainView, this.controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopThreads() {
        this.planeRandomizer.stopThread();
        this.movementAgent.stopThread();
        this.collisionAgent.stopThread();
        this.setUpAgents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseThreads() {
        this.planeRandomizer.pauseThread();
        this.movementAgent.pauseThread();
        this.collisionAgent.pauseThread();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSimulationRate(final int rate) {
        this.planeRandomizer.setMultiplier(rate);
        this.movementAgent.setMultiplier(rate);
        this.collisionAgent.setMultiplier(rate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startThreads() {
        if (this.planeRandomizer.isAlive()) {
            this.planeRandomizer.resumeThread();
        } else {
            this.planeRandomizer.start();
        }

        if (this.movementAgent.isAlive()) {
            this.movementAgent.resumeThread();
        } else {
            this.movementAgent.start();
        }
        if (this.collisionAgent.isAlive()) {
            this.collisionAgent.resumeThread();
        } else {
            this.collisionAgent.start();
        }
    }
}
