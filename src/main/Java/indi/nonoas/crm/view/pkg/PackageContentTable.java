package indi.nonoas.crm.view.pkg;

import java.util.ArrayList;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.PackageContentDto;
import indi.nonoas.crm.dao.my_orm_dao.PackageContentDao;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PackageContentTable extends TableView<PackageContentDto> {
    /**
     * ����Դ
     */
    private final ObservableList<PackageContentDto> obList = FXCollections.observableArrayList();
    /**
     * ��ǰѡ������
     */
    private PackageContentDto selectedBean;

    private final TableColumn<PackageContentDto, String> item_id = new TableColumn<>("��Ʒ���");

    private final TableColumn<PackageContentDto, String> item_name = new TableColumn<>("��Ʒ����");

    private final TableColumn<PackageContentDto, String> item_money_cost = new TableColumn<>("��Ʒ����");

    private final TableColumn<PackageContentDto, Number> item_amount = new TableColumn<>("����");

    private final TableColumn<PackageContentDto, String> item_total = new TableColumn<>("С��");

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

    public PackageContentTable() {
        initColumns();
        setItems(obList);
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            selectedBean = newValue;
        });
    }

    private void initColumns() {

        setTableMenuButtonVisible(true); // ��ʾ���˵���ť

        item_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGoodsId()));
        item_name.setCellValueFactory(param -> {
            String goodsID = param.getValue().getGoodsId();
            GoodsDto goodsBean = goodsService.selectById(goodsID);
            return new SimpleStringProperty(goodsBean.getName());
        });
        item_money_cost.setCellValueFactory(param -> {
            String goodsID = param.getValue().getGoodsId();
            GoodsDto goodsBean = goodsService.selectById(goodsID);
            double numMoney = goodsBean.getSellPrice();
            String show = String.format("��%.2f", numMoney);
            return new SimpleStringProperty(show);
        });

        item_amount.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getGoodsAmount()));

        item_total.setCellValueFactory(param -> {
            String goodsID = param.getValue().getGoodsId();
            GoodsDto goodsBean = goodsService.selectById(goodsID);
            double numMoney = goodsBean.getSellPrice();
            double amount = param.getValue().getGoodsAmount();
            String show = String.format("��%.2f", numMoney * amount);
            return new SimpleStringProperty(show);
        });

        getColumns().add(item_id);
        getColumns().add(item_name);
        getColumns().add(item_money_cost);
        getColumns().add(item_amount);
        getColumns().add(item_total);
    }

    /**
     * չʾ������Ŀ��Ϣ
     */
    public void showAllInfos(String id) {
        clearData(); // �����������
        ArrayList<PackageContentDto> listVipBeans = PackageContentDao.getInstance().selectById(id);
        if (listVipBeans != null)
            obList.addAll(listVipBeans);
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
     * @param bean ��Ҫ��ӵ�����
     */
    public void addBean(PackageContentDto bean) {
        obList.add(bean);
    }

    /**
     * ��ȡѡ�е�����
     *
     * @return ѡ�е�PackageContentBean
     */
    public PackageContentDto getSelectedData() {
        return this.selectedBean;
    }

    /**
     * �Ƴ�����
     *
     * @param bean ��Ҫ�Ƴ���PackageContentBean
     */
    public void removeData(PackageContentDto bean) {
        this.obList.remove(bean);
    }

}
