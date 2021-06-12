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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GoodsInfoController implements Initializable {

    /**
     * 商品信息表
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

    /**
     * 初始化界面
     */
    private void initView() {
        // 初始化表格
        scrollPane.setContent(table);

        // 初始化“商品分类”下拉列表框
        LinkedList<String> goodsTypes = goodsService.selectGoodsTypes();
        cb_type.getItems().addAll("所有类型", "产品类", "服务类");
        if (goodsTypes != null) {
            for (String str : goodsTypes)
                cb_type.getItems().add(str);
        }
        cb_type.setValue("所有类型");
    }


    @FXML    //添加商品事件
    private void addGoods() {
        MainController.addTab(new GoodsAddTab());
    }

    @FXML    //删除商品信息
    private void deleteInfo() {
        GoodsDto bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(Alert.AlertType.INFORMATION, "请先选择一条数据！").show();
            return;
        }
        String id = bean.getId();      //商品编号
        String name = bean.getName();  //商品名称
        MyAlert alert = new MyAlert(Alert.AlertType.CONFIRMATION,
                String.format("是否确定删除该商品的信息？\n[ 编号：%s，名称：%s ]", id, name));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.removeData(bean);
            goodsService.deleteByID(id);
        }
    }

    @FXML    //查询商品
    private void find() {
        String keyWord = tf_id.getText().trim();
        String type = cb_type.getValue().equals("所有类型") ? "" : cb_type.getValue();
        ArrayList<GoodsDto> listVipBeans = goodsService.selectByFiltrate(keyWord, keyWord, type);
        if (listVipBeans != null)
            table.replaceData(listVipBeans);
        else
            new MyAlert(Alert.AlertType.INFORMATION, "没有找到您查询的商品！").show();
    }

    @FXML    //修改商品信息
    private void updateGoods() {
        MainController.addTab(new GoodsModifyTab(table.getSelectedData()));
    }


}
