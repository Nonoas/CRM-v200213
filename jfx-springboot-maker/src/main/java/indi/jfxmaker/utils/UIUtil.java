package indi.jfxmaker.utils;

import indi.jfxmaker.common.Visibility;
import javafx.scene.Node;

/**
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
}
