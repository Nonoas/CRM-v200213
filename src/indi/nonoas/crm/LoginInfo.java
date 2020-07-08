package indi.nonoas.crm;

import indi.nonoas.crm.bean.LoginBean;

public class LoginInfo {

   private static LoginBean loginBean;

    public static void setLoginBean(LoginBean bean) {
        loginBean = bean;
    }

    public static LoginBean getLoginBean() {
        return loginBean;
    }
}
