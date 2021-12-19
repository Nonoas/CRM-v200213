package indi.jfxmaker.control;


import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;

/**
 * @author Nonoas
 * @datetime 2021/12/4 20:44
 */
public enum SysButtonEnum {

    MINIMIZE(SysButtonEnum.minimizeButton()),
    MAXIMIZE(SysButtonEnum.maximizeButton()),
    CLOSE(SysButtonEnum.closeButton());

    private final Button btn;

    SysButtonEnum(Button button) {
        this.btn = button;
    }

    public Button get() {
        return this.btn;
    }

    private static Button minimizeButton() {
        Button button = new SysButton(new ImageView("/icon/sys_min.png"));
        button.setStyle("-fx-background-color: transparent");
        button.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle("-fx-background-color: #ddd");
            } else {
                button.setStyle("-fx-background-color: transparent");
            }
        });
        return button;
    }

    private static Button maximizeButton() {
        Button button = new SysButton(new ImageView("/icon/sys_max.png"));
        button.setStyle("-fx-background-color: transparent");
        button.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle("-fx-background-color: #dedede");
            } else {
                button.setStyle("-fx-background-color: transparent");
            }
        });
        return button;
    }

    private static Button closeButton() {
//        ImageView iv = new ImageView("/icon/sys_close.png");
//
//        ImageView ivHover = new ImageView("/icon/sys_close.png");
//        ivHover.setEffect(new ColorAdjust(1, 1, 2, 1));
//
//        SysButton button = new SysButton(iv);
//        button.setStyle("-fx-background-color: transparent");
//        button.hoverProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                button.setStyle("-fx-background-color: #f55");
//                button.setIcon(ivHover);
//            } else {
//                button.setStyle("-fx-background-color: transparent");
//                button.setIcon(iv);
//            }
//        });
//        return button;


        SVGPath svgPath = new SVGPath();
//        SVGPath svgPath2 = new SVGPath();
//        svgPath2.prefHeight(20);
        svgPath.setContent("M176.661601 817.172881C168.472798 825.644055 168.701706 839.149636 177.172881 847.338438 185.644056 855.527241 199.149636 855.298332 207.338438 846.827157L826.005105 206.827157C834.193907 198.355983 833.964998 184.850403 825.493824 176.661601 817.02265 168.472798 803.517069 168.701706 795.328267 177.172881L176.661601 817.172881Z");
        Pane pane = new Pane();
//        svgPath2.setContent("M795.328267 846.827157C803.517069 855.298332 817.02265 855.527241 825.493824 847.338438 833.964998 839.149636 834.193907 825.644055 826.005105 817.172881L207.338438 177.172881C199.149636 168.701706 185.644056 168.472798 177.172881 176.661601 168.701706 184.850403 168.472798 198.355983 176.661601 206.827157L795.328267 846.827157Z");
//        Group group = new Group(svgPath, svgPath2);
        pane.setShape(svgPath);
        pane.setStyle("-fx-background-color: black");
        pane.setMinSize(StackPane.USE_PREF_SIZE, StackPane.USE_PREF_SIZE);
        pane.setPrefSize(20, 20);
        pane.setMaxSize(StackPane.USE_PREF_SIZE, StackPane.USE_PREF_SIZE);
        Button button = new Button();
        button.setGraphic(pane);
        return button;
    }

}
