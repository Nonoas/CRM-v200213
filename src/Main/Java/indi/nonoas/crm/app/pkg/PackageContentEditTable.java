package indi.nonoas.crm.app.pkg;

import indi.nonoas.crm.beans.GoodsBean;
import indi.nonoas.crm.beans.PackageContentBean;
import indi.nonoas.crm.dao.my_orm_dao.GoodsDao;
import indi.nonoas.crm.dao.PackageContentDao;
import indi.nonoas.crm.view.table.GoodsEditTable;
import indi.nonoas.crm.beans.vo.GoodsEditTableData;
import javafx.collections.ObservableList;

import java.util.ArrayList;


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
    private final ObservableList<GoodsEditTableData> obList;


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
        GoodsEditTableData data = beanToData(bean); //����ת��
        for (GoodsEditTableData d : obList) {
            String id1 = d.getId();
            String id2 = data.getId();
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
        for (GoodsEditTableData d : obList) {
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
    protected GoodsEditTableData beanToData(PackageContentBean bean) {
        String id = bean.getGoodsId();
        GoodsBean goodsBean = GoodsDao.getInstance().selectById(id);
        String name = goodsBean.getName();
        double price = goodsBean.getSellPrice();
        int amount = bean.getGoodsAmount();

        GoodsEditTableData data = new GoodsEditTableData();
        data.setId(id);
        data.setName(name);
        data.setPrice(price);
        data.setAmount(amount);
        return data;
    }

    /**
     * ������ģ��ת��Ϊbean��
     *
     * @param data �������ģ��
     * @return PackageContentBean����
     */
    @Override
    protected PackageContentBean dataToBean(GoodsEditTableData data) {
        PackageContentBean packageContentBean = new PackageContentBean();
        packageContentBean.setGoodsId(data.getId());
        packageContentBean.setGoodsAmount(data.getAmount());
        return packageContentBean;
    }

    /**
     * չʾ������Ŀ��Ϣ
     */
    public void showAllInfos(String id) {
        clearData(); // �����������
        ArrayList<PackageContentBean> listPkgContentBeans = PackageContentDao.getInstance().selectById(id);
        ArrayList<GoodsEditTableData> listData = new ArrayList<>();
        if (listPkgContentBeans != null) {
            for (PackageContentBean p : listPkgContentBeans) {
                listData.add(beanToData(p));
            }
            obList.addAll(listData);
        }
    }

}




