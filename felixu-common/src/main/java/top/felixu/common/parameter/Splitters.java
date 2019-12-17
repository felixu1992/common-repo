package top.felixu.common.parameter;

import com.google.common.base.Splitter;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link Splitter} 是将字符串拆分为集合的工具，是线程安全的，可单例使用的。此为对常用 Splitter 的预定义
 *
 * @author felixu
 * @since 2019.12.17
 * @see Splitter
 */
public class Splitters {

    private Splitters() {
    }

    public static final Splitter DOT = Splitter.on(".").omitEmptyStrings().trimResults();
    public static final Splitter COMMA = Splitter.on(",").omitEmptyStrings().trimResults();
    public static final Splitter COLON = Splitter.on(":").omitEmptyStrings().trimResults();
    public static final Splitter AT = Splitter.on("@").omitEmptyStrings().trimResults();
    public static final Splitter SLASH = Splitter.on("/").omitEmptyStrings().trimResults();
    public static final Splitter SPACE = Splitter.on(" ").omitEmptyStrings().trimResults();
    public static final Splitter UNDERSCORE = Splitter.on("_").omitEmptyStrings().trimResults();
    public static final Splitter SEMICOLON = Splitter.on(";").omitEmptyStrings().trimResults();
    public static final Splitter FILE_SEPARATOR = Splitter.on(File.separator).omitEmptyStrings().trimResults();

    public static List<Long> splitToLong(CharSequence sequence, Splitter splitter) {
        return splitter.splitToList(sequence).stream().map(Long::valueOf).collect(Collectors.toList());
    }

    public static List<Integer> splitToInteger(CharSequence sequence, Splitter splitter) {
        return splitter.splitToList(sequence).stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    public static List<String> splitToString(CharSequence sequence, Splitter splitter) {
        return splitter.splitToList(sequence);
    }
}
