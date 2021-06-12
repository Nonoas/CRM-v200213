package indi.nonoas.crm.view.vip;

import indi.nonoas.crm.pojo.dto.VipInfo;
import indi.nonoas.crm.controller.vip.VipModifyController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class VipModifyTab extends Tab {

    private final VipInfo bean;
    private VipModifyController controller;
    private Tab preTab;

    public VipModifyTab(VipInfo bean) {
        this("修改会员信息", bean);
    }

    public VipModifyTab(String str, VipInfo bean) {
        super(str);
        this.bean = bean;
        initView();
        initListener();
    }

    private void initView() {
        URL url = getClass().getResource("/fxml/vip_modify.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        try {
            Pane pane = fxmlLoader.load();
            setContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setClosable(true);
        controller = fxmlLoader.getController();
        controller.setPane(this);
        setBean(this.bean);
    }

    private void initListener() {
        setOnCloseRequest(event -> {
            TabPane tabPane = getTabPane();
            tabPane.getSelectionModel().select(preTab);
        });
    }

    /**
     * 关闭当前Tab，如果得到上一个tab的引用，则关闭之后跳转到上一个Tab
     */
    public void close() {
        TabPane tabPane = getTabPane();
        if (tabPane != null) {
            tabPane.getTabs().remove(this);
            tabPane.getSelectionModel().select(preTab);
        }
    }

    public void setBean(VipInfo vipBean) {
        controller.setBean(vipBean);
    }

    /**
     * 设置前一个Tab
     *
     * @param preTab 上一个跳转至当前Tab的Tab
     */
    public void setPreTab(Tab preTab) {
        this.preTab = preTab;
    }

}
