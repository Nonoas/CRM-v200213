package indi.nonoas.crm.app.pkg;

import indi.nonoas.crm.beans.PackageBean;
import indi.nonoas.crm.dao.my_orm_dao.PackageDao;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

/**
 * 项目信息表格
 *
 * @author Nonoas
 */
public class PackageTable extends TableView<PackageBean> {

    /**
     * 数据源
     */
    private final ObservableList<PackageBean> obList = FXCollections.observableArrayList();

    /**
     * 列集合
     */
    private final ObservableList<TableColumn<PackageBean, ?>> colList = getColumns();

    /**
     * 当前选中数据
     */
    private PackageBean selectedBean;

    /**
     * 绑定的套餐内容表格
     */
    private PackageContentTable packageContentTable;

    private final TableColumn<PackageBean, String> item_id = new TableColumn<>("编号");

    private final TableColumn<PackageBean, String> item_name = new TableColumn<>("项目名称");

    private final TableColumn<PackageBean, String> item_money_cost = new TableColumn<>("金额消费");

    private final TableColumn<PackageBean, Number> item_integral_cost = new TableColumn<>("积分消费");

    private final TableColumn<PackageBean, Number> item_min_discount = new TableColumn<>("最低折扣");

    private final TableColumn<PackageBean, String> item_type = new TableColumn<>("项目类型");

    private final TableColumn<PackageBean, String> item_other = new TableColumn<>("备注信息");

    public PackageTable() {
        initColumns();
        setItems(obList);
        showAllInfos();
        getSelectionModel().selectedItemProperty().addListener(cl_select);    //添加选中监听
    }

    public PackageTable(PackageContentTable packageContentTable) {
        this();
        this.packageContentTable = packageContentTable;
        getSelectionModel().selectedItemProperty().addListener(cl_showContent);    //添加选中监听
    }

    //声明选中监听
    ChangeListener<PackageBean> cl_select = (observable, oldValue, newValue) -> {
        System.out.println("项目信息表格选中：" + newValue);
        selectedBean = newValue;
    };

    //声明套餐表格内容显示
    ChangeListener<PackageBean> cl_showContent = (observable, oldValue, newValue) -> {
        if (selectedBean != null) {
            packageContentTable.showAllInfos(selectedBean.getId()); //更新选中表格的内容
        }
    };


    private void initColumns() {

        setTableMenuButtonVisible(true); // 显示表格菜单按钮

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId()));
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        item_money_cost.setCellValueFactory(param -> {
            double numMoney = param.getValue().getMoneyCost();
            String show = String.format("￥%.2f", numMoney);
            return new SimpleStringProperty(show);
        });
        item_integral_cost.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getIntegralCost()));
        item_min_discount.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getMinDiscount()));
        item_type.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType()));

        item_other.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOther()));

        colList.add(item_id);
        colList.add(item_name);
        colList.add(item_money_cost);
        colList.add(item_integral_cost);
        colList.add(item_min_discount);
        colList.add(item_type);
        colList.add(item_other);

    }

    /**
     * 展示所有项目信息
     */
    public void showAllInfos() {
        clearData(); // 清空所有数据
        ArrayList<PackageBean> listVipBeans = PackageDao.getInstance().selectAll();
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
     * @param bean 套餐项目bean类
     */
    public void addBean(PackageBean bean) {
        obList.add(bean);
    }

    /**
     * 获取选中的数据
     *
     * @return 选中的PackageBean
     */
    public PackageBean getSelectedData() {
        return this.selectedBean;
    }

    /**
     * 移除数据
     *
     * @param bean 需要移除的PackageBean
     */
    public void removeData(PackageBean bean) {
        this.obList.remove(bean);
    }
}
