package indi.nonoas.crm.controller.consume;


import indi.nonoas.crm.beans.OrderBean;
import indi.nonoas.crm.beans.OrderDetailBean;
import indi.nonoas.crm.beans.VipBean;
import indi.nonoas.crm.dao.PackageDao;
import indi.nonoas.crm.view.alert.MyAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author : Nonoas
 * @time : 2020-08-10 17:59
 */
public class ConsumeDialogController implements Initializable {

    private Stage stage;

    /**
     * ������
     */
    private VipBean vipBean;

    /**
     * ����
     */
    private OrderBean order;

    /**
     * ��������
     */
    private List<OrderDetailBean> orderDetails;

    /**
     * ����������
     */
    @FXML
    private Label lb_consumer;

    @FXML
    private GridPane gp_rootPane;

    /**
     * ֧����ʽ
     */
    @FXML
    private ComboBox<PayMode> cb_payMode;

    /**
     * ������
     */
    @FXML
    private TextField tf_transactor;

    /**
     * ֧������
     */
    @FXML
    private TextField tf_payValue;
    /**
     * ���
     */
    @FXML
    private Label lb_balance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_payMode.getItems().addAll(PayMode.INTEGRAL, PayMode.CASH, PayMode.BALANCE);
        cb_payMode.setValue(PayMode.CASH);
        lb_balance.setText("��ѡ���ֽ�֧��");
        //�������ѡ�����
        cb_payMode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case CASH:
                    lb_balance.setText("��ѡ���ֽ�֧��");
                    tf_payValue.setText(String.valueOf(order.getPrice()));
                    break;
                case BALANCE:
                    lb_balance.setText(String.format("��%.2f", vipBean.getBalance()));
                    tf_payValue.setText(String.valueOf(order.getPrice()));
                    break;
                case INTEGRAL:
                    lb_balance.setText(String.format("%d�����֣�", vipBean.getIntegral()));
                    tf_payValue.setText(String.valueOf(order.getIntegral_cost()));
                    break;
            }
        });

    }

    @FXML
    private void submit() {
        //TODO �ύ�����ݿ�
        new MyAlert(Alert.AlertType.INFORMATION, "����ɹ���").show();
        stage.close();
    }

    @FXML
    private void cancel() {
        stage.close();
    }


    //===========================================================================
    //                           ����ע��controller����
    //===========================================================================

    /**
     * ע��stage����
     *
     * @param stage ���ƵĴ���
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setVipBean(VipBean vipBean) {
        this.vipBean = vipBean;
        lb_consumer.setText("[" + vipBean.getId() + "] " + vipBean.getName());
    }

    public void setOrder(OrderBean order) {
        this.order = order;
        tf_payValue.setText(String.valueOf(order.getPrice()));
    }

    public void setOrderDetail(List<OrderDetailBean> orderDetail) {
        this.orderDetails = orderDetail;
    }

    /**
     * ���ý���
     */
    public void setFocus() {
        tf_transactor.requestFocus();
    }


    //===========================================================================
    //                            �ڲ���
    //===========================================================================

    /**
     * ֧��ģʽö��
     */
    private enum PayMode {

        INTEGRAL("����"),
        CASH("�ֽ�"),
        BALANCE("���");

        private final String val;

        PayMode(String str) {
            this.val = str;
        }

        @Override
        public String toString() {
            return val;
        }
    }
}
