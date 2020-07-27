//package indi.nonoas.crm.table;
//
//
//import indi.nonoas.crm.bean.GoodsBean;
//import indi.nonoas.crm.dao.GoodsDao;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.*;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//
//import java.util.ArrayList;
//import java.util.regex.Pattern;
//
//
////TODO 商品消费类
//public class GoodsConsumeTable extends TableView<GoodsConsumeTable.Data> {
//
//    /**
//     * 数据源
//     */
//    private final ObservableList<Data> obList = FXCollections.observableArrayList();
//
//    private final ObservableList<TableColumn<Data, ?>> cols = getColumns();
//    /**
//     * 当前选中数据
//     */
//    private Data selectedBean;
//
//    private final TableColumn<Data, String> item_date = new TableColumn<>("订单日期");
//
//    private final TableColumn<Data, String> item_name = new TableColumn<>("商品名称");
//
//    private final TableColumn<Data, String> item_money_cost = new TableColumn<>("商品单价");
//
//    private final TableColumn<Data, String> item_discount = new TableColumn<>("折扣");
//
//    private final TableColumn<Data, Number> item_count = new TableColumn<>("数量");
//
//    private final TableColumn<Data, String> item_total = new TableColumn<>("小计");
//
//    public GoodsConsumeTable() {
//
//        initColumns();
//        setItems(obList);
//
//        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println(newValue);
//            selectedBean = newValue;
//        });
//    }
//
//
//    /**
//     *
//     */
//    private void initColumns() {
//
//        setTableMenuButtonVisible(true); // 显示表格菜单按钮
//
//        item_date.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGoods_id()));
//        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGoods_name()));
//        item_money_cost.setCellValueFactory(param -> {
//            double numMoney = param.getValue().getGoods_price();
//            String show = String.format("￥%.2f", numMoney);
//            return new SimpleStringProperty(show);
//        });
//
//        item_count.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getGoods_amount()));
//
//        item_discount.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getDiscount()));
//
//        item_count.setCellFactory(param -> new AmountCell());   //自定义数量单元格
//
//        item_total.setCellValueFactory(param -> {
//            String show = String.format("￥%.2f", param.getValue().getSum_price());
//            return new SimpleStringProperty(show);
//        });
//
//        item_count.setMinWidth(150);
//
//        //添加所有的列
//        cols.add(item_date);
//        cols.add(item_name);
//        cols.add(item_money_cost);
//        cols.add(item_discount);
//        cols.add(item_count);
//        cols.add(item_total);
//
//    }
//
//    /**
//     * 展示所有项目信息
//     */
//    public void showAllInfos(String id) {
//        clearData(); // 清空所有数据
////        ArrayList<GoodsBean> listPkgContentBeans = PackageContentDao.getInstance().selectById(id);
////        ArrayList<PackageContentEditTable.Data> listData = new ArrayList<>();
////        if (listPkgContentBeans != null) {
////            for (GoodsBean p : listPkgContentBeans) {
////                listData.add(beanToData(p));
////            }
////            obList.addAll(listData);
////        }
//    }
//
//    /**
//     * 清空数据源
//     */
//    public void clearData() {
//        obList.clear();
//    }
//
//    /**
//     * 添加不重复的数据
//     *
//     * @param bean 需要添加的数据
//     */
//    public void addBean(GoodsBean bean) {
//        boolean hasRepeat = false;
//        Data data = beanToData(bean); //类型转换
//        for (Data d : obList) {
//            String id1 = d.getGoods_id();
//            String id2 = data.getGoods_id();
//            hasRepeat = hasRepeat || id1.equals(id2);
//        }
//        if (!hasRepeat)
//            obList.add(data);
//    }
//
//    /**
//     * 获取选中的数据
//     *
//     * @return 选中的GoodsBean
//     */
//    public Data getSelectedData() {
//        return this.selectedBean;
//    }
//
//    /**
//     * 移除数据
//     *
//     * @param bean 需要移除的GoodsBean
//     */
//    public void removeData(PackageContentEditTable.Data bean) {
//        this.obList.remove(bean);
//        refresh();
//    }
//
//    /**
//     * 获取表格内所有商品信息
//     *
//     * @return GoodsBean的集合
//     */
//    public ArrayList<GoodsBean> getAllData() {
//        ArrayList<GoodsBean> GoodsBeans = new ArrayList<>();
//        for (Data d : obList) {
//            GoodsBeans.add(dataToBean(d));
//        }
//        return GoodsBeans;
//    }
//
//    /**
//     * 将bean类转换为数据模型
//     *
//     * @return Data类对象
//     */
//    private PackageContentEditTable.Data beanToData(GoodsBean bean) {
//        String id = bean.getGoods_id();
//        GoodsBean goodsBean = GoodsDao.getInstance().selectById(id);
//        String name = goodsBean.getName();
//        double price = goodsBean.getSell_price();
//        int amount = bean.getGoods_amount();
//        double sum = price * amount;
//
//        PackageContentEditTable.Data data = new PackageContentEditTable.Data();
//        data.setGoods_id(id);
//        data.setGoods_name(name);
//        data.setGoods_price(price);
//        data.setGoods_amount(amount);
//        return data;
//    }
//
//    /**
//     * 将数据模型转换为bean类
//     *
//     * @param data 表格数据模型
//     * @return GoodsBean对象
//     */
//    private GoodsBean dataToBean(Data data) {
//        GoodsBean GoodsBean = new GoodsBean();
////        GoodsBean.setGoods_id(data.getGoods_id());
////        GoodsBean.setGoods_amount(data.getGoods_amount());
//        return GoodsBean;
//    }
//
//
//    /**
//     * 数据模型类，用于和GoodsBean相互转换
//     */
//    static class Data {
//        private String goods_id;
//        private String goods_name;
//        private double goods_price;
//        private int goods_amount;
//        private double discount;
//
//        private double sum_price;
//
//        public String getGoods_id() {
//            return goods_id;
//        }
//
//        public String getGoods_name() {
//            return goods_name;
//        }
//
//        public double getGoods_price() {
//            return goods_price;
//        }
//
//        public int getGoods_amount() {
//            return goods_amount;
//        }
//
//        public double getDiscount() {
//            return discount;
//        }
//
//        public double getSum_price() {
//            return sum_price;
//        }
//
//        public void setGoods_id(String goods_id) {
//            this.goods_id = goods_id;
//        }
//
//        public void setGoods_name(String goods_name) {
//            this.goods_name = goods_name;
//        }
//
//        public void setGoods_price(double goods_price) {
//            this.goods_price = goods_price;
//            this.sum_price = goods_price * goods_amount;
//        }
//
//        public void setDiscount(double discount) {
//            this.discount = discount;
//        }
//
//        public void setGoods_amount(int goods_amount) {
//            this.goods_amount = goods_amount;
//            this.sum_price = goods_price * goods_amount;
//        }
//
//        @Override
//        public String toString() {
//            return "Data{" +
//                    "goods_id='" + goods_id + '\'' +
//                    ", goods_name='" + goods_name + '\'' +
//                    ", goods_price=" + goods_price +
//                    ", goods_amount=" + goods_amount +
//                    ", sum_price=" + sum_price +
//                    '}';
//        }
//
//
//    }
//
//    /**
//     * 自定义“数量”单元格
//     */
//    static class AmountCell extends TableCell<PackageContentEditTable.Data, Number> {
//
//        public AmountCell() {
//        }
//
//        //设置单元格样式
//        @Override
//        protected void updateItem(Number item, boolean empty) {
//
//            super.updateItem(item, empty);
//
//            if (!empty && item != null) {
//
//                HBox hBox = new HBox();
//                Button btn_add = new Button("+");
//                Button btn_reduce = new Button("-");
//                TextField tf_number = new TextField(item.toString());
//                //设置初始尺寸
//                btn_add.setPrefWidth(35);
//                btn_reduce.setPrefWidth(35);
//                tf_number.setPrefWidth(50);
//                //设置最小尺寸
//                btn_add.setMinWidth(35);
//                btn_reduce.setMinWidth(35);
//                tf_number.setMinWidth(50);
//
//                HBox.setHgrow(tf_number, Priority.ALWAYS);
//
//                hBox.getChildren().addAll(btn_add, tf_number, btn_reduce);
//                hBox.setSpacing(10);
//
//                ObservableList<PackageContentEditTable.Data> obList = getTableView().getItems(); //获取表格源数据
//                PackageContentEditTable.Data bean = obList.get(getIndex());
//
//                //设置按钮监听
//                //加一
//                btn_add.setOnAction(event -> {
//                    int amount = Integer.parseInt(tf_number.getText()) + 1;
//                    tf_number.setText(String.valueOf(amount));
//                });
//                //减一
//                btn_reduce.setOnAction(event -> {
//                    int amount = Integer.parseInt(tf_number.getText()) - 1;
//                    if (amount > 0) {
//                        tf_number.setText(String.valueOf(amount));
//                    }
//                });
//                //文本框变化监听
//                tf_number.textProperty().addListener((observable, oldValue, newValue) -> {
//                    String pattern = "^\\d+$";
//                    boolean isNumber = Pattern.matches(pattern, newValue); //判断是否为正整数
//                    if (isNumber) {
//                        bean.setGoods_amount(Integer.parseInt(newValue));
//                        getTableView().refresh();
//                    } else {
//                        tf_number.setText(oldValue);
//                    }
//                });
//
//                this.setGraphic(hBox);
//            }
//        }
//
//    }
//
//
//}
//
//
