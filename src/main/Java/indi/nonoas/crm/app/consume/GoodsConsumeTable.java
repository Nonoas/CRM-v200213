package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.pojo.GoodsBean;
import indi.nonoas.crm.dao.my_orm_dao.GoodsDao;
import indi.nonoas.crm.view.table.GoodsEditTable;
import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author : Nonoas
 * @time : 2020-08-02 14:11
 */
public class GoodsConsumeTable extends GoodsEditTable<GoodsBean> {

    private final ObservableList<GoodsEditTableVO> obList = getItems();

    public GoodsConsumeTable() {
        super();
        setTableMenuButtonVisible(false);
    }

    @Override
    public ArrayList<GoodsBean> getAllBeans() {
        if (obList == null)
            return null;
        return (ArrayList<GoodsBean>) obList.stream()
                .map(this::dataToBean)
                .collect(Collectors.toList());

    }

    @Override
    public void addBean(GoodsBean bean) {
        String id = bean.getId();
        for (GoodsEditTableVO d : obList) {
            if (d.getId().equals(id))
                return;
        }
        GoodsEditTableVO data = beanToData(bean);
        obList.add(data);
        refresh();

        getEventHandler().execute();
    }

    @Override
    protected GoodsBean dataToBean(GoodsEditTableVO data) {
        String id = data.getId();
        return GoodsDao.getInstance().selectById(id);
    }

    @Override
    protected GoodsEditTableVO beanToData(GoodsBean bean) {
        GoodsEditTableVO data = new GoodsEditTableVO();
        data.setId(bean.getId());
        data.setName(bean.getName());
        data.setPrice(bean.getSellPrice());
        data.setAmount(1);
        return data;
    }

    /**
     * 获取表格内商品的总价格
     *
     * @return 表格内商品总价
     */
    public double getSumPrice() {
        double price = 0;
        for (GoodsEditTableVO d : obList) {
            price += d.getSum_price();
        }
        return price;
    }

}
