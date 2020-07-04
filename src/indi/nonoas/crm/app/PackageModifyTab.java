package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.controller.PackageModifyController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * ��Ŀ��Ϣ�޸����
 * @author Nonoas
 *
 */
public class PackageModifyTab extends Tab{
	public PackageModifyTab() {
		this("�޸���Ŀ��Ϣ");
	}
	public PackageModifyTab(String str) {
		super(str);
		initView();
	}

	private void initView() {
		URL url = getClass().getResource("/indi/nonoas/crm/mfxml/package_modify.fxml");
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
		PackageModifyController controller=fxmlLoader.getController();
		controller.setPane(this);
	}
}
