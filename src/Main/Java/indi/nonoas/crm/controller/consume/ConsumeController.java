package indi.nonoas.crm.controller.consume;

import indi.nonoas.crm.app.consume.*;
import indi.nonoas.crm.app.goods.GoodsSingleSelectTable;
import indi.nonoas.crm.app.pkg.PackageSingleSelectTable;
import indi.nonoas.crm.app.consume.UserGoodsTable;
import indi.nonoas.crm.app.vip.VipAddTab;
import indi.nonoas.crm.app.vip.VipInfoTable;
import indi.nonoas.crm.beans.*;
import indi.nonoas.crm.dao.*;
import indi.nonoas.crm.service.OrderService;
import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.beans.vo.GoodsEditTableData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

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

    /**
     * ɢ�ͳ���
     */
    private final static VipBean SANKE = VipBean.SANKE;

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
        tf_find.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER))
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
    private BorderPane bp_goodsTable;
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
        bp_goodsTable.setCenter(goodsSelectTable);

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
    private void payGoodsOrder() {

        if (isGoodsOrderOutOfStock())
            return;

        if (gc_table.getItems().size() == 0) {
            new MyAlert(AlertType.INFORMATION, "��������Ϊ�գ���").show();
            return;
        }

        OrderBean orderBean = generateGoodsOrder();
        List<OrderDetailBean> orderDetails = generateGoodsOrderDetails();
        ConsumeDialog consumeDialog = new ConsumeDialog(vipBean, orderBean, orderDetails);
        consumeDialog.showAndWait();
        //����ɹ��ύ�������������Ϣ
        if (consumeDialog.hasSubmit()) {
            clearGoodsOrder();
            goodsSelectTable.showAllInfos();
        }
    }

    /**
     * �ж��Ƿ񳬳����
     *
     * @return ������淵��true
     */
    private boolean isGoodsOrderOutOfStock() {
        ObservableList<GoodsEditTableData> items = gc_table.getItems();
        for (GoodsEditTableData data : items) {
            String goodsID = data.getId();
            int costCount = data.getAmount();
            GoodsBean goodsBean = GoodsDao.getInstance().selectById(goodsID);
            int storeCount = (int) goodsBean.getQuantity();
            if (costCount > storeCount) {
                MyAlert alert = new MyAlert(AlertType.WARNING,
                        "��(" + goodsID + ")" + data.getName() + "����治�� " + costCount + goodsBean.getBaseUnit() + " ��");
                alert.setHeaderText("��治��");
                alert.show();
                return true;
            }
        }
        return false;
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
        bean.setPrice(Double.parseDouble(pt_order_dis_price.getText()));
        bean.setIntegralGet(Integer.parseInt(shp_integral.getText()));
        bean.setIntegralCost(Integer.parseInt(shp_integral_cost.getText()));
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
            bean.setProductId(data.getId());
            bean.setProductAmount(data.getAmount());
            list.add(bean);
        }
        return list;
    }

    @FXML
    private void refreshGoodsTable() {
        goodsSelectTable.showAllInfos();
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
    private BorderPane bp_pkgTable;
    @FXML
    private Label tc_order_price;
    @FXML
    private Label tc_order_dis_price;
    @FXML
    private TextField tc_integral_get;
    @FXML
    private TextField tc_integral_cost;
    @FXML
    private TextField tc_orderNum;
    @FXML
    private TextField tc_orderDate;

    /**
     * ��ʼ�����ײͶ���������
     */
    private void initPackageTab() {
        bp_pkgTable.setCenter(pkgSelectTable);
        tc_borderPane.setCenter(pcTable);
        //�ۺ��
        tc_order_dis_price.setText("�ײͲ�����");
        tc_order_dis_price.setStyle("-fx-text-fill: #cf4813");
        //���ü���
        pcTable.getEventHandler().addEvent(() -> {
            //���ö�����
            if (tc_orderNum.getText().equals("")) {
                tc_orderNum.setText(OrderService.packageOrderNum());
            }
            //���ö�������
            if (tc_orderDate.getText().equals("")) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                tc_orderDate.setText(sdf.format(LocalDateTime.now()));
            }
            //�������
            tc_order_price.setText(String.format("%.2f", pcTable.getSumPrice()));
            //���ֻ�ȡ
            tc_integral_get.setText("0");
            //��������
            tc_integral_cost.setText("0");
        });

        pkgSelectTable.getEventHandler().addEvent(() -> {
            PackageBean bean = pkgSelectTable.getSelectedData();
            if (bean != null)
                pcTable.addBean(bean);
        });
    }

    /**
     * ����ײͶ���
     */
    @FXML
    private void clearPackageOrder() {
        pcTable.clearData();
        tc_orderNum.setText("");
        tc_orderDate.setText("");
        tc_integral_get.setText("");
        tc_integral_cost.setText("");
    }

    @FXML
    private void payPackageOrder() {
        //�ж��Ƿ񳬳����
        if (isPackageOrderOutOfStock())
            return;
        //�ж϶����Ƿ�Ϊ��
        if (pcTable.getItems().size() == 0) {
            new MyAlert(AlertType.INFORMATION, "��������Ϊ�գ���").show();
            return;
        }
        //���ɶ�������
        OrderBean orderBean = generatePackageOrder();
        //���ɶ�����������
        List<OrderDetailBean> orderDetails = generatePackageOrderDetails();
        //�������Ѵ���
        PackageConsumeDialog consumeDialog = new PackageConsumeDialog(vipBean, orderBean, orderDetails);
        consumeDialog.showAndWait();
        //����ɹ��ύ�������������Ϣ
        if (consumeDialog.hasSubmit()) {
            clearPackageOrder();
            pkgSelectTable.showAllInfos();
        }
    }

    /**
     * �����ײͶ���
     *
     * @return �ײͶ���
     */
    private OrderBean generatePackageOrder() {
        OrderBean bean = new OrderBean();
        bean.setUserId(vipBean.getId());
        bean.setOrderId(tc_orderNum.getText());
        bean.setDatetime(tc_orderDate.getText());
        bean.setPrice(Double.parseDouble(tc_order_price.getText()));
        bean.setIntegralGet(Integer.parseInt(tc_integral_get.getText()));
        bean.setIntegralCost(Integer.parseInt(tc_integral_cost.getText()));
        return bean;
    }

    /**
     * ���ɶ�������
     *
     * @return �������鼯��
     */
    private List<OrderDetailBean> generatePackageOrderDetails() {
        List<OrderDetailBean> list = new ArrayList<>();
        ObservableList<GoodsEditTableData> pkgItems = pcTable.getItems();
        OrderDetailBean bean;
        for (GoodsEditTableData pkg : pkgItems) {
            bean = new OrderDetailBean();
            bean.setOrderId(tc_orderNum.getText());
            bean.setProductId(pkg.getId());
            bean.setProductAmount(pkg.getAmount());
            list.add(bean);
        }
        return list;
    }

    /**
     * �ж��ײͶ����Ƿ�Ϊ��
     *
     * @return �գ�true
     */
    private boolean isPackageOrderOutOfStock() {
        PackageContentDao pcDao = PackageContentDao.getInstance();
        ObservableList<GoodsEditTableData> pkgItems = pcTable.getItems();
        //�����������ײ��б�
        for (GoodsEditTableData data : pkgItems) {
            String pkgID = data.getId();
            int costPkgCount = data.getAmount();
            //��ѯ�ײͰ�������Ʒ
            List<PackageContentBean> packageContents = pcDao.selectById(pkgID);
            if (packageContents == null)
                break;
            //�����ײ�����Ʒ�б�
            for (PackageContentBean bean : packageContents) {
                String goodsID = bean.getGoodsId();
                System.out.println("��ƷID" + goodsID);
                int costCount = bean.getGoodsAmount() * costPkgCount;   //���ĵ���Ʒ����
                GoodsBean goodsBean = GoodsDao.getInstance().selectById(goodsID);
                int storeCount = (int) goodsBean.getQuantity();     //�����Ʒ����
                System.out.println("���ģ�" + costCount + ",����" + storeCount);
                if (costCount > storeCount) {
                    String msg = "��(" + pkgID + ")" + data.getName() + "���ײ��� " +
                            "��(" + goodsID + ")" + goodsBean.getName() + "����治�� " + costCount + goodsBean.getBaseUnit() + " ��";
                    MyAlert alert = new MyAlert(AlertType.WARNING, msg);
                    alert.setHeaderText("��治��");
                    alert.show();
                    return true;
                }
            }
        }
        return false;
    }

    @FXML
    private void refreshPackageTable() {
        pkgSelectTable.showAllInfos();
    }


    //===========================================================================
    //                            �ƴ�����
    //===========================================================================

    private final CountConsumeTable ccTable = new CountConsumeTable();

    private final UserGoodsTable userGoodsTable = new UserGoodsTable();

    @FXML
    private BorderPane jc_borderPane;

    @FXML
    private BorderPane jc_goodsBorderPane;

    @FXML
    private TextField jc_orderTime;

    /**
     * ������
     */
    @FXML
    TextField jc_transactor;

    private void initCountTab() {
        jc_borderPane.setCenter(ccTable);
        jc_goodsBorderPane.setCenter(userGoodsTable);

        lb_id.textProperty().addListener((observable, oldValue, newValue) -> userGoodsTable.setVipBean(vipBean));

        //����¼�ί��
        userGoodsTable.addEvent(() -> {
            ccTable.addBean(userGoodsTable.getSelectBean());
            //�������Ϊ��������ʱ��
            if (jc_orderTime.getText().equals("")) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                jc_orderTime.setText(sdf.format(LocalDateTime.now()));
            }
        });
    }

    @FXML
    private void clearCountOrder() {
        ccTable.clearData();
        jc_orderTime.setText("");
        jc_transactor.setText("");
    }

    @FXML
    private void payCountOrder() {
        //�ж϶����Ƿ�Ϊ��
        if (ccTable.getItems().size() == 0) {
            new MyAlert(AlertType.WARNING, "��������Ϊ�գ�").show();
            return;
        }
        //�ж��Ƿ񳬳��û����
        if (isContOrderOutOfStock())
            return;

        //FIXME ��Ҫ��һ���Ż�Ϊ-����
        List<UserGoodsOrderBean> ugoBeans = generateUserGoodsOrder();
        List<UserGoods> ugoList = generateUserGoodsData(ugoBeans);
        UserGoodsOrderDao dao = UserGoodsOrderDao.getInstance();
        UserGoodsDao ugDao = UserGoodsDao.getInstance();
        dao.insertOrders(ugoBeans);
        ugDao.reduceGoods(ugoList);
        new MyAlert(AlertType.WARNING, "����ɹ���").show();

        clearCountOrder();      //��ռƴ����Ѷ���
        userGoodsTable.showAllData();       //ˢ���û���Ʒ�б�
    }

    @FXML
    private void refreshUserGoods() {
        if (vipBean != SANKE)
            userGoodsTable.showAllData();
    }

    /**
     * ���ɼƴ����Ѷ���
     *
     * @return �ƴ����Ѷ���
     */
    private List<UserGoodsOrderBean> generateUserGoodsOrder() {
        ObservableList<GoodsEditTableData> items = ccTable.getItems();
        List<UserGoodsOrderBean> ugoBeans = new ArrayList<>(items.size());
        for (GoodsEditTableData data : items) {
            UserGoodsOrderBean ugo = new UserGoodsOrderBean();
            ugo.setGoodsId(data.getId());
            ugo.setAmount(data.getAmount());
            ugo.setOrderDate(jc_orderTime.getText());
            ugo.setTransactor(jc_transactor.getText());
            ugo.setUserId(vipBean.getId());
            ugoBeans.add(ugo);
        }
        return ugoBeans;
    }

    /**
     * ������Ҫ���µ� �û�-��Ʒ��Ϣ
     *
     * @param ugoBeans �û�-��Ʒ������Ϣ
     * @return �û�-��Ʒ��Ϣ�б�
     */
    private List<UserGoods> generateUserGoodsData(List<UserGoodsOrderBean> ugoBeans) {
        List<UserGoods> userGoods = new ArrayList<>(ugoBeans.size());
        for (UserGoodsOrderBean ugo : ugoBeans) {
            UserGoods ug = UserGoodsDao.getInstance().selectByUserGoods(ugo.getUserId(), ugo.getGoodsId());
            ug.setAmount(ug.getAmount() - ugo.getAmount());
            userGoods.add(ug);
        }
        return userGoods;
    }

    /**
     * �жϼƴ������Ƿ񳬳����
     *
     * @return ������true
     */
    private boolean isContOrderOutOfStock() {
        String userID = vipBean.getId();
        ObservableList<GoodsEditTableData> items = ccTable.getItems();
        for (GoodsEditTableData data : items) {
            UserGoods userGoods = UserGoodsDao.getInstance().selectByUserGoods(userID, data.getId());
            GoodsBean goodsBean = GoodsDao.getInstance().selectById(userGoods.getGoodsId());
            int amountCost = data.getAmount();
            int amountRest = userGoods.getAmount();
            if (amountCost > amountRest) {
                String msg = String.format("�û����� [%s]%s �������� %d%s��",
                        goodsBean.getId(), goodsBean.getName(), amountCost, goodsBean.getBaseUnit());
                MyAlert alert = new MyAlert(AlertType.WARNING, msg);
                alert.setHeaderText("��������");
                alert.show();
                return true;
            }
        }
        return false;
    }

}
