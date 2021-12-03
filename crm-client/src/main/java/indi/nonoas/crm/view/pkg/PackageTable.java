package indi.nonoas.crm.view.pkg;

import indi.nonoas.crm.pojo.PackageDto;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.utils.SpringUtil;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * ��Ŀ��Ϣ���
 *
 * @author Nonoas
 */
public class PackageTable extends TableView<PackageDto> {

    private final PackageService pkgService = (PackageService) SpringUtil.getBean("PackageServiceImpl");

    /**
     * ����Դ
     */
    private final ObservableList<PackageDto> obList = FXCollections.observableArrayList();

    /**
     * �м���
     */
    private final ObservableList<TableColumn<PackageDto, ?>> colList = getColumns();

    /**
     * ��ǰѡ������
     */
    private PackageDto selectedBean;

    /**
     * �󶨵��ײ����ݱ��
     */
    private PackageContentTable packageContentTable;

    private final TableColumn<PackageDto, String> item_id = new TableColumn<>("套餐编号");

    private final TableColumn<PackageDto, String> item_name = new TableColumn<>("套餐名称");

    private final TableColumn<PackageDto, String> item_money_cost = new TableColumn<>("单价");

    private final TableColumn<PackageDto, Number> item_integral_cost = new TableColumn<>("进价");

    private final TableColumn<PackageDto, Number> item_min_discount = new TableColumn<>("最低折扣");

    private final TableColumn<PackageDto, String> item_type = new TableColumn<>("套餐类型");

    private final TableColumn<PackageDto, String> item_other = new TableColumn<>("备注");

    public PackageTable() {
        initColumns();
        setItems(obList);
        showAllInfos();
        getSelectionModel().selectedItemProperty().addListener(cl_select);    //���ѡ�м���
    }

    public PackageTable(PackageContentTable packageContentTable) {
        this();
        this.packageContentTable = packageContentTable;
        getSelectionModel().selectedItemProperty().addListener(cl_showContent);    //���ѡ�м���
    }

    ChangeListener<PackageDto> cl_select = (observable, oldValue, newValue) -> {
        selectedBean = newValue;
    };

    ChangeListener<PackageDto> cl_showContent = (observable, oldValue, newValue) -> {
        if (selectedBean != null) {
            packageContentTable.showAllInfos(selectedBean.getId()); //����ѡ�б�������
        }
    };


    private void initColumns() {

        setTableMenuButtonVisible(true);

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId()));
        item_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        item_money_cost.setCellValueFactory(param -> {
            double numMoney = param.getValue().getMoneyCost();
            String show = String.format("￥%.2f", numMoney);
            return new SimpleStringProperty(show);
        });
        item_integral_cost.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getIntegralCost()));
        item_min_discount.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getMinDiscount()));
        item_type.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType()));

        item_other.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOther()));

        colList.add(item_id);
        colList.add(item_name);
        colList.add(item_money_cost);
        colList.add(item_integral_cost);
        colList.add(item_min_discount);
        colList.add(item_type);
        colList.add(item_other);

    }

    /**
     * չʾ������Ŀ��Ϣ
     */
    public void showAllInfos() {
        clearData(); // 进价���
        List<PackageDto> pkgDtoList = pkgService.selectAll();
        if (pkgDtoList != null) {
            obList.addAll(pkgDtoList);
        }
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
     * @param bean �ײ���Ŀbean��
     */
    public void addBean(PackageDto bean) {
        obList.add(bean);
    }

    /**
     * ��ȡѡ�е�����
     *
     * @return ѡ�е�PackageBean
     */
    public PackageDto getSelectedData() {
        return this.selectedBean;
    }

    /**
     * �Ƴ�����
     *
     * @param bean ��Ҫ�Ƴ���PackageBean
     */
    public void removeData(PackageDto bean) {
        this.obList.remove(bean);
    }
}
