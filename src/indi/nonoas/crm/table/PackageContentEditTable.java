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
 * ���Ա༭���ݵġ��ײ����ݡ����
 *
 * @author: Nonoas
 * @Date: 2020/4/4 18:19
 * @Description: ���Ա༭���ݵġ��ײ����ݡ����
 */
public class PackageContentEditTable extends TableView<PackageContentBean> {
    /**
     * ����Դ
     */
    private final ObservableList<PackageContentBean> obList = FXCollections.observableArrayList();

    private final ObservableList<TableColumn<PackageContentBean, ?>> cols = getColumns();
    /**
     * ��ǰѡ������
     */
    private PackageContentBean selectedBean;

    private final TableColumn<PackageContentBean, String> item_id = new TableColumn<>("��Ʒ���");

    private final TableColumn<PackageContentBean, String> item_name = new TableColumn<>("��Ʒ����");

    private final TableColumn<PackageContentBean, String> item_money_cost = new TableColumn<>("��Ʒ����");

    private final TableColumn<PackageContentBean, Number> item_count = new TableColumn<>("����");

    private final TableColumn<PackageContentBean, String> item_total = new TableColumn<>("С��");

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

        item_count.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getGoods_amount()));

        item_count.setCellFactory(param -> new AmountCell());   //�Զ���������Ԫ��

        item_total.setCellValueFactory(param -> {
            String goodsID = param.getValue().getGoods_id();
            GoodsBean goodsBean = GoodsDao.getInstance().selectById(goodsID);
            double numMoney = goodsBean.getSell_price();
            double amount = param.getValue().getGoods_amount();
            String show = String.format("��%.2f", numMoney * amount);
            return new SimpleStringProperty(show);
        });

        item_count.setMinWidth(150);

        //������е���
        cols.add(item_id);
        cols.add(item_name);
        cols.add(item_money_cost);
        cols.add(item_count);
        cols.add(item_total);


        //�������������ݾ���,�Ҳ������� (����ᵼ���Զ��嵥Ԫ�����ݳ���)
        cols.forEach(action -> {
            action.setStyle("-fx-alignment:CENTER");
            action.setSortable(false);
        });

    }

    /**
     * չʾ������Ŀ��Ϣ
     */
    public void showAllInfos(String id) {
        clearData(); // �����������
        ArrayList<PackageContentBean> listVipBeans = new PackageContentDao().selectById(id);
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
     * ��Ӳ��ظ�������
     *
     * @param bean ��Ҫ��ӵ�����
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

/**
 * �Զ��塰��������Ԫ��
 */
class AmountCell extends TableCell<PackageContentBean, Number> {

    public AmountCell() {

    }

    //���õ�Ԫ����ʽ
    @Override
    protected void updateItem(Number item, boolean empty) {

        super.updateItem(item, empty);

        if (!empty && item != null) {

            HBox hBox = new HBox();
            Button btn_add = new Button("+");
            Button btn_reduce = new Button("-");
            TextField tf_number = new TextField(item.toString());
            //���ó�ʼ�ߴ�
            btn_add.setPrefWidth(35);
            btn_reduce.setPrefWidth(35);
            tf_number.setPrefWidth(50);
            //������С�ߴ�
            btn_add.setMinWidth(35);
            btn_reduce.setMinWidth(35);
            tf_number.setMinWidth(50);

            HBox.setHgrow(tf_number, Priority.ALWAYS);

            hBox.getChildren().addAll(btn_add, tf_number, btn_reduce);
            hBox.setSpacing(10);

            ObservableList<PackageContentBean> obList = getTableView().getItems(); //��ȡ���Դ����
            PackageContentBean bean = obList.get(getIndex());

            //���ð�ť����
            //��һ
            btn_add.setOnAction(event -> {
                int amount = Integer.parseInt(tf_number.getText()) + 1;
                tf_number.setText(String.valueOf(amount));
            });
            //��һ
            btn_reduce.setOnAction(event -> {
                int amount = Integer.parseInt(tf_number.getText()) - 1;
                if (amount > 0) {
                    tf_number.setText(String.valueOf(amount));
                }
            });
            //�ı���仯����
            tf_number.textProperty().addListener((observable, oldValue, newValue) -> {
                String pattern = "^\\d+$";
                boolean isNumber = Pattern.matches(pattern, newValue); //�ж��Ƿ�Ϊ������
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
