package indi.nonoas.crm.view.pkg;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.controller.pkg.PackageAddController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * 套餐添加Tab
 * @author Nonoas
 *
 */
public class PackageAddTab extends Tab{
	public PackageAddTab() {
		this("套餐添加");
	}
	public PackageAddTab(String str) {
		super(str);
		initView();
	}

	private void initView() {
		URL url = getClass().getResource("/fxml/pkg/package_add.fxml");
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
		PackageAddController controller=fxmlLoader.getController();
		controller.setPane(this);
	}
}
