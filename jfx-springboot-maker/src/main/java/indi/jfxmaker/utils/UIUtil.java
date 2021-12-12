package indi.jfxmaker.utils;

import indi.jfxmaker.common.Visibility;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * UI 处理工具
 *
 * @author Nonoas
 * @datetime 2021/12/5 11:25
 */
public class UIUtil {

    private UIUtil() {
    }

    /**
     * 设置组件可见度
     *
     * @param node 指定组件
     * @param v    可见枚举
     */
    public static void setVisible(Node node, Visibility v) {
        node.setVisible(v.isVisible());
        node.setManaged(v.isManaged());
    }

    /**
     * 设置ImageView的宽高
     *
     * @param widthHeight 宽和高
     */
    public static void setImageViewSize(ImageView iv, Double widthHeight) {
        iv.setFitWidth(widthHeight);
        iv.setFitHeight(widthHeight);
    }

    /**
     * 设置ImageView的宽高
     *
     * @param width  宽
     * @param height 高
     */
    public static void setImageViewSize(ImageView iv, Double width, Double height) {
        iv.setFitWidth(width);
        iv.setFitHeight(height);
    }
}
