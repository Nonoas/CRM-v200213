package indi.nonoas.crm.view.staff;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

/**
 * @author : Nonoas
 * @time : 2021-07-29 21:37
 */
public class StaffInfoTab extends Tab {
    private static volatile StaffInfoTab instance;

    private StaffInfoTab() {
        this("员工管理");
    }

    private StaffInfoTab(String str) {
        super(str);
        initView();
    }

    public static StaffInfoTab getInstance() {
        if (null == instance) {
            synchronized (StaffInfoTab.class) {
                if (null == instance) {
                    instance = new StaffInfoTab();
                }
            }
        }
        return instance;
    }


    private void initView() {
        setContent(new Label("正在开发，敬请期待！"));
    }


}
