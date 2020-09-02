package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.beans.OrderBean;
import indi.nonoas.crm.beans.OrderDetailBean;
import indi.nonoas.crm.beans.VipBean;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.controller.consume.PkgCnsDialogController;
import indi.nonoas.crm.view.annotation.CSS;
import indi.nonoas.crm.view.annotation.FXML;
import indi.nonoas.crm.view.annotation.StageProperty;
import indi.nonoas.crm.view.stage.ControllableStage;
import javafx.scene.image.Image;
import javafx.stage.Modality;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-18 15:54
 */
@StageProperty(title = "订单结算")
@CSS("css/application.css")
@FXML("/fxml/package_consume_dialog.fxml")
public class PackageConsumeDialog extends ControllableStage {

    private final PkgCnsDialogController controller;

    public PackageConsumeDialog(VipBean vip, OrderBean order, List<OrderDetailBean> orderDetails) {
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

    /**
     * 判断是否成功提交
     *
     * @return 成功返回true，失败返回false
     */
    public boolean hasSubmit() {
        return controller.hasSubmit();
    }

}
