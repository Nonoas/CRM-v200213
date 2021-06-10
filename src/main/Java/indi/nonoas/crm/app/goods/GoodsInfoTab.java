package indi.nonoas.crm.app.goods;

import indi.nonoas.crm.app.consume.VipQueryTab;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * @author : Nonoas
 * @time : 2021-06-10 12:32
 */
public class GoodsInfoTab extends Tab {

    private static volatile GoodsInfoTab instance;

    private GoodsInfoTab() {
        this("商品信息");
    }

    private GoodsInfoTab(String str) {
        super(str);
        initView();
    }

    public static GoodsInfoTab getInstance() {
        if (null == instance) {
            synchronized (VipQueryTab.class) {
                if (null == instance) {
                    instance = new GoodsInfoTab();
                }
            }
        }
        return instance;
    }

    private void initView() {
        URL url = getClass().getResource("/fxml/goods/goods_info.fxml");
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
}
