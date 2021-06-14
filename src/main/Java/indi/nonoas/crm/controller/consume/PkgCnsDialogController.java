package indi.nonoas.crm.controller.consume;


import indi.nonoas.crm.pojo.*;
import indi.nonoas.crm.dao.my_orm_dao.*;
import indi.nonoas.crm.common.PayMode;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.dto.VipInfo;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.alert.MyAlert;
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

    /**
     * ������
     */
    private VipInfo vipBean;

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
        cb_payMode.getItems().addAll(PayMode.INTEGRAL, PayMode.CASH, PayMode.BALANCE, PayMode.FREE);
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
                    tf_payValue.setText(String.valueOf(order.getIntegralCost()));
                    break;
                case FREE:
                    lb_balance.setText("��ѡ������");
                    tf_payValue.setText(String.format("��%.2f", 0.00));
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

        //���ü����������ݿ�� �û�-��Ʒ
        List<UserGoods> userGoods = userGoodsData();
        //���ü������������� ��Ʒ
        List<GoodsDto> goodsBeans = goodsData();
        //������Ҫ���µ���������Ϣ
        VipInfo vipBean = vipData();
        //�������ն�����Ϣ
        OrderBean orderBean = orderData();

        OrderDao orderDao = OrderDao.getInstance();
        hasSubmit = orderDao.placePackageOrder(orderBean, orderDetails, userGoods, goodsBeans, vipBean);
        if (hasSubmit) {
            new MyAlert(Alert.AlertType.INFORMATION, "����ɹ���").show();
        } else {
            new MyAlert(Alert.AlertType.INFORMATION, "����ʧ�ܣ�").show();
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
     * ��ȡд�����ݿ�� ���û�-��Ʒ�� bean����
     *
     * @return �û�-��Ʒ bean����
     */
    private List<UserGoods> userGoodsData() {

        List<UserGoods> userGoods = new ArrayList<>(16);       //�񶩵��е������ײ�
        String userID = order.getUserId();      //��ȡ�û�ID
        for (OrderDetailBean od : orderDetails) {   //��������
            String pkgID = od.getProductId();
            //����ײͲ�Ϊ�����࣬����ӵ��û�����Ʒ�����
            PackageDto packageDto = pkgService.selectById(pkgID);
            if (!"������".equals(packageDto.getType()))
                break;
            int pkgAmount = od.getProductAmount();        //��ȡ�ײ�����
            //��ѯ�ײͰ�������Ʒ
            List<PackageContentDto> packageContentDtos = PackageContentDao.getInstance().selectById(pkgID);
            for (PackageContentDto pkgContBean : packageContentDtos) {
                String gID = pkgContBean.getGoodsId();          //��ƷID
                int gAmount = pkgContBean.getGoodsAmount();     //��Ʒ����
                //��ѯ���ݿ��Ƿ��Ѿ����ڸ�����
                UserGoods user_goods = UserGoodsDao.getInstance().selectByUserGoods(userID, gID);
                if (user_goods == null) {
                    user_goods = new UserGoods();
                    user_goods.setUserId(userID);
                    user_goods.setGoodsId(gID);
                    user_goods.setAmount(gAmount * pkgAmount);
                } else {
                    user_goods.setAmount(user_goods.getAmount() + pkgAmount * gAmount);
                }
                userGoods.add(user_goods);
            }
        }
        return userGoods;
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
     * ��ȡ��Ҫ���µ� ����Ʒ�� bean����
     *
     * @return ��Ʒ bean�ļ���
     */
    private List<GoodsDto> goodsData() {
        List<GoodsDto> goodsBeans = new ArrayList<>(16);
        //�����ײͶ���
        for (OrderDetailBean detail : orderDetails) {
            List<PackageContentDto> pkgContBeans = PackageContentDao.getInstance().selectById(detail.getProductId());
            //�����ײ�����
            for (PackageContentDto pkgContBean : pkgContBeans) {
                GoodsDto bean = goodsService.selectById(pkgContBean.getGoodsId());
                //��Ʒ���� -> ������� - �ײ����� * �ײ�����Ʒ����
                bean.setQuantity(bean.getQuantity() - detail.getProductAmount() * pkgContBean.getGoodsAmount());     //�����ݿ��м�ȥ���������
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
    private VipInfo vipData() {
        //���������Ϊɢ�ͣ��򲻽������ݴ���
        if (vipBean == VipInfo.SANKE)
            return vipBean;

        VipInfo bean = this.vipBean;
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
     * ���ý���
     */
    public void setFocus() {
        tf_transactor.requestFocus();
    }


}
