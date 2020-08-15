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
     * 消费者
     */
    private VipBean vipBean;

    /**
     * 订单
     */
    private OrderBean order;

    /**
     * 订单详情
     */
    private List<OrderDetailBean> orderDetails;

    /**
     * 消费者姓名
     */
    @FXML
    private Label lb_consumer;

    @FXML
    private GridPane gp_rootPane;

    /**
     * 支付方式
     */
    @FXML
    private ComboBox<PayMode> cb_payMode;

    /**
     * 受理人
     */
    @FXML
    private TextField tf_transactor;

    /**
     * 支付数额
     */
    @FXML
    private TextField tf_payValue;
    /**
     * 余额
     */
    @FXML
    private Label lb_balance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_payMode.getItems().addAll(PayMode.INTEGRAL, PayMode.CASH, PayMode.BALANCE);
        cb_payMode.setValue(PayMode.CASH);
        lb_balance.setText("已选择现金支付");
        //添加下拉选项监听
        cb_payMode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case CASH:
                    lb_balance.setText("已选择现金支付");
                    tf_payValue.setText(String.valueOf(order.getPrice()));
                    break;
                case BALANCE:
                    lb_balance.setText(String.format("￥%.2f", vipBean.getBalance()));
                    tf_payValue.setText(String.valueOf(order.getPrice()));
                    break;
                case INTEGRAL:
                    lb_balance.setText(String.format("%d（积分）", vipBean.getIntegral()));
                    tf_payValue.setText(String.valueOf(order.getIntegral_cost()));
                    break;
            }
        });

    }

    @FXML
    private void submit() {
        //TODO 提交到数据库
        new MyAlert(Alert.AlertType.INFORMATION, "结算成功！").show();
        stage.close();
    }

    @FXML
    private void cancel() {
        stage.close();
    }


    //===========================================================================
    //                           主类注入controller方法
    //===========================================================================

    /**
     * 注入stage依赖
     *
     * @param stage 控制的窗口
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
     * 设置焦点
     */
    public void setFocus() {
        tf_transactor.requestFocus();
    }


    //===========================================================================
    //                            内部类
    //===========================================================================

    /**
     * 支付模式枚举
     */
    private enum PayMode {

        INTEGRAL("积分"),
        CASH("现金"),
        BALANCE("余额");

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
