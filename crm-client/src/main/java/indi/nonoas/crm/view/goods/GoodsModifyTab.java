package indi.nonoas.crm.view.goods;

import indi.nonoas.crm.controller.goods.GoodsModifyController;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * 商品修改
 *
 * @author Nonoas
 */
public class GoodsModifyTab extends Tab {

    private final GoodsDto bean;

    public GoodsModifyTab(GoodsDto bean) {
        this("商品修改", bean);
    }

    public GoodsModifyTab(String str, GoodsDto bean) {
        super(str);
        this.bean = bean;
        initView();
    }

    private void initView() {
        URL url = getClass().getResource("/fxml/goods/goods_modify.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        try {
            Pane pane = fxmlLoader.load();
            setContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setClosable(true);
        GoodsModifyController controller = fxmlLoader.getController();
        controller.setPane(this);
        controller.setBean(bean);
    }
}
