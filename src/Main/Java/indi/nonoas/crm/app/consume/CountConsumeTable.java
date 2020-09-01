package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.beans.GoodsBean;
import indi.nonoas.crm.beans.UserGoods;
import indi.nonoas.crm.dao.my_orm_dao.GoodsDao;
import indi.nonoas.crm.view.table.GoodsEditTable;
import indi.nonoas.crm.beans.vo.GoodsEditTableData;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * 计次消费表格
 *
 * @author : Nonoas
 * @time : 2020-08-07 12:31
 */
public class CountConsumeTable extends GoodsEditTable<UserGoods> {

    private final ObservableList<GoodsEditTableData> obList = getItems();

    public CountConsumeTable() {
        item_total.setVisible(false);
        item_money_cost.setVisible(false);
        setTableMenuButtonVisible(false);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }

    @Override
    public ArrayList<UserGoods> getAllBeans() {
        return null;
    }

    @Override
    public void addBean(UserGoods bean) {

        for (GoodsEditTableData data : obList) {
            if (data.getId().equals(bean.getGoodsId()))
                return;
        }

        obList.add(beanToData(bean));
        refresh();
    }

    @Override
    protected UserGoods dataToBean(GoodsEditTableData data) {
        return null;
    }

    @Override
    protected GoodsEditTableData beanToData(UserGoods bean) {
        GoodsEditTableData data = new GoodsEditTableData();
        GoodsBean goodsBean = GoodsDao.getInstance().selectById(bean.getGoodsId());
        data.setId(bean.getGoodsId());
        data.setName(goodsBean.getName());
        data.setAmount(1);
        return data;
    }
}
