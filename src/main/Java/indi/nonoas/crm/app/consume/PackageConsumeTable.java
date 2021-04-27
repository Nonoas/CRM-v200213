package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.pojo.PackageBean;
import indi.nonoas.crm.dao.my_orm_dao.PackageDao;
import indi.nonoas.crm.view.table.GoodsEditTable;
import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-08-05 12:52
 */
public class PackageConsumeTable extends GoodsEditTable<PackageBean> {

    private final ObservableList<GoodsEditTableVO> obList = getItems();

    public PackageConsumeTable() {
        item_id.setText("套餐编号");
        item_name.setText("套餐名称");
        setTableMenuButtonVisible(false);
    }

    @Override
    public ArrayList<PackageBean> getAllBeans() {
        return null;
    }

    @Override
    public void addBean(PackageBean bean) {
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
    protected PackageBean dataToBean(GoodsEditTableVO data) {
        return PackageDao.getInstance().selectById(data.getId());
    }

    @Override
    protected GoodsEditTableVO beanToData(PackageBean bean) {
        GoodsEditTableVO data = new GoodsEditTableVO();
        data.setId(bean.getId());
        data.setName(bean.getName());
        data.setPrice(bean.getMoneyCost());
        data.setAmount(1);
        return data;
    }

    /**
     * 获取表格内套餐的总价格
     *
     * @return 表格内套餐总价
     */
    public double getSumPrice() {
        double price = 0;
        for (GoodsEditTableVO d : obList) {
            price += d.getSum_price();
        }
        System.out.println("总价：" + price);
        return price;
    }


}
