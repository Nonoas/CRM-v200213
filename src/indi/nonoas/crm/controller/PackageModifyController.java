package indi.nonoas.crm.controller;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.bean.PackageBean;
import indi.nonoas.crm.bean.PackageContentBean;
import indi.nonoas.crm.dao.PackageContentDao;
import indi.nonoas.crm.dao.PackageDao;
import indi.nonoas.crm.dialog.GoodsSelectDialog;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;

public class PackageModifyController extends PackageController {

    private Tab parentTab;

    /**
     * 界面初始化
     */
    protected void initView() {
        sp_goods.setContent(pkgGoodsTable);
        tf_id.setEditable(false);
    }

    /**
     * 取消当前填写的信息
     */
    @FXML
    private void cancelInfo() {

        if (chc_isClose.isSelected()) { // 如果选择了提交后关闭，则关闭当前tab
            TabPane tabPane = parentTab.getTabPane();
            tabPane.getTabs().remove(parentTab);
        }

        tf_id.setText("");
        tf_integral.setText("");
        tf_money.setText("");
        tf_name.setText("");
        tf_min_discount.setText("");
        tf_other.setText("");

    }


    /**
     * 提交信息
     */
    @FXML
    private void commitIfo() {

        if (hasEmpty())  //检查是否有未填写的必填项目
            return;

        //套餐信息
        PackageBean packageBean = getPackageBean();
        //插入套餐信息到数据库
        PackageDao.getInstance().update(packageBean);

        //删除之前的商品内容
        PackageContentDao packageContentDao = PackageContentDao.getInstance();
        packageContentDao.deleteById(packageBean.getId());

        //重新提交商品内容
        ArrayList<PackageContentBean> packageContentBeans = getPackageContentBeans(packageBean.getId());
        packageContentDao.insertInfos(packageContentBeans);

        if (chc_isClose.isSelected()) { // 如果选择了提交后关闭，则关闭当前tab
            TabPane tabPane = parentTab.getTabPane();
            tabPane.getTabs().remove(parentTab);
        }
    }

    /**
     * 传递当前tab的引用
     */
    public void setPane(Tab tab) {
        this.parentTab = tab;
    }

    /**
     * 通过界面内的信息生成商品信息对象
     *
     * @return PackageBean实例
     */
    private PackageBean getPackageBean() {
        PackageBean packageBean = new PackageBean();
        packageBean.setId(tf_id.getText());
        packageBean.setName(tf_name.getText());
        packageBean.setMoney_cost(Double.parseDouble(tf_money.getText()));
        packageBean.setIntegral_cost(Integer.parseInt(tf_integral.getText()));
        String minDiscount = tf_min_discount.getText();
        if (!minDiscount.equals("")) {
            packageBean.setMin_discount(Double.parseDouble(minDiscount));
        }
        packageBean.setOther(tf_other.getText());
        return packageBean;
    }

    /**
     * 获取套餐内商品信息列表
     *
     * @return PackageContentBean集合
     */
    private ArrayList<PackageContentBean> getPackageContentBeans(String PkgID) {
        ArrayList<PackageContentBean> packageContentBeans = pkgGoodsTable.getAllData();
        for (PackageContentBean p : packageContentBeans) {
            p.setPkg_id(PkgID);
        }
        return packageContentBeans;
    }

    /**
     * 设置需要修改的套餐信息，并填入对应文本框
     *
     * @param packageBean 需要修改的PackageBean实例
     */
    public void setBean(PackageBean packageBean) {
        String id = packageBean.getId();
        tf_id.setText(id);
        tf_name.setText(packageBean.getName());
        tf_integral.setText(String.valueOf(packageBean.getIntegral_cost()));
        tf_money.setText(String.valueOf(packageBean.getMoney_cost()));
        tf_min_discount.setText(String.valueOf(packageBean.getMin_discount()));
        tf_other.setText(packageBean.getOther());
        pkgGoodsTable.showAllInfos(id);
    }
}
