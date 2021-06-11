package indi.nonoas.crm.view.table;

import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import per.nonoas.delegate.EventHandler;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author : Nonoas
 * @time : 2020-08-02 12:22
 */
public abstract class GoodsEditTable<S> extends TableView<GoodsEditTableVO> {

    /**
     * ����Դ
     */
    private final ObservableList<GoodsEditTableVO> obList = FXCollections.observableArrayList();

    /**
     * ����м���
     */
    private final ObservableList<TableColumn<GoodsEditTableVO, ?>> cols = getColumns();

    /**
     * ��ǰѡ������
     */
    private GoodsEditTableVO selectedBean;


    /**
     * �¼�ί����
     */
    private final EventHandler eventHandler = new EventHandler();

    protected final TableColumn<GoodsEditTableVO, String> item_id = new TableColumn<>("��Ʒ���");

    protected final TableColumn<GoodsEditTableVO, String> item_name = new TableColumn<>("��Ʒ����");

    protected final TableColumn<GoodsEditTableVO, String> item_money_cost = new TableColumn<>("��Ʒ����");

    protected final TableColumn<GoodsEditTableVO, Number> item_amount = new TableColumn<>("����");

    protected final TableColumn<GoodsEditTableVO, String> item_total = new TableColumn<>("С��");

    protected final TableColumn<GoodsEditTableVO, String> item_op = new TableColumn<>("����");

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
    public GoodsEditTableVO getSelectedData() {
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
    public void removeData(GoodsEditTableVO bean) {
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
    protected abstract S dataToBean(GoodsEditTableVO data);

    /**
     * ��ʬ����תΪ����ģ��
     *
     * @return ����ģ��
     */
    protected abstract GoodsEditTableVO beanToData(S bean);


    //===========================================================================
    //                            �ڲ���
    //===========================================================================


    /**
     * �Զ��塰��������Ԫ��
     */
    protected class AmountCell extends TableCell<GoodsEditTableVO, Number> {

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

                //���ð�ť��ʽ
                btn_reduce.getStyleClass().add("danger");

                HBox.setHgrow(tf_number, Priority.ALWAYS);

                hBox.getChildren().addAll(btn_add, tf_number, btn_reduce);
                hBox.setSpacing(10);

                ObservableList<GoodsEditTableVO> obList = getTableView().getItems(); //��ȡ���Դ����
                GoodsEditTableVO bean = obList.get(getIndex());

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
    protected class DeleteCell extends TableCell<GoodsEditTableVO, String> {

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
                    ObservableList<GoodsEditTableVO> items = tableView.getItems();
                    items.remove(getIndex());
                    tableView.refresh();

                    tableView.getEventHandler().execute();

                });

                this.setGraphic(btn_delete);
            }
        }
    }
}
