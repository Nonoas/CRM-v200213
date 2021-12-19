package indi.jfxmaker.control;

import indi.jfxmaker.pane.SVGImage;
import indi.jfxmaker.utils.ColorUtil;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * @author Nonoas
 * @datetime 2021/12/4 21:35
 */
public class SVGButton extends Button {

    public static final String STYLE_CLASS = "sys-button";

    public static final String STYLE_PATH = "/css/style.css";

    private final static Double DEFAULT_SIZE = 30.0;

    private final static Double DEFAULT_ICON_SIZE = 20.0;

    {
        getStylesheets().add(STYLE_PATH);
        getStyleClass().add(STYLE_CLASS);
        setPrefSize(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public SVGButton() {
    }

    public SVGButton(String text) {
        super(text);
    }

    public SVGButton(SVGImage node) {
        setGraphic(node);
    }


    /**
     * SysButton 构建类
     */
    public static class SvgButtonBuilder {

        private SVGImage graphic;

        private Color graphicColor;

        private Color graphicColorHover;

        private Color backgroundColor;

        private Color backgroundColorHover;

        public SvgButtonBuilder() {
            graphic = new SVGImage();
            backgroundColor = Color.TRANSPARENT;
            backgroundColorHover = Color.TRANSPARENT;
        }

        public SvgButtonBuilder graphic(SVGImage graphic) {
            this.graphic = graphic;
            graphicColor = (Color) graphic.getFill();
            graphicColorHover = (Color) graphic.getFill();
            return this;
        }

        public SvgButtonBuilder graphicColor(Color graphicColor) {
            this.graphicColor = graphicColor;
            return this;
        }

        public SvgButtonBuilder getGraphicColorHover(Color getGraphicColorHover) {
            this.graphicColorHover = getGraphicColorHover;
            return this;
        }

        public SvgButtonBuilder backgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public SvgButtonBuilder backgroundColorHover(Color backgroundColorHover) {
            this.backgroundColorHover = backgroundColorHover;
            return this;
        }

        public SVGButton build() {
            SVGButton button = new SVGButton(graphic);
            button.setStyle("-fx-background-color: " + ColorUtil.colorToHEX(backgroundColor));
            button.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    button.setStyle("-fx-background-color: " + ColorUtil.colorToHEX(backgroundColorHover));
                    graphic.setFill(graphicColorHover);
                } else {
                    button.setStyle("-fx-background-color: " + ColorUtil.colorToHEX(backgroundColor));
                    graphic.setFill(graphicColor);
                }
            });
            return button;
        }
    }
}
