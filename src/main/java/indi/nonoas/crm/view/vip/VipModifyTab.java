package indi.nonoas.crm.view.vip;

import indi.nonoas.crm.controller.vip.VipModifyController;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class VipModifyTab extends Tab {

    private final VipInfoDto bean;
    private VipModifyController controller;
    private Tab preTab;

    public VipModifyTab(VipInfoDto bean) {
        this("会员修改", bean);
    }

    public VipModifyTab(String str, VipInfoDto bean) {
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

    public void close() {
        TabPane tabPane = getTabPane();
        if (tabPane != null) {
            tabPane.getTabs().remove(this);
            tabPane.getSelectionModel().select(preTab);
        }
    }

    public void setBean(VipInfoDto vipBean) {
        controller.setBean(vipBean);
    }

    public void setPreTab(Tab preTab) {
        this.preTab = preTab;
    }

}
