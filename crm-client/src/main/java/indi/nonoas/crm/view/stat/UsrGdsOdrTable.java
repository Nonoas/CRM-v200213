package indi.nonoas.crm.view.stat;

import indi.nonoas.crm.pojo.vo.UsrGdsOdrRecordVO;
import indi.nonoas.crm.service.UsrGdsOdrService;
import indi.nonoas.crm.service.impl.UsrGdsOdrServiceImpl;
import indi.nonoas.crm.utils.SpringUtil;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 用户库存商品消费明细表
 *
 * @author : Nonoas
 * @time : 2020-09-03 17:06
 */
public class UsrGdsOdrTable extends TableView<UsrGdsOdrRecordVO> {

    private final UsrGdsOdrService usrGdsOdrService = (UsrGdsOdrServiceImpl) SpringUtil.getBean("UsrGdsOdrServiceImpl");

    private final ObservableList<UsrGdsOdrRecordVO> items = FXCollections.observableArrayList();

    private final TableColumn<UsrGdsOdrRecordVO, String> orderDate = new TableColumn<>("订单日期");
    private final TableColumn<UsrGdsOdrRecordVO, String> userId = new TableColumn<>("会员卡号");
    private final TableColumn<UsrGdsOdrRecordVO, String> userName = new TableColumn<>("会员姓名");
    private final TableColumn<UsrGdsOdrRecordVO, String> goods = new TableColumn<>("订单内容");
    private final TableColumn<UsrGdsOdrRecordVO, Number> amount = new TableColumn<>("数量");
    private final TableColumn<UsrGdsOdrRecordVO, String> transactor = new TableColumn<>("受理人");


    public UsrGdsOdrTable() {
        setItems(items);
        initColumns();
        showAllData();
    }

    private void initColumns() {

        orderDate.setCellValueFactory(param -> param.getValue().orderDateProperty());
        userId.setCellValueFactory(param -> param.getValue().userIdProperty());
        userName.setCellValueFactory(param -> param.getValue().userNameProperty());
        goods.setCellValueFactory(param -> param.getValue().goodsProperty());
        amount.setCellValueFactory(param -> param.getValue().amountProperty());
        transactor.setCellValueFactory(param -> param.getValue().transactorProperty());

        ObservableList<TableColumn<UsrGdsOdrRecordVO, ?>> cols = getColumns();
        cols.add(orderDate);
        cols.add(userId);
        cols.add(userName);
        cols.add(goods);
        cols.add(amount);
        cols.add(transactor);

    }

    public void showAllData() {
        items.clear();
        List<UsrGdsOdrRecordVO> vos = usrGdsOdrService.selectUserGoodsOrder();
        items.addAll(vos);
    }
}
