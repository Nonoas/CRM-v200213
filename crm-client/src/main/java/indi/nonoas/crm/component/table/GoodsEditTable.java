package indi.nonoas.crm.component.table;

import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import indi.nonoas.crm.common.delegate.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author : Nonoas
 * @time : 2020-08-02 12:22
 */
public abstract class GoodsEditTable<S> extends TableView<GoodsEditTableVO> {

    private final Logger logger = LoggerFactory.getLogger(GoodsEditTable.class);

    private final ObservableList<GoodsEditTableVO> obList = FXCollections.observableArrayList();

    private final ObservableList<TableColumn<GoodsEditTableVO, ?>> cols = getColumns();

    private GoodsEditTableVO selectedData;

    private final EventHandler eventHandler = new EventHandler();

    protected final TableColumn<GoodsEditTableVO, String> item_id = new TableColumn<>("商品编号");

    protected final TableColumn<GoodsEditTableVO, String> item_name = new TableColumn<>("商品名称");

    protected final TableColumn<GoodsEditTableVO, String> item_money_cost = new TableColumn<>("单价");

    protected final TableColumn<GoodsEditTableVO, Number> item_amount = new TableColumn<>("数量");

    protected final TableColumn<GoodsEditTableVO, String> item_total = new TableColumn<>("小计");

    protected final TableColumn<GoodsEditTableVO, String> item_op = new TableColumn<>("操作");

    public GoodsEditTable() {

        initColumns();
        setItems(obList);
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.debug(newValue.toString());
            selectedData = newValue;
        });

    }

    protected void initColumns() {

        setTableMenuButtonVisible(true);

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId()));
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        item_money_cost.setCellValueFactory(param -> {
            double numMoney = param.getValue().getPrice();
            String show = String.format("￥%.2f", numMoney);
            return new SimpleStringProperty(show);
        });

        item_amount.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getAmount()));

        item_total.setCellValueFactory(param -> {
            String show = String.format("￥%.2f", param.getValue().getSum_price());
            return new SimpleStringProperty(show);
        });

        item_amount.setCellFactory(param -> new AmountCell());
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
     * 获取选中的数据
     *
     * @return GoodsEditTableVO
     */
    public GoodsEditTableVO getSelectedData() {
        return selectedData;
    }

    /**
     * 清空表中数据
     */
    public void clearData() {
        obList.clear();
        eventHandler.execute();
    }

    /**
     * 移除某个元素
     *
     * @param bean 表格元素
     */
    public void removeData(GoodsEditTableVO bean) {
        obList.remove(bean);
    }

    /**
     * 获取事件处理类
     *
     * @return EventHandler
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    //===========================================================================
    //                            ���󷽷�
    //===========================================================================

    /**
     * 获取所有的数据
     *
     * @return S 指定的类型
     */
    public abstract List<S> getAllBeans();

    /**
     * 添加数据
     *
     * @param bean ��Ҫ��ӵ�����
     */
    public abstract void addBean(S bean);


    /**
     * 将data映射到某个对象
     */
    protected abstract S dataToBean(GoodsEditTableVO data);

    /**
     * 将类型 S 映射到 GoodsEditTableVO
     *
     * @param bean S
     * @return GoodsEditTableVO
     */
    protected abstract GoodsEditTableVO beanToData(S bean);


    //===========================================================================
    //                            自定义单元格
    //===========================================================================


    /**
     * 数量单元格，包含 +、-两个按钮
     */
    protected class AmountCell extends TableCell<GoodsEditTableVO, Number> {

        public AmountCell() {
        }

        @Override
        protected void updateItem(Number item, boolean empty) {

            super.updateItem(item, empty);

            if (!empty && item != null) {

                Button btn_add = new Button("+");
                Button btn_reduce = new Button("-");
                TextField tf_number = new TextField(item.toString());

                btn_add.setPrefWidth(35);
                btn_reduce.setPrefWidth(35);
                tf_number.setPrefWidth(50);

                HBox.setHgrow(tf_number, Priority.ALWAYS);

                HBox hBox = new HBox(10, btn_add, tf_number, btn_reduce);

                ObservableList<GoodsEditTableVO> obList = getTableView().getItems();
                GoodsEditTableVO bean = obList.get(getIndex());

                // 减少按钮
                btn_add.setOnAction(event -> {
                    int amount = Integer.parseInt(tf_number.getText()) + 1;
                    tf_number.setText(String.valueOf(amount));

                });
                // 增加按钮
                btn_reduce.setOnAction(event -> {
                    int amount = Integer.parseInt(tf_number.getText()) - 1;
                    if (amount > 0) {
                        tf_number.setText(String.valueOf(amount));
                    }
                });

                tf_number.textProperty().addListener((observable, oldValue, newValue) -> {
                    // 整数正则表达式
                    String pattern = "^\\d+$";
                    boolean isNumber = Pattern.matches(pattern, newValue);
                    if (isNumber) {
                        bean.setAmount(Integer.parseInt(newValue));
                        GoodsEditTable<S> table = (GoodsEditTable<S>) getTableView();
                        table.getEventHandler().execute();
                        refresh();
                    } else {
                        tf_number.setText(oldValue);
                    }
                });
                this.setGraphic(hBox);
            } else {
                setGraphic(null);
            }
        }
    }


    /**
     * 删除单元格
     */
    protected class DeleteCell extends TableCell<GoodsEditTableVO, String> {

        public DeleteCell() {

        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                Button btnDelete = new Button("删除");
                btnDelete.getStyleClass().add("danger");

                btnDelete.setOnAction(event -> {
                    GoodsEditTable<S> tableView = (GoodsEditTable<S>) getTableView();
                    ObservableList<GoodsEditTableVO> items = tableView.getItems();
                    items.remove(getIndex());
                    tableView.getEventHandler().execute();
                });

                this.setAlignment(Pos.CENTER);
                this.setGraphic(btnDelete);
            } else {
                setGraphic(null);
            }
        }
    }
}
