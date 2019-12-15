package top.felixu.common.enums;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * 所有需要传给前端或由前端传来的枚举都必须实现该接口，并放在该包内！
 * 所有枚举类和实例都请务必好好起名，是要展示给用户和存入数据库的，名称一旦确定将不允许再修改！
 *
 * @author felixu
 * @since 2019.12.15
 */
public interface Describable {

    /**
     * 获得枚举的描述，该描述将被系统传给前端，用于显示给用户，请谨慎设置
     *
     * @return 对枚举的描述
     */
    String getDesc();

    /**
     * 是否默认
     *
     * @return 默认
     */
    default boolean isDefault() {
        return false;
    }

    static <T extends Enum> Optional<T> ofDesc(String desc, Class<T> clazz) {
        if (!clazz.isEnum() || !Describable.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException();
        }
        try {
            Method method = clazz.getDeclaredMethod("values");
            method.setAccessible(true);
            Describable[] enums = (Describable[]) method.invoke(null);
            for (Describable anEnum : enums) {
                if (Objects.equals(desc, anEnum.getDesc())) {
                    return Optional.of((T) anEnum);
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    static <T extends Enum> Optional<T> ofDefault(Class<T> clazz) {
        if (!clazz.isEnum() || !Describable.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException();
        }
        try {
            Method method = clazz.getDeclaredMethod("values");
            method.setAccessible(true);
            Describable[] enums = (Describable[]) method.invoke(null);
            for (Describable anEnum : enums) {
                if (anEnum.isDefault()) {
                    return Optional.of((T) anEnum);
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
