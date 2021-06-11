package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.pojo.PackageDto;
import indi.nonoas.crm.pojo.PackageContentBean;
import indi.nonoas.crm.dao.my_orm_dao.PackageContentDao;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;

public class PackageModifyController extends PackageController {

    private Tab parentTab;

    private final PackageService pkgService = (PackageService) SpringUtil.getBean("PackageServiceImpl");

    /**
     * �����ʼ��
     */
    protected void initView() {
        sp_goods.setContent(pkgGoodsTable);
        tf_id.setEditable(false);
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
        PackageDto packageBean = getPackageBean();
        //�����ײ���Ϣ�����ݿ�
        pkgService.update(packageBean);

        //ɾ��֮ǰ����Ʒ����
        PackageContentDao packageContentDao = PackageContentDao.getInstance();
        packageContentDao.deleteById(packageBean.getId());

        //�����ύ��Ʒ����
        ArrayList<PackageContentBean> packageContentBeans = getPackageContentBeans(packageBean.getId());
        packageContentDao.insertInfos(packageContentBeans);

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

    /**
     * ͨ�������ڵ���Ϣ������Ʒ��Ϣ����
     *
     * @return PackageBeanʵ��
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
    private ArrayList<PackageContentBean> getPackageContentBeans(String PkgID) {
        ArrayList<PackageContentBean> packageContentBeans = pkgGoodsTable.getAllBeans();
        for (PackageContentBean p : packageContentBeans) {
            p.setPkgId(PkgID);
        }
        return packageContentBeans;
    }

    /**
     * ������Ҫ�޸ĵ��ײ���Ϣ���������Ӧ�ı���
     *
     * @param packageBean ��Ҫ�޸ĵ�PackageBeanʵ��
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
