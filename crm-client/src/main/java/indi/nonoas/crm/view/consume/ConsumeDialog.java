package indi.nonoas.crm.view.consume;

import indi.nonoas.crm.component.annotation.CSS;
import indi.nonoas.crm.component.annotation.FXML;
import indi.nonoas.crm.component.annotation.StageProperty;
import indi.nonoas.crm.component.stage.ControllableStage;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.controller.consume.ConsumeDialogController;
import indi.nonoas.crm.pojo.OrderDetailBean;
import indi.nonoas.crm.pojo.OrderDto;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import java.util.List;
import javafx.scene.image.Image;
import javafx.stage.Modality;

/**
 * @author : Nonoas
 * @time : 2020-08-10 17:39
 */
@StageProperty(title = "商品消费")
@FXML("/fxml/consume_dialog.fxml")
@CSS("css/bootstrap3.css")
public class ConsumeDialog extends ControllableStage {

    private final ConsumeDialogController controller;

    public ConsumeDialog(VipInfoDto vip, OrderDto order, List<OrderDetailBean> orderDetails) {
        initView();
        controller = (ConsumeDialogController) getController();
        controller.setStage(this);
        controller.setVipBean(vip);
        controller.setOrder(order);
        controller.setOrderDetail(orderDetails);
        controller.setFocus();
    }

    private void initView() {
        initModality(Modality.APPLICATION_MODAL);
        getIcons().add(new Image(ImageSrc.lOGO_PATH));
        setResizable(false);
    }

    /**
     * 判断是否结算成功
     */
    public boolean hasSubmit() {
        return controller.hasSubmit();
    }

}
