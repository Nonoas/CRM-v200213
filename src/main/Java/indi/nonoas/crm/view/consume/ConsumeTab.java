package indi.nonoas.crm.view.consume;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * @author : Nonoas
 * @time : 2021-06-09 12:24
 */
public class ConsumeTab extends Tab {

    private static volatile ConsumeTab instance;

    private ConsumeTab() {
        this("商品消费");
    }

    private ConsumeTab(String str) {
        super(str);
        initView();
    }

    private void initView() {
        URL url = getClass().getResource("/fxml/consume/consume.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        try {
            Pane pane = fxmlLoader.load();
            setContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setClosable(false);
    }

    public static ConsumeTab getInstance() {
        if (null == instance) {
            synchronized (VipQueryTab.class) {
                if (null == instance) {
                    instance = new ConsumeTab();
                }
            }
        }
        return instance;
    }

}
