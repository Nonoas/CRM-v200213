package indi.nonoas.crm.view.pkg;

import indi.nonoas.crm.dao.my_orm_dao.PackageContentDao;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.PackageContentBean;
import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.table.GoodsEditTable;
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
    private final ObservableList<GoodsEditTableVO> obList;

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");


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
        GoodsEditTableVO data = beanToData(bean); //����ת��
        for (GoodsEditTableVO d : obList) {
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
        for (GoodsEditTableVO d : obList) {
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
    protected GoodsEditTableVO beanToData(PackageContentBean bean) {
        String id = bean.getGoodsId();
        GoodsDto goodsBean = goodsService.selectById(id);
        String name = goodsBean.getName();
        double price = goodsBean.getSellPrice();
        int amount = bean.getGoodsAmount();

        GoodsEditTableVO data = new GoodsEditTableVO();
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
    protected PackageContentBean dataToBean(GoodsEditTableVO data) {
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
        ArrayList<GoodsEditTableVO> listData = new ArrayList<>();
        if (listPkgContentBeans != null) {
            for (PackageContentBean p : listPkgContentBeans) {
                listData.add(beanToData(p));
            }
            obList.addAll(listData);
        }
    }

}




