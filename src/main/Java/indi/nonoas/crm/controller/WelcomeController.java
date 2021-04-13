package indi.nonoas.crm.controller;

import de.felixroske.jfxsupport.FXMLController;
import indi.nonoas.crm.ApplicationStarter;
import indi.nonoas.crm.beans.LoginBean;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.common.ClientSession;
import indi.nonoas.crm.service.LoginService;
import indi.nonoas.crm.utils.SaltUtil;
import indi.nonoas.crm.view.alert.MyAlert;
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

    @FXML // ��¼��ť�¼�
    private void login() {
        String username = tf_username.getValue();
        String password = pf_password.getText();
        // �ж��û����Ƿ�Ϊ��
        if (username.equals("")) {
            new MyAlert(AlertType.WARNING, "�û�������Ϊ�գ�").show();
            return;
        }
        // �ж������Ƿ�Ϊ��
        if (password.equals("")) {
            new MyAlert(AlertType.WARNING, "���벻��Ϊ�գ�").show();
            return;
        }

        btn_Login.setDisable(true);
        btn_Login.setText("��֤�����С�����");

        VerifyTask vTask = new VerifyTask(username, password); // �½��̣߳�������֤���벢����UI
        new Thread(vTask).start();
        vTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getId() != null) {
                ClientSession.addAttribute("user", newValue);
                ApplicationStarter.toMainStageView();
            } else {
                btn_Login.setText("��¼");
                btn_Login.setDisable(false);
                new MyAlert(AlertType.INFORMATION, "�û������������").showAndWait();
            }
        });
    }

    /**
     * ���߳�����������֤�û�������
     */
    private static class VerifyTask extends javafx.concurrent.Task<LoginBean> {

        String username;
        String password;

        VerifyTask(String u, String p) {
            this.username = u;
            this.password = new SaltUtil(u, "MD5").encode(p);
        }

        @Override
        protected LoginBean call() {
            LoginService service = (LoginService) SpringUtil.getBean("LoginServiceImpl");
            LoginBean loginBean = service.verify(username, password);
            // �����ѯ���Ϊ��Ϊ�գ��򷵻ظ�LoginBean����
            if (loginBean != null)
                return loginBean;
                // ���򷵻�һ���յ�LoginBean()����
                // ���ֱ�ӷ���null����ChangeListener����ص�����ΪoldֵΪnull
            else
                return new LoginBean();
        }

    }
}