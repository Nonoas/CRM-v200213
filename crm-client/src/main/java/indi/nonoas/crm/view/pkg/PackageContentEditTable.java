package indi.nonoas.crm.view.pkg;

import indi.nonoas.crm.component.table.GoodsEditTable;
import indi.nonoas.crm.pojo.PackageContentDto;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


/**
 * ���Ա༭���ݵġ��ײ����ݡ����
 *
 * @author: Nonoas
 * @Date: 2020/4/4 18:19
 * @Description: ���Ա༭���ݵġ��ײ����ݡ����
 */
public class PackageContentEditTable extends GoodsEditTable<PackageContentDto> {
    /**
     * ����Դ
     */
    private final ObservableList<GoodsEditTableVO> obList;

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");
    private final PackageService pkgService = (PackageService) SpringUtil.getBean("PackageServiceImpl");


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
    public void addBean(PackageContentDto bean) {
        boolean hasRepeat = false;
        GoodsEditTableVO data = beanToData(bean); //����ת��
        for (GoodsEditTableVO d : obList) {
            String id1 = d.getId();
            String id2 = data.getId();
            hasRepeat = hasRepeat || id1.equals(id2);
        }
        if (!hasRepeat) {
            obList.add(data);
        }
//        refresh();
    }

    /**
     * ��ȡ进价���Ʒ��Ϣ
     *
     * @return PackageContentBean�ļ���
     */
    @Override
    public List<PackageContentDto> getAllBeans() {
        List<PackageContentDto> packageContents = new ArrayList<>();
        for (GoodsEditTableVO d : obList) {
            packageContents.add(dataToBean(d));
        }
        return packageContents;
    }

    /**
     * ��bean��ת��Ϊ����ģ��
     *
     * @return Data�����
     */
    @Override
    protected GoodsEditTableVO beanToData(PackageContentDto bean) {
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
    protected PackageContentDto dataToBean(GoodsEditTableVO data) {
        PackageContentDto packageContentDto = new PackageContentDto();
        packageContentDto.setGoodsId(data.getId());
        packageContentDto.setGoodsAmount(data.getAmount());
        return packageContentDto;
    }

    /**
     * չʾ������Ŀ��Ϣ
     */
    public void showAllInfos(String id) {
        clearData(); // 进价���
        List<PackageContentDto> listPkgContentBeans = pkgService.listPkgContentByPkgId(id);
        ArrayList<GoodsEditTableVO> listData = new ArrayList<>();
        if (listPkgContentBeans != null) {
            for (PackageContentDto p : listPkgContentBeans) {
                listData.add(beanToData(p));
            }
            obList.addAll(listData);
        }
    }

}




