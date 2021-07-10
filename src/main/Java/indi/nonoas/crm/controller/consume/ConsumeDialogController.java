package indi.nonoas.crm.controller.consume;


import indi.nonoas.crm.pojo.*;
import indi.nonoas.crm.common.PayMode;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.service.OrderService;
import indi.nonoas.crm.service.UsrGdsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.alert.MyAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author : Nonoas
 * @time : 2020-08-10 17:59
 */
public class ConsumeDialogController implements Initializable {

    private Stage stage;

    private boolean hasSubmit = false;

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

    private final OrderService orderService = (OrderService) SpringUtil.getBean("OrderServiceImpl");

    private final UsrGdsService ugService = (UsrGdsService) SpringUtil.getBean("UsrGdsServiceImpl");

    /**
     * ������
     */
    private VipInfoDto vipBean;

    /**
     * ����
     */
    private OrderBean order;

    /**
     * 进价
     */
    private List<OrderDetailBean> orderDetails;

    /**
     * 进价��
     */
    @FXML
    private Label lb_consumer;

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
        cb_payMode.getItems().addAll(PayMode.INTEGRAL, PayMode.CASH, PayMode.BALANCE, PayMode.FREE);
        cb_payMode.setValue(PayMode.CASH);
        lb_balance.setText("已选择现金支付");

        cb_payMode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case CASH:
                    lb_balance.setText("已选择现金支付");
                    tf_payValue.setText(String.valueOf(order.getPrice()));
                    break;
                case BALANCE:
                    lb_balance.setText(String.format("￥%.2f", vipBean.getBalance()));
                    tf_payValue.setText(String.format("￥%.2f", order.getPrice()));
                    break;
                case INTEGRAL:
                    lb_balance.setText(String.format("%d积分", vipBean.getIntegral()));
                    tf_payValue.setText(String.valueOf(order.getIntegralCost()));
                    break;
                case FREE:
                    lb_balance.setText("赠送");
                    tf_payValue.setText(String.format("￥%.2f", 0.00));
                    break;
            }
        });

    }

    @FXML
    private void submit() {

        //�ж��Ƿ�����
        if (isOutOfBalance()) {
            stage.close();
            return;
        }
        order.setTransactor(tf_transactor.getText());   //��ȡ������

        //进价���ݿ�� �û�-��Ʒ
        List<UserGoods> userGoods = userGoodsData();
        //进价������ ��Ʒ
        List<GoodsDto> goodsBeans = goodsData();
        //��Ҫ���µ进价�Ϣ
        VipInfoDto vipBean = vipData();
        //���ն�����Ϣ
        OrderBean orderBean = orderData();

        String msg = null;
        try {
            orderService.placeGoodsOrder(orderBean, orderDetails, userGoods, goodsBeans, vipBean);
            hasSubmit = true;
        } catch (Exception e) {
            msg = e.getMessage();
            e.printStackTrace();
            hasSubmit = false;
        }

        if (hasSubmit) {
            new MyAlert(Alert.AlertType.INFORMATION, "����ɹ���").show();
        } else {
            new MyAlert(Alert.AlertType.ERROR, "����ʧ�ܣ�\n������Ϣ��" + msg).show();
        }
        stage.close();
    }

    /**
     * �ж��Ƿ񳬳����
     *
     * @return ������true
     */
    private boolean isOutOfBalance() {
        PayMode payMode = cb_payMode.getValue();
        boolean flag;
        switch (payMode) {
            case BALANCE:
                flag = vipBean.getBalance() < order.getPrice();
                if (flag) new MyAlert(Alert.AlertType.WARNING, "�ֽ����㣡").show();
                return flag;
            case INTEGRAL:
                flag = vipBean.getBalance() < order.getPrice();
                if (flag) new MyAlert(Alert.AlertType.WARNING, "�������㣡").show();
                return flag;
            default:
                return false;
        }
    }

    /**
     * ��ȡ���ն�����Ϣ
     *
     * @return ���ն�����Ϣ
     */
    private OrderBean orderData() {
        PayMode payMode = cb_payMode.getValue();
        order.setPayMode(payMode.val());
        switch (payMode) {
            case CASH:
            case BALANCE:
                order.setIntegralCost(0);
                break;
            case INTEGRAL:
                order.setPrice(0);
                break;
            case FREE:
                order.setPrice(0);
                order.setIntegralCost(0);
                break;
        }
        return order;
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
            String gID = od.getProductId();

            //�����ƷΪ�����࣬����ӵ��û�����Ʒ�����
            GoodsDto bean = goodsService.selectById(gID);
            if (!bean.getType().equals("������"))
                break;

            int gAmount = od.getProductAmount();
            //��ѯ���ݿ��Ƿ��Ѿ����ڸ�����
            UserGoods goods = ugService.selectByUserGoods(userID, gID);
            if (goods == null) {
                goods = new UserGoods();
                goods.setUserId(userID);
                goods.setGoodsId(gID);
                goods.setAmount(od.getProductAmount());
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
    private List<GoodsDto> goodsData() {

        //进价�б��-������
        return orderDetails.stream()
                .map(odrDtlBean -> {
                    GoodsDto bean = goodsService.selectById(odrDtlBean.getProductId());
                    //�����ݿ��м�ȥ进价�
                    bean.setQuantity(bean.getQuantity() - odrDtlBean.getProductAmount());
                    return bean;
                })
                .collect(Collectors.toList());

    }

    /**
     * ��ȡ��Ҫ���µ� ���û��� ����
     *
     * @return ������
     */
    private VipInfoDto vipData() {
        //进价�Ϊɢ�ͣ��򲻽������ݴ���
        if (vipBean == VipInfoDto.SANKE)
            return vipBean;

        VipInfoDto bean = this.vipBean;
        PayMode payMode = cb_payMode.getValue();
        //��Ҫ�۳������ݣ���� || ����
        switch (payMode) {
            case CASH:
            case FREE:
                break;
            case BALANCE:
                bean.setBalance(bean.getBalance() - order.getPrice());
                break;
            case INTEGRAL:
                bean.setIntegral(bean.getIntegral() - order.getIntegralCost());
                break;
        }
        //��Ҫ���ӵģ�����
        bean.setIntegral(bean.getIntegral() + order.getIntegralGet());
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

    public void setVipBean(VipInfoDto vipBean) {
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

}
