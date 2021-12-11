package indi.jfxmaker.control;

import javafx.scene.layout.VBox;

/**
 * 一组 TitledPane
 */
public class TitledGroup extends VBox {

    private static final String STYLE_CLASS = "titled-group";

    public TitledGroup() {
        getStyleClass().add(STYLE_CLASS);
    }

}
