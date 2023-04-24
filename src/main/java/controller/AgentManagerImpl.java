package controller;

import java.util.List;

import model.Model;
import view.View;

public class AgentManagerImpl implements AgentManager {
    private List<AbstractAgent> agents;
    private final Model model;
    private final Controller controller;
    private final View mainView;

    public AgentManagerImpl(final Model model, final View view, final Controller controller) {
        this.model = model;
        this.mainView = view;
        this.controller = controller;
        this.setUpAgents();
    }

    /**
     * In this method we can find the creation of all the agents of the application.
     */
    private void setUpAgents() {
        RandomizerAgent planeRandomizer = new RandomizerAgent(this.model);
        MovementAgent movementAgent = new MovementAgent(this.model, this.mainView, this.controller);
        CollisionAgent collisionAgent = new CollisionAgent(this.model, this.mainView, this.controller);
        this.agents = List.of(planeRandomizer, movementAgent, collisionAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopThreads() {
        this.agents.stream().forEach(agent -> agent.stopThread());
        this.setUpAgents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseThreads() {
        this.agents.stream().forEach(agent -> agent.pauseThread());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSimulationRate(final int rate) {
        this.agents.stream().forEach(agent -> agent.setMultiplier(rate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startThreads() {
        this.agents.stream().forEach(agent -> {
            if (agent.isAlive()) {
                agent.resumeThread();
            } else {
                agent.start();
            }
        });
    }
}
