package indi.nonoas.crm.view.goods;

import indi.nonoas.crm.component.table.ConditionTable;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 商品信息表
 *
 * @author Nonoas
 */
public class GoodsInfoTable extends ConditionTable<GoodsDto> {

    private final static String MENU_CODE = "GoodsInfo";

    private final ObservableList<GoodsDto> obList = FXCollections.observableArrayList();

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
     * �������
     *
     * @param bean Ҫ��ӵ���Ʒ
     */
    public void addBean(GoodsDto bean) {
        obList.add(bean);
    }

    /**
     * �������
     *
     * @param beans Ҫ��ӵ���Ʒ����
     */
    public void addAllBeans(List<GoodsDto> beans) {
        obList.addAll(beans);
    }

}
