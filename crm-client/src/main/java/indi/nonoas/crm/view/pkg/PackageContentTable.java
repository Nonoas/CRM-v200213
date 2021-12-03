package indi.nonoas.crm.view.pkg;

import indi.nonoas.crm.pojo.PackageContentDto;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.service.PackageService;
import indi.nonoas.crm.utils.SpringUtil;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PackageContentTable extends TableView<PackageContentDto> {

    private final ObservableList<PackageContentDto> obList = FXCollections.observableArrayList();

    /**
     * 选择的数据
     */
    private PackageContentDto selectedBean;

    private final TableColumn<PackageContentDto, String> item_id = new TableColumn<>("套餐编号");

    private final TableColumn<PackageContentDto, String> item_name = new TableColumn<>("套餐名称");

    private final TableColumn<PackageContentDto, String> item_money_cost = new TableColumn<>("单价");

    private final TableColumn<PackageContentDto, Number> item_amount = new TableColumn<>("数量");

    private final TableColumn<PackageContentDto, String> item_total = new TableColumn<>("小计");

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");
    private final PackageService pkgService = (PackageService) SpringUtil.getBean("PackageServiceImpl");

    public PackageContentTable() {
        initColumns();
        setItems(obList);
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            selectedBean = newValue;
        });
    }

    private void initColumns() {

        setTableMenuButtonVisible(true);

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
            String show = String.format("￥%.2f", numMoney);
            return new SimpleStringProperty(show);
        });

        item_amount.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getGoodsAmount()));

        item_total.setCellValueFactory(param -> {
            String goodsID = param.getValue().getGoodsId();
            GoodsDto goodsBean = goodsService.selectById(goodsID);
            double numMoney = goodsBean.getSellPrice();
            double amount = param.getValue().getGoodsAmount();
            String show = String.format("￥%.2f", numMoney * amount);
            return new SimpleStringProperty(show);
        });

        getColumns().add(item_id);
        getColumns().add(item_name);
        getColumns().add(item_money_cost);
        getColumns().add(item_amount);
        getColumns().add(item_total);
    }

    /**
     * 显示套餐下所有商品
     * @param id 套餐id
     */
    public void showAllInfos(String id) {
        clearData();
        List<PackageContentDto> listVipBeans = pkgService.listPkgContentByPkgId(id);
        if (listVipBeans != null)
            obList.addAll(listVipBeans);
    }

    /**
     * 清空
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
