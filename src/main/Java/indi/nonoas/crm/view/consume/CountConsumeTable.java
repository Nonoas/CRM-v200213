package indi.nonoas.crm.view.consume;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.UserGoods;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.table.GoodsEditTable;
import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * 计次消费表格
 *
 * @author : Nonoas
 * @time : 2020-08-07 12:31
 */
public class CountConsumeTable extends GoodsEditTable<UserGoods> {

    private final ObservableList<GoodsEditTableVO> obList = getItems();

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

    public CountConsumeTable() {
        item_total.setVisible(false);
        item_money_cost.setVisible(false);
        setTableMenuButtonVisible(false);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }

    @Override
    public ArrayList<UserGoods> getAllBeans() {
        return null;
    }

    @Override
    public void addBean(UserGoods bean) {
        for (GoodsEditTableVO data : obList) {
            if (data.getId().equals(bean.getGoodsId()))
                return;
        }
        obList.add(beanToData(bean));
        refresh();
    }

    @Override
    protected UserGoods dataToBean(GoodsEditTableVO data) {
        return null;
    }

    @Override
    protected GoodsEditTableVO beanToData(UserGoods bean) {
        GoodsEditTableVO data = new GoodsEditTableVO();
        GoodsDto goodsBean = goodsService.selectById(bean.getGoodsId());
        data.setId(bean.getGoodsId());
        data.setName(goodsBean.getName());
        data.setAmount(1);
        return data;
    }
}
