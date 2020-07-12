package indi.nonoas.crm.table;

import indi.nonoas.crm.bean.PackageBean;
import indi.nonoas.crm.dao.PackageDao;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

/**
 * ��Ŀ��Ϣ���
 *
 * @author Nonoas
 */
public class PackageTable extends TableView<PackageBean> {

    /**
     * ����Դ
     */
    private final ObservableList<PackageBean> obList = FXCollections.observableArrayList();
    /**
     * ��ǰѡ������
     */
    private PackageBean selectedBean;
    /**
     * �󶨵��ײ����ݱ��
     */
    private PackageContentTable packageContentTable;

    private final TableColumn<PackageBean, String> item_id = new TableColumn<>("���");

    private final TableColumn<PackageBean, String> item_name = new TableColumn<>("��Ŀ����");

    private final TableColumn<PackageBean, String> item_money_cost = new TableColumn<>("�������");

    private final TableColumn<PackageBean, Number> item_integral_cost = new TableColumn<>("��������");

    private final TableColumn<PackageBean, Number> item_min_discount = new TableColumn<>("����ۿ�");

    private final TableColumn<PackageBean, String> item_other = new TableColumn<>("��ע��Ϣ");

    public PackageTable(PackageContentTable packageContentTable) {

        this.packageContentTable = packageContentTable;

        initColumns();
        setItems(obList);
        showAllInfos();

        getSelectionModel().selectedItemProperty().addListener(cl_select);    //���ѡ�м���
    }

    //����ѡ�м���
    ChangeListener<PackageBean> cl_select = (observable, oldValue, newValue) -> {
        System.out.println("��Ŀ��Ϣ���ѡ�У�" + newValue);
        selectedBean = newValue;
        if(selectedBean!=null){
            packageContentTable.showAllInfos(selectedBean.getId()); //����ѡ�б�������
        }
    };


    private void initColumns() {

        setTableMenuButtonVisible(true); // ��ʾ���˵���ť

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId()));
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        item_money_cost.setCellValueFactory(param -> {
            double numMoney = param.getValue().getMoney_cost();
            String show = String.format("��%.2f", numMoney);
            return new SimpleStringProperty(show);
        });
        item_integral_cost.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getIntegral_cost()));
        item_min_discount.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getMin_discount()));

        item_other.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOther()));

        getColumns().add(item_id);
        getColumns().add(item_name);
        getColumns().add(item_money_cost);
        getColumns().add(item_integral_cost);
        getColumns().add(item_min_discount);
        getColumns().add(item_other);

    }

    /**
     * չʾ������Ŀ��Ϣ
     */
    public void showAllInfos() {
        clearData(); // �����������
        ArrayList<PackageBean> listVipBeans = PackageDao.getInstance().selectAll();
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
     * �������
     *
     * @param bean �ײ���Ŀbean��
     */
    public void addBean(PackageBean bean) {
        obList.add(bean);
    }

    /**
     * ��ȡѡ�е�����
     *
     * @return ѡ�е�PackageBean
     */
    public PackageBean getSelectedData() {
        return this.selectedBean;
    }

    /**
     * �Ƴ�����
     *
     * @param bean ��Ҫ�Ƴ���PackageBean
     */
    public void removeData(PackageBean bean) {
        this.obList.remove(bean);
    }
}
