package indi.nonoas.crm;

import indi.jfxmaker.AbstractApp;
import indi.nonoas.crm.component.CustomSplash;
import indi.nonoas.crm.view.WelcomeView;

/**
 * @author Nonoas
 * @datetime 2022/3/12 20:22
 */
public class Starter {
    public static void main(String[] args) {
        AbstractApp.launch(App.class, WelcomeView.class, new CustomSplash(), args);
    }
}
