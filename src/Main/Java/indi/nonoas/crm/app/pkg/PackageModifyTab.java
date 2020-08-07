package indi.nonoas.crm.app.pkg;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.beans.PackageBean;
import indi.nonoas.crm.controller.goods.PackageModifyController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * 项目信息修改面板
 *
 * @author Nonoas
 */
public class PackageModifyTab extends Tab {

    private final PackageBean packageBean;

    public PackageModifyTab(PackageBean packageBean) {
        this("修改项目信息", packageBean);
    }

    public PackageModifyTab(String str, PackageBean packageBean) {
        super(str);
        this.packageBean = packageBean;
        initView();
    }

    private void initView() {
        URL url = getClass().getResource("/fxml/package_modify.fxml");
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
        PackageModifyController controller = fxmlLoader.getController();
        controller.setPane(this);
        controller.setBean(packageBean);
    }
}
