package indi.nonoas.crm.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 加密工具类
 */
public class SaltUtil {

    private final Object salt;
    private final String algorithm;

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public SaltUtil(Object salt, String algorithm) {
        this.salt = salt;
        this.algorithm = algorithm;
    }

    public String encode(String rawPass) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            //���ܺ���ַ���
            result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private String mergePasswordAndSalt(String password) {
        if (password == null) {
            password = "";
        }

        if ((salt == null) || salt.equals("")) {
            return password;
        } else {
            return password + "{" + salt + "}";
        }
    }

    /**
     * byteArrayToHexString
     *
     * @param b 字节
     * @return 16进制加密字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte value : b) {
            resultSb.append(byteToHexString(value));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

}
