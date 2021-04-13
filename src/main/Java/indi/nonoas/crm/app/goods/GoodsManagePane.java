package indi.nonoas.crm.app.goods;

import indi.nonoas.crm.view.annotation.FXML;
import indi.nonoas.crm.view.stage.FXLayoutFactory;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/**
 * ��Ʒ�������
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