package indi.jfxmaker.common;

/**
 * UI组件的可见常量
 *
 * @author Nonoas
 * @datetime 2021/12/5 11:26
 */
public enum Visibility {

    VISIBLE(true, true),
    INVISIBLE(false, true),
    GONE(false, false);

    private final boolean visible;
    private final boolean managed;

    /**
     * @param visible 是否可见
     * @param managed 是否占用空间
     */
    Visibility(boolean visible, boolean managed) {
        this.visible = visible;
        this.managed = managed;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isManaged() {
        return managed;
    }
}
