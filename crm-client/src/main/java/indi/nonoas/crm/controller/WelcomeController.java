package indi.nonoas.crm.controller;

import de.felixroske.jfxsupport.FXMLController;
import indi.nonoas.crm.App;
import indi.nonoas.crm.common.ClientSession;
import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.pojo.LoginDto;
import indi.nonoas.crm.service.LoginService;
import indi.nonoas.crm.utils.SaltUtil;
import indi.nonoas.crm.utils.SpringUtil;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

@FXMLController
public class WelcomeController implements Initializable {

    @FXML
    private VBox vbRoot;
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
        if (username == null || "".equals(username.trim())) {
            new MyAlert(AlertType.WARNING, "用户名不能为空").show();
            return;
        }
        if (password == null || "".equals(password.trim())) {
            new MyAlert(AlertType.WARNING, "密码不能为空").show();
            return;
        }

        btn_Login.setDisable(true);
        btn_Login.setText("登录验证中...");

        VerifyTask vTask;
        try {
            vTask = new VerifyTask(username, password);
        } catch (Exception ex) {
            resetLoginButton();
            new MyAlert(AlertType.ERROR, "登录初始化失败：" + ex.getMessage()).showAndWait();
            return;
        }
        vTask.setOnSucceeded(event -> {
            LoginDto loginDto = vTask.getValue();
            if (loginDto != null && loginDto.getId() != null) {
                try {
                    ClientSession.addAttribute("user", loginDto);
                    App.toMainStageView();
                } catch (Exception ex) {
                    resetLoginButton();
                    new MyAlert(AlertType.ERROR, "进入主界面失败：" + ex.getMessage()).showAndWait();
                }
            } else {
                resetLoginButton();
                new MyAlert(AlertType.INFORMATION, "用户名或密码错误").showAndWait();
            }
        });

        vTask.setOnFailed(event -> {
            resetLoginButton();
            Throwable throwable = vTask.getException();
            String msg = throwable == null ? "未知错误" : throwable.getMessage();
            new MyAlert(AlertType.ERROR, "登录失败：" + msg).showAndWait();
        });

        vTask.setOnCancelled(event -> resetLoginButton());

        Thread verifyThread = new Thread(vTask, "login-verify-thread");
        verifyThread.setDaemon(true);
        verifyThread.start();
    }

    private void resetLoginButton() {
        btn_Login.setText("登  录");
        btn_Login.setDisable(false);
    }

    /**
     * 密码校验线程
     */
    private static class VerifyTask extends Task<LoginDto> {

        private final LoginService service;

        private final String username;
        private final String password;


        VerifyTask(String u, String p) {
            this.service = (LoginService) SpringUtil.getBean("LoginServiceImpl");
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
