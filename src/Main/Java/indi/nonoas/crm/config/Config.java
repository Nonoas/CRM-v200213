package indi.nonoas.crm.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * »´æ÷≈‰÷√¿‡
 *
 * @author : Nonoas
 * @time : 2020-08-06 14:28
 */
public class Config {

    private static final String PATH = "/config/config.properties";

    private static final Properties properties;

    static {
        InputStream in = Config.class.getResourceAsStream(PATH);
        properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Config() {
        
    }

    public static String get(String key) {
        return (String) properties.get(key);
    }

}
