package controller;

import javafx.application.Platform;
import model.Model;
import model.Plane;
import view.View;

public class CollisionAgent extends AbstractAgent {

    private static final int COLLISION_DISTANCE = 500;
    private static final int WARNING_DISTANCE = 2500;
    private static final int WARNING_ALTITUDE = 100;
    private final View view;
    private final Controller controller;

    public CollisionAgent(final Model model, final View view, final Controller controller) {
        super(model);
        this.view = view;
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeAgentAction() {
        this.getModel().getAllPlanes().stream().filter(plane -> plane.canMove()).peek(x -> x.setCollisionWarning(false))
                .forEach(x -> checkCollision(x));
    }

    /**
     * Method that watches if a plane is colliding.
     * 
     * @param p is the plane to check.
     */
    private void checkCollision(final Plane p) {
        if (this.getModel().getAllPlanes().stream().filter(plane -> plane.canMove())
                .filter(plane -> plane.getAirplaneId() != p.getAirplaneId())
                .filter(plane -> Math.abs(plane.getAltitude() - p.getAltitude()) <= WARNING_ALTITUDE)
                .filter(plane -> Math.abs(plane.getPosition().distanceFrom(p.getPosition())) <= WARNING_DISTANCE)
                .peek(plane -> plane.setCollisionWarning(true)).peek(plane -> p.setCollisionWarning(true))
                .filter(plane -> Math.abs(plane.getPosition().distanceFrom(p.getPosition())) <= COLLISION_DISTANCE)
                .count() != 0) {

            collisionDetected();
        }

    }

    /**
     * Method that close the game if two plane collide.
     */
    private void collisionDetected() {
        this.controller.resetGameContext();
        this.view.windowAlert("Hai perso", "C'è stata una collisione tra due aerei");
        Platform.runLater(() -> this.view.changeScene(this.view.getSceneFactory().loadMenu()));

    }
}
