package indi.nonoas.crm.app.goods;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.controller.goods.GoodsAddController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public class GoodsAddTab extends Tab {
	
	public GoodsAddTab() {
		this("ÃÌº”…Ã∆∑");
	}

	private GoodsAddTab(String str) {
		super(str);
		initView();
	}

	private void initView() {
		URL url = getClass().getResource("/fxml/goods_add.fxml");
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
		GoodsAddController contronller=fxmlLoader.getController();
		contronller.setPane(this);
	}

}
