package indi.nonoas.crm.table;

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
 * ��Ʒ��Ϣ���
 * 
 * @author Nonoas
 *
 */
public class GoodsInfoTable extends TableView<GoodsBean> {

	/** ����Դ */
	private final ObservableList<GoodsBean> obList = FXCollections.observableArrayList();
	/** ��ǰѡ������ */
	private GoodsBean selectedBean;

	private final TableColumn<GoodsBean, String> item_id = new TableColumn<GoodsBean, String>("���");

	private final TableColumn<GoodsBean, String> item_name = new TableColumn<GoodsBean, String>("��Ʒ����");

	private final TableColumn<GoodsBean, Number> item_sell_price = new TableColumn<GoodsBean, Number>("Ԥ�۵���");

	private final TableColumn<GoodsBean, Number> item_purchas_price = new TableColumn<GoodsBean, Number>("��������");

	private final TableColumn<GoodsBean, String> item_quantity = new TableColumn<GoodsBean, String>("ʣ������");

	private final TableColumn<GoodsBean, Number> item_min_discount = new TableColumn<GoodsBean, Number>("����ۿ�");

	private final TableColumn<GoodsBean, Number> item_deduction = new TableColumn<GoodsBean, Number>("��ɽ��");

	private final TableColumn<GoodsBean, Number> item_deduction_rate = new TableColumn<GoodsBean, Number>("��ɱ���");

	private final TableColumn<GoodsBean, String> item_type = new TableColumn<GoodsBean, String>("�������");

	public GoodsInfoTable() {
		initColumns();
		setItems(obList);
		showAllInfos();
		getSelectionModel().selectedItemProperty().addListener(cl_select);
	}

	private ChangeListener<GoodsBean> cl_select = (observable, oldValue, newValue) -> {
//		System.out.println(observable);
		System.out.println(newValue);
		selectedBean = newValue;
	};

	private void initColumns() {

		setTableMenuButtonVisible(true);

		item_id.setCellValueFactory(parm -> new SimpleStringProperty(parm.getValue().getId()));
		item_name.setCellValueFactory(parm -> new SimpleStringProperty(parm.getValue().getName()));
		item_sell_price.setCellValueFactory(parm -> new SimpleDoubleProperty(parm.getValue().getSell_price()));
		item_purchas_price.setCellValueFactory(parm -> new SimpleDoubleProperty(parm.getValue().getPurchas_price()));
		item_quantity.setCellValueFactory(parm -> {
			double quantity = parm.getValue().getQuantity(); // ����
			String unit = parm.getValue().getBase_unit(); // ��λ
			return new SimpleStringProperty(quantity + " " + unit);
		});
		item_min_discount.setCellValueFactory(parm -> new SimpleDoubleProperty(parm.getValue().getMin_discount()));
		item_deduction.setCellValueFactory(parm -> new SimpleDoubleProperty(parm.getValue().getDeduction()));
		item_deduction_rate.setCellValueFactory(parm -> new SimpleDoubleProperty(parm.getValue().getDeduction_rate()));
		item_type.setCellValueFactory(parm -> new SimpleStringProperty(parm.getValue().getType()));

		getColumns().add(item_id);
		getColumns().add(item_name);
		getColumns().add(item_sell_price);
		getColumns().add(item_purchas_price);
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
	 * �������
	 * 
	 * @param bean
	 */
	public void addBean(GoodsBean bean) {
		obList.add(bean);
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
