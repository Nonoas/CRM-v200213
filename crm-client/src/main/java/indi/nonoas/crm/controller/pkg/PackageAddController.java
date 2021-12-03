package indi.nonoas.crm.controller.pkg;

import indi.nonoas.crm.pojo.PackageContentDto;
import indi.nonoas.crm.pojo.PackageDto;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.utils.SpringUtil;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PackageAddController extends PackageController {

    //��ǰtab������
    private Tab parentTab;

    private final PackageService pkgService = (PackageService) SpringUtil.getBean("PackageServiceImpl");

    @Override
    protected void initView() {
        sp_goods.setContent(pkgGoodsTable);
        cb_pkgType.setValue("产品类");
    }

    /**
     * 取消添加
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
        PackageDto packageBean = new PackageDto();
        packageBean.setId(tf_id.getText());
        packageBean.setName(tf_name.getText());
        packageBean.setMoneyCost(Double.parseDouble(tf_money.getText()));
        packageBean.setIntegralCost(Integer.parseInt(tf_integral.getText()));
        String minDiscount = tf_min_discount.getText();
        if (!minDiscount.equals("")) {
            packageBean.setMinDiscount(Double.parseDouble(minDiscount));
        }
        packageBean.setOther(tf_other.getText());

        //�ײ�������Ϣ
        List<PackageContentDto> packageContents = pkgGoodsTable.getAllBeans();
        for (PackageContentDto p : packageContents) {
            p.setPkgId(packageBean.getId());
        }
        //�����ײ���Ϣ�����ݿ�
        pkgService.insert(packageBean, packageContents);

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
