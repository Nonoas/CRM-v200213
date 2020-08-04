package indi.nonoas.crm.app.goods;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.view.table.GoodsEditTable;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-08-02 14:11
 */
public class GoodsConsumeTable extends GoodsEditTable<GoodsBean> {

    private final ObservableList<Data> obList = getItems();

    public GoodsConsumeTable() {
        super();
        setTableMenuButtonVisible(false);
    }

    @Override
    public ArrayList<GoodsBean> getAllBeans() {
        if (obList == null)
            return null;
        ArrayList<GoodsBean> goodsBeans = new ArrayList<>();
        for (Data d : obList) {
            goodsBeans.add(dataToBean(d));
        }
        return goodsBeans;
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

        getEventHandler().execute();
    }

    @Override
    protected GoodsBean dataToBean(Data data) {
        String id = data.getGoods_id();
        return GoodsDao.getInstance().selectById(id);
    }

    @Override
    protected Data beanToData(GoodsBean bean) {
        Data data = new Data();
        data.setGoods_id(bean.getId());
        data.setGoods_name(bean.getName());
        data.setGoods_price(bean.getSell_price());
        data.setGoods_amount(1);
        return data;
    }

    /**
     * 获取表格内商品的总价格
     *
     * @return 表格内商品总价
     */
    public double getSumPrice() {
        double price = 0;
        for (Data d : obList) {
            price += d.getSum_price();
        }
        System.out.println("总价："+price);
        return price;
    }

}
