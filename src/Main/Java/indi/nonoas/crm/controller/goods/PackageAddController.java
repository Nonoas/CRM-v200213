package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.beans.PackageBean;
import indi.nonoas.crm.dao.PackageContentDao;
import indi.nonoas.crm.dao.my_orm_dao.PackageDao;
import indi.nonoas.crm.beans.PackageContentBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class PackageAddController extends PackageController {

    private Tab parentTab;  //当前tab的引用

    @Override
    protected void initView() {
        sp_goods.setContent(pkgGoodsTable);
        cb_pkgType.setValue("产品类");
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
        PackageBean packageBean = new PackageBean();
        packageBean.setId(tf_id.getText());
        packageBean.setName(tf_name.getText());
        packageBean.setMoneyCost(Double.parseDouble(tf_money.getText()));
        packageBean.setIntegralCost(Integer.parseInt(tf_integral.getText()));
        String minDiscount = tf_min_discount.getText();
        if (!minDiscount.equals("")) {
            packageBean.setMinDiscount(Double.parseDouble(minDiscount));
        }
        packageBean.setOther(tf_other.getText());
        //插入套餐信息到数据库
        PackageDao.getInstance().insert(packageBean);
        //套餐内容信息
        ArrayList<PackageContentBean> packageContentBeans = pkgGoodsTable.getAllBeans();
        for (PackageContentBean p : packageContentBeans) {
            p.setPkgId(packageBean.getId());
        }
        PackageContentDao.getInstance().insertInfos(packageContentBeans);

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

}
