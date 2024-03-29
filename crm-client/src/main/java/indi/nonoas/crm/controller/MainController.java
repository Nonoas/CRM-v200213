package indi.nonoas.crm.controller;

import de.felixroske.jfxsupport.FXMLController;
import indi.jfxmaker.AppState;
import indi.jfxmaker.utils.UIUtil;
import indi.nonoas.crm.common.ClientSession;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.pojo.LoginDto;
import indi.nonoas.crm.service.OrderService;
import indi.nonoas.crm.view.consume.ConsumeTab;
import indi.nonoas.crm.view.consume.VipQueryTab;
import indi.nonoas.crm.view.goods.GoodsInfoTab;
import indi.nonoas.crm.view.goods.GoodsTypeTab;
import indi.nonoas.crm.view.goods.PackageInfoTab;
import indi.nonoas.crm.view.staff.StaffInfoTab;
import indi.nonoas.crm.view.stat.OrderTable;
import indi.nonoas.crm.view.stat.UsrGdsOdrTable;
import indi.nonoas.crm.view.vip.VipManageTab;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@FXMLController
public class MainController implements Initializable {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);

    private static final double IMG_SIZE = 30;


    @Autowired
    private OrderService odrService;

    private static final TabPane rootTabPane;

    static {
        rootTabPane = new TabPane();
        rootTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    @FXML
    private ToolBar toolBar;

    /**
     * 根布局
     */
    @FXML
    private BorderPane bp_root;

    @FXML
    private Button btn_shift;

    @FXML
    private Button btn_setting;

    @FXML
    private Button btn_exit;

    @FXML
    private Button btn_backups;

    /**
     * 左侧菜单VBox
     */
    @FXML
    private VBox leftMenuVb;

    public MainController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AppState.getAppStage().registryDragger(toolBar);

        //启动后台任务
        startBackgroundTask();

        initLeftMenu();
        initTopMenu();

        bp_root.setStyle("-fx-padding: -1px");
        bp_root.setCenter(rootTabPane);
        addTab(ConsumeTab.getInstance());

        LoginDto loginDto = (LoginDto) ClientSession.getAttribute("user");

    }

    /**
     * 添加Tab到主TabPane
     *
     * @param tab tab面板
     */
    public static void addTab(Tab tab) {
        ObservableList<Tab> tabs = rootTabPane.getTabs();
        if (!tabs.contains(tab)) {
            tabs.add(tab);
        }
        rootTabPane.getSelectionModel().select(tab);
    }

    /**
     * 从主TabPane移除Tab
     *
     * @param tab tab面板
     */
    public static void removeTab(Tab tab) {
        ObservableList<Tab> tabs = rootTabPane.getTabs();
        tabs.remove(tab);
    }

    private void initTopMenu() {
        // 初始化顶部菜单图标
        ImageView img_shift = new ImageView(ImageSrc.SHIFT_PATH); // 换班图标
        ImageView img_backups = new ImageView(ImageSrc.BACKUPS_PATH); // 换班图标
        ImageView img_setting = new ImageView(ImageSrc.SETTING_PATH); // 设置图标
        ImageView img_exit = new ImageView(ImageSrc.EXIT_PATH); // 退出图标

        UIUtil.setImageViewSize(img_shift, IMG_SIZE);
        UIUtil.setImageViewSize(img_backups, IMG_SIZE);
        UIUtil.setImageViewSize(img_setting, IMG_SIZE);
        UIUtil.setImageViewSize(img_exit, IMG_SIZE - 1);

        btn_shift.setGraphic(img_shift);
        btn_backups.setGraphic(img_backups);
        btn_setting.setGraphic(img_setting);
        btn_exit.setGraphic(img_exit);
    }

    /**
     * 初始化左侧菜单
     */
    private void initLeftMenu() {

        leftMenuVb.getStylesheets().add("/css/leftmenu.css");
        Accordion accordion = new Accordion();
        leftMenuVb.getChildren().add(accordion);
        accordion.getPanes().addAll(
                this.consumeLeftMenu(),
                this.vipManageLeftMenu(),
                this.goodsManageLeftMenu(),
                this.staffManageLeftMenu(),
                this.statLeftMenu());
    }

    /**
     * 会员消费菜单
     *
     * @return Node节点
     */
    private TitledPane consumeLeftMenu() {
        ListView<Label> lv = new ListView<>();
        Label vipConsumeLb = new LeftMenuItemLabel("商品消费");
        Label vipQueryLb = new LeftMenuItemLabel("会员查询");

        lv.getItems().add(vipConsumeLb);
        lv.getItems().add(vipQueryLb);

        lv.setPrefHeight(2 * (LeftMenuItemLabel.LEFT_MENUITEM_SIZE + 8));

        //点击事件定义 beg
        vipConsumeLb.setOnMouseClicked(event -> addTab(ConsumeTab.getInstance()));
        vipQueryLb.setOnMouseClicked(event -> addTab(VipQueryTab.getInstance()));
        //点击事件定义 end

        return createTitledPane("用户消费", lv);
    }

    /**
     * 会员管理菜单
     *
     * @return Node节点
     */
    private TitledPane vipManageLeftMenu() {
        ListView<Label> lv = new ListView<>();
        Label vipManageLb = new LeftMenuItemLabel("会员管理");
        vipManageLb.setPrefHeight(LeftMenuItemLabel.LEFT_MENUITEM_SIZE);

        lv.getItems().add(vipManageLb);

        lv.setPrefHeight(1 * (LeftMenuItemLabel.LEFT_MENUITEM_SIZE + 8));

        vipManageLb.setOnMouseClicked(event -> addTab(VipManageTab.getInstance()));

        return createTitledPane("会员管理", lv);
    }

    /**
     * 商品管理菜单
     *
     * @return Node节点
     */
    private TitledPane goodsManageLeftMenu() {
        ListView<Label> lv = new ListView<>();
        Label goodsInfoLb = new LeftMenuItemLabel("商品信息");
        Label goodsTypeLb = new LeftMenuItemLabel("商品类别");
        Label pkgLb = new LeftMenuItemLabel("套餐项目");

        ObservableList<Label> items = lv.getItems();
        items.add(goodsInfoLb);
        items.add(goodsTypeLb);
        items.add(pkgLb);

        lv.setPrefHeight(items.size() * (LeftMenuItemLabel.LEFT_MENUITEM_SIZE + 8));

        goodsInfoLb.setOnMouseClicked(event -> addTab(GoodsInfoTab.getInstance()));
        goodsTypeLb.setOnMouseClicked(event -> addTab(GoodsTypeTab.getInstance()));
        pkgLb.setOnMouseClicked(event -> addTab(PackageInfoTab.getInstance()));

        return createTitledPane("商品管理", lv);
    }

    /**
     * 员工管理菜单
     *
     * @return Node节点
     */
    @SuppressWarnings("all")
    private TitledPane staffManageLeftMenu() {
        ListView<Label> lv = new ListView<>();
        Label yggl = new LeftMenuItemLabel("员工管理");

        lv.getItems().add(yggl);

        lv.setPrefHeight(lv.getItems().size() * (LeftMenuItemLabel.LEFT_MENUITEM_SIZE + 8));

        yggl.setOnMouseClicked(event -> {
            addTab(StaffInfoTab.getInstance());
        });

        return createTitledPane("员工管理", lv);
    }

    /**
     * 统计报表菜单
     *
     * @return Node节点
     */
    @SuppressWarnings("all")
    private TitledPane statLeftMenu() {
        ListView<Label> lv = new ListView<>();
        Label spxfjl = new LeftMenuItemLabel("商品消费记录");
        Label tcxfjl = new LeftMenuItemLabel("套餐消费记录");
        Label hykhjl = new LeftMenuItemLabel("会员开户记录");
        Label spdhjl = new LeftMenuItemLabel("商品兑换记录");
        Label spkcjl = new LeftMenuItemLabel("商品库存统计");
        Label sjjcjl = new LeftMenuItemLabel("商品进出记录");
        Label ygtcjl = new LeftMenuItemLabel("员工提成记录");

        lv.getItems().add(spxfjl);
        lv.getItems().add(tcxfjl);
        lv.getItems().add(hykhjl);
        lv.getItems().add(spdhjl);
        lv.getItems().add(spkcjl);
        lv.getItems().add(sjjcjl);
        lv.getItems().add(ygtcjl);

        lv.setPrefHeight(7 * (LeftMenuItemLabel.LEFT_MENUITEM_SIZE + 8));

        spxfjl.setOnMouseClicked(event -> {
            // todo dialog面板需要抽出单独类，且界面需要更详细
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("商品订单记录");
            dialog.setResizable(true);
            DialogPane pane = dialog.getDialogPane();
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ImageSrc.lOGO_PATH));
            pane.getButtonTypes().add(ButtonType.OK);
            pane.setContent(new OrderTable());
            dialog.show();
        });

        tcxfjl.setOnMouseClicked(event -> {
            // todo dialog面板需要抽出单独类，且界面需要更详细
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("用户库存消费记录");
            dialog.setResizable(true);
            DialogPane pane = dialog.getDialogPane();
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ImageSrc.lOGO_PATH));
            pane.getButtonTypes().add(ButtonType.OK);
            pane.setContent(new UsrGdsOdrTable());
            dialog.show();
        });

        return createTitledPane("统计报表", lv);
    }

    private TitledPane createTitledPane(String label, ListView<Label> content) {
        TitledPane titledPane = new TitledPane(label, content);
        titledPane.setExpanded(false);
        return titledPane;
    }


    @FXML
    private void linkAuthor() {
        AppState.getHostServices().showDocument("https://me.csdn.net/weixin_44155115");
    }


    private void startBackgroundTask() {
        //删除旧订单
        new Thread(() -> {
            odrService.delete365dAgo();
            logger.debug("\n==================\n" +
                    "删除一年前的订单" +
                    "\n===================");
        }).start();
    }

    /**
     * 左侧菜单按钮Label，事先设置好通用样式
     */
    private static class LeftMenuItemLabel extends Label {

        /**
         * 左侧按钮高度
         */
        private static final double LEFT_MENUITEM_SIZE = 30.0;

        /**
         * 最大尺寸数值
         */
        private static final double MAX_VALUE = Double.MAX_VALUE;

        private LeftMenuItemLabel(String text) {
            super(text);
            this.setPrefHeight(LEFT_MENUITEM_SIZE);
            this.setMaxWidth(MAX_VALUE);
            this.setAlignment(Pos.CENTER_LEFT);
            setPadding(new Insets(0, 0, 0, 20));
        }
    }
}
