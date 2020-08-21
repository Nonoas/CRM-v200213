package indi.nonoas.crm.app.stat;

import indi.nonoas.crm.beans.vo.OrderRecordVO;
import indi.nonoas.crm.dao.OrderRecordVODao;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;


/**
 * @author : Nonoas
 * @time : 2020-08-21 20:37
 */
public class OrderTable extends TableView<OrderRecordVO> {

    private final ObservableList<OrderRecordVO> items = FXCollections.observableArrayList();

    private final TableColumn<OrderRecordVO, String> orderId = new TableColumn<>("������");
    private final TableColumn<OrderRecordVO, String> userId = new TableColumn<>("��Ա����");
    private final TableColumn<OrderRecordVO, String> userName = new TableColumn<>("��Ա����");
    private final TableColumn<OrderRecordVO, String> datetime = new TableColumn<>("��������");
    private final TableColumn<OrderRecordVO, Number> price = new TableColumn<>("�������");
    private final TableColumn<OrderRecordVO, Number> integralCost = new TableColumn<>("��������");
    private final TableColumn<OrderRecordVO, Number> integralGet = new TableColumn<>("���ֻ�ȡ");
    private final TableColumn<OrderRecordVO, String> transactor = new TableColumn<>("������");
    private final TableColumn<OrderRecordVO, String> payMode = new TableColumn<>("֧����ʽ");
    private final TableColumn<OrderRecordVO, String> content = new TableColumn<>("��������");


    public OrderTable() {
        setItems(items);
        initColumns();
        showAllData();
    }

    private void initColumns() {

        orderId.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrderId()));
        userId.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUserId()));
        userName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUserName()));
        datetime.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDatetime()));
        price.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getPrice()));
        integralCost.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIntegralCost()));
        integralGet.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIntegralGet()));
        transactor.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTransactor()));
        payMode.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPayMode()));

        ObservableList<TableColumn<OrderRecordVO, ?>> cols = getColumns();
        cols.add(orderId);
        cols.add(userId);
        cols.add(userName);
        cols.add(datetime);
        cols.add(content);
        cols.add(price);
        cols.add(integralCost);
        cols.add(integralGet);
        cols.add(transactor);
        cols.add(payMode);

    }

    public void showAllData() {
        items.clear();
        List<OrderRecordVO> vos = OrderRecordVODao.getInstance().selectAll();
        items.addAll(vos);
    }

}
