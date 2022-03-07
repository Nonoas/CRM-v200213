package indi.nonoas.crm.pojo.dto;

/**
 * @author Nonoas
 * @datetime 2022/3/7 20:55
 */
public class MenuConditionDto {

    private String menuCode;
    private String elementCode;
    private String conditionType;
    private String elementName;
    private int sortNo;
    private byte visible;
    private int dataWidth;
    private String controlType;


    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }


    public String getElementCode() {
        return elementCode;
    }

    public void setElementCode(String elementCode) {
        this.elementCode = elementCode;
    }


    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }


    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }


    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }


    public byte getVisible() {
        return visible;
    }

    public void setVisible(byte visible) {
        this.visible = visible;
    }


    public int getDataWidth() {
        return dataWidth;
    }

    public void setDataWidth(int dataWidth) {
        this.dataWidth = dataWidth;
    }


    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }
}
