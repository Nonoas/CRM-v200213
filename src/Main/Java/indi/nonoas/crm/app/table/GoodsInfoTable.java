package indi.nonoas.crm.app.table;

import java.util.ArrayList;

import indi.nonoas.crm.bean.GoodsBean;
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

        item_id.setCellValueFactory(parm -> new SimpleStringProperty(parm.getValue().getId()));
        item_name.setCellValueFactory(parm -> new SimpleStringProperty(parm.getValue().getName()));
        item_sell_price.setCellValueFactory(parm -> new SimpleDoubleProperty(parm.getValue().getSell_price()));
        item_purchase_price.setCellValueFactory(parm -> new SimpleDoubleProperty(parm.getValue().getPurchase_price()));
        item_quantity.setCellValueFactory(parm -> {
            double quantity = parm.getValue().getQuantity(); // 数量
            String unit = parm.getValue().getBase_unit(); // 单位
            return new SimpleStringProperty(quantity + " " + unit);
        });
        item_min_discount.setCellValueFactory(parm -> new SimpleDoubleProperty(parm.getValue().getMin_discount()));
        item_deduction.setCellValueFactory(parm -> new SimpleDoubleProperty(parm.getValue().getDeduction()));
        item_deduction_rate.setCellValueFactory(parm -> new SimpleDoubleProperty(parm.getValue().getDeduction_rate()));
        item_type.setCellValueFactory(parm -> new SimpleStringProperty(parm.getValue().getType()));

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
     * 添加数据
     *
     * @param bean
     */
    public void addBean(GoodsBean bean) {
        obList.add(bean);
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
