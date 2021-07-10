package indi.nonoas.crm.view.pkg;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.pojo.PackageDto;
import indi.nonoas.crm.controller.goods.PackageModifyController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * 套餐修改Tab
 *
 * @author Nonoas
 */
public class PackageModifyTab extends Tab {

    private final PackageDto packageBean;

    public PackageModifyTab(PackageDto packageBean) {
        this("套餐修改", packageBean);
    }

    public PackageModifyTab(String str, PackageDto packageBean) {
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
