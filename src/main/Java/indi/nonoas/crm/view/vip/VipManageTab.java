package indi.nonoas.crm.view.vip;

import indi.nonoas.crm.view.consume.VipQueryTab;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * VIP管理界面
 *
 * @author Nonoas
 */
public class VipManageTab extends Tab {

    private static volatile VipManageTab instance;

    private VipManageTab() {
        this("会员管理");
    }

    private VipManageTab(String str) {
        super(str);
        initView();
    }

    private void initView() {
        URL url = getClass().getResource("/fxml/vip/vip_manage.fxml");
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
    }

    public static VipManageTab getInstance() {
        if (null == instance) {
            synchronized (VipQueryTab.class) {
                if (null == instance) {
                    instance = new VipManageTab();
                }
            }
        }
        return instance;
    }

}
