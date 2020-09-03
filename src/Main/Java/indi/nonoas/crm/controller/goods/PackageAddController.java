package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.beans.PackageBean;
import indi.nonoas.crm.dao.PackageContentDao;
import indi.nonoas.crm.dao.my_orm_dao.PackageDao;
import indi.nonoas.crm.beans.PackageContentBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class PackageAddController extends PackageController {

    private Tab parentTab;  //��ǰtab������

    @Override
    protected void initView() {
        sp_goods.setContent(pkgGoodsTable);
        cb_pkgType.setValue("��Ʒ��");
    }

    /**
     * ȡ����ǰ��д����Ϣ
     */
    @FXML
    private void cancelInfo() {

        if (chc_isClose.isSelected()) { // ���ѡ�����ύ��رգ���رյ�ǰtab
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
     * �ύ��Ϣ
     */
    @FXML
    private void commitIfo() {

        if (hasEmpty())  //����Ƿ���δ��д�ı�����Ŀ
            return;

        //�ײ���Ϣ
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
        //�����ײ���Ϣ�����ݿ�
        PackageDao.getInstance().insert(packageBean);
        //�ײ�������Ϣ
        ArrayList<PackageContentBean> packageContentBeans = pkgGoodsTable.getAllBeans();
        for (PackageContentBean p : packageContentBeans) {
            p.setPkgId(packageBean.getId());
        }
        PackageContentDao.getInstance().insertInfos(packageContentBeans);

        if (chc_isClose.isSelected()) { // ���ѡ�����ύ��رգ���رյ�ǰtab
            TabPane tabPane = parentTab.getTabPane();
            tabPane.getTabs().remove(parentTab);
        }
    }


    /**
     * ���ݵ�ǰtab������
     */
    public void setPane(Tab tab) {
        this.parentTab = tab;
    }

}
