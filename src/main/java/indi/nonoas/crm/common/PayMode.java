package indi.nonoas.crm.common;

/**
 * 支付方式枚举
 */
public enum PayMode {
    INTEGRAL("积分"),
    CASH("现金"),
    BALANCE("余额"),
    FREE("赠送");

    private final String val;

    PayMode(String str) {
        this.val = str;
    }

    public String val() {
        return val;
    }

    @Override
    public String toString() {
        return val;
    }
}
