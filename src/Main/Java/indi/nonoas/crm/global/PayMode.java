package indi.nonoas.crm.global;

/**
 * ֧����ʽö��
 */
public enum PayMode {
    INTEGRAL("����"),
    CASH("�ֽ�"),
    BALANCE("���"),
    FREE("���");

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
