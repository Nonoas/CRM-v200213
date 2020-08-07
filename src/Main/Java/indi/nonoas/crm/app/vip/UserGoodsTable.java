package indi.nonoas.crm.app.vip;

import indi.nonoas.crm.beans.UserGoods;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * @author : Nonoas
 * @time : 2020-08-07 15:45
 */
public class UserGoodsTable extends TableView<UserGoodsTable.Data> {

    private final ObservableList<Data> obList = FXCollections.observableArrayList();
    private final ObservableList<TableColumn<Data, ?>> columns = getColumns();

    private final TableColumn<Data, String> col_goodsID = new TableColumn<>("商品编号");
    private final TableColumn<Data, String> col_goodsName = new TableColumn<>("商品名称");
    private final TableColumn<Data, Number> col_amount = new TableColumn<>("剩余数量");
    private final TableColumn<Data, String> col_type = new TableColumn<>("商品类型");


    public UserGoodsTable() {
        initColumn();
    }

    public void addBean(UserGoods bean) {
        //TODO
    }



    @SuppressWarnings("unchecked")
    private void initColumn() {
        col_goodsID.setCellValueFactory(param -> param.getValue().idProperty());
        col_goodsName.setCellValueFactory(param -> param.getValue().nameProperty());
        col_amount.setCellValueFactory(param -> param.getValue().amountProperty());
        col_type.setCellValueFactory(param -> param.getValue().typeProperty());

        columns.addAll(col_goodsID, col_goodsName, col_amount, col_type);
    }


    //===========================================================================
    //                            内部类
    //===========================================================================

    protected static class Data {

        private final SimpleStringProperty id = new SimpleStringProperty();
        private final SimpleStringProperty name = new SimpleStringProperty();
        private final SimpleIntegerProperty amount = new SimpleIntegerProperty();
        private final SimpleStringProperty type = new SimpleStringProperty();

        public SimpleStringProperty idProperty() {
            return this.id;
        }

        public SimpleStringProperty nameProperty() {
            return this.name;
        }

        public SimpleIntegerProperty amountProperty() {
            return this.amount;
        }

        public SimpleStringProperty typeProperty() {
            return this.type;
        }

        public String getId() {
            return id.get();
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public int getAmount() {
            return amount.get();
        }

        public void setAmount(int amount) {
            this.amount.set(amount);
        }

        public String getType() {
            return type.get();
        }

        public void setType(String type) {
            this.type.set(type);
        }
    }
}
