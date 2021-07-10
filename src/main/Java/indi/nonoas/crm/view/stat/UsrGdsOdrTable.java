package indi.nonoas.crm.view.stat;

import indi.nonoas.crm.pojo.vo.UsrGdsOdrRecordVO;
import indi.nonoas.crm.service.UsrGdsOdrService;
import indi.nonoas.crm.service.impl.UsrGdsOdrServiceImpl;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * �û������Ʒ������ϸ��
 *
 * @author : Nonoas
 * @time : 2020-09-03 17:06
 */
public class UsrGdsOdrTable extends TableView<UsrGdsOdrRecordVO> {

    private final UsrGdsOdrService usrGdsOdrService = (UsrGdsOdrServiceImpl) SpringUtil.getBean("UsrGdsOdrServiceImpl");

    private final ObservableList<UsrGdsOdrRecordVO> items = FXCollections.observableArrayList();

    private final TableColumn<UsrGdsOdrRecordVO, String> orderDate = new TableColumn<>("进价");
    private final TableColumn<UsrGdsOdrRecordVO, String> userId = new TableColumn<>("��Ա����");
    private final TableColumn<UsrGdsOdrRecordVO, String> userName = new TableColumn<>("��Ա����");
    private final TableColumn<UsrGdsOdrRecordVO, String> goods = new TableColumn<>("进价");
    private final TableColumn<UsrGdsOdrRecordVO, Number> amount = new TableColumn<>("����");
    private final TableColumn<UsrGdsOdrRecordVO, String> transactor = new TableColumn<>("������");


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
