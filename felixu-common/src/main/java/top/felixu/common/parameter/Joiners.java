package top.felixu.common.parameter;

import com.google.common.base.Joiner;

import java.io.File;

/**
 * {@link Joiner} 是将集合拼接为字符串的工具，是线程安全的，可单例使用的。此为对常用 Joiner 的预定义
 *
 * @author felixu
 * @since 2019.12.17
 * @see Joiner
 */
public class Joiners {

    private Joiners() {
    }

    public static final Joiner DOT = Joiner.on(".").skipNulls();
    public static final Joiner COMMA = Joiner.on(",").skipNulls();
    public static final Joiner COLON = Joiner.on(":").skipNulls();
    public static final Joiner AT = Joiner.on("@").skipNulls();
    public static final Joiner SLASH = Joiner.on("/").skipNulls();
    public static final Joiner SPACE = Joiner.on(" ").skipNulls();
    public static final Joiner UNDERSCORE = Joiner.on("_").skipNulls();
    public static final Joiner SEMICOLON = Joiner.on(";").skipNulls();
    public static final Joiner FILE_SEPARATOR = Joiner.on(File.separator).skipNulls();
}
