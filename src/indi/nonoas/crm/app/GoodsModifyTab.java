package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.controller.GoodsModifyController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * 修改商品界面
 * @author Nonoas
 *
 */
public class GoodsModifyTab extends Tab {

	private GoodsBean bean;
	
	public GoodsModifyTab(GoodsBean bean) {
		this("修改商品信息",bean);
	}
	public GoodsModifyTab(String str,GoodsBean bean) {
		super(str);
		this.bean=bean;
		initView();
	}

	private void initView() {
		URL url = getClass().getResource("/indi/nonoas/crm/mfxml/goods_modify.fxml");
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
		GoodsModifyController contronller=fxmlLoader.getController();
		contronller.setPane(this);
		contronller.setBean(bean);
	}
}
