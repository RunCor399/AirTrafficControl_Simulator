package view.sceneController;

import java.util.Set;
import model.Plane;

public interface RadarController extends SceneController {

    /**
     * This method allows the radar to draw all the planes which are actually in the radar sight.
     * 
     * @param planes the set of planes to draw in the radar.
     */
    void updatePlanes(Set<Plane> planes);
    
    /**
     * 
     */
    void callMovementGUIUpdate(Plane p);

}
