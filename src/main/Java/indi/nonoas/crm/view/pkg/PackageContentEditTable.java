package indi.nonoas.crm.view.pkg;

import indi.nonoas.crm.dao.my_orm_dao.PackageContentDao;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.PackageContentDto;
import indi.nonoas.crm.pojo.vo.GoodsEditTableVO;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.table.GoodsEditTable;
import javafx.collections.ObservableList;

import java.util.ArrayList;


/**
 * 可以编辑内容的“套餐内容”表格
 *
 * @author: Nonoas
 * @Date: 2020/4/4 18:19
 * @Description: 可以编辑内容的“套餐内容”表格
 */
public class PackageContentEditTable extends GoodsEditTable<PackageContentDto> {
    /**
     * 数据源
     */
    private final ObservableList<GoodsEditTableVO> obList;

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");


    public PackageContentEditTable() {
        super();
        obList = getItems();
    }

    /**
     * 添加不重复的数据
     *
     * @param bean 需要添加的数据
     */
    @Override
    public void addBean(PackageContentDto bean) {
        boolean hasRepeat = false;
        GoodsEditTableVO data = beanToData(bean); //类型转换
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
     * 获取表格内所有商品信息
     *
     * @return PackageContentBean的集合
     */
    @Override
    public ArrayList<PackageContentDto> getAllBeans() {
        ArrayList<PackageContentDto> packageContents = new ArrayList<>();
        for (GoodsEditTableVO d : obList) {
            packageContents.add(dataToBean(d));
        }
        return packageContents;
    }

    /**
     * 将bean类转换为数据模型
     *
     * @return Data类对象
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
     * 将数据模型转换为bean类
     *
     * @param data 表格数据模型
     * @return PackageContentBean对象
     */
    @Override
    protected PackageContentDto dataToBean(GoodsEditTableVO data) {
        PackageContentDto packageContentDto = new PackageContentDto();
        packageContentDto.setGoodsId(data.getId());
        packageContentDto.setGoodsAmount(data.getAmount());
        return packageContentDto;
    }

    /**
     * 展示所有项目信息
     */
    public void showAllInfos(String id) {
        clearData(); // 清空所有数据
        ArrayList<PackageContentDto> listPkgContentBeans = PackageContentDao.getInstance().selectById(id);
        ArrayList<GoodsEditTableVO> listData = new ArrayList<>();
        if (listPkgContentBeans != null) {
            for (PackageContentDto p : listPkgContentBeans) {
                listData.add(beanToData(p));
            }
            obList.addAll(listData);
        }
    }

}




