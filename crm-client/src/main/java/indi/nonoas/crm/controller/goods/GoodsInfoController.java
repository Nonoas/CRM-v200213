package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.component.table.ConditionTable;
import indi.nonoas.crm.controller.MainController;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.view.goods.GoodsAddTab;
import indi.nonoas.crm.view.goods.GoodsInfoTable;
import indi.nonoas.crm.view.goods.GoodsModifyTab;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

public class GoodsInfoController implements Initializable {

    @SuppressWarnings("all")
    private final String MENU_CODE = "GoodsInfo";

    private ConditionTable<GoodsDto> table ;

    GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

    @FXML
    private ComboBox<String> cb_type;

    @FXML
    private TextField tf_id;

    @FXML
    private ScrollPane scrollPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    private void initView() {
        table = new ConditionTable<>(MENU_CODE, Collections.emptyList());
        scrollPane.setContent(table);

        List<String> goodsTypes = goodsService.selectGoodsTypes();
        cb_type.getItems().addAll("全部类型", "产品类", "服务类");
        if (goodsTypes != null) {
            for (String str : goodsTypes) {
                cb_type.getItems().add(str);
            }
        }
        cb_type.setValue("全部类型");
    }


    /**
     * 商品添加
     */
    @FXML
    private void addGoods() {
        MainController.addTab(new GoodsAddTab());
    }

    /**
     * 商品删除
     */
    @FXML
    private void deleteInfo() {
        GoodsDto dto = table.getSelectedData();
        if (null == dto) {
            new MyAlert(Alert.AlertType.INFORMATION, "请选择一条商品信息！").show();
            return;
        }
        String id = dto.getId();
        String name = dto.getName();

        // 判断商品是否可以删除
        if (!(goodsDeletable(dto))) {
            return;
        }

        MyAlert alert = new MyAlert(Alert.AlertType.CONFIRMATION,
                String.format("您是否确定删除以下商品信息？\n[编号：%s，名称%s ]", id, name));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.removeData(dto);
            goodsService.deleteByID(id);
        }
    }

    /**
     * 判断商品信息是否能删除
     *
     * @return 可以删除：true
     */
    private boolean goodsDeletable(GoodsDto dto) {

        // todo 解决商品删除或修改时，订单关联商品信息改变的问题
        if(goodsService.pkgContains(dto)){
           new MyAlert(Alert.AlertType.WARNING,"有套餐内包含该商品，暂时无法删除！").show();
           return false;
        }
        return true;
    }

    /**
     * 查询
     */
    @FXML
    private void find() {
        String keyWord = tf_id.getText().trim();
        String type = "全部类型".equals(cb_type.getValue()) ? "" : cb_type.getValue();
        ArrayList<GoodsDto> listVipBeans = goodsService.selectByFiltrate(keyWord, keyWord, type);
        if (listVipBeans != null) {
            table.replaceData(listVipBeans);
        } else {
            new MyAlert(Alert.AlertType.INFORMATION, "没有找到您查询的商品信息！").show();
        }
    }

    @FXML
    private void updateGoods() {
        GoodsDto dto = table.getSelectedData();
        if (null == dto) {
            new MyAlert(Alert.AlertType.WARNING, "请先选择一条商品信息！").show();
            return;
        }
        MainController.addTab(new GoodsModifyTab(dto));
    }


}
