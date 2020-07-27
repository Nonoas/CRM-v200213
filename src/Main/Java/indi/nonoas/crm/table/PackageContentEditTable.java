package indi.nonoas.crm.table;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.bean.PackageContentBean;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.dao.PackageContentDao;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * ���Ա༭���ݵġ��ײ����ݡ�����
 *
 * @author: Nonoas
 * @Date: 2020/4/4 18:19
 * @Description: ���Ա༭���ݵġ��ײ����ݡ�����
 */
public class PackageContentEditTable extends TableView<PackageContentEditTable.Data> {
    /**
     * ����Դ
     */
    private final ObservableList<Data> obList = FXCollections.observableArrayList();

    private final ObservableList<TableColumn<Data, ?>> cols = getColumns();
    /**
     * ��ǰѡ������
     */
    private Data selectedBean;

    private final TableColumn<Data, String> item_id = new TableColumn<>("��Ʒ���");

    private final TableColumn<Data, String> item_name = new TableColumn<>("��Ʒ����");

    private final TableColumn<Data, String> item_money_cost = new TableColumn<>("��Ʒ����");

    private final TableColumn<Data, Number> item_count = new TableColumn<>("����");

    private final TableColumn<Data, String> item_total = new TableColumn<>("С��");

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

        setTableMenuButtonVisible(true); // ��ʾ����˵���ť

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGoods_id()));
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGoods_name()));
        item_money_cost.setCellValueFactory(param -> {
            double numMoney = param.getValue().getGoods_price();
            String show = String.format("��%.2f", numMoney);
            return new SimpleStringProperty(show);
        });

        item_count.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getGoods_amount()));

        item_count.setCellFactory(param -> new AmountCell());   //�Զ���������Ԫ��

        item_total.setCellValueFactory(param -> {
            String show = String.format("��%.2f", param.getValue().getSum_price());
            return new SimpleStringProperty(show);
        });

        item_count.setMinWidth(150);

        //�������е���
        cols.add(item_id);
        cols.add(item_name);
        cols.add(item_money_cost);
        cols.add(item_count);
        cols.add(item_total);

    }

    /**
     * չʾ������Ŀ��Ϣ
     */
    public void showAllInfos(String id) {
        clearData(); // �����������
        ArrayList<PackageContentBean> listPkgContentBeans = PackageContentDao.getInstance().selectById(id);
        ArrayList<Data> listData = new ArrayList<>();
        if (listPkgContentBeans != null) {
            for (PackageContentBean p : listPkgContentBeans) {
                listData.add(beanToData(p));
            }
            obList.addAll(listData);
        }
    }

    /**
     * �������Դ
     */
    public void clearData() {
        obList.clear();
    }

    /**
     * ���Ӳ��ظ�������
     *
     * @param bean ��Ҫ���ӵ�����
     */
    public void addBean(PackageContentBean bean) {
        boolean hasRepeat = false;
        Data data = beanToData(bean); //����ת��
        for (Data d : obList) {
            String id1 = d.getGoods_id();
            String id2 = data.getGoods_id();
            hasRepeat = hasRepeat || id1.equals(id2);
        }
        if (!hasRepeat)
            obList.add(data);
    }

    /**
     * ��ȡѡ�е�����
     *
     * @return ѡ�е�PackageContentBean
     */
    public Data getSelectedData() {
        return this.selectedBean;
    }

    /**
     * �Ƴ�����
     *
     * @param bean ��Ҫ�Ƴ���PackageContentBean
     */
    public void removeData(Data bean) {
        this.obList.remove(bean);
        refresh();
    }

    /**
     * ��ȡ������������Ʒ��Ϣ
     *
     * @return PackageContentBean�ļ���
     */
    public ArrayList<PackageContentBean> getAllData() {
        ArrayList<PackageContentBean> packageContentBeans = new ArrayList<>();
        for (Data d : obList) {
            packageContentBeans.add(dataToBean(d));
        }
        return packageContentBeans;
    }

    /**
     * ��bean��ת��Ϊ����ģ��
     *
     * @return Data�����
     */
    private Data beanToData(PackageContentBean bean) {
        String id = bean.getGoods_id();
        GoodsBean goodsBean = GoodsDao.getInstance().selectById(id);
        String name = goodsBean.getName();
        double price = goodsBean.getSell_price();
        int amount = bean.getGoods_amount();
        double sum = price * amount;

        Data data = new Data();
        data.setGoods_id(id);
        data.setGoods_name(name);
        data.setGoods_price(price);
        data.setGoods_amount(amount);
        return data;
    }

    /**
     * ������ģ��ת��Ϊbean��
     *
     * @param data ��������ģ��
     * @return PackageContentBean����
     */
    private PackageContentBean dataToBean(Data data) {
        PackageContentBean packageContentBean = new PackageContentBean();
        packageContentBean.setGoods_id(data.getGoods_id());
        packageContentBean.setGoods_amount(data.getGoods_amount());
        return packageContentBean;
    }


    /**
     * ����ģ���࣬���ں�PackageContentBean�໥ת��
     */
    static class Data {
        private String goods_id;
        private String goods_name;
        private double goods_price;
        private int goods_amount;
        private double sum_price;

        public String getGoods_id() {
            return goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public double getGoods_price() {
            return goods_price;
        }

        public int getGoods_amount() {
            return goods_amount;
        }

        public double getSum_price() {
            return sum_price;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setGoods_price(double goods_price) {
            this.goods_price = goods_price;
            this.sum_price = goods_price * goods_amount;
        }

        public void setGoods_amount(int goods_amount) {
            this.goods_amount = goods_amount;
            this.sum_price = goods_price * goods_amount;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "goods_id='" + goods_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", goods_price=" + goods_price +
                    ", goods_amount=" + goods_amount +
                    ", sum_price=" + sum_price +
                    '}';
        }
    }

    /**
     * �Զ��塰��������Ԫ��
     */
    static class AmountCell extends TableCell<PackageContentEditTable.Data, Number> {

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

                ObservableList<PackageContentEditTable.Data> obList = getTableView().getItems(); //��ȡ����Դ����
                PackageContentEditTable.Data bean = obList.get(getIndex());

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
                        getTableView().refresh();
                    } else {
                        tf_number.setText(oldValue);
                    }
                });

                this.setGraphic(hBox);
            }
        }
    }


}



