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
     * 数据源
     */
    private final ObservableList<PackageContentDto> obList = FXCollections.observableArrayList();
    /**
     * 当前选中数据
     */
    private PackageContentDto selectedBean;

    private final TableColumn<PackageContentDto, String> item_id = new TableColumn<>("商品编号");

    private final TableColumn<PackageContentDto, String> item_name = new TableColumn<>("商品名称");

    private final TableColumn<PackageContentDto, String> item_money_cost = new TableColumn<>("商品单价");

    private final TableColumn<PackageContentDto, Number> item_amount = new TableColumn<>("数量");

    private final TableColumn<PackageContentDto, String> item_total = new TableColumn<>("小计");

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

        setTableMenuButtonVisible(true); // 显示表格菜单按钮

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
     * 展示所有项目信息
     */
    public void showAllInfos(String id) {
        clearData(); // 清空所有数据
        ArrayList<PackageContentDto> listVipBeans = PackageContentDao.getInstance().selectById(id);
        if (listVipBeans != null)
            obList.addAll(listVipBeans);
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
     * @param bean 需要添加的数据
     */
    public void addBean(PackageContentDto bean) {
        obList.add(bean);
    }

    /**
     * 获取选中的数据
     *
     * @return 选中的PackageContentBean
     */
    public PackageContentDto getSelectedData() {
        return this.selectedBean;
    }

    /**
     * 移除数据
     *
     * @param bean 需要移除的PackageContentBean
     */
    public void removeData(PackageContentDto bean) {
        this.obList.remove(bean);
    }

}
