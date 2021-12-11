package indi.nonoas.crm.view.goods;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * @author : Nonoas
 * @time : 2021-06-10 17:29
 */
public class PackageInfoTab extends Tab {
    private static volatile PackageInfoTab instance;

    private PackageInfoTab() {
        this("套餐管理");
    }

    private PackageInfoTab(String str) {
        super(str);
        initView();
    }

    public static PackageInfoTab getInstance() {
        if (null == instance) {
            synchronized (GoodsTypeTab.class) {
                if (null == instance) {
                    instance = new PackageInfoTab();
                }
            }
        }
        return instance;
    }

    private void initView() {
        URL url = getClass().getResource("/fxml/pkg/package_info.fxml");
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
