package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.beans.PackageBean;
import indi.nonoas.crm.dao.PackageDao;
import indi.nonoas.crm.view.table.GoodsEditTable;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-08-05 12:52
 */
public class PackageConsumeTable extends GoodsEditTable<PackageBean> {

    private final ObservableList<Data> obList = getItems();

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
        for (Data d : obList) {
            if (d.getId().equals(id))
                return;
        }
        Data data = beanToData(bean);
        obList.add(data);
        refresh();

        getEventHandler().execute();
    }

    @Override
    protected PackageBean dataToBean(Data data) {
        return PackageDao.getInstance().selectById(data.getId());
    }

    @Override
    protected Data beanToData(PackageBean bean) {
        Data data = new Data();
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
        for (Data d : obList) {
            price += d.getSum_price();
        }
        System.out.println("�ܼۣ�" + price);
        return price;
    }


}
