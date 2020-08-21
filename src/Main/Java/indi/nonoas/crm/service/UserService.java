package indi.nonoas.crm.service;

import indi.nonoas.crm.config.UserConfig;

import java.math.BigInteger;

/**
 * @author : Nonoas
 * @time : 2020-08-15 22:35
 */
public class UserService {

    public static synchronized String generateVipID() {
        String prefix = UserConfig.getPrefix();
        String lastID = UserConfig.getLastID();
        BigInteger bigInteger = new BigInteger(lastID).add(new BigInteger("1"));
        UserConfig.setLastID(bigInteger.toString());
        String suffix = bigInteger.toString(32).toUpperCase();
        return prefix + suffix;
    }
}
