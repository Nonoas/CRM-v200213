package indi.nonoas.crm.view.goods;

import indi.nonoas.crm.component.annotation.FXML;
import indi.nonoas.crm.component.stage.FXLayoutFactory;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/**
 * 商品管理面板
 *
 * @author Nonoas
 */
@FXML("/fxml/goods_manage.fxml")
public class GoodsManagePane extends Pane {
    public GoodsManagePane() {
        TabPane pane = (TabPane) FXLayoutFactory.createFXLayout(GoodsManagePane.class);
        getChildren().add(pane);
        setStyle("-fx-border-color:#DDD");
        if (pane != null) {
            pane.prefHeightProperty().bind(this.heightProperty());
            pane.prefWidthProperty().bind(this.widthProperty());
        }
    }
}
