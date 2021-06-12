package indi.nonoas.crm.view.vip;

import java.util.ArrayList;
import java.util.List;

import indi.nonoas.crm.pojo.dto.VipInfo;
import indi.nonoas.crm.service.VipService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.progress.TableProgressIndicator;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.log4j.Logger;

/**
 * ��Ա��Ϣ��TableView
 *
 * @author Nonoas
 */
public class VipInfoTable extends TableView<VipInfo> {

    private final Logger logger = Logger.getLogger(VipInfoTable.class);

    private final VipService vipService = (VipService) SpringUtil.getBean("UserServiceImpl");

    /**
     * ����Դ�б�
     */
    private final ObservableList<VipInfo> obList = FXCollections.observableArrayList();

    /**
     * ������
     */
    private final ObservableList<TableColumn<VipInfo, ?>> colList = getColumns();
    /**
     * ��ǰѡ������
     */
    private VipInfo selectedBean;

    private final TableColumn<VipInfo, String> item_balance = new TableColumn<>("�������");

    private final TableColumn<VipInfo, Number> item_integral = new TableColumn<>("��Ա����");

    private final TableColumn<VipInfo, String> item_telephone = new TableColumn<>("��ϵ�绰");

    private final TableColumn<VipInfo, String> item_id = new TableColumn<>("��Ա����");

    private final TableColumn<VipInfo, String> item_address = new TableColumn<>("��ϵ��ַ");

    private final TableColumn<VipInfo, String> item_name = new TableColumn<>("��Ա����");

    private final TableColumn<VipInfo, String> item_career = new TableColumn<>("��λְҵ");

    private final TableColumn<VipInfo, String> item_admission = new TableColumn<>("���ʱ��");

    private final TableColumn<VipInfo, String> item_sex = new TableColumn<>("�Ա�");

    private final TableColumn<VipInfo, Number> item_discount = new TableColumn<>("�����ۿ�");

    private final TableColumn<VipInfo, String> item_email = new TableColumn<>("����");

    private final TableColumn<VipInfo, Number> item_cumulative = new TableColumn<>("�ۼ�����");

    private final TableColumn<VipInfo, String> item_idCard = new TableColumn<>("���֤����");

    private final TableColumn<VipInfo, String> item_birthday = new TableColumn<>("��������");

    private final TableColumn<VipInfo, String> item_card_level = new TableColumn<>("��Ա�ȼ�");

    private final TableColumn<VipInfo, String> item_other = new TableColumn<>("������Ϣ");

    public VipInfoTable() {
        initColumns();
        setItems(obList);
        showAllInfos();
        ChangeListener<VipInfo> cl_select = (observable, oldValue, newValue) -> {
            logger.debug(newValue);
            selectedBean = newValue;
        };
        getSelectionModel().selectedItemProperty().addListener(cl_select);
    }

    /**
     * ��ʼ�������
     */
    private void initColumns() {

        setTableMenuButtonVisible(true);

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId())); // ��Ա����
        item_admission.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdmissionDate())); // �������
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName())); // ����
        item_sex.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSex())); // �Ա�
        item_card_level.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCardLevel())); // ��Ա�ȼ�
        item_discount.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getDiscount()));
        item_balance.setCellValueFactory(param -> {
            String str = String.format("��%.2f", param.getValue().getBalance());
            return new SimpleStringProperty(str);
        }); // �������
        item_cumulative.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getCumulative())); // �ۼ�����
        item_address.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAddress())); // ��ϵ��ַ
        item_integral.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIntegral())); // ��Ա����
        item_telephone.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTelephone())); // ��ϵ�绰
        item_idCard.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdcard())); // ֤������
        item_birthday.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBirthday())); // ��������
        item_career.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCareer())); // ��λְҵ
        item_email.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmail())); // ����
        item_other.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOther())); // ����


        colList.add(item_id);
        colList.add(item_admission);
        colList.add(item_name);
        colList.add(item_sex);
        colList.add(item_card_level);
        colList.add(item_discount);
        colList.add(item_balance);
        colList.add(item_cumulative);
        colList.add(item_address);
        colList.add(item_integral);
        colList.add(item_telephone);
        colList.add(item_idCard);
        colList.add(item_birthday);
        colList.add(item_career);
        colList.add(item_email);
        colList.add(item_other);
    }

    /**
     * չʾ�����û���Ϣ
     */
    public void showAllInfos() {
        clearData(); // �����������
        setPlaceholder(new TableProgressIndicator());
        //���̲߳�ѯ����
        Task<List<VipInfo>> task = new Task<List<VipInfo>>() {
            @Override
            protected List<VipInfo> call() {
                List<VipInfo> beans = vipService.selectAllUser();
                if (beans != null)
                    return beans;
                else
                    return new ArrayList<>(0);
            }
        };
        new Thread(task).start();
        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.size() != 0)
                obList.addAll(newValue);
            else
                setPlaceholder(new Label("����������"));
        });
    }

    /**
     * �������Դ
     */
    public void clearData() {
        obList.clear();
    }

    /**
     * �������
     *
     * @param bean ��Ҫ��ӵ�VIPBean
     */
    public void addBean(VipInfo bean) {
        obList.add(bean);
    }

    /**
     * ��ȡѡ�е�����
     *
     * @return ѡ�е�VIPBean
     */
    public VipInfo getSelectedData() {
        return this.selectedBean;
    }

    /**
     * �Ƴ�����
     *
     * @param bean ��Ҫ�Ƴ���VIPBean
     */
    public void removeData(VipInfo bean) {
        this.obList.remove(bean);
    }
}
