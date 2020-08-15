package indi.nonoas.crm.controller.consume;

import indi.nonoas.crm.app.consume.ConsumeDialog;
import indi.nonoas.crm.app.consume.CountConsumeTable;
import indi.nonoas.crm.app.consume.GoodsConsumeTable;
import indi.nonoas.crm.app.consume.PackageConsumeTable;
import indi.nonoas.crm.app.goods.GoodsSingleSelectTable;
import indi.nonoas.crm.app.pkg.PackageSingleSelectTable;
import indi.nonoas.crm.app.vip.UserGoodsTable;
import indi.nonoas.crm.app.vip.VipAddTab;
import indi.nonoas.crm.app.vip.VipInfoTable;
import indi.nonoas.crm.beans.OrderBean;
import indi.nonoas.crm.beans.OrderDetailBean;
import indi.nonoas.crm.beans.PackageBean;
import indi.nonoas.crm.beans.VipBean;
import indi.nonoas.crm.dao.VipInfoDao;
import indi.nonoas.crm.dao.VipLevelDao;
import indi.nonoas.crm.service.OrderService;
import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.view.table.GoodsEditTableData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ConsumeController implements Initializable {

    private final Logger logger = Logger.getLogger(ConsumeController.class);

    /**
     * ��Ա��ϢDAO
     */
    private final VipInfoDao vipInfoDao = VipInfoDao.getInstance();

    //TODO ��Ҫ����һ��ɢ��
    /**
     * ɢ�ͳ���
     */
    private final static VipBean SANKE = new VipBean();

    static {
        SANKE.setId("0000");
        SANKE.setName("ɢ��");
        SANKE.setSex("����");
        SANKE.setDiscount(1);
    }

    /**
     * ��Ա��Ϣ
     */
    private VipBean vipBean = SANKE;
    @FXML
    private TabPane tp_rootPane;
    @FXML
    private Label lb_cardState;
    @FXML
    private Label lb_id;
    @FXML
    private Label lb_integral;
    @FXML
    private Label lb_name;
    @FXML
    private Label lb_cardLevel;
    @FXML
    private TextField tf_find;
    @FXML
    private TextField tf_findInfo;
    @FXML
    private ScrollPane sp_userInfo;
    @FXML
    private ComboBox<String> cb_disType;

    private final VipInfoTable tv_vipInfo = new VipInfoTable(); // ��Ա��Ϣ��

    public ConsumeController() {
        initData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
        //���ûس���ѯ
        tf_find.setOnKeyPressed(keyEven -> {
            if (keyEven.getCode().equals(KeyCode.ENTER))
                inquireVIP();
        });
    }

    @FXML // ������Ϣ
    private void inquireVIP() {
        String str = tf_find.getText().trim();
        if (str.equals(""))
            return;
        vipBean = vipInfoDao.getInfoByIdOrName(str, str);
        if (vipBean != null) {
            showFindResult(vipBean);
        } else {
            new MyAlert(AlertType.INFORMATION, "û���ҵ�����ѯ�Ļ�Ա��").show();
        }
    }

    /**
     * ��ʾ�������
     *
     * @param bean �������û���Ϣ
     */
    private void showFindResult(VipBean bean) {
        if (bean == null)
            return;
        lb_cardState.setText("����");
        lb_id.setText(bean.getId());
        lb_integral.setText(String.valueOf(bean.getIntegral()));
        lb_cardLevel.setText(bean.getCardLevel());
        lb_name.setText(bean.getName());
        if (bean == SANKE)
            lb_name.setStyle("-fx-text-fill: #cf4813");
        else
            lb_name.setStyle("-fx-text-fill: black");
    }

    @FXML
    private void inquireVIPInfo() {
        String str = "%" + tf_findInfo.getText().trim() + "%";
        String str0 = cb_disType.getValue().equals("ȫ������") ? "" : cb_disType.getValue();
        String disType = str0 + "%";
        if (str.equals("%%"))
            return;
        ArrayList<VipBean> listVipBeans = vipInfoDao.selectByFiltrate(str, str, disType);
        if (listVipBeans != null) {
            tv_vipInfo.clearData();
            for (VipBean bean : listVipBeans)
                tv_vipInfo.addBean(bean);
        } else {
            new MyAlert(AlertType.INFORMATION, "û���ҵ�����ѯ�Ļ�Ա��").show();
        }
    }

    @FXML // ��ʾȫ����Ϣ
    private void showAll() {
        tv_vipInfo.showAllInfos();
    }

    @FXML // ���չʾ����Ϣ
    private void clearInfo() {
        tf_find.setText("");
        vipBean = SANKE;
        showFindResult(vipBean);
    }

    @FXML // ��ӻ�Ա��Ϣ
    private void addVip() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "��ӻ�Ա";
        for (Tab tab : obList) { // �����жϸ�tab�Ƿ��Ѿ����
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tab.setClosable(true);
                tp_rootPane.getSelectionModel().select(tab); // ����Ѿ��������ʾ��tab������
                return;
            }
        }
        VipAddTab tab = new VipAddTab();
        tab.setUserData(DATA);
        tp_rootPane.getTabs().add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

    private void initData() {
    }

    private void initView() {
        showFindResult(vipBean);
        tv_vipInfo.showAllInfos();
        sp_userInfo.setContent(tv_vipInfo);
        // �����ݿ�������û�Ա�ȼ�������ʼ��ComboBox
        LinkedList<String> listName = new VipLevelDao().selectAllNames();
        cb_disType.getItems().add("ȫ������");
        for (String str : listName) {
            cb_disType.getItems().add(str);
        }
        cb_disType.setValue("ȫ������");

        initGoodsTab();        //��ʼ����Ʒ�������
        initPackageTab();       //��ʼ���ײ��������
        initCountTab();         //��ʼ���ƴ��������
    }


    //===========================================================================
    //                             ��Ʒ����
    //===========================================================================

    private final GoodsConsumeTable gc_table = new GoodsConsumeTable();

    private final GoodsSingleSelectTable goodsSelectTable = new GoodsSingleSelectTable(gc_table);
    @FXML
    private BorderPane pt_borderPane;
    @FXML
    private ScrollPane pt_sp_goods;
    @FXML
    private Label pt_order_price;
    @FXML
    private Label pt_order_dis_price;
    @FXML
    private TextField shp_integral_cost;
    @FXML
    private TextField shp_integral;
    @FXML
    private TextField shp_orderNum;
    @FXML
    private TextField shp_orderDate;

    private void initGoodsTab() {
        pt_borderPane.setCenter(gc_table);
        pt_sp_goods.setContent(goodsSelectTable);

        //���ñ��ļ����¼�
        gc_table.getEventHandler().addEvent(() -> {
            if (shp_orderNum.getText().equals("")) {
                shp_orderNum.setText(OrderService.goodsOrderNum());
            }
            if (shp_orderDate.getText().equals("")) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                shp_orderDate.setText(sdf.format(LocalDateTime.now()));
            }
            pt_order_price.setText(String.format("%.2f", gc_table.getSumPrice()));

            //�û��ۿ�
            double discount = vipBean.getDiscount();
            pt_order_dis_price.setText(String.format("%.2f", gc_table.getSumPrice() * discount));
            //���ֻ�ȡ
            shp_integral.setText("0");
            //��������
            shp_integral_cost.setText("0");


        });

        //���ۺ��->�û�ID������
        lb_id.textProperty().addListener((observable, oldValue, newValue) -> {
            logger.debug("��Ա���ţ�" + newValue);
            double discount = vipBean.getDiscount();
            pt_order_dis_price.setText(String.format("%.2f", gc_table.getSumPrice() * discount));
        });
    }


    @FXML
    private void clearGoodsOrder() {
        gc_table.clearData();
        shp_orderNum.setText("");
        shp_orderDate.setText("");
        shp_integral.setText("");
        shp_integral_cost.setText("");
    }

    @FXML
    private void goodsOrderPay() {
        if (gc_table.getItems().size() == 0) {
            new MyAlert(AlertType.INFORMATION, "��������Ϊ�գ���").show();
            return;
        }
        OrderBean orderBean = generateGoodsOrder();
        List<OrderDetailBean> orderDetails = generateGoodsOrderDetails();
        ConsumeDialog consumeDialog = new ConsumeDialog(vipBean, orderBean, orderDetails);
        consumeDialog.showAndWait();
        //����ɹ��ύ�������������Ϣ
        if (consumeDialog.hasSubmit())
            clearGoodsOrder();
    }

    /**
     * ���ɶ���
     *
     * @return ������Ϣ
     */
    private OrderBean generateGoodsOrder() {
        OrderBean bean = new OrderBean();
        bean.setUserId(vipBean.getId());
        bean.setOrderId(shp_orderNum.getText());
        bean.setDatetime(shp_orderDate.getText());
        bean.setPrice(new BigDecimal(pt_order_dis_price.getText()));
        bean.setIntegral_get(Integer.parseInt(shp_integral.getText()));
        bean.setIntegral_cost(Integer.parseInt(shp_integral_cost.getText()));
        return bean;
    }

    /**
     * ���ɶ�������
     *
     * @return �������鼯��
     */
    private List<OrderDetailBean> generateGoodsOrderDetails() {
        List<OrderDetailBean> list = new ArrayList<>();
        ObservableList<GoodsEditTableData> items = gc_table.getItems();
        OrderDetailBean bean;
        for (GoodsEditTableData data : items) {
            bean = new OrderDetailBean();
            bean.setOrderId(shp_orderNum.getText());
            bean.setGoodsId(data.getId());
            bean.setGoodsAmount(data.getAmount());
            list.add(bean);
        }
        return list;
    }


    //===========================================================================
    //                            �ײ�����
    //===========================================================================

    /**
     * �ײ͵�ѡ���
     */
    private final PackageSingleSelectTable pkgSelectTable = new PackageSingleSelectTable();

    /**
     * �ײ����ѱ��
     */
    private final PackageConsumeTable pcTable = new PackageConsumeTable();
    @FXML
    private BorderPane tc_borderPane;
    @FXML
    private ScrollPane tc_sp_goods;
    @FXML
    private Label tc_order_price;
    @FXML
    private Label tc_order_dis_price;
    @FXML
    private TextField tc_integral;
    @FXML
    private TextField tc_orderNum;
    @FXML
    private TextField tc_orderDate;

    private void initPackageTab() {
        tc_sp_goods.setContent(pkgSelectTable);
        tc_borderPane.setCenter(pcTable);
        //���ü���
        pcTable.getEventHandler().addEvent(() -> {
            if (tc_orderNum.getText().equals("")) {
                tc_orderNum.setText(OrderService.packageOrderNum());
            }
            if (tc_orderDate.getText().equals("")) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                tc_orderDate.setText(sdf.format(LocalDateTime.now()));
            }
            tc_order_price.setText(String.format("%.2f", pcTable.getSumPrice()));
            tc_order_dis_price.setText("0.00");
            tc_integral.setText("0");
        });

        pkgSelectTable.getEventHandler().addEvent(() -> {
            PackageBean bean = pkgSelectTable.getSelectedData();
            if (bean != null)
                pcTable.addBean(bean);
        });
    }

    @FXML
    private void clearPackageTable() {
        pcTable.clearData();
    }


    //===========================================================================
    //                            �ƴ�����
    //===========================================================================

    private final CountConsumeTable ccTable = new CountConsumeTable();

    private final UserGoodsTable userGoodsTable = new UserGoodsTable();

    @FXML
    private BorderPane jc_borderPane;

    @FXML
    private ScrollPane jc_sp_goods;

    private void initCountTab() {
        jc_borderPane.setCenter(ccTable);
        jc_sp_goods.setContent(userGoodsTable);
    }

}
