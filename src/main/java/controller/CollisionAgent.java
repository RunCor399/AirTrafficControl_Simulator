package controller;


import javafx.application.Platform;
import model.Model;
import model.Plane;
import view.View;

public class CollisionAgent extends AbstractAgent {

    private static final int WARNING_DISTANCE = 500;
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
    public void run() {
        while (!this.isStopped()) {
            try {
                synchronized (this) {
                    if (this.isPaused()) {
                        this.wait();
                    }
                }
                sleep(DELTA_TIME / this.getMultiplier());
                this.getModel().getAllPlanes().stream()
                    .filter(plane -> !(plane.getPlaneAction().equals(Plane.Action.TAKEOFF) && !plane.isActionPerformed()))
                    .filter(plane -> !(plane.getPlaneAction().equals(Plane.Action.LAND) && plane.isActionPerformed()))
                    .forEach(x -> checkCollision(x));
            } catch (InterruptedException e) {

            }
        }
    }

    /**
     * Method that watches if a plane is colliding.
     * @param p is the plane to check. 
     */

    private void checkCollision(final Plane p) {
         if (this.getModel().getAllPlanes().stream()
            .filter(x -> Math.abs(x.getAltitude() - p.getAltitude()) <= WARNING_ALTITUDE)
            .filter(x -> Math.abs(x.getPosition().distanceFrom(p.getPosition())) <= WARNING_DISTANCE)
            .filter(x -> x.getAirplaneId() != p.getAirplaneId())
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
