package indi.jfxmaker.utils;

import javafx.scene.paint.Color;

/**
 * @author Nonoas
 * @datetime 2021/12/19 14:23
 */
public class ColorUtil {
    private ColorUtil(){}

    public static String colorToHEX(Color color) {
        return color.toString().replace("0x", "#");
    }
}
