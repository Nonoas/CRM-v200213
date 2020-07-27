package indi.nonoas.crm.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import indi.nonoas.crm.global.ClientSession;
import indi.nonoas.crm.app.MainStage;
import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.bean.LoginBean;
import indi.nonoas.crm.dao.LoginDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        // TODO ����Ϊ���Դ���
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
                Stage newStage = null;
                try {
                    ClientSession.addAttribute("user", newValue);
                    newStage = new MainStage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage oldStage = (Stage) vb_root.getScene().getWindow();
                oldStage.close();
                if (newStage != null)
                    newStage.show();
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
    private class VerifyTask extends javafx.concurrent.Task<LoginBean> {

        String username;
        String password;

        VerifyTask(String u, String p) {
            this.username = u;
            this.password = p;
        }

        @Override
        protected LoginBean call() {
            LoginBean loginBean = new LoginDao().verify(username, password);
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