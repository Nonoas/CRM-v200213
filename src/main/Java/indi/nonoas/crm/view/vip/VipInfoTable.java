package indi.nonoas.crm.view.vip;

import java.util.ArrayList;
import java.util.List;

import indi.nonoas.crm.pojo.dto.VipInfoDto;
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
public class VipInfoTable extends TableView<VipInfoDto> {

    private final Logger logger = Logger.getLogger(VipInfoTable.class);

    private final VipService vipService = (VipService) SpringUtil.getBean("UserServiceImpl");

    private final ObservableList<VipInfoDto> obList = FXCollections.observableArrayList();

    private final ObservableList<TableColumn<VipInfoDto, ?>> colList = getColumns();

    private VipInfoDto selectedBean;

    private final TableColumn<VipInfoDto, String> item_balance = new TableColumn<>("余额");

    private final TableColumn<VipInfoDto, Number> item_integral = new TableColumn<>("积分");

    private final TableColumn<VipInfoDto, String> item_telephone = new TableColumn<>("电话");

    private final TableColumn<VipInfoDto, String> item_id = new TableColumn<>("会员卡号");

    private final TableColumn<VipInfoDto, String> item_address = new TableColumn<>("住址ַ");

    private final TableColumn<VipInfoDto, String> item_name = new TableColumn<>("姓名");

    private final TableColumn<VipInfoDto, String> item_career = new TableColumn<>("职业");

    private final TableColumn<VipInfoDto, String> item_admission = new TableColumn<>("入会时间");

    private final TableColumn<VipInfoDto, String> item_sex = new TableColumn<>("性别");

    private final TableColumn<VipInfoDto, Number> item_discount = new TableColumn<>("优惠折扣");

    private final TableColumn<VipInfoDto, String> item_email = new TableColumn<>("邮箱");

    private final TableColumn<VipInfoDto, Number> item_cumulative = new TableColumn<>("累积消费");

    private final TableColumn<VipInfoDto, String> item_idCard = new TableColumn<>("身份证号");

    private final TableColumn<VipInfoDto, String> item_birthday = new TableColumn<>("生日");

    private final TableColumn<VipInfoDto, String> item_card_level = new TableColumn<>("会员等级");

    private final TableColumn<VipInfoDto, String> item_other = new TableColumn<>("备注");

    public VipInfoTable() {
        initColumns();
        setItems(obList);
        showAllInfos();
        ChangeListener<VipInfoDto> cl_select = (observable, oldValue, newValue) -> {
            logger.debug(newValue);
            selectedBean = newValue;
        };
        getSelectionModel().selectedItemProperty().addListener(cl_select);
    }

    /**
     * 初始化列
     */
    private void initColumns() {

        setTableMenuButtonVisible(true);

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId()));
        item_admission.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdmissionDate()));
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        item_sex.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSex()));
        item_card_level.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCardLevel()));
        item_discount.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getDiscount()));
        item_balance.setCellValueFactory(param -> {
            String str = String.format("￥%.2f", param.getValue().getBalance());
            return new SimpleStringProperty(str);
        });
        item_cumulative.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getCumulative()));
        item_address.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAddress()));
        item_integral.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIntegral()));
        item_telephone.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTelephone()));
        item_idCard.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdcard()));
        item_birthday.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBirthday()));
        item_career.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCareer()));
        item_email.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmail()));
        item_other.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOther()));


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
        clearData(); // 进价���
        setPlaceholder(new TableProgressIndicator());
        //���̲߳�ѯ����
        Task<List<VipInfoDto>> task = new Task<List<VipInfoDto>>() {
            @Override
            protected List<VipInfoDto> call() {
                List<VipInfoDto> beans = vipService.selectAllUser();
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
                setPlaceholder(new Label("没有数据"));
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
    public void addBean(VipInfoDto bean) {
        obList.add(bean);
    }

    /**
     * ��ȡѡ�е�����
     *
     * @return ѡ�е�VIPBean
     */
    public VipInfoDto getSelectedData() {
        return this.selectedBean;
    }

    /**
     * �Ƴ�����
     *
     * @param bean ��Ҫ�Ƴ���VIPBean
     */
    public void removeData(VipInfoDto bean) {
        this.obList.remove(bean);
    }
}
