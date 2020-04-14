package controller;


import java.util.Iterator;

import javafx.application.Platform;
import model.Model;
import model.Plane;
import view.View;

public class CollisionAgent extends AbstractAgent {

    private Iterator<Plane> planes;
    private static final double WARNING_DISTANCE = 1000;
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
                this.planes = this.getModel().getAllPlanes().iterator();
                Plane plane;
                Plane tmp;
                while (planes.hasNext()) {
                    plane = this.planes.next();
                    Iterator<Plane> tmpPlanes = this.getModel().getAllPlanes().iterator();
                    while (tmpPlanes.hasNext()) {
                           tmp = tmpPlanes.next();
                           if (tmp.getAirplaneId() != plane.getAirplaneId()) {
                               if (plane.getAltitude() == tmp.getAltitude()) {
                                   double pointDistance = plane.getPosition().distanceFrom(tmp.getPosition());
                                   if (pointDistance > 0 && pointDistance < WARNING_DISTANCE) {
                                       collisionDetected();
                                   }
                               }
                           }
                    }
                }

            } catch (InterruptedException e) {

            }
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
