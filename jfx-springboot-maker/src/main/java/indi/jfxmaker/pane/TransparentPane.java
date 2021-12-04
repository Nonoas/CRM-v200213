package indi.jfxmaker.pane;

import indi.jfxmaker.common.InsetConstant;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * @author Nonoas
 * @datetime 2021/12/4 15:42
 */
public class TransparentPane extends AnchorPane {

    /**
     * 内容布局
     */
    private final AnchorPane contentPane = new AnchorPane();

    /**
     * 按钮布局
     */
    private final ObservableList<Node> sysButtons;


    public TransparentPane() {

        HBox sysBtnBox = new HBox();
        sysButtons = sysBtnBox.getChildren();

        sysBtnBox.setAlignment(Pos.CENTER_RIGHT);

        this.setPadding(InsetConstant.INSET_25);

        setStyle("-fx-background-color: rgb(0, 0, 0, 0)");

        contentPane.setStyle("-fx-background-color: white");
        contentPane.setEffect(getDropShadow());

        getChildren().setAll(contentPane, sysBtnBox);

        AnchorPane.setTopAnchor(contentPane, 0.0);
        AnchorPane.setRightAnchor(contentPane, 0.0);
        AnchorPane.setBottomAnchor(contentPane, 0.0);
        AnchorPane.setLeftAnchor(contentPane, 0.0);

        AnchorPane.setTopAnchor(sysBtnBox, 0.0);
        AnchorPane.setRightAnchor(sysBtnBox, 0.0);

    }

    public ObservableList<Node> getSysButtons() {
        return sysButtons;
    }

    /**
     * 设置根布局
     *
     * @param content 根布局
     */
    public void setContent(Node content) {
        if (content instanceof Region) {
            // 双向绑定宽高，使布局宽高随窗口变化
            Region region = (Region) content;
            AnchorPane.setTopAnchor(region, 0.0);
            AnchorPane.setRightAnchor(region, 0.0);
            AnchorPane.setBottomAnchor(region, 0.0);
            AnchorPane.setLeftAnchor(region, 0.0);
            contentPane.getChildren().setAll(region);
        } else {
            contentPane.getChildren().setAll(content);
        }
    }

    private DropShadow getDropShadow() {
        DropShadow dropshadow = new DropShadow();// 阴影向外
        dropshadow.setRadius(20);// 颜色蔓延的距离
        dropshadow.setSpread(0.1);// 颜色变淡的程度
        dropshadow.setColor(Color.rgb(0, 0, 0, 0.3));// 设置颜色
        return dropshadow;
    }

}
