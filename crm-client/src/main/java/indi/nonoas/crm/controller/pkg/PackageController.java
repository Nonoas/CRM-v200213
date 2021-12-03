package indi.nonoas.crm.controller.pkg;

import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.component.dialog.GoodsSelectDialog;
import indi.nonoas.crm.pojo.PackageContentDto;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.view.pkg.PackageContentEditTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public abstract class PackageController implements Initializable {


    protected PackageContentEditTable pkgGoodsTable = new PackageContentEditTable();

    @FXML
    protected Button btn_upload;

    @FXML
    protected TextField tf_other;

    @FXML
    protected CheckBox chc_isClose;

    @FXML
    protected HBox hBox_root;

    @FXML
    protected TextField tf_money;

    @FXML
    protected TextField tf_name;

    @FXML
    protected ScrollPane sp_goods;

    @FXML
    protected TextField tf_id;

    @FXML
    protected TextField tf_integral;

    @FXML
    protected TextField tf_min_discount;

    @FXML
    protected ComboBox<String> cb_pkgType;

    @FXML
    protected ImageView img_photo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化分类下拉选项
        cb_pkgType.getItems().addAll("产品类", "服务类");
        initView();
    }

    /**
     * 初始化视图，通常是在子类构造器中调用
     */
    protected abstract void initView();

    /**
     * 添加商品
     */
    @FXML
    protected void addGoods() {

        GoodsSelectDialog dialog = new GoodsSelectDialog();
        dialog.showAndWait();
        ObservableList<GoodsDto> beans = dialog.getSelectGoods();
        if (beans != null && !beans.isEmpty()) {
            for (GoodsDto goodsBean : beans) {
                PackageContentDto packageContentDto = new PackageContentDto();
                packageContentDto.setPkgId(tf_id.getText());
                packageContentDto.setGoodsId(goodsBean.getId());
                packageContentDto.setGoodsAmount(1);
                pkgGoodsTable.addBean(packageContentDto);
            }
        }
    }

    /**
     * 删除
     */
    @FXML
    protected void deleteGoods() {
        pkgGoodsTable.removeData(pkgGoodsTable.getSelectedData());
    }

    /**
     * 清空表单
     */
    @FXML
    protected void clearGoods() {
        pkgGoodsTable.clearData();
    }

    /**
     * 非空校验
     * @return 有非法数据：true
     */
    protected boolean hasEmpty() {

        if ("".equals(tf_id.getText())) {
            new MyAlert(Alert.AlertType.WARNING, "套餐编号不能为空！").show();
            return true;
        } else if ("".equals(tf_name.getText())) {
            new MyAlert(Alert.AlertType.WARNING, "套餐名称不能为空！").show();
            return true;
        } else if ("".equals(tf_money.getText())) {
            new MyAlert(Alert.AlertType.WARNING, "套餐单价不能为空！").show();
            return true;
        } else if ("".equals(tf_integral.getText())) {
            new MyAlert(Alert.AlertType.WARNING, "套餐积分不能为空！").show();
            return true;
        }
        return false;

    }

}
