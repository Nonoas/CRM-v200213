package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.controller.PackageAddController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * 项目添加面板
 * @author Nonoas
 *
 */
public class PackageAddTab extends Tab{
	public PackageAddTab() {
		this("添加项目");
	}
	public PackageAddTab(String str) {
		super(str);
		initView();
	}

	private void initView() {
		URL url = getClass().getResource("/fxml/package_add.fxml");
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
		PackageAddController contronller=fxmlLoader.getController();
		contronller.setPane(this);
	}
}
