package indi.jfxmaker.control;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 * 一组 TitledPane
 *
 * @author Nonoas
 */
public class TitledGroup extends VBox {

    private static final String STYLE_CLASS = "titled-group";

    public TitledGroup() {
        getStyleClass().add(STYLE_CLASS);
    }

    /**
     * 方法禁用,添加子节点请使用 {@link TitledGroup#getChildren()} 方法 <br/>
     *
     * @throws UnsupportedOperationException TitledGroup 类不支持获取子节点数组
     */
    @Deprecated
    @Override
    public ObservableList<Node> getChildren() {
        throw new UnsupportedOperationException("方法禁用,添加子节点请使用 TitledGroup#add(TitledPane pane) 方法");
    }

    /**
     * 添加子节点 TitledPane
     */
    public void add(TitledPane pane) {
        super.getChildren().add(pane);

        // 设置展开其中一个时，关闭其他所有
        pane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                return;
            }
            ObservableList<Node> children = TitledGroup.super.getChildren();
            TitledPane tmpPane;
            for (Node node : children) {
                tmpPane = (TitledPane) node;
                if (tmpPane != observable && tmpPane.isExpanded()) {
                    tmpPane.setExpanded(false);
                }
            }
        });
    }

}
