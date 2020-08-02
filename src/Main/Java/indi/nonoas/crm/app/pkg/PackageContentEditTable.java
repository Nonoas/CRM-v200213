package indi.nonoas.crm.app.pkg;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.bean.PackageContentBean;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.dao.PackageContentDao;
import indi.nonoas.crm.view.table.GoodsEditTable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * ���Ա༭���ݵġ��ײ����ݡ����
 *
 * @author: Nonoas
 * @Date: 2020/4/4 18:19
 * @Description: ���Ա༭���ݵġ��ײ����ݡ����
 */
public class PackageContentEditTable extends GoodsEditTable<PackageContentBean> {
    /**
     * ����Դ
     */
    private final ObservableList<Data> obList;


    public PackageContentEditTable() {
        super();
        obList = getItems();
    }

    /**
     * ��Ӳ��ظ�������
     *
     * @param bean ��Ҫ��ӵ�����
     */
    @Override
    public void addBean(PackageContentBean bean) {
        boolean hasRepeat = false;
        Data data = beanToData(bean); //����ת��
        for (Data d : obList) {
            String id1 = d.getGoods_id();
            String id2 = data.getGoods_id();
            hasRepeat = hasRepeat || id1.equals(id2);
        }
        if (!hasRepeat)
            obList.add(data);
    }

    /**
     * ��ȡ�����������Ʒ��Ϣ
     *
     * @return PackageContentBean�ļ���
     */
    @Override
    public ArrayList<PackageContentBean> getAllBeans() {
        ArrayList<PackageContentBean> packageContentBeans = new ArrayList<>();
        for (Data d : obList) {
            packageContentBeans.add(dataToBean(d));
        }
        return packageContentBeans;
    }

    /**
     * ��bean��ת��Ϊ����ģ��
     *
     * @return Data�����
     */
    @Override
    protected Data beanToData(PackageContentBean bean) {
        String id = bean.getGoods_id();
        GoodsBean goodsBean = GoodsDao.getInstance().selectById(id);
        String name = goodsBean.getName();
        double price = goodsBean.getSell_price();
        int amount = bean.getGoods_amount();

        Data data = new Data();
        data.setGoods_id(id);
        data.setGoods_name(name);
        data.setGoods_price(price);
        data.setGoods_amount(amount);
        return data;
    }

    /**
     * ������ģ��ת��Ϊbean��
     *
     * @param data �������ģ��
     * @return PackageContentBean����
     */
    @Override
    protected PackageContentBean dataToBean(Data data) {
        PackageContentBean packageContentBean = new PackageContentBean();
        packageContentBean.setGoods_id(data.getGoods_id());
        packageContentBean.setGoods_amount(data.getGoods_amount());
        return packageContentBean;
    }

    /**
     * չʾ������Ŀ��Ϣ
     */
    public void showAllInfos(String id) {
        clearData(); // �����������
        ArrayList<PackageContentBean> listPkgContentBeans = PackageContentDao.getInstance().selectById(id);
        ArrayList<Data> listData = new ArrayList<>();
        if (listPkgContentBeans != null) {
            for (PackageContentBean p : listPkgContentBeans) {
                listData.add(beanToData(p));
            }
            obList.addAll(listData);
        }
    }

}




