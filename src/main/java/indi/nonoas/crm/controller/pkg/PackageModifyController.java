package indi.nonoas.crm.controller.pkg;

import indi.nonoas.crm.pojo.PackageDto;
import indi.nonoas.crm.pojo.PackageContentDto;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.List;

public class PackageModifyController extends PackageController {

    private Tab parentTab;

    private final PackageService pkgService = (PackageService) SpringUtil.getBean("PackageServiceImpl");

    /**
     * 初始化视图
     */
    protected void initView() {
        sp_goods.setContent(pkgGoodsTable);
        tf_id.setEditable(false);
    }

    @FXML
    private void cancelInfo() {

        if (chc_isClose.isSelected()) {
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
     * 提交
     */
    @FXML
    private void commitIfo() {
        if (hasEmpty()) {
            return;
        }
        // 获取套餐信息
        PackageDto packageBean = getPackageBean();
        // 获取套餐内商品信息
        List<PackageContentDto> pkgContents = getPackageContentBeans(packageBean.getId());

        pkgService.update(packageBean, pkgContents);

        // 判断是否关闭当前窗口
        if (chc_isClose.isSelected()) {
            TabPane tabPane = parentTab.getTabPane();
            tabPane.getTabs().remove(parentTab);
        }
    }

    public void setPane(Tab tab) {
        this.parentTab = tab;
    }

    /**
     * 根据表单生成套餐数据
     * @return 用于插入数据库的套餐数据
     */
    private PackageDto getPackageBean() {
        PackageDto packageBean = new PackageDto();
        packageBean.setId(tf_id.getText());
        packageBean.setName(tf_name.getText());
        packageBean.setMoneyCost(Double.parseDouble(tf_money.getText()));
        packageBean.setIntegralCost(Integer.parseInt(tf_integral.getText()));
        String minDiscount = tf_min_discount.getText();
        if (!minDiscount.equals("")) {
            packageBean.setMinDiscount(Double.parseDouble(minDiscount));
        }
        packageBean.setType(cb_pkgType.getValue());
        packageBean.setOther(tf_other.getText());
        return packageBean;
    }

    /**
     * ��ȡ�ײ�����Ʒ��Ϣ�б�
     *
     * @return PackageContentBean����
     */
    private List<PackageContentDto> getPackageContentBeans(String PkgID) {
        List<PackageContentDto> packageContentDtos = pkgGoodsTable.getAllBeans();
        for (PackageContentDto p : packageContentDtos) {
            p.setPkgId(PkgID);
        }
        return packageContentDtos;
    }

    /**
     * 初始化需要修改的套餐信息
     * @param packageBean 套餐信息
     */
    public void setBean(PackageDto packageBean) {
        String id = packageBean.getId();
        tf_id.setText(id);
        tf_name.setText(packageBean.getName());
        tf_integral.setText(String.valueOf(packageBean.getIntegralCost()));
        tf_money.setText(String.valueOf(packageBean.getMoneyCost()));
        tf_min_discount.setText(String.valueOf(packageBean.getMinDiscount()));
        tf_other.setText(packageBean.getOther());
        pkgGoodsTable.showAllInfos(id);
        cb_pkgType.setValue(packageBean.getType());
    }
}
