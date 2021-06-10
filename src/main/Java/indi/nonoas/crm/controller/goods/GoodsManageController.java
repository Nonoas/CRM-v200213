package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.app.goods.GoodsAddTab;
import indi.nonoas.crm.app.goods.GoodsInfoTable;
import indi.nonoas.crm.app.goods.GoodsModifyTab;
import indi.nonoas.crm.app.pkg.PackageAddTab;
import indi.nonoas.crm.app.pkg.PackageContentTable;
import indi.nonoas.crm.app.pkg.PackageModifyTab;
import indi.nonoas.crm.app.pkg.PackageTable;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.PackageBean;
import indi.nonoas.crm.dao.my_orm_dao.PackageContentDao;
import indi.nonoas.crm.dao.my_orm_dao.PackageDao;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.view.alert.MyAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GoodsManageController implements Initializable {

    private PackageBean packageBean;

    @FXML
    private TabPane tp_rootPane; // ������

    @FXML
    private TextField tf_pkgID;
    @FXML
    private TextField tf_moneyLow;
    @FXML
    private TextField tf_moneyHigh;

    @FXML
    private ScrollPane sp_package; // ��Ŀ����Pane
    @FXML
    private ScrollPane sp_pkg_content; // ��Ŀ���ݵ�Pane

    public GoodsManageController() {
        initData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPkgView();
    }


    /**
     * ��ʼ���ײͽ���
     */
    private void initPkgView() {
        // ��ʼ���������
        sp_package.setContent(pkgTable);
        sp_pkg_content.setContent(pkgContTable);
    }

    private void initData() {

    }



    //===========================================================================
    //                            �ײ���Ŀ
    //===========================================================================

    private final PackageContentTable pkgContTable = new PackageContentTable(); //�ײ���Ʒ���

    private final PackageTable pkgTable = new PackageTable(pkgContTable);   //�ײͱ��

    @FXML
    private void findPackage() {
        String id = "%" + tf_pkgID.getText().trim() + "%";
        String money1 = tf_moneyLow.getText().trim();
        String money2 = tf_moneyHigh.getText().trim();
        double mLow = money1.equals("") ? 0 : Double.parseDouble(money1);
        double mHigh = money2.equals("") ? 99999999 : Double.parseDouble(money2);

        ArrayList<PackageBean> list = PackageDao.getInstance().findByFilter(id, id, mLow, mHigh);
        if (list == null) {
            new MyAlert(AlertType.INFORMATION, "û���ҵ�����ѯ����Ŀ��Ϣ��").show();
        } else {
            pkgTable.clearData();
            for (PackageBean bean : list)
                pkgTable.addBean(bean);
        }
    }

    @FXML
    private void deletePackage() {
        PackageBean bean = pkgTable.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "����ѡ��һ�����ݣ�").show();
            return;
        }
        String id = "���:" + bean.getId();
        String name = "����:" + bean.getName();
        MyAlert alert = new MyAlert(AlertType.CONFIRMATION, "�Ƿ�ȷ��ɾ������Ŀ����Ϣ��\n(" + id + "��" + name + ")");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            pkgTable.removeData(bean);
            PackageDao.getInstance().deleteByID(bean);
            PackageContentDao.getInstance().deleteById(bean.getId());
        }
    }

    @FXML
    private void addPackage() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        // �����жϸ�Tab�Ƿ��Ѿ����,�������ӣ���ֱ���л�����Tab
        final String DATA = "�����Ŀ";
        for (Tab tab : obList) {
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // ����Ѿ��������ʾ��tab������
                return;
            }
        }

        PackageAddTab tab = new PackageAddTab();
        tab.setUserData(DATA);
        tp_rootPane.getTabs().add(tab);
        tp_rootPane.getSelectionModel().select(tab);

    }

    //�޸��ײ���Ϣ
    @FXML
    private void modifyPackage() {
        PackageBean bean = pkgTable.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "����ѡ��һ���ײ���Ŀ��Ϣ��").show();
            return;
        }

        ObservableList<Tab> obList = tp_rootPane.getTabs();
        // �����жϸ�Tab�Ƿ��Ѿ����,�������ӣ���ֱ���л�����Tab
        final String DATA = "�޸���Ŀ��Ϣ";
        for (Tab tab : obList) {
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // ����Ѿ��������ʾ��tab������
                return;
            }
        }

        PackageModifyTab tab = new PackageModifyTab(pkgTable.getSelectedData());
        tab.setUserData(DATA);
        obList.add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }
}
