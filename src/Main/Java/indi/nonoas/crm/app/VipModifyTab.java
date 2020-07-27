package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.bean.VipBean;
import indi.nonoas.crm.controller.VipModifyController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public class VipModifyTab extends Tab {
	
	private VipBean bean;
	
	public VipModifyTab(VipBean bean) {
		this("修改会员信息",bean);
	}
	public VipModifyTab(String str,VipBean bean) {
		super(str);
		this.bean=bean;
		initView();
	}

	private void initView() {
		URL url = getClass().getResource("/fxml/vip_modify.fxml");
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
		VipModifyController contronller=fxmlLoader.getController();
		contronller.setPane(this);
		contronller.setBean(this.bean);
	}

}
