package top.felixu.common.bean;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对 Dozer 进行封装
 *
 * @author felixu
 * @since 2019.12.13
 */
public class BeanUtils {

    private static final Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

    /**
     * 使用原对象，产生新对象，并进行属性拷贝
     *
     * @param source    原对象
     * @param destClazz 目标类型
     * @param <T>       目标范型
     * @return 目标对象实例
     */
    public static <T> T map(Object source, Class<T> destClazz) {
        return MAPPER.map(source, destClazz);
    }

    /**
     * 使用原对象，产生新对象，并进行属性拷贝(只拷贝不为 null 的属性)
     *
     * @param srcClass  原始类型
     * @param source    原对象
     * @param destClazz 目标类型
     * @param <T>       目标范型
     * @return 目标对象实例
     */
    public static <T> T mapNotNull(Class<?> srcClass, Object source, Class<T> destClazz) {
        return getNotNullMapper(srcClass, destClazz).map(source, destClazz);
    }

    /**
     * 使用原对象，产生新对象，并进行属性拷贝(只拷贝不为 null 的属性)
     *
     * @param srcClass  原始类型
     * @param source    原对象
     * @param destClazz 目标类型
     * @param <T>       目标范型
     * @return 目标对象实例
     */
    public static <T> T mapNotBlank(Class<?> srcClass, Object source, Class<T> destClazz) {
        return getNotBlankMapper(srcClass, destClazz).map(source, destClazz);
    }

    /**
     * 使用原对象，产生新对象，并进行属性拷贝(只拷贝不为 null 的属性)
     *
     * @param srcClass  原始类型
     * @param source    原对象
     * @param destClazz 目标类型
     * @param <T>       目标范型
     * @return 目标对象实例
     */
    public static <T> T mapNotEmpty(Class<?> srcClass, Object source, Class<T> destClazz) {
        return getNotEmptyMapper(srcClass, destClazz).map(source, destClazz);
    }

    /**
     * 使用原对象集合，产生新对象集合，并进行属性拷贝
     *
     * @param source    原对象集合
     * @param destClazz 目标类型
     * @param <T>       目标范型
     * @return 目标对象实例集合
     */
    public static <T> List<T> map(List<?> source, Class<T> destClazz) {
        if (null == source)
            return Collections.emptyList();
        return source.stream().map(src -> map(src, destClazz)).collect(Collectors.toList());
    }

    /**
     * 使用原对象集合，产生新对象集合，并进行属性拷贝(只拷贝不为 null 的属性)
     *
     * @param srcClass  原始类型
     * @param source    原对象集合
     * @param destClazz 目标类型
     * @param <T>       目标范型
     * @return 目标对象实例集合
     */
    public static <T> List<T> mapNotNull(Class<?> srcClass, List<?> source, Class<T> destClazz) {
        if (null == source)
            return Collections.emptyList();
        return source.stream().map(src -> mapNotNull(srcClass, src, destClazz)).collect(Collectors.toList());
    }

    /**
     * 使用原对象集合，产生新对象集合，并进行属性拷贝(只拷贝不为 null 的属性)
     *
     * @param srcClass  原始类型
     * @param source    原对象集合
     * @param destClazz 目标类型
     * @param <T>       目标范型
     * @return 目标对象实例集合
     */
    public static <T> List<T> mapNotBlank(Class<?> srcClass, List<?> source, Class<T> destClazz) {
        if (null == source)
            return Collections.emptyList();
        return source.stream().map(src -> mapNotBlank(srcClass, src, destClazz)).collect(Collectors.toList());
    }

    /**
     * 使用原对象集合，产生新对象集合，并进行属性拷贝(只拷贝不为 null 的属性)
     *
     * @param srcClass  原始类型
     * @param source    原对象集合
     * @param destClazz 目标类型
     * @param <T>       目标范型
     * @return 目标对象实例集合
     */
    public static <T> List<T> mapNotEmpty(Class<?> srcClass, List<?> source, Class<T> destClazz) {
        if (null == source)
            return Collections.emptyList();
        return source.stream().map(src -> mapNotEmpty(srcClass, src, destClazz)).collect(Collectors.toList());
    }

    /**
     * 将指定对象的属性，设置到目标对象下
     *
     * @param source        原始对象
     * @param destination   目标对象
     */
    public static void copy(Object source, Object destination) {
        MAPPER.map(source, destination);
    }

    /**
     * 将指定对象的属性，设置到目标对象下(只拷贝不为 null 的属性)
     *
     * @param srcClass      原始类型
     * @param source        原始对象
     * @param destClass     目标类型
     * @param destination   目标对象
     */
    public static void copyNotNull(Class<?> srcClass, Object source, Class<?> destClass, Object destination) {
        getNotNullMapper(srcClass, destClass).map(source, destination);
    }

    /**
     * 将指定对象的属性，设置到目标对象下(只拷贝不为空字符的属性)
     *
     * @param srcClass      原始类型
     * @param source        原始对象
     * @param destClass     目标类型
     * @param destination   目标对象
     */
    public static void copyNotBlank(Class<?> srcClass, Object source, Class<?> destClass, Object destination) {
        getNotBlankMapper(srcClass, destClass).map(source, destination);
    }

    /**
     * 将指定对象的属性，设置到目标对象下(只拷贝不为 null 不为空字符的属性)
     *
     * @param srcClass      原始类型
     * @param source        原始对象
     * @param destClass     目标类型
     * @param destination   目标对象
     */
    public static void copyNotEmpty(Class<?> srcClass, Object source, Class<?> destClass, Object destination) {
        getNotEmptyMapper(srcClass, destClass).map(source, destination);
    }

    private static Mapper getNotNullMapper(Class<?> srcClass, Class<?> destClass) {
        return DozerBeanMapperBuilder.create().withMappingBuilder(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(srcClass, destClass, TypeMappingOptions.oneWay(), TypeMappingOptions.mapNull(false));
            }
        }).build();
    }

    private static Mapper getNotBlankMapper(Class<?> srcClass, Class<?> destClass) {
        return DozerBeanMapperBuilder.create().withMappingBuilder(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(srcClass, destClass, TypeMappingOptions.oneWay(), TypeMappingOptions.mapEmptyString(false));
            }
        }).build();
    }

    private static Mapper getNotEmptyMapper(Class<?> srcClass, Class<?> destClass) {
        return DozerBeanMapperBuilder.create().withMappingBuilder(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(srcClass, destClass, TypeMappingOptions.oneWay(), TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(false));
            }
        }).build();
    }
}
