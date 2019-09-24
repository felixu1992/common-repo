package top.felixu.common.utils;

import java.util.Optional;

/**
 * 对值进行处理的工具
 *
 * @author felixu
 * @date 2019.07.01
 */
public class ValueUtils {

    /**
     * 用于处理可能为null的值，避免判断null，并设置默认值
     * Java 8 以下
     * if (null == value) {
     *     return def;
     * }
     * return value;
     * @param value 待判断的值
     * @param def 默认值
     * @param <T> 范型对象
     * @return 返回对象值
     * @since Java 8 +
     */
    public static <T> T nullAs(T value, T def) {
        return Optional.ofNullable(value)
                .orElse(def);
    }
}