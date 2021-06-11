package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.app.pkg.PackageAddTab;
import indi.nonoas.crm.app.pkg.PackageContentTable;
import indi.nonoas.crm.app.pkg.PackageModifyTab;
import indi.nonoas.crm.app.pkg.PackageTable;
import indi.nonoas.crm.controller.MainController;
import indi.nonoas.crm.dao.my_orm_dao.PackageContentDao;
import indi.nonoas.crm.dao.my_orm_dao.PackageDao;
import indi.nonoas.crm.pojo.PackageDto;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.view.alert.MyAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PackageInfoController implements Initializable {

    private final PackageService pkgService = (PackageService) SpringUtil.getBean("PackageServiceImpl");

    @FXML
    private TextField tf_pkgID;

    @FXML
    private TextField tf_moneyLow;

    @FXML
    private TextField tf_moneyHigh;

    @FXML
    private ScrollPane sp_package;

    @FXML
    private ScrollPane sp_pkg_content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPkgView();
    }

    private final PackageContentTable pkgContTable = new PackageContentTable(); //�ײ���Ʒ���

    private final PackageTable pkgTable = new PackageTable(pkgContTable);   //�ײͱ��

    @FXML
    private void findPackage() {
        String id = "%" + tf_pkgID.getText().trim() + "%";
        String money1 = tf_moneyLow.getText().trim();
        String money2 = tf_moneyHigh.getText().trim();
        double mLow = "".equals(money1) ? 0 : Double.parseDouble(money1);
        double mHigh = "".equals(money2) ? 99999999 : Double.parseDouble(money2);

        ArrayList<PackageDto> list = PackageDao.getInstance().findByFilter(id, id, mLow, mHigh);
        if (list == null) {
            new MyAlert(Alert.AlertType.INFORMATION, "û���ҵ�����ѯ����Ŀ��Ϣ��").show();
        } else {
            pkgTable.clearData();
            for (PackageDto bean : list)
                pkgTable.addBean(bean);
        }
    }

    @FXML
    private void deletePackage() {
        PackageDto bean = pkgTable.getSelectedData();
        if (bean == null) {
            new MyAlert(Alert.AlertType.INFORMATION, "����ѡ��һ�����ݣ�").show();
            return;
        }
        String id = "���:" + bean.getId();
        String name = "����:" + bean.getName();
        MyAlert alert = new MyAlert(Alert.AlertType.CONFIRMATION, "�Ƿ�ȷ��ɾ������Ŀ����Ϣ��\n(" + id + "��" + name + ")");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            pkgTable.removeData(bean);
            pkgService.deleteById(bean.getId());
            PackageContentDao.getInstance().deleteById(bean.getId());
        }
    }

    @FXML
    private void addPackage() {
        MainController.addTab(new PackageAddTab());
    }

    /**
     * �޸��ײ���Ϣ
     */
    @FXML
    private void modifyPackage() {
        MainController.addTab(new PackageModifyTab(pkgTable.getSelectedData()));
    }

    /**
     * ��ʼ���ײͽ���
     */
    private void initPkgView() {
        // ��ʼ���������
        sp_package.setContent(pkgTable);
        sp_pkg_content.setContent(pkgContTable);
    }


}
