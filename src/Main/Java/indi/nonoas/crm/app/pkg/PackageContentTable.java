package indi.nonoas.crm.app.pkg;

import java.util.ArrayList;

import indi.nonoas.crm.beans.GoodsBean;
import indi.nonoas.crm.beans.PackageContentBean;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.dao.PackageContentDao;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PackageContentTable extends TableView<PackageContentBean> {
    /**
     * ����Դ
     */
    private final ObservableList<PackageContentBean> obList = FXCollections.observableArrayList();
    /**
     * ��ǰѡ������
     */
    private PackageContentBean selectedBean;

    private final TableColumn<PackageContentBean, String> item_id = new TableColumn<>("��Ʒ���");

    private final TableColumn<PackageContentBean, String> item_name = new TableColumn<>("��Ʒ����");

    private final TableColumn<PackageContentBean, String> item_money_cost = new TableColumn<>("��Ʒ����");

    private final TableColumn<PackageContentBean, Number> item_amount = new TableColumn<>("����");

    private final TableColumn<PackageContentBean, String> item_total = new TableColumn<>("С��");

    public PackageContentTable() {
        initColumns();
        setItems(obList);
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            selectedBean = newValue;
        });
    }

    private void initColumns() {

        setTableMenuButtonVisible(true); // ��ʾ���˵���ť

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
			String show = String.format("��%.2f", numMoney);
			return new SimpleStringProperty(show);
		});

		item_amount.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getGoods_amount()));

		item_total.setCellValueFactory(param -> {
            String goodsID = param.getValue().getGoods_id();
            GoodsBean goodsBean = GoodsDao.getInstance().selectById(goodsID);
            double numMoney = goodsBean.getSell_price();
            double amount=param.getValue().getGoods_amount();
            String show=String.format("��%.2f", numMoney*amount);
			return new SimpleStringProperty(show);
		});

        getColumns().add(item_id);
        getColumns().add(item_name);
        getColumns().add(item_money_cost);
        getColumns().add(item_amount);
        getColumns().add(item_total);
    }

    /**
     * չʾ������Ŀ��Ϣ
     */
    public void showAllInfos(String id) {
        clearData(); // �����������
        ArrayList<PackageContentBean> listVipBeans = PackageContentDao.getInstance().selectById(id);
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
     * @param bean ��Ҫ��ӵ�����
     */
    public void addBean(PackageContentBean bean) {
        obList.add(bean);
    }

    /**
     * ��ȡѡ�е�����
     *
     * @return ѡ�е�PackageContentBean
     */
    public PackageContentBean getSelectedData() {
        return this.selectedBean;
    }

    /**
     * �Ƴ�����
     *
     * @param bean ��Ҫ�Ƴ���PackageContentBean
     */
    public void removeData(PackageContentBean bean) {
        this.obList.remove(bean);
    }

}
