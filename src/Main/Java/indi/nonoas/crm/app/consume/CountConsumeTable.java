package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.beans.GoodsBean;
import indi.nonoas.crm.view.table.GoodsEditTable;
import indi.nonoas.crm.view.table.GoodsEditTableData;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * 计次消费表格
 *
 * @author : Nonoas
 * @time : 2020-08-07 12:31
 */
public class CountConsumeTable extends GoodsEditTable<GoodsBean> {

    private final ObservableList<GoodsEditTableData> obList = getItems();

    public CountConsumeTable() {
        item_total.setVisible(false);
        item_money_cost.setVisible(false);
        setTableMenuButtonVisible(false);
    }

    @Override
    public ArrayList<GoodsBean> getAllBeans() {
        return null;
    }

    @Override
    public void addBean(GoodsBean bean) {

    }

    @Override
    protected GoodsBean dataToBean(GoodsEditTableData data) {
        return null;
    }

    @Override
    protected GoodsEditTableData beanToData(GoodsBean bean) {
        return null;
    }
}
