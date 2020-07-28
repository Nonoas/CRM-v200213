package indi.nonoas.crm.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.app.table.VipInfoTable;
import indi.nonoas.crm.app.vip.VipAddTab;
import indi.nonoas.crm.bean.VipBean;
import indi.nonoas.crm.dao.VipInfoDao;
import indi.nonoas.crm.dao.VipLevelDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class ConsumeController implements Initializable {

    /**
     * ��Ա��ϢDAO
     */
    private final VipInfoDao vipInfoDao = VipInfoDao.getInstence();
    @FXML
    private TabPane tp_rootPane;
    @FXML
    private Label lb_discountType;
    @FXML
    private Label lb_frequency;
    @FXML
    private Label lb_cardState;
    @FXML
    private Label lb_cumulative;
    @FXML
    private Label lb_id;
    @FXML
    private Label lb_integral;
    @FXML
    private Label lb_name;
    @FXML
    private Label lb_cardLevel;
    @FXML
    private TextField tf_find;
    @FXML
    private TextField tf_findInfo;
    @FXML
    private ScrollPane sp_userInfo;
    @FXML
    private ComboBox<String> cb_disType;

    private final VipInfoTable tv_vipInfo = new VipInfoTable(); // ��Ա��Ϣ��

    public ConsumeController() {
        initData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    @FXML // ������Ϣ
    private void inquireVIP() {
        String str = tf_find.getText().trim();
        if (str.equals(""))
            return;
        VipBean vipBean = vipInfoDao.getInfoByIdOrName(str, str);
        if (vipBean != null) {
            lb_frequency.setText(String.valueOf(vipBean.getFrequency()));
            lb_cardState.setText("����");
            lb_cumulative.setText("��" + vipBean.getCumulative());
            lb_id.setText(vipBean.getId());
            lb_integral.setText(String.valueOf(vipBean.getIntegral()));
            lb_cardLevel.setText(vipBean.getCard_level());
            lb_name.setText(vipBean.getName());
        } else {
            new MyAlert(AlertType.INFORMATION, "û���ҵ�����ѯ�Ļ�Ա��").show();
        }
    }

    @FXML
    private void inquireVIPInfo() {
        String str = "%" + tf_findInfo.getText().trim() + "%";
        String str0 = cb_disType.getValue().equals("ȫ������") ? "" : cb_disType.getValue();
        String disType = str0 + "%";
        if (str.equals("%%"))
            return;
        ArrayList<VipBean> listVipBeans = vipInfoDao.selectByFiltrate(str, str, disType);
        if (listVipBeans != null) {
            tv_vipInfo.clearData();
            for (VipBean bean : listVipBeans)
                tv_vipInfo.addBean(bean);
        } else {
            new MyAlert(AlertType.INFORMATION, "û���ҵ�����ѯ�Ļ�Ա��").show();
        }
    }

    @FXML // ��ʾȫ����Ϣ
    private void showAll() {
        tv_vipInfo.showAllInfos();
    }

    @FXML // ���չʾ����Ϣ
    private void clearInfo() {
        tf_find.setText("");
        lb_discountType.setText("--");
        lb_frequency.setText("--");
        lb_cardState.setText("--");
        lb_cumulative.setText("--");
        lb_id.setText("--");
        lb_integral.setText("--");
        lb_cardLevel.setText("--");
        lb_name.setText("--");
    }

    @FXML // ��ӻ�Ա��Ϣ
    private void addVip() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "��ӻ�Ա";
        for (Tab tab : obList) { // �����жϸ�tab�Ƿ��Ѿ����
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tab.setClosable(true);
                tp_rootPane.getSelectionModel().select(tab); // ����Ѿ��������ʾ��tab������
                return;
            }

        }
        VipAddTab tab = new VipAddTab();
        tab.setUserData(DATA);
        tp_rootPane.getTabs().add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

    private void initData() {
    }

    private void initView() {
        tv_vipInfo.showAllInfos();
        sp_userInfo.setContent(tv_vipInfo);
        // �����ݿ�������û�Ա�ȼ�������ʼ��ComboBox
        LinkedList<String> listName = new VipLevelDao().selectAllNames();
        cb_disType.getItems().add("ȫ������");
        for (String str : listName) {
            cb_disType.getItems().add(str);
        }
        cb_disType.setValue("ȫ������");
    }

    ////////////////////////��Ʒ����///////////////////////////


}
