package indi.nonoas.crm.controller.vip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import indi.nonoas.crm.utils.JXLUtil;
import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.app.vip.VipAddTab;
import indi.nonoas.crm.app.vip.VipInfoTable;
import indi.nonoas.crm.app.vip.VipModifyTab;
import indi.nonoas.crm.bean.VipBean;
import indi.nonoas.crm.dao.VipInfoDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class VipManageController implements Initializable {

    /**
     * ��Ա��ϢDAO
     */
    private final VipInfoDao vipInfoDao = VipInfoDao.getInstence();

    private VipInfoTable table;

    @FXML
    private Tab tab_manage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TabPane tp_rootPane;
    @FXML
    private TextField tf_findInfo;
    @FXML
    private ComboBox<String> cbb_level;    //��Ա�ȼ�
    @FXML
    private DatePicker dpk_from;    //���ʱ�䷶Χ����ʼ��
    @FXML
    private DatePicker dpk_to;    //���ʱ�䷶Χ��������

    public VipManageController() {
        initData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    /**
     * ��ʼ������
     */
    private void initView() {
        table = new VipInfoTable();
        scrollPane.setContent(table);

        cbb_level.getItems().addAll("���еȼ�", "��ͨ��Ա", "������Ա");
        cbb_level.setValue("���еȼ�");

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String str_from = "1900-01-01";
        LocalDate from = LocalDate.parse(str_from, df);
        LocalDate to = LocalDate.now();
        dpk_from.setValue(from);    //���ʱ������
        dpk_to.setValue(to);    //���ʱ������

//        		FadeTransition ft = new FadeTransition();
//        		ft.setNode(tp_rootPane);
//        		ft.setDuration(Duration.seconds(1));
//        		ft.setFromValue(0);
//        		ft.setToValue(1);
//        		ft.setAutoReverse(true);
//        		ft.play();
    }

    private void initData() {

    }

    @FXML
    private void findVIP() {

        String idOrName = "%" + tf_findInfo.getText().trim() + "%";    //���Ż�����
        String str0 = cbb_level.getValue().equals("���еȼ�") ? "" : cbb_level.getValue();
        String level = str0 + "%";    //��Ա�ȼ�
        String dateFrom = dpk_from.getValue().toString();    //ʱ������
        String dateTo = dpk_to.getValue().toString();    //ʱ������

        ArrayList<VipBean> listVipBeans = vipInfoDao.selectByFiltrate(idOrName, idOrName, level, dateFrom, dateTo);
        if (listVipBeans != null) {
            table.clearData();
            for (VipBean bean : listVipBeans)
                table.addBean(bean);
        } else {
            new MyAlert(AlertType.INFORMATION, "û���ҵ�����ѯ�Ļ�Ա��").show();
        }
    }

    @FXML // ��ӻ�Ա��Ϣ
    private void addVip() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "��ӻ�Ա";
        for (Tab tab : obList) { // �����жϸ�tab�Ƿ��Ѿ����
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // ����Ѿ��������ʾ��tab������
                return;
            }

        }
        Tab tab = new VipAddTab();
        tab.setUserData(DATA);
        obList.add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

    /**
     * ɾ����Ա��Ϣ
     */
    @FXML
    private void deleteVip() {
        VipBean bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "����ѡ��һ�����ݣ�").show();
            return;
        }
        String id = "����:" + bean.getId();
        String name = "����:" + bean.getName();
        MyAlert alert = new MyAlert(AlertType.CONFIRMATION, "�Ƿ�ȷ��ɾ�����û�����Ϣ��\n(" + id + "��" + name + ")");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.removeData(bean);
            vipInfoDao.deleteByID(bean);
        }
    }

    /**
     * �޸Ļ�Ա��Ϣ
     */
    @FXML
    private void modifyVip() {
        //TODO ��ӡ·�����޸�
        VipBean bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "����ѡ��һ�����ݣ�").show();
            return;
        }
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "�޸Ļ�Ա��Ϣ";
        for (Tab tab : obList) { // �����жϸ�tab�Ƿ��Ѿ����
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // ����Ѿ��������ʾ��tab������
                return;
            }

        }
        VipModifyTab tab = new VipModifyTab(bean);
        tab.setUserData(DATA);
        tab.setPreTab(tab_manage);
        obList.add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

    @FXML
    private void printInfo() {
        File file = new File("D:/��Ա��Ϣ.xls");
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<TableColumn<VipBean, ?>> columns = table.getColumns();
        ObservableList<VipBean> items = table.getItems();
        List<Object> contentList = new ArrayList<>(items);
        String[] titles = new String[columns.size()];
        String[] fieldNames = {"id", "admission_date", "name", "sex", "card_level", "balance", "frequency", "cumulative", "address", "integral", "telephone", "idcard", "birthday", "career", "email", "other"};
        for (int i = 0; i < titles.length; i++) {
            titles[i] = columns.get(i).getText();
        }
        boolean hasPrint = JXLUtil.exportExcel(titles, fieldNames, contentList, os);
        if (hasPrint) {
            new MyAlert(AlertType.INFORMATION, "��ӡ�ɹ���\n�ļ�����λ��Ϊ��" + file.getAbsolutePath()).show();
        } else {
            new MyAlert(AlertType.INFORMATION, "��ӡʧ�ܣ�");
        }
    }

}
