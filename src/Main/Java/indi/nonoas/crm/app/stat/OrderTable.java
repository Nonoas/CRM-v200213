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

    private final TableColumn<OrderRecordVO, String> orderId = new TableColumn<>("订单号");
    private final TableColumn<OrderRecordVO, String> userId = new TableColumn<>("会员卡号");
    private final TableColumn<OrderRecordVO, String> userName = new TableColumn<>("会员姓名");
    private final TableColumn<OrderRecordVO, String> datetime = new TableColumn<>("消费日期");
    private final TableColumn<OrderRecordVO, Number> price = new TableColumn<>("订单金额");
    private final TableColumn<OrderRecordVO, Number> integralCost = new TableColumn<>("积分消耗");
    private final TableColumn<OrderRecordVO, Number> integralGet = new TableColumn<>("积分获取");
    private final TableColumn<OrderRecordVO, String> transactor = new TableColumn<>("受理人");
    private final TableColumn<OrderRecordVO, String> payMode = new TableColumn<>("支付方式");
    private final TableColumn<OrderRecordVO, String> content = new TableColumn<>("订单内容");


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
