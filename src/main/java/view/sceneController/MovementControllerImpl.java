package view.sceneController;

import java.util.Objects;

import controller.Controller;
import controller.ControllerImpl;
import model.Direction;
import model.Speed;
import model.Vor;

public class MovementControllerImpl extends AbstractSceneController implements SceneController {
    private final Controller controller = new ControllerImpl();

    /**
     * method that decides which plane is to be set as current.
     * 
     * @param planeId
     */
    public void setTargetAirplane(final int planeId) {
        Objects.requireNonNull(planeId);
        this.controller.selectTargetPlane(planeId);
    }

    /**
     * method that passes to controller the airplane's speed to be set.
     * 
     * @param targetSpeed
     */
    public void setCurrentSpeed(final Speed targetSpeed) {
        Objects.requireNonNull(targetSpeed);
        this.controller.setPlaneSpeed(targetSpeed);
    }

    /**
     * method that passes to controller the airplane's direction to be set.
     * 
     * @param targetDirection
     */
    public void setCurrentHeading(final Direction targetDirection) {
        Objects.requireNonNull(targetDirection);
        this.controller.setPlaneHeading(targetDirection);
    }

    /**
     * method that passes to controller the airplane's altitude to be set.
     * 
     * @param targetAltitude
     */
    public void setCurrentAltitude(final double targetAltitude) {
        Objects.requireNonNull(targetAltitude);
        this.controller.setPlaneAltitude(targetAltitude);
    }

    /**
     * method that makes a plane takeoff from the airport.
     */
    public void executeTakeoff() {
        this.controller.takeOff();
    }

    /**
     * method that makes a plane land in a specific runway of the airport.
     */
    public void executeLanding() {
        this.controller.land();
    }

    /**
     * method that directs a plane to a specific vor.
     * 
     * @param targetVor
     */
    public void headToVor(final Vor targetVor) {
        Objects.requireNonNull(targetVor);
        this.controller.goToVor(targetVor);
    }
}
