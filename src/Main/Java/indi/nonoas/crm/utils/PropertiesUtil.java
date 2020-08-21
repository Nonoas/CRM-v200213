package indi.nonoas.crm.utils;

import indi.nonoas.crm.config.Config;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * @author : Nonoas
 * @time : 2020-08-21 12:08
 */
public class PropertiesUtil {

    private Properties properties;

    private final String path;

    public PropertiesUtil(String path) {
        this.path = path;
        load(path);
    }

    private void load(String path) {
        InputStream in = Config.class.getResourceAsStream(path);
        properties = new Properties();
        try {
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
            URL url=getClass().getResource(path);
            OutputStream out=new FileOutputStream(new File(url.toURI()));
            properties.store(out,"¸üÐÂ");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        load(path);
    }
}
