package indi.jfxmaker.stage;

import indi.jfxmaker.common.InsetConstant;
import indi.jfxmaker.common.Visibility;
import indi.jfxmaker.control.SysButtonEnum;
import indi.jfxmaker.pane.TransparentPane;
import indi.jfxmaker.utils.UIUtil;
import java.util.Collection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * App窗口，通常作为唯一窗口
 */
public class AppStage {

    private double xOffset = 0;
    private double yOffset = 0;

    private final Stage stage = new Stage();

    /**
     * 窗口根布局
     */
    private final TransparentPane stageRootPane = new TransparentPane();

    public AppStage() {
        initView();
    }

    private void initView() {

        Scene scene = new Scene(stageRootPane);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        initSysButtons();
    }

    /**
     * 初始化窗口按钮，包括监听事件
     */
    private void initSysButtons() {
        Button minBtn = SysButtonEnum.MINIMIZE.get();
        Button maxBtn = SysButtonEnum.MAXIMIZE.get();
        Button closeBtn = SysButtonEnum.CLOSE.get();

        minBtn.setOnAction(event -> this.stage.setIconified(true));
        maxBtn.setOnAction(event -> this.setMaximized(!stage.isMaximized()));

        stage.resizableProperty().addListener((observable, oldValue, resizable) -> {
            if (resizable) {
                UIUtil.setVisible(maxBtn, Visibility.VISIBLE);
            } else {
                UIUtil.setVisible(maxBtn, Visibility.GONE);
            }
        });

        closeBtn.setOnAction(event -> this.close());

        stageRootPane.getSysButtons().addAll(minBtn, maxBtn, closeBtn);
    }

    /**
     * 设置根布局
     *
     * @param parent 根布局
     */
    public void setContentView(Parent parent) {
        // 设置窗口拖动
        parent.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        parent.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stageRootPane.setContent(parent);
    }

    public void setMaximized(boolean maximized) {
        if (maximized) {
            stageRootPane.setPadding(InsetConstant.INSET_EMPTY);
        } else {
            stageRootPane.setPadding(InsetConstant.INSET_25);
        }
        stage.setMaximized(maximized);
    }

    public ObservableList<Node> getSystemButtons() {
        return stageRootPane.getSysButtons();
    }

    /**
     * 显示窗口
     */
    public void show() {
        stage.show();
    }

    /**
     * 添加图标
     *
     * @param images 图标集合
     */
    public void addIcons(Collection<Image> images) {
        stage.getIcons().addAll(images);
    }

    public void close() {
        stage.close();
    }

    public void setMinWidth(int i) {
        stage.setMinWidth(i);
    }

    public void setMinHeight(int i) {
        stage.setMinHeight(i);
    }

    public void setResizable(boolean b) {
        stage.setResizable(b);
    }
}
