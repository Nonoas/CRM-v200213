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
     * 数据源
     */
    private final ObservableList<GoodsEditTableVO> obList = FXCollections.observableArrayList();

    /**
     * 表格列集合
     */
    private final ObservableList<TableColumn<GoodsEditTableVO, ?>> cols = getColumns();

    /**
     * 当前选中数据
     */
    private GoodsEditTableVO selectedBean;


    /**
     * 事件委托类
     */
    private final EventHandler eventHandler = new EventHandler();

    protected final TableColumn<GoodsEditTableVO, String> item_id = new TableColumn<>("商品编号");

    protected final TableColumn<GoodsEditTableVO, String> item_name = new TableColumn<>("商品名称");

    protected final TableColumn<GoodsEditTableVO, String> item_money_cost = new TableColumn<>("商品单价");

    protected final TableColumn<GoodsEditTableVO, Number> item_amount = new TableColumn<>("数量");

    protected final TableColumn<GoodsEditTableVO, String> item_total = new TableColumn<>("小计");

    protected final TableColumn<GoodsEditTableVO, String> item_op = new TableColumn<>("操作");

    public GoodsEditTable() {
        initColumns();
        setItems(obList);
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            selectedBean = newValue;
        });
    }

    protected void initColumns() {

        setTableMenuButtonVisible(true); // 显示表格菜单按钮

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

        item_amount.setCellFactory(param -> new AmountCell());   //自定义数量单元格
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
     * @return 选中的PackageContentBean
     */
    public GoodsEditTableVO getSelectedData() {
        return selectedBean;
    }

    /**
     * 清空数据源
     */
    public void clearData() {
        obList.clear();
        eventHandler.execute();
    }

    /**
     * 移除数据
     *
     * @param bean 需要移除的PackageContentBean
     */
    public void removeData(GoodsEditTableVO bean) {
        obList.remove(bean);
        refresh();
    }

    /**
     * 获取事件委托对象
     *
     * @return 事件委托对象
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    //===========================================================================
    //                            抽象方法
    //===========================================================================

    /**
     * 获取所有表格数据
     *
     * @return S对象集合
     */
    public abstract ArrayList<S> getAllBeans();

    /**
     * 添加数据
     *
     * @param bean 需要添加的数据
     */
    public abstract void addBean(S bean);


    /**
     * 将数据模型转为实体类
     *
     * @return 实体类
     */
    protected abstract S dataToBean(GoodsEditTableVO data);

    /**
     * 将尸体类转为数据模型
     *
     * @return 数据模型
     */
    protected abstract GoodsEditTableVO beanToData(S bean);


    //===========================================================================
    //                            内部类
    //===========================================================================


    /**
     * 自定义“数量”单元格
     */
    protected class AmountCell extends TableCell<GoodsEditTableVO, Number> {

        public AmountCell() {
        }

        //设置单元格样式
        @Override
        protected void updateItem(Number item, boolean empty) {

            super.updateItem(item, empty);

            if (!empty && item != null) {

                HBox hBox = new HBox();
                Button btn_add = new Button("+");
                Button btn_reduce = new Button("-");
                TextField tf_number = new TextField(item.toString());
                //设置初始尺寸
                btn_add.setPrefWidth(35);
                btn_reduce.setPrefWidth(35);
                tf_number.setPrefWidth(50);
                //设置最小尺寸
                btn_add.setMinWidth(35);
                btn_reduce.setMinWidth(35);
                tf_number.setMinWidth(50);

                //设置按钮样式
                btn_reduce.getStyleClass().add("danger");

                HBox.setHgrow(tf_number, Priority.ALWAYS);

                hBox.getChildren().addAll(btn_add, tf_number, btn_reduce);
                hBox.setSpacing(10);

                ObservableList<GoodsEditTableVO> obList = getTableView().getItems(); //获取表格源数据
                GoodsEditTableVO bean = obList.get(getIndex());

                //设置按钮监听
                //加一
                btn_add.setOnAction(event -> {
                    int amount = Integer.parseInt(tf_number.getText()) + 1;
                    tf_number.setText(String.valueOf(amount));
                });
                //减一
                btn_reduce.setOnAction(event -> {
                    int amount = Integer.parseInt(tf_number.getText()) - 1;
                    if (amount > 0) {
                        tf_number.setText(String.valueOf(amount));
                    }
                });

                //文本框变化监听
                tf_number.textProperty().addListener((observable, oldValue, newValue) -> {
                    String pattern = "^\\d+$";
                    boolean isNumber = Pattern.matches(pattern, newValue); //判断是否为正整数
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
     * 自定义操作单元格
     */
    protected class DeleteCell extends TableCell<GoodsEditTableVO, String> {

        public DeleteCell() {
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                Button btn_delete = new Button("删除");
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
