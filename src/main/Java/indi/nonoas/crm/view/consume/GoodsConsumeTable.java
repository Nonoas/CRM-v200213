package indi.nonoas.crm.view.consume;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.table.GoodsEditTable;
import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author : Nonoas
 * @time : 2020-08-02 14:11
 */
public class GoodsConsumeTable extends GoodsEditTable<GoodsDto> {

    private final ObservableList<GoodsEditTableVO> obList = getItems();

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

    public GoodsConsumeTable() {
        super();
        setTableMenuButtonVisible(false);
    }

    @Override
    public ArrayList<GoodsDto> getAllBeans() {
        if (obList == null)
            return null;
        return (ArrayList<GoodsDto>) obList.stream()
                .map(this::dataToBean)
                .collect(Collectors.toList());

    }

    @Override
    public void addBean(GoodsDto bean) {
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
    protected GoodsDto dataToBean(GoodsEditTableVO data) {
        String id = data.getId();
        return goodsService.selectById(id);
    }

    @Override
    protected GoodsEditTableVO beanToData(GoodsDto bean) {
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
