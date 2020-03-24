package view.sceneController;

public class MenuControllerImpl extends AbstractSceneController implements SceneController {

    /**
     * Method that switches into game Scenery.
     */
    public void switchToGameScenery() {
        this.getView().changeScene(this.getView().getSceneFactory().loadGame());
    }

    /**
     * Method that switches into tutorial Scenery.
     */
    public void switchToTutorialScenery() {
        this.getView().changeScene(this.getView().getSceneFactory().loadTutorial());
    }

    /**
     * Method that leaves the game.
     */
    public void quitGame() {
        System.exit(0);
    }
}
