package indi.nonoas.crm.utils;

import cn.hutool.core.util.StrUtil;

import java.util.Objects;

/**
 * @author Nonoas
 * @datetime 2022/3/7 22:53
 */
public class StringUtil extends StrUtil {
    private StringUtil() {
    }

    /**
     * 首字母大写
     *
     * @param name 原单词
     * @return 首字母大写之后的单词
     */
    public static String captureName(String name) {
        final char[] cs = Objects.requireNonNull(name).trim().toCharArray();
        if (cs[0] > 'z' || cs[0] < 'a') {
            throw new IllegalArgumentException("参数首字符必须在 'a' 至 'z' 之间");
        }
        cs[0] -= 32;
        return String.valueOf(cs);

    }
}
