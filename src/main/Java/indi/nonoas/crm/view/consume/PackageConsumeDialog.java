package indi.nonoas.crm.view.consume;

import indi.nonoas.crm.pojo.OrderBean;
import indi.nonoas.crm.pojo.OrderDetailBean;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.controller.consume.PkgCnsDialogController;
import indi.nonoas.crm.component.annotation.FXML;
import indi.nonoas.crm.component.annotation.StageProperty;
import indi.nonoas.crm.component.stage.ControllableStage;
import javafx.scene.image.Image;
import javafx.stage.Modality;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-18 15:54
 */
@StageProperty(title = "套餐消费")
@FXML("/fxml/package_consume_dialog.fxml")
public class PackageConsumeDialog extends ControllableStage {

    private final PkgCnsDialogController controller;

    public PackageConsumeDialog(VipInfoDto vip, OrderBean order, List<OrderDetailBean> orderDetails) {
        initView();
        controller = (PkgCnsDialogController) getController();
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

    public boolean hasSubmit() {
        return controller.hasSubmit();
    }

}
