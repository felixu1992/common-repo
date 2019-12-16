package top.felixu.common.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.MonthDayDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import top.felixu.common.date.DateFormatUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Jackson 处理 json
 *
 * @author felixu
 * @since 2019.12.16
 */
public class JsonUtils {

    private static final ObjectMapper NON_EMPTY = newObjectMapper(Include.NON_EMPTY);
    private static final ObjectMapper NON_DEFAULT = newObjectMapper(Include.NON_DEFAULT);
    private static final ObjectMapper ALWAYS = newObjectMapper(Include.ALWAYS);
    private static final ObjectMapper NON_NULL = newObjectMapper(Include.NON_NULL);

    /**
     * 只输出非 Empty(也非 null)的属性到 Json 字符串中
     */
    public static String toNonEmptyJson(Object object) {
        try {
            return NON_EMPTY.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 只输出初始值被改变的属性到 Json
     */
    public static String toNonDefaultJson(Object object) {
        try {
            return NON_DEFAULT.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 可输出全部字段到 json
     */
    public static String toAlwaysJson(Object object) {
        try {
            return ALWAYS.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 仅输出非 null 字段到 json
     */
    public static String toNonNullJson(Object object) {
        try {
            return NON_NULL.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化无泛型的简单 bean
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return ALWAYS.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化带有泛型的复杂对象，如List<Bean>，可以先使用方法 constructParametricType 构造类型，然后调用本方法
     *
     * @see #constructParametricType(Class, Class...)
     */
    public static <T> T fromJson(String jsonString, JavaType javaType) {
        try {
            return ALWAYS.readValue(jsonString, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> elementClass) {
        return fromJson(json, constructParametricType(List.class, elementClass));
    }

    public static <K, V> Map<K, V> fromJsonToMap(String json, Class<K> keyClass, Class<V> valueClass) {
        return fromJson(json, constructParametricType(Map.class, keyClass, valueClass));
    }

    public static Map<String, Object> fromJsonToMap(String json) {
        return fromJson(json, constructParametricType(Map.class, String.class, Object.class));
    }

    /**
     * 构造泛型的 JavaType，如：<br>
     * {@code ArrayList<MyBean>}, 则调用 constructParametricType(ArrayList.class,MyBean.class)<br>
     * {@code HashMap<String, MyBean>}, 则调用 constructParametricType(HashMap.class,String.class, MyBean.class)
     */
    public static JavaType constructParametricType(Class<?> parametrized, Class<?>... elementClasses) {
        return ALWAYS.getTypeFactory().constructParametricType(parametrized, elementClasses);
    }

    /**
     * 创建一个新的 {@link ObjectMapper}，并进行一些关键设置，
     * 并注册这些扩展Module：{@link GuavaModule}、{@link JavaTimeModule}、{@link Jdk8Module}。
     *
     * @param include 设置输出时包含属性的风格，此设置仅对序列化有效，反序列化时不同的 Include 并没有区别
     * @return {@link ObjectMapper}
     */
    public static ObjectMapper newObjectMapper(Include include) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(include);

        // 开启忽略 Java bean 中不存在的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 禁止使用数字类型来反序列化 Enum
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

        // 禁用时间戳风格的日期时间序列化，该设置对 Date(及其他旧的日期时间类)和 Java8 time API 都有影响
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 设置时区(使用操作系统的设置)，该设置仅对 Date(及其他旧的日期时间类)有影响
        mapper.setTimeZone(TimeZone.getDefault());

        // 设置格式化风格，该方法内部会禁用 WRITE_DATES_AS_TIMESTAMPS，从而使此格式仅对 Date(及其他旧的日期时间类)有影响
        mapper.setDateFormat(new SimpleDateFormat(DateFormatUtils.FULL_DATE_SECONDS.getPattern()));

        // 注册要支持的第三方类库
        mapper.registerModule(new GuavaModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        registerCustomModule(mapper);
        return mapper;
    }

    public static void registerCustomModule(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer(DateFormatUtils.FULL_DATE_SECONDS.getFormatter()));
        module.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer(DateFormatUtils.FULL_DATE.getFormatter()));
        module.addDeserializer(LocalTime.class, new CustomLocalTimeDeserializer(DateFormatUtils.FULL_TIME.getFormatter()));
        module.addDeserializer(YearMonth.class, new CustomYearMonthDeserializer(DateFormatUtils.CH_MONTH.getFormatter()));
        module.addDeserializer(MonthDay.class, new CustomMonthDayDeserializer(DateFormatUtils.CH_MONTH_DATE.getFormatter()));
        mapper.registerModule(module);
    }

    private static class CustomLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

        private static final long serialVersionUID = 1L;

        private final DateTimeFormatter formatterOnFailure;

        private CustomLocalDateTimeDeserializer(DateTimeFormatter formatterOnFailure) {
            super(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            this.formatterOnFailure = formatterOnFailure;
        }

        @Override
        public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            try {
                return super.deserialize(parser, context);
            } catch (IOException e) {
                String string = parser.getText().trim();
                return LocalDateTime.parse(string, formatterOnFailure);
            }
        }
    }

    private static class CustomLocalDateDeserializer extends LocalDateDeserializer {

        private static final long serialVersionUID = 1L;

        private final DateTimeFormatter formatterOnFailure;

        private CustomLocalDateDeserializer(DateTimeFormatter formatterOnFailure) {
            super(DateTimeFormatter.ISO_LOCAL_DATE);
            this.formatterOnFailure = formatterOnFailure;
        }

        @Override
        public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            try {
                return super.deserialize(parser, context);
            } catch (IOException e) {
                String string = parser.getText().trim();
                return LocalDate.parse(string, formatterOnFailure);
            }
        }
    }

    private static class CustomLocalTimeDeserializer extends LocalTimeDeserializer {

        private static final long serialVersionUID = 1L;

        private final DateTimeFormatter formatterOnFailure;

        private CustomLocalTimeDeserializer(DateTimeFormatter formatterOnFailure) {
            super(DateTimeFormatter.ISO_LOCAL_TIME);
            this.formatterOnFailure = formatterOnFailure;
        }

        @Override
        public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            try {
                return super.deserialize(parser, context);
            } catch (IOException e) {
                String string = parser.getText().trim();
                return LocalTime.parse(string, formatterOnFailure);
            }
        }
    }

    private static class CustomYearMonthDeserializer extends YearMonthDeserializer {

        private static final long serialVersionUID = 1L;

        private final DateTimeFormatter formatterOnFailure;

        private CustomYearMonthDeserializer(DateTimeFormatter formatterOnFailure) {
            super(DateTimeFormatter.ofPattern("uuuu-MM"));
            this.formatterOnFailure = formatterOnFailure;
        }

        @Override
        public YearMonth deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            try {
                return super.deserialize(parser, context);
            } catch (IOException e) {
                return YearMonth.parse(parser.getText().trim(), formatterOnFailure);
            }
        }
    }

    private static class CustomMonthDayDeserializer extends MonthDayDeserializer {

        private static final long serialVersionUID = 1L;

        private final DateTimeFormatter formatterOnFailure;

        private CustomMonthDayDeserializer(DateTimeFormatter formatterOnFailure) {
            super(null);
            this.formatterOnFailure = formatterOnFailure;
        }

        @Override
        public MonthDay deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            try {
                return super.deserialize(parser, context);
            } catch (IOException e) {
                return MonthDay.parse(parser.getText().trim(), formatterOnFailure);
            }
        }
    }
}
