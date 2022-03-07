package indi.nonoas.crm.controller.consume;

import static indi.nonoas.crm.pojo.dto.VipInfoDto.SANKE;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.SerializationUtils;
import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.controller.MainController;
import indi.nonoas.crm.pojo.OrderDetailBean;
import indi.nonoas.crm.pojo.OrderDto;
import indi.nonoas.crm.pojo.PackageContentDto;
import indi.nonoas.crm.pojo.PackageDto;
import indi.nonoas.crm.pojo.UserGoods;
import indi.nonoas.crm.pojo.UserGoodsDto;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.service.OrderService;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.service.UsrGdsService;
import indi.nonoas.crm.service.VipService;
import indi.nonoas.crm.service.impl.OrderServiceImpl;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.view.consume.ConsumeDialog;
import indi.nonoas.crm.view.consume.CountConsumeTable;
import indi.nonoas.crm.view.consume.GoodsConsumeTable;
import indi.nonoas.crm.view.consume.PackageConsumeDialog;
import indi.nonoas.crm.view.consume.PackageConsumeTable;
import indi.nonoas.crm.view.consume.UserGoodsTable;
import indi.nonoas.crm.view.goods.GoodsSingleSelectTable;
import indi.nonoas.crm.view.pkg.PackageSingleSelectTable;
import indi.nonoas.crm.view.vip.VipAddTab;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumeController implements Initializable {

    private final Logger logger = LoggerFactory.getLogger(ConsumeController.class);

    private final VipService vipService = (VipService) SpringUtil.getBean("UserServiceImpl");

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

    private final UsrGdsService ugService = (UsrGdsService) SpringUtil.getBean("UsrGdsServiceImpl");

    private final OrderService odrService = (OrderService) SpringUtil.getBean("OrderServiceImpl");

    /**
     * 会员信息
     */
    private VipInfoDto vipBean = SANKE;
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
    private Label lb_balance;
    @FXML
    private TextField tf_find;


    public ConsumeController() {
        initData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
        //设置回车查询
        tf_find.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                inquireVIP();
            }
        });
    }

    @FXML // 清空展示的信息
    private void clearInfo() {
        tf_find.setText("");
        vipBean = SANKE;
        showFindResult(vipBean);
    }

    @FXML // 查找信息
    private void inquireVIP() {
        String keyWord = tf_find.getText().trim();
        if ( StrUtil.isBlank(keyWord)) {
            return;
        }
        vipBean = vipService.getInfoByIdOrName(keyWord, keyWord);
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
    private void showFindResult(VipInfoDto bean) {
        if (bean == null) {
            return;
        }
        lb_cardState.setText("可用");
        lb_id.setText(bean.getId());
        lb_integral.setText(String.valueOf(bean.getIntegral()));
        lb_cardLevel.setText(bean.getCardLevel());
        lb_name.setText(bean.getName());
        lb_balance.setText(String.format("￥%.2f", bean.getBalance()));
        if (bean == SANKE) {
            lb_name.setStyle("-fx-text-fill: #d9534f");
        } else {
            lb_name.setStyle("-fx-text-fill: black");
        }
    }


    private void initData() {
    }

    private void initView() {
        showFindResult(vipBean);

        initGoodsTab();        //初始化商品消费面板
        initPackageTab();       //初始化套餐消费面板
        initCountTab();         //初始化计次消费面板
    }

    @FXML // 添加会员信息
    private void addVip() {
        MainController.addTab(new VipAddTab());
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
            if ( StrUtil.isBlank(shp_orderNum.getText())) {
                shp_orderNum.setText(OrderServiceImpl.goodsOrderNum());
            }
            if ( StrUtil.isBlank(shp_orderDate.getText())) {
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

        if (isGoodsOrderOutOfStock()) {
            return;
        }

        if (gc_table.getItems().isEmpty()) {
            new MyAlert(AlertType.INFORMATION, "订单内容为空！！").show();
            return;
        }

        OrderDto orderDto = generateGoodsOrder();
        List<OrderDetailBean> orderDetails = generateGoodsOrderDetails();

        // 判断消费用户是否为散客，如果不是散客则拷贝当前用户信息，防止出现内存脏数据
        VipInfoDto vipInfoDto = vipBean == SANKE
            ? SANKE
            : SerializationUtils.clone(vipBean);
        ConsumeDialog consumeDialog = new ConsumeDialog(vipInfoDto, orderDto, orderDetails);
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

        ObservableList<GoodsEditTableVO> items = gc_table.getItems();
        for (GoodsEditTableVO data : items) {
            String goodsID = data.getId();
            int costCount = data.getAmount();
            GoodsDto goodsBean = goodsService.selectById(goodsID);
            int storeCount = (int) goodsBean.getQuantity();

            // 如果消费数量超出库存，提示数量不足
            if (costCount > storeCount) {
                MyAlert alert = new MyAlert(AlertType.WARNING,
                    String.format("《(%s)%s》库存不足 %d%s ！", goodsID, data.getName(), costCount,
                        goodsBean.getBaseUnit()));
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
    private OrderDto generateGoodsOrder() {
        OrderDto bean = new OrderDto();
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

        ObservableList<GoodsEditTableVO> items = gc_table.getItems();

        return items.parallelStream()
            .map(data -> {
                OrderDetailBean bean = new OrderDetailBean();
                bean.setOrderId(shp_orderNum.getText());
                bean.setProductId(data.getId());
                bean.setProductAmount(data.getAmount());
                return bean;
            })
            .collect(Collectors.toList());

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

    private final PackageService pkgService =
        (PackageService) SpringUtil.getBean("PackageServiceImpl");

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
            if ( StrUtil.isBlank(tc_orderNum.getText())) {
                tc_orderNum.setText(OrderServiceImpl.packageOrderNum());
            }
            //设置订单日期
            if ( StrUtil.isBlank(tc_orderDate.getText())) {
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
            PackageDto bean = pkgSelectTable.getSelectedData();
            if (bean != null) {
                pcTable.addBean(bean);
            }
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
        if (isPackageOrderOutOfStock()) {
            return;
        }
        //判断订单是否为空
        if (pcTable.getItems().size() == 0) {
            new MyAlert(AlertType.INFORMATION, "订单内容为空！！").show();
            return;
        }
        //生成订单数据
        OrderDto orderDto = generatePackageOrder();
        //生成订单详情数据
        List<OrderDetailBean> orderDetails = generatePackageOrderDetails();
        // 判断消费用户是否为散客，如果不是散客则拷贝当前用户信息，防止出现内存脏数据
        VipInfoDto vipInfoDto = vipBean == SANKE
            ? SANKE
            : SerializationUtils.clone(vipBean);
        //弹出消费窗口
        PackageConsumeDialog consumeDialog =
            new PackageConsumeDialog(vipBean, orderDto, orderDetails);
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
    private OrderDto generatePackageOrder() {
        OrderDto bean = new OrderDto();
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
        ObservableList<GoodsEditTableVO> pkgItems = pcTable.getItems();
        OrderDetailBean bean;
        for (GoodsEditTableVO pkg : pkgItems) {
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

        ObservableList<GoodsEditTableVO> pkgItems = pcTable.getItems();
        //遍历订单内套餐列表
        for (GoodsEditTableVO data : pkgItems) {
            String pkgID = data.getId();
            int costPkgCount = data.getAmount();
            //查询套餐包含的商品
            List<PackageContentDto> packageContents = pkgService.listPkgContentByPkgId(pkgID);
            if (packageContents == null) {
                break;
            }
            //遍历套餐内商品列表
            for (PackageContentDto bean : packageContents) {
                String goodsID = bean.getGoodsId();
                //消耗的商品数量
                int costCount = bean.getGoodsAmount() * costPkgCount;
                GoodsDto goodsBean = goodsService.selectById(goodsID);
                //库存商品数量
                int storeCount = (int) goodsBean.getQuantity();
                if (costCount > storeCount) {
                    String msg = String.format("《(%s)%s》套餐内 《(%s)%s》库存不足 %d%s ！",
                        pkgID, data.getName(), goodsID, goodsBean.getName(), costCount,
                        goodsBean.getBaseUnit());
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

        lb_id.textProperty()
            .addListener((observable, oldValue, newValue) -> userGoodsTable.setVipBean(vipBean));

        //添加事件委托
        userGoodsTable.addEvent(() -> {
            ccTable.addBean(userGoodsTable.getSelectBean());
            //如果订单为空则设置时间
            if ( StrUtil.isBlank(jc_orderTime.getText())) {
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
        if (isContOrderOutOfStock()) {
            return;
        }

        List<UserGoodsDto> ugoBeans = generateUserGoodsOrder();
        List<UserGoods> ugoList = generateUserGoodsData(ugoBeans);

        odrService.placeCountOrder(ugoBeans, ugoList);
        new MyAlert(AlertType.WARNING, "结算成功！").show();

        clearCountOrder();      //清空计次消费订单
        userGoodsTable.showAllData();       //刷新用户商品列表
    }

    @FXML
    private void refreshUserGoods() {
        if (vipBean != SANKE) {
            userGoodsTable.showAllData();
        }
    }

    /**
     * 生成计次消费订单
     *
     * @return 计次消费订单
     */
    private List<UserGoodsDto> generateUserGoodsOrder() {
        ObservableList<GoodsEditTableVO> items = ccTable.getItems();
        List<UserGoodsDto> ugoBeans = new ArrayList<>(items.size());
        for (GoodsEditTableVO data : items) {
            UserGoodsDto ugo = new UserGoodsDto();
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
    private List<UserGoods> generateUserGoodsData(List<UserGoodsDto> ugoBeans) {
        List<UserGoods> userGoods = new ArrayList<>(ugoBeans.size());
        for (UserGoodsDto ugo : ugoBeans) {
            UserGoods ug = ugService.selectByUserGoods(ugo.getUserId(), ugo.getGoodsId());
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
        ObservableList<GoodsEditTableVO> items = ccTable.getItems();
        for (GoodsEditTableVO data : items) {
            UserGoods userGoods = ugService.selectByUserGoods(userID, data.getId());
            GoodsDto goodsBean = goodsService.selectById(userGoods.getGoodsId());
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
