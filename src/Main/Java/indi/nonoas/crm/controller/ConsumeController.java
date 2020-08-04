package indi.nonoas.crm.controller;

import indi.nonoas.crm.app.goods.GoodsConsumeTable;
import indi.nonoas.crm.app.goods.GoodsSingleSelectTable;
import indi.nonoas.crm.app.vip.VipAddTab;
import indi.nonoas.crm.app.vip.VipInfoTable;
import indi.nonoas.crm.bean.VipBean;
import indi.nonoas.crm.dao.VipInfoDao;
import indi.nonoas.crm.dao.VipLevelDao;
import indi.nonoas.crm.view.alert.MyAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ConsumeController implements Initializable {

    /**
     * ��Ա��ϢDAO
     */
    private final VipInfoDao vipInfoDao = VipInfoDao.getInstence();
    @FXML
    private TabPane tp_rootPane;
    @FXML
    private Label lb_cardState;
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
            lb_cardState.setText("����");
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
        lb_cardState.setText("--");
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
        pt_borderPane.setCenter(gc_table);
        pt_sp_goods.setContent(goodsSelectTable);

        //���ñ��ļ����¼�
        gc_table.getEventHandler().addEvent(() -> {
            if (tf_orderNum.getText().equals("")) {
                //TODO ���ɶ����ŵķ�����Ҫ����ʵ��
                tf_orderNum.setText("SP131660614");
            }
            if (tf_orderDate.getText().equals("")) {
                tf_orderDate.setText(String.valueOf(LocalDateTime.now()));

            }
            pt_order_price.setText(String.format("%.2f", gc_table.getSumPrice()));
            pt_order_dis_price.setText("0.00");
            pt_integral.setText("0");

        });
    }

    //===========================================================================
    //                             ��ͨ����
    //===========================================================================

    private final GoodsConsumeTable gc_table = new GoodsConsumeTable();

    private final GoodsSingleSelectTable goodsSelectTable = new GoodsSingleSelectTable(gc_table);
    @FXML
    private BorderPane pt_borderPane;
    @FXML
    private ScrollPane pt_sp_goods;
    @FXML
    private Label pt_order_price;
    @FXML
    private Label pt_order_dis_price;
    @FXML
    private Label pt_integral;
    @FXML
    private TextField tf_orderNum;
    @FXML
    private TextField tf_orderDate;

    @FXML
    private void clearGoodsTable() {
        gc_table.clearData();
    }


    //===========================================================================
    //                            �ײ�����
    //===========================================================================

}
