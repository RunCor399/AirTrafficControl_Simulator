package view.sceneController;

import controller.Controller;
import view.View;

/**
 * 
 * An abstract implementation of the {@link SceneController} interface.
 *
 */
public abstract class AbstractSceneController implements SceneController {

    private Controller controller;
    private View view;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameters(final Controller controller, final View view) {
        this.controller = controller;
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Controller getController() {
        return this.controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final Controller controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView() {
        return this.view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(final View view) {
        this.view = view;
    }

}
