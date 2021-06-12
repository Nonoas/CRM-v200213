package indi.nonoas.crm.controller;

import de.felixroske.jfxsupport.FXMLController;
import indi.nonoas.crm.view.consume.ConsumeTab;
import indi.nonoas.crm.view.consume.VipQueryTab;
import indi.nonoas.crm.view.goods.GoodsInfoTab;
import indi.nonoas.crm.view.goods.GoodsTypeTab;
import indi.nonoas.crm.view.goods.PackageInfoTab;
import indi.nonoas.crm.view.stat.OrderTable;
import indi.nonoas.crm.view.stat.UsrGdsOdrTable;
import indi.nonoas.crm.view.vip.VipManageTab;
import indi.nonoas.crm.common.ClientSession;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.pojo.LoginBean;
import indi.nonoas.crm.service.OrderService;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class MainController implements Initializable {

    private final Logger logger = Logger.getLogger(MainController.class);

    private static final double IMG_SIZE = 30;


    @Autowired
    private OrderService odrService;

    private static final TabPane rootTabPane;

    static {
        rootTabPane = new TabPane();
        rootTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

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

    @FXML
    private Label label_operator; // 操作员

    /**
     * 左侧菜单VBox
     */
    @FXML
    private VBox leftMenuVb;

    public MainController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //启动后台任务
        startBackgroundTask();

        initLeftMenu();
        initTopMenu();

        bp_root.setCenter(rootTabPane);
        addTab(ConsumeTab.getInstance());

        LoginBean loginBean = (LoginBean) ClientSession.getAttribute("user");

        label_operator.setText("操作员：" + loginBean.getName());

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

    private void initTopMenu() {
        // 初始化顶部菜单图标
        ImageView img_shift = new ImageView(ImageSrc.SHIFT_PATH); // 换班图标
        ImageView img_backups = new ImageView(ImageSrc.BACKUPS_PATH); // 换班图标
        ImageView img_setting = new ImageView(ImageSrc.SETTING_PATH); // 设置图标
        ImageView img_exit = new ImageView(ImageSrc.EXIT_PATH); // 退出图标

        img_shift.setFitHeight(IMG_SIZE);
        img_shift.setFitWidth(IMG_SIZE);
        img_backups.setFitHeight(IMG_SIZE);
        img_backups.setFitWidth(IMG_SIZE);
        img_setting.setFitHeight(IMG_SIZE);
        img_setting.setFitWidth(IMG_SIZE);
        img_exit.setFitHeight(IMG_SIZE - 1);
        img_exit.setFitWidth(IMG_SIZE - 1);

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
        ObservableList<Node> menuList = leftMenuVb.getChildren();
        menuList.add(this.consumeLeftMenu());
        menuList.add(this.vipManageLeftMenu());
        menuList.add(this.goodsManageLeftMenu());
        menuList.add(this.staffManageLeftMenu());
        menuList.add(this.statLeftMenu());
    }

    /**
     * 会员消费菜单
     *
     * @return Node节点
     */
    private Node consumeLeftMenu() {
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
        TitledPane titledPane = new TitledPane("用户消费", lv);
        titledPane.setExpanded(false);
        return titledPane;
    }

    /**
     * 会员管理菜单
     *
     * @return Node节点
     */
    private Node vipManageLeftMenu() {
        ListView<Label> lv = new ListView<>();
        Label vipManageLb = new LeftMenuItemLabel("会员管理");
        vipManageLb.setPrefHeight(LeftMenuItemLabel.LEFT_MENUITEM_SIZE);

        lv.getItems().add(vipManageLb);

        lv.setPrefHeight(1 * (LeftMenuItemLabel.LEFT_MENUITEM_SIZE + 8));

        vipManageLb.setOnMouseClicked(event -> addTab(VipManageTab.getInstance()));

        TitledPane titledPane = new TitledPane("会员管理", lv);
        titledPane.setExpanded(false);
        return titledPane;
    }

    /**
     * 商品管理菜单
     *
     * @return Node节点
     */
    private Node goodsManageLeftMenu() {
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

        TitledPane titledPane = new TitledPane("商品管理", lv);
        titledPane.setExpanded(false);
        return titledPane;
    }

    /**
     * 员工管理菜单
     *
     * @return Node节点
     */
    @SuppressWarnings("all")
    private Node staffManageLeftMenu() {
        ListView<Label> lv = new ListView<>();
        Label yggl = new LeftMenuItemLabel("员工管理");

        lv.getItems().add(yggl);

        lv.setPrefHeight(lv.getItems().size() * (LeftMenuItemLabel.LEFT_MENUITEM_SIZE + 8));

//        yggl.setOnMouseClicked(event -> toStaffManagePane());

        TitledPane titledPane = new TitledPane("员工管理", lv);
        titledPane.setExpanded(false);
        return titledPane;
    }

    /**
     * 统计报表菜单
     *
     * @return Node节点
     */
    @SuppressWarnings("all")
    private Node statLeftMenu() {
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

        TitledPane titledPane = new TitledPane("统计报表", lv);
        titledPane.setExpanded(false);
        return titledPane;
    }


    @FXML
    private void linkAuthor() {
        Application app = new Application() {
            @Override
            public void start(Stage primaryStage) {
            }
        };
        app.getHostServices().showDocument("https://me.csdn.net/weixin_44155115");
        try {
            app.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startBackgroundTask() {
//        OrderService odrService = (OrderService) SpringUtil.getBean("OrderServiceImpl");
        //删除旧订单
        new Thread(() -> {
            odrService.delete365dAgo();
            logger.debug("==================\n" +
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
        private static final double LEFT_MENUITEM_SIZE = 30;

        /**
         * 最大尺寸数值
         */
        private static final double MAX_VALUE = 9999999;

        private LeftMenuItemLabel(String text) {
            super(text);
            setPrefHeight(LEFT_MENUITEM_SIZE);
            setMaxWidth(MAX_VALUE);
            setAlignment(Pos.CENTER);
        }
    }
}
