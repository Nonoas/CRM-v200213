package indi.nonoas.crm.table;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.bean.PackageBean;
import indi.nonoas.crm.bean.PackageContentBean;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.dao.PackageContentDao;
import indi.nonoas.crm.dao.PackageDao;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * 可以编辑内容的“套餐内容”表格
 *
 * @author: Nonoas
 * @Date: 2020/4/4 18:19
 * @Description: 可以编辑内容的“套餐内容”表格
 */
public class PackageContentEditTable extends TableView<PackageContentBean> {
    /**
     * 数据源
     */
    private final ObservableList<PackageContentBean> obList = FXCollections.observableArrayList();

    private final ObservableList<TableColumn<PackageContentBean, ?>> cols = getColumns();
    /**
     * 当前选中数据
     */
    private PackageContentBean selectedBean;

    private final TableColumn<PackageContentBean, String> item_id = new TableColumn<>("商品编号");

    private final TableColumn<PackageContentBean, String> item_name = new TableColumn<>("商品名称");

    private final TableColumn<PackageContentBean, String> item_money_cost = new TableColumn<>("商品单价");

    private final TableColumn<PackageContentBean, Number> item_count = new TableColumn<>("数量");

    private final TableColumn<PackageContentBean, String> item_total = new TableColumn<>("小计");

    public PackageContentEditTable() {

        initColumns();
        setItems(obList);

        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            selectedBean = newValue;
        });
    }

    /**
     *
     */
    private void initColumns() {

        setTableMenuButtonVisible(true); // 显示表格菜单按钮

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGoods_id()));
        item_name.setCellValueFactory(param -> {
            String goodsID = param.getValue().getGoods_id();
            GoodsBean goodsBean = GoodsDao.getInstance().selectById(goodsID);
            return new SimpleStringProperty(goodsBean.getName());
        });
        item_money_cost.setCellValueFactory(param -> {
            String goodsID = param.getValue().getGoods_id();
            GoodsBean goodsBean = GoodsDao.getInstance().selectById(goodsID);
            double numMoney = goodsBean.getSell_price();
            String show = String.format("￥%.2f", numMoney);
            return new SimpleStringProperty(show);
        });

        item_count.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getGoods_amount()));

        item_count.setCellFactory(param -> new AmountCell());   //自定义数量单元格

        item_total.setCellValueFactory(param -> {
            String goodsID = param.getValue().getGoods_id();
            GoodsBean goodsBean = GoodsDao.getInstance().selectById(goodsID);
            double numMoney = goodsBean.getSell_price();
            double amount = param.getValue().getGoods_amount();
            String show = String.format("￥%.2f", numMoney * amount);
            return new SimpleStringProperty(show);
        });

        item_count.setMinWidth(150);

        //添加所有的列
        cols.add(item_id);
        cols.add(item_name);
        cols.add(item_money_cost);
        cols.add(item_count);
        cols.add(item_total);


        //设置所有列内容居中,且不可排序 (排序会导致自定义单元格数据出错)
        cols.forEach(action -> {
            action.setStyle("-fx-alignment:CENTER");
            action.setSortable(false);
        });

    }

    /**
     * 展示所有项目信息
     */
    public void showAllInfos(String id) {
        clearData(); // 清空所有数据
        ArrayList<PackageContentBean> listVipBeans = new PackageContentDao().selectById(id);
        if (listVipBeans != null)
            obList.addAll(listVipBeans);
    }

    /**
     * 清空数据源
     */
    public void clearData() {
        obList.clear();
    }

    /**
     * 添加不重复的数据
     *
     * @param bean 需要添加的数据
     */
    public void addBean(PackageContentBean bean) {
        boolean hasRepeat = false;
        for (PackageContentBean packageContentBean : obList) {
            String id1 = packageContentBean.getGoods_id();
            String id2 = bean.getGoods_id();
            hasRepeat = hasRepeat || id1.equals(id2);
        }
        if (!hasRepeat)
            obList.add(bean);
    }

    /**
     * 获取选中的数据
     *
     * @return 选中的PackageContentBean
     */
    public PackageContentBean getSelectedData() {
        return this.selectedBean;
    }

    /**
     * 移除数据
     *
     * @param bean 需要移除的PackageContentBean
     */
    public void removeData(PackageContentBean bean) {
        this.obList.remove(bean);
    }

}

/**
 * 自定义“数量”单元格
 */
class AmountCell extends TableCell<PackageContentBean, Number> {

    public AmountCell() {

    }

    //设置单元格样式
    @Override
    protected void updateItem(Number item, boolean empty) {

        super.updateItem(item, empty);

        if (!empty && item != null) {

            HBox hBox = new HBox();
            Button btn_add = new Button("+");
            Button btn_reduce = new Button("-");
            TextField tf_number = new TextField(item.toString());
            //设置初始尺寸
            btn_add.setPrefWidth(35);
            btn_reduce.setPrefWidth(35);
            tf_number.setPrefWidth(50);
            //设置最小尺寸
            btn_add.setMinWidth(35);
            btn_reduce.setMinWidth(35);
            tf_number.setMinWidth(50);

            HBox.setHgrow(tf_number, Priority.ALWAYS);

            hBox.getChildren().addAll(btn_add, tf_number, btn_reduce);
            hBox.setSpacing(10);

            ObservableList<PackageContentBean> obList = getTableView().getItems(); //获取表格源数据
            PackageContentBean bean = obList.get(getIndex());

            //设置按钮监听
            //加一
            btn_add.setOnAction(event -> {
                int amount = Integer.parseInt(tf_number.getText()) + 1;
                tf_number.setText(String.valueOf(amount));
            });
            //减一
            btn_reduce.setOnAction(event -> {
                int amount = Integer.parseInt(tf_number.getText()) - 1;
                if (amount > 0) {
                    tf_number.setText(String.valueOf(amount));
                }
            });
            //文本框变化监听
            tf_number.textProperty().addListener((observable, oldValue, newValue) -> {
                String pattern = "^\\d+$";
                boolean isNumber = Pattern.matches(pattern, newValue); //判断是否为正整数
                if (isNumber) {
                    bean.setGoods_amount(Integer.parseInt(newValue));
                } else {
                    tf_number.setText(oldValue);
                }
            });

            this.setGraphic(hBox);
        }
    }
}
