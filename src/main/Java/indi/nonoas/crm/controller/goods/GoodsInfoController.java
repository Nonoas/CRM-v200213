package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.view.goods.GoodsAddTab;
import indi.nonoas.crm.view.goods.GoodsInfoTable;
import indi.nonoas.crm.view.goods.GoodsModifyTab;
import indi.nonoas.crm.controller.MainController;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.alert.MyAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

public class GoodsInfoController implements Initializable {

    /**
     * ��Ʒ��Ϣ��
     */
    private final GoodsInfoTable table = new GoodsInfoTable();

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
        scrollPane.setContent(table);

        List<String> goodsTypes = goodsService.selectGoodsTypes();
        cb_type.getItems().addAll("全部类型", "产品类", "服务类");
        if (goodsTypes != null) {
            for (String str : goodsTypes)
                cb_type.getItems().add(str);
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
        GoodsDto bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(Alert.AlertType.INFORMATION, "请选择一条商品信息！").show();
            return;
        }
        String id = bean.getId();
        String name = bean.getName();
        MyAlert alert = new MyAlert(Alert.AlertType.CONFIRMATION,
                String.format("您是否确定删除以下商品信息？\n[编号：%s，名称%s ]", id, name));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.removeData(bean);
            goodsService.deleteByID(id);
        }
    }

    /**
     * 查询
     */
    @FXML
    private void find() {
        String keyWord = tf_id.getText().trim();
        String type = cb_type.getValue().equals("全部类型") ? "" : cb_type.getValue();
        ArrayList<GoodsDto> listVipBeans = goodsService.selectByFiltrate(keyWord, keyWord, type);
        if (listVipBeans != null)
            table.replaceData(listVipBeans);
        else
            new MyAlert(Alert.AlertType.INFORMATION, "没有找到您查询的商品信息！").show();
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
