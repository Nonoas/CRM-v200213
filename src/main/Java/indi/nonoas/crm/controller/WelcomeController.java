package indi.nonoas.crm.controller;

import de.felixroske.jfxsupport.FXMLController;
import indi.nonoas.crm.ApplicationStarter;
import indi.nonoas.crm.pojo.LoginDto;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.common.ClientSession;
import indi.nonoas.crm.service.LoginService;
import indi.nonoas.crm.utils.SaltUtil;
import indi.nonoas.crm.component.alert.MyAlert;
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

    @FXML // 登录按钮事件
    private void login() {
        String username = tf_username.getValue();
        String password = pf_password.getText();
        // 判断用户名是否为空
        if (username.equals("")) {
            new MyAlert(AlertType.WARNING, "用户名不能为空！").show();
            return;
        }
        // 判断密码是否为空
        if (password.equals("")) {
            new MyAlert(AlertType.WARNING, "密码不能为空！").show();
            return;
        }

        btn_Login.setDisable(true);
        btn_Login.setText("验证密码中···");

        VerifyTask vTask = new VerifyTask(username, password); // 新建线程，用于验证密码并更新UI
        new Thread(vTask).start();
        vTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getUserId() != null) {
                ClientSession.addAttribute("user", newValue);
                ApplicationStarter.toMainStageView();
            } else {
                btn_Login.setText("登录");
                btn_Login.setDisable(false);
                new MyAlert(AlertType.INFORMATION, "用户名或密码错误！").showAndWait();
            }
        });
    }

    /**
     * 子线程任务，用于验证用户名密码
     */
    private static class VerifyTask extends javafx.concurrent.Task<LoginDto> {

        String username;
        String password;

        VerifyTask(String u, String p) {
            this.username = u;
            this.password = new SaltUtil(u, "MD5").encode(p);
        }

        @Override
        protected LoginDto call() {
            LoginService service = (LoginService) SpringUtil.getBean("LoginServiceImpl");
            LoginDto loginDto = service.verify(username, password);
            // 如果查询结果为不为空，则返回该LoginBean对象
            if (loginDto != null)
                return loginDto;
                // 否则返回一个空的LoginBean()对象
                // 如果直接返回null，则ChangeListener不会回调，因为old值为null
            else
                return new LoginDto();
        }

    }
}
