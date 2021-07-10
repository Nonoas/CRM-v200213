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
     * 消费者
     */
    private VipInfoDto vipBean;

    /**
     * 订单
     */
    private OrderDto order;

    /**
     * 订单详情
     */
    private List<OrderDetailBean> orderDetails;

    /**
     * 消费者姓名
     */
    @FXML
    private Label lb_consumer;

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

        // 判断是否超余额
        if (isOutOfBalance()) {
            stage.close();
            return;
        }

        // 设置受理人
        order.setTransactor(tf_transactor.getText());

        // 用户商品信息
        List<UserGoods> userGoods = userGoodsData();
        // 商品信息
        List<GoodsDto> goodsBeans = goodsData();
        // 用户信息
        VipInfoDto vipBean = vipData();
        // 订单信息
        OrderDto orderDto = orderData();

        String msg = null;
        try {
            orderService.placeGoodsOrder(orderDto, orderDetails, userGoods, goodsBeans, vipBean);
            hasSubmit = true;
        } catch (Exception e) {
            msg = e.getMessage();
            hasSubmit = false;
            e.printStackTrace();
        }

        if (hasSubmit) {
            new MyAlert(Alert.AlertType.INFORMATION, "结算成功").show();
        } else {
            new MyAlert(Alert.AlertType.ERROR, "结算失败\n错误信息：" + msg).show();
        }
        stage.close();
    }

    /**
     * 判断是否超出余额
     *
     * @return 超出：true
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
     * 获取最终订单信息
     *
     * @return 最终订单信息
     */
    private OrderDto orderData() {
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
     * 获取写入数据库的 “用户-商品” bean对象
     *
     * @return 用户-商品 bean集合
     */
    private List<UserGoods> userGoodsData() {
        List<UserGoods> userGoods = new ArrayList<>(orderDetails.size());
        String userID = order.getUserId();
        for (OrderDetailBean od : orderDetails) {
            String gID = od.getProductId();

            GoodsDto bean = goodsService.selectById(gID);
            if (!bean.getType().equals("服务类"))
                break;

            int gAmount = od.getProductAmount();
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
     * 设置消费或的商品信息
     *
     * @return 消费后的商品信息
     */
    private List<GoodsDto> goodsData() {

        return orderDetails.stream()
                .map(odrDtlBean -> {
                    GoodsDto bean = goodsService.selectById(odrDtlBean.getProductId());
                    bean.setQuantity(bean.getQuantity() - odrDtlBean.getProductAmount());
                    return bean;
                })
                .collect(Collectors.toList());

    }

    /**
     * 获取消费或的用户信息
     *
     * @return VipInfoDto
     */
    private VipInfoDto vipData() {
        // 判断消费者是否为散客
        if (vipBean == VipInfoDto.SANKE)
            return vipBean;

        VipInfoDto bean = this.vipBean;
        PayMode payMode = cb_payMode.getValue();

        // 根据支付方式来设置用户余额
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
        // 设置获取的积分
        bean.setIntegral(bean.getIntegral() + order.getIntegralGet());
        return bean;
    }

    @FXML
    private void cancel() {
        stage.close();
    }


    public boolean hasSubmit() {
        return hasSubmit;
    }


    //===========================================================================
    //                           ����ע��controller����
    //===========================================================================

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setVipBean(VipInfoDto vipBean) {
        this.vipBean = vipBean;
        lb_consumer.setText("[" + vipBean.getId() + "] " + vipBean.getName());
    }

    public void setOrder(OrderDto order) {
        this.order = order;
        tf_payValue.setText(String.valueOf(order.getPrice()));
    }

    public void setOrderDetail(List<OrderDetailBean> orderDetail) {
        this.orderDetails = orderDetail;
    }

    public void setFocus() {
        tf_transactor.requestFocus();
    }

}
