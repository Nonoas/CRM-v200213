package indi.nonoas.crm.app.goods;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.view.table.GoodsEditTable;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-08-02 14:11
 */
public class GoodsConsumeTable extends GoodsEditTable<GoodsBean> {

    private final ObservableList<Data> obList = getItems();

    public GoodsConsumeTable() {
        super();
    }

    @Override
    public ArrayList<GoodsBean> getAllBeans() {
        return null;
    }

    @Override
    public void addBean(GoodsBean bean) {
        Data data = new Data();
        String id = bean.getId();
        for (Data d : obList) {
            if (d.getGoods_id().equals(id))
                return;
        }
        data.setGoods_id(id);
        data.setGoods_name(bean.getName());
        data.setGoods_price(bean.getSell_price());
        data.setGoods_amount(1);
        obList.add(data);
        refresh();
    }

    @Override
    protected GoodsBean dataToBean(Data data) {
        return null;
    }

    @Override
    protected Data beanToData(GoodsBean bean) {
        return null;
    }
}
