package indi.nonoas.crm.controller;

import indi.nonoas.crm.app.GoodsAddTab;
import indi.nonoas.crm.app.GoodsModifyTab;
import indi.nonoas.crm.app.PackageAddTab;
import indi.nonoas.crm.app.PackageModifyTab;
import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.bean.PackageBean;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.dao.GoodsTypeDao;
import indi.nonoas.crm.dao.PackageDao;
import indi.nonoas.crm.dialog.MyAlert;
import indi.nonoas.crm.table.GoodsInfoTable;
import indi.nonoas.crm.table.PackageContentTable;
import indi.nonoas.crm.table.PackageTable;
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

    private final GoodsInfoTable table = new GoodsInfoTable(); // ��Ʒ��Ϣ��

    private final GoodsDao goodsDao = GoodsDao.getInstance();

    private PackageBean packageBean;
    @FXML
    private ScrollPane scrollPane; // ���ĸ�����
    @FXML
    private TextField tf_id;
    @FXML
    private TabPane tp_rootPane; // ������
    @FXML
    private ComboBox<String> cb_type; // ��Ʒ����

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
        initView();
        initPkgView();
    }

    /**
     * ��ʼ������
     */
    private void initView() {
        // ��ʼ�����
        scrollPane.setContent(table);

        // ��ʼ������Ʒ���ࡱ�����б��
        LinkedList<String> goodsTypes = new GoodsTypeDao().selectAllNames();
        cb_type.getItems().add("��������");
        if (goodsTypes != null) {
            for (String str : goodsTypes)
                cb_type.getItems().add(str);
        }
        cb_type.setValue("��������");
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

    ////////////// ��Ʒ���� ////////////////////

    @FXML    //��ѯ��Ʒ
    private void find() {
        String str = "%" + tf_id.getText().trim() + "%";
        String str0 = cb_type.getValue().equals("��������") ? "" : cb_type.getValue();
        String type = "%" + str0 + "%";
        ArrayList<GoodsBean> listVipBeans = goodsDao.selectByFiltrate(str, str, type);
        if (listVipBeans != null) {
            table.clearData();
            for (GoodsBean bean : listVipBeans)
                table.addBean(bean);
        } else {
            new MyAlert(AlertType.INFORMATION, "û���ҵ�����ѯ����Ʒ��").show();
        }
    }

    @FXML    //�޸���Ʒ��Ϣ
    private void updateInfo() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "�޸���Ʒ��Ϣ";
        for (Tab tab : obList) { // �����жϸ�tab�Ƿ��Ѿ����
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // ����Ѿ��������ʾ��tab������
                return;
            }
        }
        Tab tab = new GoodsModifyTab(table.getSelectedData());
        tab.setUserData(DATA);
        obList.add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

    @FXML    //�����Ʒ�¼�
    private void addGoods() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "�����Ʒ";
        for (Tab tab : obList) { // �����жϸ�tab�Ƿ��Ѿ����
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // ����Ѿ��������ʾ��tab������
                return;
            }

        }
        Tab tab = new GoodsAddTab();
        tab.setUserData(DATA);
        obList.add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

    @FXML    //ɾ����Ʒ��Ϣ
    private void deleteInfo() {
        GoodsBean bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "����ѡ��һ�����ݣ�").show();
            return;
        }
        String id = "���:" + bean.getId();
        String name = "����:" + bean.getName();
        MyAlert alert = new MyAlert(AlertType.CONFIRMATION, "�Ƿ�ȷ��ɾ������Ʒ����Ϣ��\n(" + id + "��" + name + ")");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.removeData(bean);
            goodsDao.deleteByID(bean);
        }
    }


    ///////////////////  �ײ���Ŀ /////////////////////////

    private final PackageContentTable pkgContTable = new PackageContentTable(); //�ײ���Ʒ���

    private final PackageTable pkgTable = new PackageTable(pkgContTable);   //�ײͱ��

    @FXML
    private void findPackage() {
        String id = "%" + tf_pkgID.getText().trim() + "%";
        String money1 = tf_moneyLow.getText().trim();
        String money2 = tf_moneyHigh.getText().trim();
        double mLow = money1.equals("") ? 0 : Double.parseDouble(money1);
        double mHigh = money2.equals("") ? 9999999 : Double.parseDouble(money2);

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
