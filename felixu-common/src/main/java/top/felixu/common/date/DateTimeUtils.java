package top.felixu.common.date;


import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * 时间相关处理
 *
 * @author felixu
 * @date 2019.07.04
 */
public class DateTimeUtils {

    /**
     * 将Date类型转为LocalDateTime类型
     *
     * @param date 需要被转换的日期
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将Date类型转为LocalDate类型
     *
     * @param date 需要被转换的日期
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        return dateToLocalDateTime(date).toLocalDate();
    }

    /**
     * 将Date类型转为LocalTime类型
     *
     * @param date 需要被转换的日期
     * @return LocalTime
     */
    public static LocalTime dateToLocalTime(Date date) {
        return dateToLocalDateTime(date).toLocalTime();
    }

    /**
     * 将LocalDateTime类型转为Date类型
     *
     * @param date 需要被转换的日期
     * @return Date
     */
    public static Date localDateTimeToDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalDate类型转为Date类型
     *
     * @param date 需要被转换的日期
     * @return Date
     */
    public static Date localDateToDate(LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalTime类型转为Date类型
     *
     * @param date LocalDate
     * @param time LocalTime
     * @return Date
     */
    public static Date localTimeToDate(LocalDate date, LocalTime time) {
        return Date.from(LocalDateTime.of(date, time).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将时间戳转成LocalDateTime
     *
     * @param time 时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime longToLocalDateTime(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }

    /**
     * 将时间戳转成LocalDate
     *
     * @param time 时间戳
     * @return LocalDate
     */
    public static LocalDate longToLocalDate(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 按指定格式将时间格式化为字符串
     * 常用格式请使用{@link DateFormatUtils}
     *
     * @param source 时间
     * @param pattern 格式
     * @return String
     * @see DateFormatUtils
     */
    private static String format(TemporalAccessor source, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(source);
    }

    /**
     * 按指定格式将时间格式化为字符串
     * 常用格式请使用{@link DateFormatUtils}
     *
     * @param source 时间
     * @param pattern 格式
     * @return String
     * @see DateFormatUtils
     */
    public static String format(Date source, String pattern) {
        return format(source.toInstant().atZone(ZoneId.systemDefault()), pattern);
    }

    /**
     * 将 LocalDateTime 转为 Long 类型的时间戳
     *
     * @param source 给定时间
     * @return 毫秒值
     */
    public static Long toTimestamp(LocalDateTime source) {
        return source.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 判断当前给定时间是否为周末
     *
     * @param source 给定时间
     * @return 是否周末
     */
    public static boolean isWeekend(LocalDateTime source) {
        return source.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || source.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }
}
