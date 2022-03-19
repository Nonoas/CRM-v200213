package indi.nonoas.crm.view.consume;

import indi.nonoas.crm.component.table.GoodsEditTable;
import indi.nonoas.crm.pojo.PackageDto;
import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.utils.SpringUtil;
import java.util.List;
import javafx.collections.ObservableList;

/**
 * @author : Nonoas
 * @time : 2020-08-05 12:52
 */
public class PackageConsumeTable extends GoodsEditTable<PackageDto> {

    private final ObservableList<GoodsEditTableVO> obList = getItems();

    private final PackageService pkgService = (PackageService) SpringUtil.getBean("PackageServiceImpl");

    public PackageConsumeTable() {
        item_id.setText("套餐编号");
        item_name.setText("套餐名称");
        setTableMenuButtonVisible(false);
    }

    @Override
    public List<PackageDto> getAllBeans() {
        return null;
    }

    @Override
    public void addBean(PackageDto bean) {
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
    protected PackageDto dataToBean(GoodsEditTableVO data) {
        return pkgService.selectById(data.getId());
    }

    @Override
    protected GoodsEditTableVO beanToData(PackageDto bean) {
        GoodsEditTableVO data = new GoodsEditTableVO();
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
        for (GoodsEditTableVO d : obList) {
            price += d.getSum_price();
        }
        System.out.println("�ܼۣ�" + price);
        return price;
    }


}
