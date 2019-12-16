package top.felixu.common.date;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * 提供常用格式化时间的枚举
 *
 * @author felixu
 * @date 2019.09.24
 */
public enum DateFormatUtils {

    /**
     * 约定自定义pattern使用default
     */
    DEFAULT("yyyy-MM-dd HH:mm:ss"),
    FULL_DATE_SECONDS("yyyy-MM-dd HH:mm:ss"),
    FULL_DATE_MINUTE("yyyy-MM-dd HH:mm"),
    FULL_DATE("yyyy-MM-dd"),
    FULL_MONTH("yyyy-MM"),
    FULL_TIME("HH:mm:ss"),
    FULL_MINUTE("HH:mm"),
    SHORT_DATE_SECONDS("yyyyMMddHHmmss"),
    SHORT_DATE_MINUTE("yyyyMMddHHmm"),
    SHORT_DATE("yyyyMMdd"),
    SHORT_MONTH("yyyyMM"),
    SHORT_TIME("HHmmss"),
    SHORT_MINUTE("HHmm"),
    CH_DATE_SECONDS("yyyy年MM月dd日 HH时mm分ss秒"),
    CH_DATE_MINUTE("yyyy年MM月dd日 HH时mm分"),
    CH_DATE("yyyy年MM月dd日"),
    CH_MONTH_DATE("MM月dd日"),
    CH_MONTH("yyyy年MM月"),
    CH_TIME("HH时mm分ss秒"),
    CH_MINUTE("HH时mm分"),
    CH_WEEK("yyyy年第w周"),
    ;

    /**
     * 格式
     */
    @Getter
    private String pattern;

    /**
     * 格式化解析器
     */
    @Getter
    private DateTimeFormatter formatter;

    /**
     * 构造
     *
     * @param pattern 格式
     */
    DateFormatUtils(String pattern) {
        this.pattern = pattern;
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * 按当前格式返回当前时间格式化后的结果
     *
     * @return 当前时间格式化后的字符串
     */
    public String now() {
        return this.formatter.format(LocalDateTime.now());
    }

    /**
     * 按当前格式格式化给定时间
     * 针对 Java 8 提供的时间类，如 LocalDateTime、LocalDate、LocalTime
     *
     * @param source 需要被格式化的时间
     * @return 当前时间格式化后的字符串
     */
    public String format(TemporalAccessor source) {
        return this.formatter.format(source);
    }

    /**
     * 按当前格式格式化给定时间
     * 针对 Date 类型
     *
     * @param source 需要被格式化的时间
     * @return 当前时间格式化后的字符串
     */
    public String format(Date source) {
        return this.format(source.toInstant().atZone(ZoneId.systemDefault()));
    }

    /**
     * 将当前格式的字符串解析为 LocalDateTime
     *
     * @param source 源字符串
     * @return LocalDateTime
     */
    public LocalDateTime parseToLocalDateTime(String source) {
        return LocalDateTime.parse(source, this.formatter);
    }

    /**
     * 将当前格式的字符串解析为 LocalDate
     *
     * @param source 源字符串
     * @return LocalDate
     */
    public LocalDate parseToLocalDate(String source) {
        return LocalDate.parse(source, this.formatter);
    }

    /**
     * 将当前格式的字符串解析为 LocalTime
     *
     * @param source 源字符串
     * @return LocalTime
     */
    public LocalTime parseToLocalTime(String source) {
        return LocalTime.parse(source, this.formatter);
    }
}
