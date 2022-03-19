package indi.nonoas.crm.view.goods;

import indi.nonoas.crm.component.table.ConditionTable;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品信息表
 *
 * @author Nonoas
 */
public class GoodsInfoTable extends ConditionTable<GoodsDto> {

    private final static String MENU_CODE = "GoodsInfo";

    private final ObservableList<GoodsDto> obList = getItems();

    public GoodsInfoTable(List<GoodsDto> items) {
        super(MENU_CODE, items, GoodsDto.class);
        setItems(obList);
        showAllInfos();
    }

    /**
     * 展示所有商品信息
     */
    public void showAllInfos() {
        clearData();
        GoodsService service = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");
        ArrayList<GoodsDto> listVipBeans = service.selectAll();
        if (listVipBeans != null)
            obList.addAll(listVipBeans);
    }

    /**
     * 添加一条数据
     */
    public void addBean(GoodsDto bean) {
        obList.add(bean);
    }

    /**
     * 添加一组数据
     */
    public void addAllBeans(List<GoodsDto> beans) {
        obList.addAll(beans);
    }

}
