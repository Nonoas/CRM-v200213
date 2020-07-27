package indi.nonoas.crm.global;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端会话类
 */
public class ClientSession {

    private static final Map<String, Object> session = new HashMap<>();

    public static Object getAttribute(String key) {
        return session.get(key);
    }

    public static void addAttribute(String key, Object obj) {
        session.put(key, obj);
    }

    /**
     * 一次性地使用一个属性，使用之后将其清除
     *
     * @param key 属性名
     * @return 属性值
     */
    public static Object singleUse(String key) {
        Object obj = session.get(key);
        session.remove(key);
        return obj;
    }

}
