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
     * 会员信息DAO
     */
    private final VipInfoDao vipInfoDao = VipInfoDao.getInstance();

    /**
     * 散客常量
     */
    private final static VipBean SANKE = VipBean.SANKE;

    /**
     * 会员信息
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

    private final VipInfoTable tv_vipInfo = new VipInfoTable(); // 会员信息表

    public ConsumeController() {
        initData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
        //设置回车查询
        tf_find.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER))
                inquireVIP();
        });
    }

    @FXML // 查找信息
    private void inquireVIP() {
        String str = tf_find.getText().trim();
        if (str.equals(""))
            return;
        vipBean = vipInfoDao.getInfoByIdOrName(str, str);
        if (vipBean != null) {
            showFindResult(vipBean);
        } else {
            new MyAlert(AlertType.INFORMATION, "没有找到您查询的会员！").show();
        }
    }

    /**
     * 显示搜索结果
     *
     * @param bean 搜索的用户信息
     */
    private void showFindResult(VipBean bean) {
        if (bean == null)
            return;
        lb_cardState.setText("可用");
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
        String str0 = cb_disType.getValue().equals("全部类型") ? "" : cb_disType.getValue();
        String disType = str0 + "%";
        if (str.equals("%%"))
            return;
        ArrayList<VipBean> listVipBeans = vipInfoDao.selectByFiltrate(str, str, disType);
        if (listVipBeans != null) {
            tv_vipInfo.clearData();
            for (VipBean bean : listVipBeans)
                tv_vipInfo.addBean(bean);
        } else {
            new MyAlert(AlertType.INFORMATION, "没有找到您查询的会员！").show();
        }
    }

    @FXML // 显示全部信息
    private void showAll() {
        tv_vipInfo.showAllInfos();
    }

    @FXML // 清空展示的信息
    private void clearInfo() {
        tf_find.setText("");
        vipBean = SANKE;
        showFindResult(vipBean);
    }

    @FXML // 添加会员信息
    private void addVip() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "添加会员";
        for (Tab tab : obList) { // 遍历判断该tab是否已经添加
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tab.setClosable(true);
                tp_rootPane.getSelectionModel().select(tab); // 如果已经添加则显示该tab并返回
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
        // 从数据库读出所用会员等级，并初始化ComboBox
        LinkedList<String> listName = new VipLevelDao().selectAllNames();
        cb_disType.getItems().add("全部类型");
        for (String str : listName) {
            cb_disType.getItems().add(str);
        }
        cb_disType.setValue("全部类型");

        initGoodsTab();        //初始化商品消费面板
        initPackageTab();       //初始化套餐消费面板
        initCountTab();         //初始化计次消费面板
    }


    //===========================================================================
    //                             商品消费
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

        //设置表格的监听事件
        gc_table.getEventHandler().addEvent(() -> {
            if (shp_orderNum.getText().equals("")) {
                shp_orderNum.setText(OrderService.goodsOrderNum());
            }
            if (shp_orderDate.getText().equals("")) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                shp_orderDate.setText(sdf.format(LocalDateTime.now()));
            }
            pt_order_price.setText(String.format("%.2f", gc_table.getSumPrice()));

            //用户折扣
            double discount = vipBean.getDiscount();
            pt_order_dis_price.setText(String.format("%.2f", gc_table.getSumPrice() * discount));
            //积分获取
            shp_integral.setText("0");
            //积分消耗
            shp_integral_cost.setText("0");


        });

        //“折后价->用户ID”监听
        lb_id.textProperty().addListener((observable, oldValue, newValue) -> {
            logger.debug("会员卡号：" + newValue);
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
            new MyAlert(AlertType.INFORMATION, "订单内容为空！！").show();
            return;
        }

        OrderBean orderBean = generateGoodsOrder();
        List<OrderDetailBean> orderDetails = generateGoodsOrderDetails();
        ConsumeDialog consumeDialog = new ConsumeDialog(vipBean, orderBean, orderDetails);
        consumeDialog.showAndWait();
        //如果成功提交，则清除订单信息
        if (consumeDialog.hasSubmit()) {
            clearGoodsOrder();
            goodsSelectTable.showAllInfos();
        }
    }

    /**
     * 判断是否超出库存
     *
     * @return 超出库存返回true
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
                        "《(" + goodsID + ")" + data.getName() + "》库存不足 " + costCount + goodsBean.getBaseUnit() + " ！");
                alert.setHeaderText("库存不足");
                alert.show();
                return true;
            }
        }
        return false;
    }

    /**
     * 生成订单
     *
     * @return 订单信息
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
     * 生成订单详情
     *
     * @return 订单详情集合
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
    //                            套餐消费
    //===========================================================================

    /**
     * 套餐单选表格
     */
    private final PackageSingleSelectTable pkgSelectTable = new PackageSingleSelectTable();

    /**
     * 套餐消费表格
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
     * 初始化“套餐订单”界面
     */
    private void initPackageTab() {
        bp_pkgTable.setCenter(pkgSelectTable);
        tc_borderPane.setCenter(pcTable);
        //折后价
        tc_order_dis_price.setText("套餐不打折");
        tc_order_dis_price.setStyle("-fx-text-fill: #cf4813");
        //设置监听
        pcTable.getEventHandler().addEvent(() -> {
            //设置订单号
            if (tc_orderNum.getText().equals("")) {
                tc_orderNum.setText(OrderService.packageOrderNum());
            }
            //设置订单日期
            if (tc_orderDate.getText().equals("")) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                tc_orderDate.setText(sdf.format(LocalDateTime.now()));
            }
            //订单金额
            tc_order_price.setText(String.format("%.2f", pcTable.getSumPrice()));
            //积分获取
            tc_integral_get.setText("0");
            //积分消耗
            tc_integral_cost.setText("0");
        });

        pkgSelectTable.getEventHandler().addEvent(() -> {
            PackageBean bean = pkgSelectTable.getSelectedData();
            if (bean != null)
                pcTable.addBean(bean);
        });
    }

    /**
     * 清空套餐订单
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
        //判断是否超出库存
        if (isPackageOrderOutOfStock())
            return;
        //判断订单是否为空
        if (pcTable.getItems().size() == 0) {
            new MyAlert(AlertType.INFORMATION, "订单内容为空！！").show();
            return;
        }
        //生成订单数据
        OrderBean orderBean = generatePackageOrder();
        //生成订单详情数据
        List<OrderDetailBean> orderDetails = generatePackageOrderDetails();
        //弹出消费窗口
        PackageConsumeDialog consumeDialog = new PackageConsumeDialog(vipBean, orderBean, orderDetails);
        consumeDialog.showAndWait();
        //如果成功提交，则清除订单信息
        if (consumeDialog.hasSubmit()) {
            clearPackageOrder();
            pkgSelectTable.showAllInfos();
        }
    }

    /**
     * 生成套餐订单
     *
     * @return 套餐订单
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
     * 生成订单详情
     *
     * @return 订单详情集合
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
     * 判断套餐订单是否为空
     *
     * @return 空：true
     */
    private boolean isPackageOrderOutOfStock() {
        PackageContentDao pcDao = PackageContentDao.getInstance();
        ObservableList<GoodsEditTableData> pkgItems = pcTable.getItems();
        //遍历订单内套餐列表
        for (GoodsEditTableData data : pkgItems) {
            String pkgID = data.getId();
            int costPkgCount = data.getAmount();
            //查询套餐包含的商品
            List<PackageContentBean> packageContents = pcDao.selectById(pkgID);
            if (packageContents == null)
                break;
            //遍历套餐内商品列表
            for (PackageContentBean bean : packageContents) {
                String goodsID = bean.getGoodsId();
                System.out.println("商品ID" + goodsID);
                int costCount = bean.getGoodsAmount() * costPkgCount;   //消耗的商品数量
                GoodsBean goodsBean = GoodsDao.getInstance().selectById(goodsID);
                int storeCount = (int) goodsBean.getQuantity();     //库存商品数量
                System.out.println("消耗：" + costCount + ",储存" + storeCount);
                if (costCount > storeCount) {
                    String msg = "《(" + pkgID + ")" + data.getName() + "》套餐内 " +
                            "《(" + goodsID + ")" + goodsBean.getName() + "》库存不足 " + costCount + goodsBean.getBaseUnit() + " ！";
                    MyAlert alert = new MyAlert(AlertType.WARNING, msg);
                    alert.setHeaderText("库存不足");
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
    //                            计次消费
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
     * 受理人
     */
    @FXML
    TextField jc_transactor;

    private void initCountTab() {
        jc_borderPane.setCenter(ccTable);
        jc_goodsBorderPane.setCenter(userGoodsTable);

        lb_id.textProperty().addListener((observable, oldValue, newValue) -> userGoodsTable.setVipBean(vipBean));

        //添加事件委托
        userGoodsTable.addEvent(() -> {
            ccTable.addBean(userGoodsTable.getSelectBean());
            //如果订单为空则设置时间
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
        //判断订单是否为空
        if (ccTable.getItems().size() == 0) {
            new MyAlert(AlertType.WARNING, "订单内容为空！").show();
            return;
        }
        //判断是否超出用户库存
        if (isContOrderOutOfStock())
            return;

        //FIXME 需要进一步优化为-事务
        List<UserGoodsOrderBean> ugoBeans = generateUserGoodsOrder();
        List<UserGoods> ugoList = generateUserGoodsData(ugoBeans);
        UserGoodsOrderDao dao = UserGoodsOrderDao.getInstance();
        UserGoodsDao ugDao = UserGoodsDao.getInstance();
        dao.insertOrders(ugoBeans);
        ugDao.reduceGoods(ugoList);
        new MyAlert(AlertType.WARNING, "结算成功！").show();

        clearCountOrder();      //清空计次消费订单
        userGoodsTable.showAllData();       //刷新用户商品列表
    }

    @FXML
    private void refreshUserGoods() {
        if (vipBean != SANKE)
            userGoodsTable.showAllData();
    }

    /**
     * 生成计次消费订单
     *
     * @return 计次消费订单
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
     * 生成需要更新的 用户-商品信息
     *
     * @param ugoBeans 用户-商品订单信息
     * @return 用户-商品信息列表
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
     * 判断计次消费是否超出库存
     *
     * @return 超出：true
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
                String msg = String.format("用户账下 [%s]%s 数量不足 %d%s！",
                        goodsBean.getId(), goodsBean.getName(), amountCost, goodsBean.getBaseUnit());
                MyAlert alert = new MyAlert(AlertType.WARNING, msg);
                alert.setHeaderText("余量不足");
                alert.show();
                return true;
            }
        }
        return false;
    }

}
