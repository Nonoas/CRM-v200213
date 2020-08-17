package indi.nonoas.crm.controller.consume;


import indi.nonoas.crm.beans.*;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.dao.OrderDao;
import indi.nonoas.crm.dao.PackageDao;
import indi.nonoas.crm.dao.UserGoodsDao;
import indi.nonoas.crm.view.alert.MyAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author : Nonoas
 * @time : 2020-08-10 17:59
 */
public class ConsumeDialogController implements Initializable {

    private Stage stage;

    private boolean hasSubmit = false;

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
        order.setTransactor(tf_transactor.getText());   //��ȡ������

        //���ü����������ݿ�� �û�-��Ʒ
        List<UserGoods> userGoods = userGoodsData();
        //���ü������������� ��Ʒ
        List<GoodsBean> goodsBeans = goodsData();
        //������Ҫ���µ���������Ϣ
        VipBean vipBean = vipData();

        OrderDao orderDao = OrderDao.getInstance();
        hasSubmit = orderDao.placeGoodsOrder(order, orderDetails, userGoods, goodsBeans, vipBean);
        if (hasSubmit) {
            new MyAlert(Alert.AlertType.INFORMATION, "����ɹ���").show();
        } else {
            new MyAlert(Alert.AlertType.INFORMATION, "����ʧ�ܣ�").show();
        }
        stage.close();
    }

    /**
     * ��ȡд�����ݿ�� ���û�-��Ʒ�� bean����
     *
     * @return �û�-��Ʒ bean����
     */
    private List<UserGoods> userGoodsData() {
        List<UserGoods> userGoods = new ArrayList<>(orderDetails.size());
        String userID = order.getUserId();
        for (OrderDetailBean od : orderDetails) {
            String gID = od.getGoodsId();

            //�����ƷΪ�����࣬����ӵ��û�����Ʒ�����
            GoodsBean bean = GoodsDao.getInstance().selectById(gID);
            if (!bean.getType().equals("������"))
                break;

            int gAmount = od.getGoodsAmount();
            //��ѯ���ݿ��Ƿ��Ѿ����ڸ�����
            UserGoods goods = UserGoodsDao.getInstance().selectByUserGoods(userID, gID);
            if (goods == null) {
                goods = new UserGoods();
                goods.setUserId(userID);
                goods.setGoodsId(gID);
                goods.setAmount(od.getGoodsAmount());
            } else {
                goods.setAmount(goods.getAmount() + gAmount);
            }
            userGoods.add(goods);
        }
        return userGoods;
    }


    /**
     * ��ȡ��Ҫ���µ� ����Ʒ�� bean����
     *
     * @return ��Ʒ bean�ļ���
     */
    private List<GoodsBean> goodsData() {
        List<GoodsBean> goodsBeans = new ArrayList<>(orderDetails.size());
        for (OrderDetailBean detail : orderDetails) {
            GoodsBean bean = GoodsDao.getInstance().selectById(detail.getGoodsId());
            bean.setQuantity(bean.getQuantity() - detail.getGoodsAmount());     //�����ݿ��м�ȥ���������
            goodsBeans.add(bean);
        }
        return goodsBeans;
    }

    /**
     * ��ȡ��Ҫ���µ� ���û��� ����
     *
     * @return ������
     */
    private VipBean vipData() {
        //���������Ϊɢ�ͣ��򲻽������ݴ���
        if (vipBean == VipBean.SANKE)
            return vipBean;

        VipBean bean = this.vipBean;
        PayMode payMode = cb_payMode.getValue();
        //��Ҫ�۳������ݣ���� || ����
        switch (payMode) {
            case CASH:
                break;
            case BALANCE:
                bean.setBalance(bean.getBalance() - order.getPrice());
                break;
            case INTEGRAL:
                bean.setIntegral(bean.getIntegral() - order.getIntegral_cost());
                break;
        }
        //��Ҫ���ӵģ�����
        bean.setIntegral(bean.getIntegral() + order.getIntegral_get());
        return bean;
    }

    @FXML
    private void cancel() {
        stage.close();
    }

    /**
     * �ж��Ƿ�ɹ��ύ
     *
     * @return �ɹ�����true��ʧ�ܷ���false
     */
    public boolean hasSubmit() {
        return hasSubmit;
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
