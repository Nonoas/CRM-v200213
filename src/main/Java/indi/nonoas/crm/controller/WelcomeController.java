package indi.nonoas.crm.controller;

import de.felixroske.jfxsupport.FXMLController;
import indi.nonoas.crm.ApplicationStarter;
import indi.nonoas.crm.pojo.LoginDto;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.common.ClientSession;
import indi.nonoas.crm.service.LoginService;
import indi.nonoas.crm.utils.SaltUtil;
import indi.nonoas.crm.component.alert.MyAlert;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class WelcomeController implements Initializable {

    @FXML
    private VBox vb_root;
    @FXML
    private ComboBox<String> tf_username;
    @FXML
    private Button btn_Login;
    @FXML
    private PasswordField pf_password;

    public WelcomeController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tf_username.setValue("admin");
        pf_password.setText("admin");
    }

    @FXML
    private void login() {
        String username = tf_username.getValue();
        String password = pf_password.getText();
        // 用户名密码非空校验
        if ("".equals(username.trim())) {
            new MyAlert(AlertType.WARNING, "用户名不能为空").show();
            return;
        }
        if ("".equals(password.trim())) {
            new MyAlert(AlertType.WARNING, "密码不能为空").show();
            return;
        }

        btn_Login.setDisable(true);
        btn_Login.setText("登录验证中...");

        VerifyTask vTask = new VerifyTask(username, password);
        new Thread(vTask).start();
        vTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getId() != null) {
                ClientSession.addAttribute("user", newValue);
                ApplicationStarter.toMainStageView();
            } else {
                btn_Login.setText("登  录");
                btn_Login.setDisable(false);
                new MyAlert(AlertType.INFORMATION, "用户名或密码错误").showAndWait();
            }
        });
    }

    /**
     * 密码校验线程
     */
    private static class VerifyTask extends Task<LoginDto> {

        LoginService service = (LoginService) SpringUtil.getBean("LoginServiceImpl");

        String username;
        String password;


        VerifyTask(String u, String p) {
            this.username = u;
            this.password = new SaltUtil(u, "MD5").encode(p);
        }

        @Override
        protected LoginDto call() {

            LoginDto loginDto = service.verify(username, password);

            if (loginDto != null) {
                return loginDto;
            } else {
                return new LoginDto();
            }
        }

    }
}
