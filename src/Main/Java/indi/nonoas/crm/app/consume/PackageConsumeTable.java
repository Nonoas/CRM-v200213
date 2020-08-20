package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.beans.PackageBean;
import indi.nonoas.crm.dao.PackageDao;
import indi.nonoas.crm.view.table.GoodsEditTable;
import indi.nonoas.crm.beans.vo.GoodsEditTableData;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-08-05 12:52
 */
public class PackageConsumeTable extends GoodsEditTable<PackageBean> {

    private final ObservableList<GoodsEditTableData> obList = getItems();

    public PackageConsumeTable() {
        item_id.setText("�ײͱ��");
        item_name.setText("�ײ�����");
        setTableMenuButtonVisible(false);
    }

    @Override
    public ArrayList<PackageBean> getAllBeans() {
        return null;
    }

    @Override
    public void addBean(PackageBean bean) {
        String id = bean.getId();
        for (GoodsEditTableData d : obList) {
            if (d.getId().equals(id))
                return;
        }
        GoodsEditTableData data = beanToData(bean);
        obList.add(data);
        refresh();

        getEventHandler().execute();
    }

    @Override
    protected PackageBean dataToBean(GoodsEditTableData data) {
        return PackageDao.getInstance().selectById(data.getId());
    }

    @Override
    protected GoodsEditTableData beanToData(PackageBean bean) {
        GoodsEditTableData data = new GoodsEditTableData();
        data.setId(bean.getId());
        data.setName(bean.getName());
        data.setPrice(bean.getMoneyCost());
        data.setAmount(1);
        return data;
    }

    /**
     * ��ȡ������ײ͵��ܼ۸�
     *
     * @return ������ײ��ܼ�
     */
    public double getSumPrice() {
        double price = 0;
        for (GoodsEditTableData d : obList) {
            price += d.getSum_price();
        }
        System.out.println("�ܼۣ�" + price);
        return price;
    }


}
