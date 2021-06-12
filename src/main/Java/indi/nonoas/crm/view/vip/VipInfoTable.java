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
 * 会员信息的TableView
 *
 * @author Nonoas
 */
public class VipInfoTable extends TableView<VipInfo> {

    private final Logger logger = Logger.getLogger(VipInfoTable.class);

    private final VipService vipService = (VipService) SpringUtil.getBean("UserServiceImpl");

    /**
     * 数据源列表
     */
    private final ObservableList<VipInfo> obList = FXCollections.observableArrayList();

    /**
     * 列数组
     */
    private final ObservableList<TableColumn<VipInfo, ?>> colList = getColumns();
    /**
     * 当前选中数据
     */
    private VipInfo selectedBean;

    private final TableColumn<VipInfo, String> item_balance = new TableColumn<>("卡内余额");

    private final TableColumn<VipInfo, Number> item_integral = new TableColumn<>("会员积分");

    private final TableColumn<VipInfo, String> item_telephone = new TableColumn<>("联系电话");

    private final TableColumn<VipInfo, String> item_id = new TableColumn<>("会员卡号");

    private final TableColumn<VipInfo, String> item_address = new TableColumn<>("联系地址");

    private final TableColumn<VipInfo, String> item_name = new TableColumn<>("会员姓名");

    private final TableColumn<VipInfo, String> item_career = new TableColumn<>("单位职业");

    private final TableColumn<VipInfo, String> item_admission = new TableColumn<>("入会时间");

    private final TableColumn<VipInfo, String> item_sex = new TableColumn<>("性别");

    private final TableColumn<VipInfo, Number> item_discount = new TableColumn<>("享受折扣");

    private final TableColumn<VipInfo, String> item_email = new TableColumn<>("邮箱");

    private final TableColumn<VipInfo, Number> item_cumulative = new TableColumn<>("累计消费");

    private final TableColumn<VipInfo, String> item_idCard = new TableColumn<>("身份证号码");

    private final TableColumn<VipInfo, String> item_birthday = new TableColumn<>("出生日期");

    private final TableColumn<VipInfo, String> item_card_level = new TableColumn<>("会员等级");

    private final TableColumn<VipInfo, String> item_other = new TableColumn<>("其他信息");

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
     * 初始化表格列
     */
    private void initColumns() {

        setTableMenuButtonVisible(true);

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId())); // 会员卡号
        item_admission.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdmissionDate())); // 入会日期
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName())); // 姓名
        item_sex.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSex())); // 性别
        item_card_level.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCardLevel())); // 会员等级
        item_discount.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getDiscount()));
        item_balance.setCellValueFactory(param -> {
            String str = String.format("￥%.2f", param.getValue().getBalance());
            return new SimpleStringProperty(str);
        }); // 卡内余额
        item_cumulative.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getCumulative())); // 累计消费
        item_address.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAddress())); // 联系地址
        item_integral.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIntegral())); // 会员积分
        item_telephone.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTelephone())); // 联系电话
        item_idCard.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIdcard())); // 证件号码
        item_birthday.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBirthday())); // 出生日期
        item_career.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCareer())); // 单位职业
        item_email.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmail())); // 邮箱
        item_other.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOther())); // 邮箱


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
     * 展示所有用户信息
     */
    public void showAllInfos() {
        clearData(); // 清空所有数据
        setPlaceholder(new TableProgressIndicator());
        //子线程查询数据
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
                setPlaceholder(new Label("表中无内容"));
        });
    }

    /**
     * 清空数据源
     */
    public void clearData() {
        obList.clear();
    }

    /**
     * 添加数据
     *
     * @param bean 需要添加的VIPBean
     */
    public void addBean(VipInfo bean) {
        obList.add(bean);
    }

    /**
     * 获取选中的数据
     *
     * @return 选中的VIPBean
     */
    public VipInfo getSelectedData() {
        return this.selectedBean;
    }

    /**
     * 移除数据
     *
     * @param bean 需要移除的VIPBean
     */
    public void removeData(VipInfo bean) {
        this.obList.remove(bean);
    }
}
