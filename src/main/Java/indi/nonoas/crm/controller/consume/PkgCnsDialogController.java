package indi.nonoas.crm.controller.consume;


import indi.nonoas.crm.common.PayMode;
import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.pojo.*;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.service.OrderService;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.service.UsrGdsService;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
public class PkgCnsDialogController implements Initializable {

    private Stage stage;

    private boolean hasSubmit = false;

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

    private final PackageService pkgService = (PackageService) SpringUtil.getBean("PackageServiceImpl");

    private final UsrGdsService usrGdsService = (UsrGdsService) SpringUtil.getBean("UsrGdsServiceImpl");

    private final OrderService orderService = (OrderService) SpringUtil.getBean("OrderServiceImpl");

    /**
     * ������
     */
    private VipInfoDto vipBean;

    /**
     * ����
     */
    private OrderDto order;

    /**
     * 进价
     */
    private List<OrderDetailBean> orderDetails;

    /**
     * 进价��
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

        // 判断是否超出库存
        if (isOutOfBalance()) {
            stage.close();
            return;
        }

        order.setTransactor(tf_transactor.getText());   //��ȡ������

        // 即将修改的 用户-商品 数据
        List<UserGoods> userGoods = userGoodsData();
        // 即将修改的商品数据
        List<GoodsDto> goodsBeans = goodsData();

        // TODO 修改用户数据，可能存在支付失败但是用户类修改的情况
        VipInfoDto vipBean = vipData();
        // 订单数据
        OrderDto orderDto = orderData();

        try {
            orderService.placePackageOrder(orderDto, orderDetails, userGoods, goodsBeans, vipBean);
            hasSubmit = true;
            new MyAlert(Alert.AlertType.INFORMATION, "结算成功！").show();
        } catch (Exception e) {
            hasSubmit = false;
            new MyAlert(Alert.AlertType.INFORMATION, "结算失败！").show();
        }
        stage.close();
    }

    /**
     * 判断余额是否不足
     *
     * @return 不足：true
     */
    private boolean isOutOfBalance() {
        PayMode payMode = cb_payMode.getValue();
        boolean flag;
        switch (payMode) {
            case BALANCE:
                flag = vipBean.getBalance() < order.getPrice();
                if (flag) new MyAlert(Alert.AlertType.WARNING, "余额不足").show();
                return flag;
            case INTEGRAL:
                flag = vipBean.getBalance() < order.getPrice();
                if (flag) new MyAlert(Alert.AlertType.WARNING, "积分不足").show();
                return flag;
            default:
                return false;
        }
    }

    /**
     * 获取即将写入数据库的 用户-商品 信息
     *
     * @return 用户-商品 集合
     */
    private List<UserGoods> userGoodsData() {

        List<UserGoods> userGoods = new ArrayList<>(16);
        String userID = order.getUserId();

        for (OrderDetailBean od : orderDetails) {
            String pkgID = od.getProductId();
            // 如果不是服务类则跳过
            PackageDto packageDto = pkgService.selectById(pkgID);
            if (!"服务类".equals(packageDto.getType())) {
                break;
            }
            int pkgAmount = od.getProductAmount();
            // 查询套餐内包含的商品
            List<PackageContentDto> pkgContList = pkgService.listPkgContentByPkgId(pkgID);
            for (PackageContentDto pkgContBean : pkgContList) {

                UserGoods usrGoods = getUsrGoodsByPkgCont(userID, pkgAmount, pkgContBean);
                userGoods.add(usrGoods);
            }
        }
        return userGoods;
    }

    /**
     * 跳过套餐内容获取 用户-商品
     *
     * @param userId    用户id
     * @param pkgAmount 套餐数量
     * @param pkgCont   套餐内容
     * @return UserGoods 用户-商品
     */
    private UserGoods getUsrGoodsByPkgCont(String userId, int pkgAmount, PackageContentDto pkgCont) {
        String gID = pkgCont.getGoodsId();
        int gAmount = pkgCont.getGoodsAmount();

        UserGoods usrGoods = usrGdsService.selectByUserGoods(userId, gID);
        if (usrGoods == null) {
            usrGoods = new UserGoods();
            usrGoods.setUserId(userId);
            usrGoods.setGoodsId(gID);
            usrGoods.setAmount(gAmount * pkgAmount);
        } else {
            usrGoods.setAmount(usrGoods.getAmount() + pkgAmount * gAmount);
        }
        return usrGoods;
    }

    /**
     * 获取即将提交的 订单数据
     *
     * @return OrderBean
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
     * ��ȡ��Ҫ���µ� ����Ʒ�� bean����
     *
     * @return ��Ʒ bean�ļ���
     */
    private List<GoodsDto> goodsData() {
        List<GoodsDto> goodsBeans = new ArrayList<>(16);
        //�����ײͶ���
        for (OrderDetailBean detail : orderDetails) {
            List<PackageContentDto> pkgContBeans = pkgService.listPkgContentByPkgId(detail.getProductId());
            //�����ײ�����
            for (PackageContentDto pkgContBean : pkgContBeans) {
                GoodsDto bean = goodsService.selectById(pkgContBean.getGoodsId());
                //��Ʒ���� -> ������� - �ײ����� * �ײ�����Ʒ����
                bean.setQuantity(bean.getQuantity() - detail.getProductAmount() * pkgContBean.getGoodsAmount());     //�����ݿ��м�ȥ进价�
                goodsBeans.add(bean);
            }
        }
        return goodsBeans;
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

    public void setOrder(OrderDto order) {
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
