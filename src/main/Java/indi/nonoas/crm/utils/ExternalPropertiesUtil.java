package indi.nonoas.crm.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author : Nonoas
 * @time : 2020-08-21 12:08
 */
public class ExternalPropertiesUtil {

    private Properties properties;

    private final String path;

    public ExternalPropertiesUtil(String path) {
        this.path = path;
        load(path);
    }

    private void load(String path) {
        InputStream in;
        try {
            in = new FileInputStream(path);
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return (String) properties.get(key);
    }

    public void set(String k, String v) {
        properties.setProperty(k, v);
        try {
            OutputStream out = new FileOutputStream(path);
            properties.store(out, "¸üÐÂ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        load(path);
    }
}
