package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.controller.VipAddController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public class VipAddTab extends Tab {
	
	public VipAddTab() {
		this("ÃÌº”ª·‘±");
	}
	public VipAddTab(String str) {
		super(str);
		initView();
	}

	private void initView() {
		URL url = getClass().getResource("/fxml/vip_add.fxml");
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
		VipAddController contronller=fxmlLoader.getController();
		contronller.setPane(this);
	}

}
