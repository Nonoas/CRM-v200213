package indi.nonoas.crm.common;

import java.util.HashMap;
import java.util.Map;

/**
 * �ͻ��˻Ự��
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
     * һ���Ե�ʹ��һ�����ԣ�ʹ��֮�������
     *
     * @param key ������
     * @return ����ֵ
     */
    public static Object singleUse(String key) {
        Object obj = session.get(key);
        session.remove(key);
        return obj;
    }

}
