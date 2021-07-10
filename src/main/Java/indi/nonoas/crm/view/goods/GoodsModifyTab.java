package indi.nonoas.crm.view.goods;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.controller.goods.GoodsModifyController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * �޸���Ʒ����
 * @author Nonoas
 *
 */
public class GoodsModifyTab extends Tab {

	private final GoodsDto bean;
	
	public GoodsModifyTab(GoodsDto bean) {
		this("�޸���Ʒ��Ϣ",bean);
	}
	public GoodsModifyTab(String str, GoodsDto bean) {
		super(str);
		this.bean=bean;
		initView();
	}

	private void initView() {
		URL url = getClass().getResource("/fxml/goods/goods_modify.fxml");
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
		GoodsModifyController controller=fxmlLoader.getController();
		controller.setPane(this);
		controller.setBean(bean);
	}
}
