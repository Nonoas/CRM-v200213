package indi.nonoas.crm.config;

import indi.nonoas.crm.utils.PropertiesUtil;

/**
 * @author : Nonoas
 * @time : 2020-08-21 12:07
 */
public class UserConfig {

    private static final PropertiesUtil util = new PropertiesUtil("/config/userConfig.properties");

    private final static String PREFIX = "prefix";
    private final static String LAST_ID = "lastID";

    public static String getPrefix() {
        return util.get(PREFIX);
    }

    public static String getLastID() {
        return util.get(LAST_ID);
    }

    public static void setLastID(String lastID) {
        util.set(LAST_ID, lastID);
    }
}
