package indi.nonoas.crm.app.pkg;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.bean.PackageContentBean;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.dao.PackageContentDao;
import indi.nonoas.crm.view.table.GoodsEditTable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.regex.Pattern;


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
    private final ObservableList<Data> obList;


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
        Data data = beanToData(bean); //类型转换
        for (Data d : obList) {
            String id1 = d.getGoods_id();
            String id2 = data.getGoods_id();
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
        for (Data d : obList) {
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
    protected Data beanToData(PackageContentBean bean) {
        String id = bean.getGoods_id();
        GoodsBean goodsBean = GoodsDao.getInstance().selectById(id);
        String name = goodsBean.getName();
        double price = goodsBean.getSell_price();
        int amount = bean.getGoods_amount();

        Data data = new Data();
        data.setGoods_id(id);
        data.setGoods_name(name);
        data.setGoods_price(price);
        data.setGoods_amount(amount);
        return data;
    }

    /**
     * 将数据模型转换为bean类
     *
     * @param data 表格数据模型
     * @return PackageContentBean对象
     */
    @Override
    protected PackageContentBean dataToBean(Data data) {
        PackageContentBean packageContentBean = new PackageContentBean();
        packageContentBean.setGoods_id(data.getGoods_id());
        packageContentBean.setGoods_amount(data.getGoods_amount());
        return packageContentBean;
    }

    /**
     * 展示所有项目信息
     */
    public void showAllInfos(String id) {
        clearData(); // 清空所有数据
        ArrayList<PackageContentBean> listPkgContentBeans = PackageContentDao.getInstance().selectById(id);
        ArrayList<Data> listData = new ArrayList<>();
        if (listPkgContentBeans != null) {
            for (PackageContentBean p : listPkgContentBeans) {
                listData.add(beanToData(p));
            }
            obList.addAll(listData);
        }
    }

}




