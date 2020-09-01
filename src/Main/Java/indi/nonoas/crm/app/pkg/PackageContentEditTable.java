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
 * 可以编辑内容的“套餐内容”表格
 *
 * @author: Nonoas
 * @Date: 2020/4/4 18:19
 * @Description: 可以编辑内容的“套餐内容”表格
 */
public class PackageContentEditTable extends GoodsEditTable<PackageContentBean> {
    /**
     * 数据源
     */
    private final ObservableList<GoodsEditTableData> obList;


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
    public void addBean(PackageContentBean bean) {
        boolean hasRepeat = false;
        GoodsEditTableData data = beanToData(bean); //类型转换
        for (GoodsEditTableData d : obList) {
            String id1 = d.getId();
            String id2 = data.getId();
            hasRepeat = hasRepeat || id1.equals(id2);
        }
        if (!hasRepeat)
            obList.add(data);
    }

    /**
     * 获取表格内所有商品信息
     *
     * @return PackageContentBean的集合
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
     * 将bean类转换为数据模型
     *
     * @return Data类对象
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
     * 将数据模型转换为bean类
     *
     * @param data 表格数据模型
     * @return PackageContentBean对象
     */
    @Override
    protected PackageContentBean dataToBean(GoodsEditTableData data) {
        PackageContentBean packageContentBean = new PackageContentBean();
        packageContentBean.setGoodsId(data.getId());
        packageContentBean.setGoodsAmount(data.getAmount());
        return packageContentBean;
    }

    /**
     * 展示所有项目信息
     */
    public void showAllInfos(String id) {
        clearData(); // 清空所有数据
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




