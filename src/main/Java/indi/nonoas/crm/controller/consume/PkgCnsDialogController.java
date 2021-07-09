package indi.nonoas.crm.controller.consume;


import indi.nonoas.crm.common.PayMode;
import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.pojo.*;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.dto.VipInfo;
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
     * 消费者
     */
    private VipInfo vipBean;

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
        cb_payMode.getItems().addAll(PayMode.INTEGRAL, PayMode.CASH, PayMode.BALANCE, PayMode.FREE);
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
                    tf_payValue.setText(String.valueOf(order.getIntegralCost()));
                    break;
                case FREE:
                    lb_balance.setText("已选择赠送");
                    tf_payValue.setText(String.format("￥%.2f", 0.00));
                    break;
            }
        });

    }

    @FXML
    private void submit() {

        //判断是否余额不足
        if (isOutOfBalance()) {
            stage.close();
            return;
        }

        order.setTransactor(tf_transactor.getText());   //获取受理人

        //设置即将传入数据库的 用户-商品
        List<UserGoods> userGoods = userGoodsData();
        //设置即将减少数量的 商品
        List<GoodsDto> goodsBeans = goodsData();
        //设置需要更新的消费者信息

        // TODO 可能存在消费不成功，但是用户Bean数据被修改的情况
        VipInfo vipBean = vipData();
        //设置最终订单信息
        OrderBean orderBean = orderData();

        try {
            orderService.placePackageOrder(orderBean, orderDetails, userGoods, goodsBeans, vipBean);
            hasSubmit = true;
            new MyAlert(Alert.AlertType.INFORMATION, "结算成功！").show();
        } catch (Exception e) {
            hasSubmit = false;
            new MyAlert(Alert.AlertType.INFORMATION, "结算失败！").show();
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
                if (flag) new MyAlert(Alert.AlertType.WARNING, "现金余额不足！").show();
                return flag;
            case INTEGRAL:
                flag = vipBean.getBalance() < order.getPrice();
                if (flag) new MyAlert(Alert.AlertType.WARNING, "积分余额不足！").show();
                return flag;
            default:
                return false;
        }
    }

    /**
     * 获取写入数据库的 “用户-商品” bean对象集合
     *
     * @return 用户-商品 bean集合
     */
    private List<UserGoods> userGoodsData() {

        List<UserGoods> userGoods = new ArrayList<>(16);
        String userID = order.getUserId();      //获取用户ID

        for (OrderDetailBean od : orderDetails) {   //遍历订单
            String pkgID = od.getProductId();
            //如果套餐不为服务类，则不添加到用户的商品库存中
            PackageDto packageDto = pkgService.selectById(pkgID);
            if (!"服务类".equals(packageDto.getType())) {
                break;
            }
            int pkgAmount = od.getProductAmount();        //获取套餐数量
            //查询套餐包含的商品
            List<PackageContentDto> pkgContList = pkgService.listPkgContentByPkgId(pkgID);
            for (PackageContentDto pkgContBean : pkgContList) {

                UserGoods usrGoods = getUsrGoodsByPkgCont(userID, pkgAmount, pkgContBean);
                userGoods.add(usrGoods);
            }
        }
        return userGoods;
    }

    /**
     * 通过套餐内容来生成要插入数据库的 用户-商品 数据
     *
     * @param userId    用户id
     * @param pkgAmount 套餐数量
     * @param pkgCont   套餐内容
     * @return UserGoods 非空
     */
    private UserGoods getUsrGoodsByPkgCont(String userId, int pkgAmount, PackageContentDto pkgCont) {
        String gID = pkgCont.getGoodsId();          //商品ID
        int gAmount = pkgCont.getGoodsAmount();     //商品数量
        //查询数据库是否已经存在该主键对应的数据
        UserGoods usrGoods = usrGdsService.selectByUserGoods(userId, gID);
        if (usrGoods == null) {
            usrGoods = new UserGoods();
            usrGoods.setUserId(userId);
            usrGoods.setGoodsId(gID);
            usrGoods.setAmount(gAmount * pkgAmount);
        } else {
            // 如果存在则直接加上购买数量
            usrGoods.setAmount(usrGoods.getAmount() + pkgAmount * gAmount);
        }
        return usrGoods;
    }

    /**
     * 获取最终订单信息
     *
     * @return 最终订单信息
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
     * 获取需要更新的 “商品” bean对象
     *
     * @return 商品 bean的集合
     */
    private List<GoodsDto> goodsData() {
        List<GoodsDto> goodsBeans = new ArrayList<>(16);
        //遍历套餐订单
        for (OrderDetailBean detail : orderDetails) {
            List<PackageContentDto> pkgContBeans = pkgService.listPkgContentByPkgId(detail.getProductId());
            //遍历套餐内容
            for (PackageContentDto pkgContBean : pkgContBeans) {
                GoodsDto bean = goodsService.selectById(pkgContBean.getGoodsId());
                //商品数量 -> 库存数量 - 套餐数量 * 套餐内商品数量
                bean.setQuantity(bean.getQuantity() - detail.getProductAmount() * pkgContBean.getGoodsAmount());     //从数据库中减去购买的数量
                goodsBeans.add(bean);
            }
        }
        return goodsBeans;
    }

    /**
     * 获取需要更新的 “用户” 对象
     *
     * @return 消费者
     */
    private VipInfo vipData() {
        //如果消费者为散客，则不进行数据处理
        if (vipBean == VipInfo.SANKE)
            return vipBean;

        VipInfo bean = this.vipBean;
        PayMode payMode = cb_payMode.getValue();
        //需要扣除的数据：余额 || 积分
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
        //需要增加的：积分
        bean.setIntegral(bean.getIntegral() + order.getIntegralGet());
        return bean;
    }

    @FXML
    private void cancel() {
        stage.close();
    }

    /**
     * 判断是否成功提交
     *
     * @return 成功返回true，失败返回false
     */
    public boolean hasSubmit() {
        return hasSubmit;
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

    public void setVipBean(VipInfo vipBean) {
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


}
