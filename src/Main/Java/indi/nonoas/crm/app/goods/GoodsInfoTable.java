package indi.nonoas.crm.app.goods;

import java.util.ArrayList;
import java.util.List;

import indi.nonoas.crm.beans.GoodsBean;
import indi.nonoas.crm.dao.GoodsDao;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 商品信息表格
 *
 * @author Nonoas
 */
public class GoodsInfoTable extends TableView<GoodsBean> {

    /**
     * 数据源
     */
    private final ObservableList<GoodsBean> obList = FXCollections.observableArrayList();
    /**
     * 当前选中数据
     */
    private GoodsBean selectedBean;

    protected final TableColumn<GoodsBean, String> item_id = new TableColumn<>("编号");

    protected final TableColumn<GoodsBean, String> item_name = new TableColumn<>("商品名称");

    protected final TableColumn<GoodsBean, Number> item_sell_price = new TableColumn<>("预售单价");

    protected final TableColumn<GoodsBean, Number> item_purchase_price = new TableColumn<>("进货单价");

    protected final TableColumn<GoodsBean, String> item_quantity = new TableColumn<>("剩余数量");

    protected final TableColumn<GoodsBean, Number> item_min_discount = new TableColumn<>("最低折扣");

    protected final TableColumn<GoodsBean, Number> item_deduction = new TableColumn<>("提成金额");

    protected final TableColumn<GoodsBean, Number> item_deduction_rate = new TableColumn<>("提成比例");

    protected final TableColumn<GoodsBean, String> item_type = new TableColumn<>("所属类别");

    public GoodsInfoTable() {
        initColumns();
        setItems(obList);
        showAllInfos();
        ChangeListener<GoodsBean> cl_select = (observable, oldValue, newValue) -> {
            System.out.println(newValue);
            selectedBean = newValue;
        };
        getSelectionModel().selectedItemProperty().addListener(cl_select);
    }

    private void initColumns() {

        setTableMenuButtonVisible(true);

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId()));
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        item_sell_price.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getSellPrice()));
        item_purchase_price.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getPurchasePrice()));
        item_quantity.setCellValueFactory(param -> {
            double quantity = param.getValue().getQuantity(); // 数量
            String unit = param.getValue().getBaseUnit(); // 单位
            return new SimpleStringProperty(quantity + " " + unit);
        });
        item_min_discount.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getMinDiscount()));
        item_deduction.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getDeduction()));
        item_deduction_rate.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getDeductionRate()));
        item_type.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType()));

        getColumns().add(item_id);
        getColumns().add(item_name);
        getColumns().add(item_sell_price);
        getColumns().add(item_purchase_price);
        getColumns().add(item_quantity);
        getColumns().add(item_min_discount);
        getColumns().add(item_deduction);
        getColumns().add(item_deduction_rate);
        getColumns().add(item_type);

    }

    /**
     * 展示所有用户信息
     */
    public void showAllInfos() {
        clearData(); // 清空所有数据
        ArrayList<GoodsBean> listVipBeans = GoodsDao.getInstance().selectAll();
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
     * 替换表格中的数据
     *
     * @param beans 需要替换的数据
     */
    public void replaceData(List<GoodsBean> beans) {
        obList.clear();
        obList.addAll(beans);
    }

    /**
     * 添加数据
     *
     * @param bean 要添加的商品
     */
    public void addBean(GoodsBean bean) {
        obList.add(bean);
    }

    /**
     * 添加数据
     *
     * @param beans 要添加的商品集合
     */
    public void addAllBeans(List<GoodsBean> beans) {
        obList.addAll(beans);
    }

    /**
     * 获取选中的数据
     *
     * @return 选中的GoodsBean
     */
    public GoodsBean getSelectedData() {
        return this.selectedBean;
    }

    /**
     * 移除数据
     *
     * @param bean 需要移除的GoodsBean
     */
    public void removeData(GoodsBean bean) {
        this.obList.remove(bean);
    }
}
