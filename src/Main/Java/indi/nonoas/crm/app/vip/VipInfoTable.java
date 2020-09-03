package indi.nonoas.crm.app.vip;

import java.util.ArrayList;
import java.util.List;

import indi.nonoas.crm.beans.UserBean;
import indi.nonoas.crm.dao.VipInfoDao;
import indi.nonoas.crm.service.UserService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.view.progress.TableProgressIndicator;
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
public class VipInfoTable extends TableView<UserBean> {

    private final Logger logger = Logger.getLogger(VipInfoTable.class);

    private final UserService userService = (UserService) SpringUtil.getBean("UserServiceImpl");

    /**
     * ����Դ
     */
    private final ObservableList<UserBean> obList = FXCollections.observableArrayList();

    /**
     * ������
     */
    private final ObservableList<TableColumn<UserBean, ?>> colList = getColumns();
    /**
     * ��ǰѡ������
     */
    private UserBean selectedBean;

    private final TableColumn<UserBean, String> item_balance = new TableColumn<>("�������");

    private final TableColumn<UserBean, Number> item_integral = new TableColumn<>("��Ա����");

    private final TableColumn<UserBean, String> item_telephone = new TableColumn<>("��ϵ�绰");

    private final TableColumn<UserBean, String> item_id = new TableColumn<>("��Ա����");

    private final TableColumn<UserBean, String> item_address = new TableColumn<>("��ϵ��ַ");

    private final TableColumn<UserBean, String> item_name = new TableColumn<>("��Ա����");

    private final TableColumn<UserBean, String> item_career = new TableColumn<>("��λְҵ");

    private final TableColumn<UserBean, String> item_admission = new TableColumn<>("���ʱ��");

    private final TableColumn<UserBean, String> item_sex = new TableColumn<>("�Ա�");

    private final TableColumn<UserBean, Number> item_discount = new TableColumn<>("�����ۿ�");

    private final TableColumn<UserBean, String> item_email = new TableColumn<>("����");

    private final TableColumn<UserBean, Number> item_cumulative = new TableColumn<>("�ۼ�����");

    private final TableColumn<UserBean, String> item_idCard = new TableColumn<>("���֤����");

    private final TableColumn<UserBean, String> item_birthday = new TableColumn<>("��������");

    private final TableColumn<UserBean, String> item_card_level = new TableColumn<>("��Ա�ȼ�");

    private final TableColumn<UserBean, String> item_other = new TableColumn<>("������Ϣ");

    public VipInfoTable() {
        initColumns();
        setItems(obList);
        showAllInfos();
        ChangeListener<UserBean> cl_select = (observable, oldValue, newValue) -> {
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
        Task<List<UserBean>> task = new Task<List<UserBean>>() {
            @Override
            protected List<UserBean> call() {
                List<UserBean> beans = userService.selectAllUser();
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
    public void addBean(UserBean bean) {
        obList.add(bean);
    }

    /**
     * ��ȡѡ�е�����
     *
     * @return ѡ�е�VIPBean
     */
    public UserBean getSelectedData() {
        return this.selectedBean;
    }

    /**
     * �Ƴ�����
     *
     * @param bean ��Ҫ�Ƴ���VIPBean
     */
    public void removeData(UserBean bean) {
        this.obList.remove(bean);
    }
}
