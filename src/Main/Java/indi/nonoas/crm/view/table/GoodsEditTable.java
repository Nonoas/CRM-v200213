package indi.nonoas.crm.view.table;

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
 * @author : Nonoas
 * @time : 2020-08-02 12:22
 */
public abstract class GoodsEditTable<S> extends TableView<GoodsEditTable.Data> {
    /**
     * ����Դ
     */
    private final ObservableList<GoodsEditTable.Data> obList = FXCollections.observableArrayList();

    /**
     * ����м���
     */
    private final ObservableList<TableColumn<GoodsEditTable.Data, ?>> cols = getColumns();

    /**
     * ��ǰѡ������
     */
    private Data selectedBean;

    protected final TableColumn<Data, String> item_id = new TableColumn<>("��Ʒ���");

    protected final TableColumn<Data, String> item_name = new TableColumn<>("��Ʒ����");

    protected final TableColumn<Data, String> item_money_cost = new TableColumn<>("��Ʒ����");

    protected final TableColumn<Data, Number> item_amount = new TableColumn<>("����");

    protected final TableColumn<Data, String> item_total = new TableColumn<>("С��");

    protected final TableColumn<Data, String> item_op = new TableColumn<>("����");

    public GoodsEditTable() {
        initColumns();
        setItems(obList);
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            selectedBean = newValue;
        });
    }

    protected void initColumns() {

        setTableMenuButtonVisible(true); // ��ʾ���˵���ť

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGoods_id()));
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGoods_name()));
        item_money_cost.setCellValueFactory(param -> {
            double numMoney = param.getValue().getGoods_price();
            String show = String.format("��%.2f", numMoney);
            return new SimpleStringProperty(show);
        });

        item_amount.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getGoods_amount()));

        item_total.setCellValueFactory(param -> {
            String show = String.format("��%.2f", param.getValue().getSum_price());
            return new SimpleStringProperty(show);
        });

        item_amount.setCellFactory(param -> new AmountCell());   //�Զ���������Ԫ��
        item_op.setCellFactory(param -> new DeleteCell());

        item_amount.setMinWidth(150);
        item_op.setResizable(false);
        item_op.setSortable(false);

        cols.add(item_id);
        cols.add(item_name);
        cols.add(item_money_cost);
        cols.add(item_amount);
        cols.add(item_total);
        cols.add(item_op);
    }

    /**
     * ��ȡѡ�е�����
     *
     * @return ѡ�е�PackageContentBean
     */
    public Data getSelectedData() {
        return selectedBean;
    }

    /**
     * �������Դ
     */
    public void clearData() {
        obList.clear();
    }

    /**
     * �Ƴ�����
     *
     * @param bean ��Ҫ�Ƴ���PackageContentBean
     */
    public void removeData(Data bean) {
        obList.remove(bean);
        refresh();
    }

    //===========================================================================
    //                            ���󷽷�
    //===========================================================================

    /**
     * ��ȡ���б������
     *
     * @return S���󼯺�
     */
    public abstract ArrayList<S> getAllBeans();

    /**
     * �������
     *
     * @param bean ��Ҫ��ӵ�����
     */
    public abstract void addBean(S bean);


    /**
     * ������ģ��תΪʵ����
     *
     * @return ʵ����
     */
    protected abstract S dataToBean(Data data);

    /**
     * ��ʬ����תΪ����ģ��
     *
     * @return ����ģ��
     */
    protected abstract Data beanToData(S bean);


    //===========================================================================
    //                            �ڲ���
    //===========================================================================


    /**
     * ����ģ���࣬���ں�PackageContentBean�໥ת��
     */
    protected static class Data {

        private String goods_id;
        private String goods_name;
        private double goods_price;
        private int goods_amount;
        private double sum_price;

        public Data() {
        }

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
    protected static class AmountCell extends TableCell<Data, Number> {

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

                ObservableList<Data> obList = getTableView().getItems(); //��ȡ���Դ����
                Data bean = obList.get(getIndex());

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


    /**
     * �Զ��������Ԫ��
     */
    protected static class DeleteCell extends TableCell<Data, String> {

        public DeleteCell() {
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                Hyperlink hl_delete = new Hyperlink("ɾ��");
                hl_delete.setStyle("-fx-text-fill: #cf4813");

                hl_delete.setOnAction(event -> {
                    TableView<Data> tableView = getTableView();
                    ObservableList<Data> items = tableView.getItems();
                    items.remove(getIndex());
                    tableView.refresh();
                });

                this.setGraphic(hl_delete);
            }
        }
    }
}
