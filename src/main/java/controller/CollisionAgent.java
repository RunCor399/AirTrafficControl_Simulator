package controller;


import java.util.Iterator;

import javafx.application.Platform;
import model.Model;
import model.Plane;
import model.RadarPosition;
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
                                   double pointDistance = getPointDistance(plane.getPosition(), tmp.getPosition());
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
     * Method that find the distance between two planes.
     * @param p the position of the first plane.
     * @param p2 the position of the second lane.
     * @return pointDistance.
     */
    private double getPointDistance(final RadarPosition p, final RadarPosition p2) {
        double x = Math.pow(p.getPosition().getX() - p2.getPosition().getX(), 2);
        double y = Math.pow(p.getPosition().getY() - p2.getPosition().getY(), 2);
        double pointDistance = Math.sqrt(x + y);
        return pointDistance;
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
