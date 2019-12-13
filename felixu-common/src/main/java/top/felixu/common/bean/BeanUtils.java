package top.felixu.common.bean;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

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
     * @param source 原对象
     * @param destClazz 目标类型
     * @param <T> 目标范型
     * @return 目标对象实例
     */
    public static <T> T map(Object source, Class<T> destClazz) {
        return MAPPER.map(source, destClazz);
    }

    /**
     * 使用原对象集合，产生新对象集合，并进行属性拷贝
     *
     * @param source 原对象集合
     * @param destClazz 目标类型
     * @param <T> 目标范型
     * @return 目标对象实例集合
     */
    public static <T> List<T> map(List<Object> source, Class<T> destClazz) {
        if (null == source)
            return Collections.emptyList();
        return source.stream().map(src -> map(src, destClazz)).collect(Collectors.toList());
    }

    public static void copy(Object source, Object destination) {
        MAPPER.map(source, destination);
    }
//    public static Map<String, Object> toMap(Object source) {
//
//    }
}
