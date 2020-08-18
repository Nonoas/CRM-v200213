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
 * ��Ʒ��Ϣ���
 *
 * @author Nonoas
 */
public class GoodsInfoTable extends TableView<GoodsBean> {

    /**
     * ����Դ
     */
    private final ObservableList<GoodsBean> obList = FXCollections.observableArrayList();
    /**
     * ��ǰѡ������
     */
    private GoodsBean selectedBean;

    protected final TableColumn<GoodsBean, String> item_id = new TableColumn<>("���");

    protected final TableColumn<GoodsBean, String> item_name = new TableColumn<>("��Ʒ����");

    protected final TableColumn<GoodsBean, Number> item_sell_price = new TableColumn<>("Ԥ�۵���");

    protected final TableColumn<GoodsBean, Number> item_purchase_price = new TableColumn<>("��������");

    protected final TableColumn<GoodsBean, String> item_quantity = new TableColumn<>("ʣ������");

    protected final TableColumn<GoodsBean, Number> item_min_discount = new TableColumn<>("����ۿ�");

    protected final TableColumn<GoodsBean, Number> item_deduction = new TableColumn<>("��ɽ��");

    protected final TableColumn<GoodsBean, Number> item_deduction_rate = new TableColumn<>("��ɱ���");

    protected final TableColumn<GoodsBean, String> item_type = new TableColumn<>("�������");

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
            double quantity = param.getValue().getQuantity(); // ����
            String unit = param.getValue().getBaseUnit(); // ��λ
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
     * չʾ�����û���Ϣ
     */
    public void showAllInfos() {
        clearData(); // �����������
        ArrayList<GoodsBean> listVipBeans = GoodsDao.getInstance().selectAll();
        if (listVipBeans != null)
            obList.addAll(listVipBeans);
    }

    /**
     * �������Դ
     */
    public void clearData() {
        obList.clear();
    }

    /**
     * �滻����е�����
     *
     * @param beans ��Ҫ�滻������
     */
    public void replaceData(List<GoodsBean> beans) {
        obList.clear();
        obList.addAll(beans);
    }

    /**
     * �������
     *
     * @param bean Ҫ��ӵ���Ʒ
     */
    public void addBean(GoodsBean bean) {
        obList.add(bean);
    }

    /**
     * �������
     *
     * @param beans Ҫ��ӵ���Ʒ����
     */
    public void addAllBeans(List<GoodsBean> beans) {
        obList.addAll(beans);
    }

    /**
     * ��ȡѡ�е�����
     *
     * @return ѡ�е�GoodsBean
     */
    public GoodsBean getSelectedData() {
        return this.selectedBean;
    }

    /**
     * �Ƴ�����
     *
     * @param bean ��Ҫ�Ƴ���GoodsBean
     */
    public void removeData(GoodsBean bean) {
        this.obList.remove(bean);
    }
}
