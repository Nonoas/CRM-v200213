package indi.nonoas.crm.view.goods;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * @author : Nonoas
 * @time : 2021-06-10 17:19
 */
public class GoodsTypeTab extends Tab {

    private static volatile GoodsTypeTab instance;

    private GoodsTypeTab() {
        this("商品类别");
    }

    private GoodsTypeTab(String str) {
        super(str);
        initView();
    }

    public static GoodsTypeTab getInstance() {
        if (null == instance) {
            synchronized (GoodsTypeTab.class) {
                if (null == instance) {
                    instance = new GoodsTypeTab();
                }
            }
        }
        return instance;
    }

    private void initView() {
        URL url = getClass().getResource("/fxml/goods/goods_type.fxml");
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
