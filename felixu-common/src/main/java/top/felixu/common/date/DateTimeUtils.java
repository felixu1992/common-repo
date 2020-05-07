package top.felixu.common.date;


import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 时间相关处理
 * <p>
 * 提供以下功能
 * <ul>
 * <li>提供包括 Java8 时间与 Date 和 Long 类型之间的相互转换</li>
 * <li>提供格式化方法</li>
 * <li>提供判断是否为周末</li>
 * <li>提供查询给定时间段中的每一天的列表</li>
 * </ul>
 * </p>
 *
 * @author felixu
 * @date 2019.07.04
 */
public class DateTimeUtils {

    /**
     * 将 Date 类型转为 LocalDateTime 类型
     *
     * @param date 需要被转换的日期
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将 Date 类型转为 LocalDate 类型
     *
     * @param date 需要被转换的日期
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        return dateToLocalDateTime(date).toLocalDate();
    }

    /**
     * 将 Date 类型转为 LocalTime 类型
     *
     * @param date 需要被转换的日期
     * @return LocalTime
     */
    public static LocalTime dateToLocalTime(Date date) {
        return dateToLocalDateTime(date).toLocalTime();
    }

    /**
     * 将 LocalDateTime 类型转为 Date 类型
     *
     * @param date 需要被转换的日期
     * @return Date
     */
    public static Date localDateTimeToDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将 LocalDate 类型转为 Date 类型
     *
     * @param date 需要被转换的日期
     * @return Date
     */
    public static Date localDateToDate(LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将 LocalTime 类型转为 Date 类型
     *
     * @param date LocalDate
     * @param time LocalTime
     * @return Date
     */
    public static Date localTimeToDate(LocalDate date, LocalTime time) {
        return Date.from(LocalDateTime.of(date, time).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将时间戳转成 LocalDateTime
     *
     * @param time 时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime longToLocalDateTime(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }

    /**
     * 将时间戳转成 LocalDate
     *
     * @param time 时间戳
     * @return LocalDate
     */
    public static LocalDate longToLocalDate(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 按指定格式将时间格式化为字符串
     * 常用格式请使用{@link DateFormatter}
     *
     * @param source 时间
     * @param pattern 格式
     * @return String
     * @see DateFormatter
     */
    private static String format(TemporalAccessor source, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(source);
    }

    /**
     * 按指定格式将时间格式化为字符串
     * 常用格式请使用{@link DateFormatter}
     *
     * @param source 时间
     * @param pattern 格式
     * @return String
     * @see DateFormatter
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

    /**
     * 获取给定时间段中的每一天(含 start 和 end 在内)
     *
     * @param start 开始时间
     * @param end 结束时间
     * @param pattern 字符串时间格式
     * @return 给定时间段中的时间列表
     */
    public static List<LocalDate> getEveryDayOfSpecified(String start, String end, String pattern) {
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(pattern);
        return getEveryDayOfSpecified(LocalDate.parse(start, ofPattern), LocalDate.parse(end, ofPattern));
    }

    /**
     * 获取给定时间段中的每一天(含 start 和 end 在内)
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 给定时间段中的时间列表
     */
    public static List<LocalDate> getEveryDayOfSpecified(LocalDate start, LocalDate end) {
        List<LocalDate> result = new ArrayList<>();
        while (!start.isAfter(end)) {
            result.add(start);
            start = start.plusDays(1);
        }
        return result;
    }

    /**
     * 获取给定时间段中的每一天(含 start 和 end 在内)
     *
     * @param start 开始时间
     * @param end 结束时间
     * @param sourcePattern 传入字符串时间的格式
     * @param formatter 返回字符串时间的格式
     * @return 给定时间段中的时间列表
     */
    public static List<String> getEveryDayOfSpecified(String start, String end, String sourcePattern, DateFormatter formatter) {
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(sourcePattern);
        return getEveryDayOfSpecified(LocalDate.parse(start, ofPattern), LocalDate.parse(end, ofPattern), formatter);
    }

    /**
     * 获取给定时间段中的每一天(含 start 和 end 在内)
     *
     * @param start 开始时间
     * @param end 结束时间
     * @param formatter 返回字符串时间的格式
     * @return 给定时间段中的时间列表
     */
    public static List<String> getEveryDayOfSpecified(LocalDate start, LocalDate end, DateFormatter formatter) {
        List<String> result = new ArrayList<>();
        while (!start.isAfter(end)) {
            result.add(start.format(formatter.getFormatter()));
            start = start.plusDays(1);
        }
        return result;
    }
}
