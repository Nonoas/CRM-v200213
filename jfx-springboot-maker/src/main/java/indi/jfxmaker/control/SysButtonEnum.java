package indi.jfxmaker.control;


import indi.jfxmaker.pane.SVGImage;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

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
        SVGImage svgImage = new SVGImage(
                "M917.333333 554.666667H106.666667a21.333333 21.333333 0 0 1 " +
                        "0-42.666667h810.666666a21.333333 21.333333 0 0 1 0 42.666667z",
                Color.valueOf("#515151")
        );
        svgImage.setSize(15, 1);
        return getBaseButton(svgImage);
    }

    private static Button maximizeButton() {
        SVGImage svgImage = new SVGImage(
                "M789.333333 192a42.666667 42.666667 0 0 1 42.666667 42.666667v554.666666a42.666667 42.666667 0 0 1-42.666667 " +
                        "42.666667h-554.666666a42.666667 42.666667 0 0 1-42.666667-42.666667v-554.666666a42.666667 " +
                        "42.666667 0 0 1 42.666667-42.666667h554.666666z m0 " +
                        "42.666667h-554.666666v554.666666h554.666666v-554.666666z",
                Color.valueOf("#515151")
        );
        svgImage.setSize(15, 15);
        return getBaseButton(svgImage);
    }

    private static Button closeButton() {
        SVGImage svgImage = new SVGImage(
                "M176.661601 817.172881C168.472798 825.644055 168.701706 839.149636 177.172881 847.338438 185.644056 855.527241 " +
                        "199.149636 855.298332 207.338438 846.827157L826.005105 206.827157C834.193907 198.355983 833.964998 184.850403 " +
                        "825.493824 176.661601 817.02265 168.472798 803.517069 168.701706 795.328267 177.172881L176.661601 817.172881Z " +
                        "M795.328267 846.827157C803.517069 855.298332 817.02265 855.527241 825.493824 847.338438 833.964998 " +
                        "839.149636 834.193907 825.644055 826.005105 817.172881L207.338438 177.172881C199.149636 168.701706 185.644056 " +
                        "168.472798 177.172881 176.661601 168.701706 184.850403 168.472798 198.355983 176.661601 206.827157L795.328267 846.827157Z",
                Color.valueOf("#515151")
        );
        svgImage.setSize(15, 15);
        return new SVGButton.SvgButtonBuilder()
                .graphic(svgImage)
                .graphicColor(Color.valueOf("#515151"))
                .getGraphicColorHover(Color.WHITE)
                .backgroundColor(Color.TRANSPARENT)
                .backgroundColorHover(Color.valueOf("#f55"))
                .build();
    }

    private static Button getBaseButton(SVGImage svgImage) {
        return new SVGButton.SvgButtonBuilder()
                .graphic(svgImage)
                .backgroundColor(Color.TRANSPARENT)
                .backgroundColorHover(Color.valueOf("#dedede"))
                .build();
    }

}
