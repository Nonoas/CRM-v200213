package indi.nonoas.crm.view.stat;

import indi.nonoas.crm.pojo.OrderDetailBean;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.vo.OrderRecordVO;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.service.OrderService;
import indi.nonoas.crm.utils.SpringUtil;
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

    private final OrderService orderService = (OrderService) SpringUtil.getBean("OrderServiceImpl");

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

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
        content.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getContent()));
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
        List<OrderRecordVO> vos = orderService.selectGdsOrds();
        if (vos == null)
            return;

        for (OrderRecordVO vo : vos) {
            List<OrderDetailBean> details = orderService.selectByOrder(vo.getOrderId());
            StringBuilder sb = new StringBuilder();
            for (int i = 0, size = details.size(); i < size; i++) {
                OrderDetailBean detail = details.get(i);
                GoodsDto goods = goodsService.selectById(detail.getProductId());
                if (goods != null)
                    sb.append(goods.getName()).append(detail.getProductAmount()).append(goods.getBaseUnit());
                if (i < size - 1)
                    sb.append("，");
            }
            vo.setContent(sb.toString());
        }
        items.addAll(vos);
    }

}
