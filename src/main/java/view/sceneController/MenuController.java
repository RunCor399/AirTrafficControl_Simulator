package view.sceneController;

public interface MenuController extends SceneController {

    /**
     * Method that switches into game Scenery.
     */
    void switchToGameScenery();

    /**
     * Method that switches into game Scenery.
     */
    void switchToAirportSelection();

    /**
     * Method that switches into tutorial Scenery.
     */
    void switchToTutorialScenery();

    /**
     * Method that leaves the game.
     */
    void quitGame();
}
