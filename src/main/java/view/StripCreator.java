package view;

import javafx.scene.layout.Pane;

/**
 * Interface that models a strip creator.
 */
public interface StripCreator {
    /**
     * Method that initializes labels of the strip.
     * 
     * @return pane which describes the strip
     */
    Pane createStrip();

}
