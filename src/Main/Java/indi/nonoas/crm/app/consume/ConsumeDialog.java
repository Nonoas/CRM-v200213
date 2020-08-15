package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.beans.OrderBean;
import indi.nonoas.crm.beans.OrderDetailBean;
import indi.nonoas.crm.beans.VipBean;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.controller.consume.ConsumeDialogController;
import indi.nonoas.crm.view.annotation.CSS;
import indi.nonoas.crm.view.annotation.StageProperty;
import indi.nonoas.crm.view.stage.ControllableStage;
import indi.nonoas.crm.view.annotation.FXML;
import javafx.scene.image.Image;
import javafx.stage.Modality;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-10 17:39
 */
@StageProperty(title = "订单结算")
@FXML("/fxml/consume_dialog.fxml")
@CSS("css/application.css")
public class ConsumeDialog extends ControllableStage {

    private ConsumeDialogController controller;

    public ConsumeDialog(VipBean vip, OrderBean order, List<OrderDetailBean> orderDetails) {
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
     * 判断是否成功提交
     *
     * @return 成功返回true，失败返回false
     */
    public boolean hasSubmit() {
        return controller.hasSubmit();
    }

}
