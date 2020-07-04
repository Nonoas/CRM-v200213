package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.bean.LoginBean;
import indi.nonoas.crm.controller.MainController;
import indi.nonoas.crm.utils.ImageSrc;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainStage extends Stage {

	private final String username;

	public MainStage(LoginBean loginBean) throws IOException {
		this.username = loginBean.getName();
		System.out.println(username);
		initUI();
	}

	private void initUI() throws IOException {

		URL location = getClass().getResource("/indi/nonoas/crm/mfxml/main.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = fxmlLoader.load();

		setTitle("客户管理系统");
		getIcons().add(new Image(ImageSrc.lOGO_PATH));
		setScene(new Scene(root));
		// 设置最小宽高
		setMinHeight(800);
		setMinWidth(1280);

		getScene().getStylesheets().add("indi/nonoas/crm/mcss/application.css");

		MainController controller = fxmlLoader.getController(); // 获取Controller的实例对象
		controller.initialize(location, null);
		controller.setInfos(username);

		setMaximized(true);
		show();
	}
}
