package indi.nonoas.crm.view.consume;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * @author : Nonoas
 * @time : 2021-06-08 21:53
 */
public class VipQueryTab extends Tab {

    private static volatile VipQueryTab instance;

    private VipQueryTab() {
        this("会员查询");
    }

    private VipQueryTab(String str) {
        super(str);
        initView();
    }

    private void initView() {
        URL url = getClass().getResource("/fxml/consume/vip_query.fxml");
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

    public static VipQueryTab getInstance() {
        if (null == instance) {
            synchronized (VipQueryTab.class) {
                if (null == instance) {
                    instance = new VipQueryTab();
                }
            }
        }
        return instance;
    }
}
