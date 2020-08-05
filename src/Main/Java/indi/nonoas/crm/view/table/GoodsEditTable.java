package indi.nonoas.crm.view.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import per.nonoas.delegate.EventHandler;

import java.beans.PropertyChangeSupport;
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


    /**
     * �¼�ί����
     */
    private final EventHandler eventHandler = new EventHandler();

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

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId()));
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        item_money_cost.setCellValueFactory(param -> {
            double numMoney = param.getValue().getPrice();
            String show = String.format("��%.2f", numMoney);
            return new SimpleStringProperty(show);
        });

        item_amount.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getAmount()));

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
        eventHandler.execute();
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

    /**
     * ��ȡ�¼�ί�ж���
     *
     * @return �¼�ί�ж���
     */
    public EventHandler getEventHandler() {
        return eventHandler;
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

        private String id;
        private String name;
        private double price;
        private int amount;
        private double sum_price;

        private final PropertyChangeSupport psc = new PropertyChangeSupport(this);

        public Data() {
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getAmount() {
            return amount;
        }

        public double getSum_price() {
            return sum_price;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(double price) {
            double oldValue = this.price;
            this.price = price;
            this.sum_price = price * amount;
            psc.firePropertyChange("goods_price", oldValue, price);
        }

        public void setAmount(int amount) {
            int oldValue = this.amount;
            this.amount = amount;
            this.sum_price = price * amount;
            psc.firePropertyChange("goods_amount", oldValue, amount);
        }

        public PropertyChangeSupport getPropertyChangeSupport() {
            return psc;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "goods_id='" + id + '\'' +
                    ", goods_name='" + name + '\'' +
                    ", goods_price=" + price +
                    ", goods_amount=" + amount +
                    ", sum_price=" + sum_price +
                    '}';
        }
    }

    /**
     * �Զ��塰��������Ԫ��
     */
    protected class AmountCell extends TableCell<Data, Number> {

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
                        bean.setAmount(Integer.parseInt(newValue));
                        GoodsEditTable<S> table = (GoodsEditTable<S>) getTableView();
                        table.refresh();
                        table.getEventHandler().execute();
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
    protected class DeleteCell extends TableCell<Data, String> {

        public DeleteCell() {
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                Button btn_delete = new Button("ɾ��");
                btn_delete.getStyleClass().add("negative-btn");

                btn_delete.setOnAction(event -> {
                    GoodsEditTable<S> tableView = (GoodsEditTable<S>) getTableView();
                    ObservableList<Data> items = tableView.getItems();
                    items.remove(getIndex());
                    tableView.refresh();

                    tableView.getEventHandler().execute();

                });

                this.setGraphic(btn_delete);
            }
        }
    }
}
