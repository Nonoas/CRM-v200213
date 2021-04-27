package indi.nonoas.crm.app.vip;

import indi.nonoas.crm.pojo.UserBean;
import indi.nonoas.crm.controller.vip.VipModifyController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class VipModifyTab extends Tab {

    private final UserBean bean;
    private VipModifyController controller;
    private Tab preTab;

    public VipModifyTab(UserBean bean) {
        this("�޸Ļ�Ա��Ϣ", bean);
    }

    public VipModifyTab(String str, UserBean bean) {
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
     * �رյ�ǰTab������õ���һ��tab�����ã���ر�֮����ת����һ��Tab
     */
    public void close() {
        TabPane tabPane = getTabPane();
        if (tabPane != null) {
            tabPane.getTabs().remove(this);
            tabPane.getSelectionModel().select(preTab);
        }
    }

    public void setBean(UserBean vipBean) {
        controller.setBean(vipBean);
    }

    /**
     * ����ǰһ��Tab
     *
     * @param preTab ��һ����ת����ǰTab��Tab
     */
    public void setPreTab(Tab preTab) {
        this.preTab = preTab;
    }

}
