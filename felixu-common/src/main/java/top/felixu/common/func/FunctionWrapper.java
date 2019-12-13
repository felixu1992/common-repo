package top.felixu.common.func;

import java.util.function.Function;

/**
 * 包装Function函数式接口可能出现的受检异常
 *
 * @author felixu
 * @date 2018.12.27
 */
@FunctionalInterface
public interface FunctionWrapper<T, R, E extends Exception> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws E the exception
     */
    R accept(T t) throws E;

    /**
     * wrapper checked exception
     *
     * @param wrapper the function wrapper
     * @param <T> the type of the input to the function
     * @param <R> the type of the result of the function
     * @param <E> the exception of the thrown of the function
     * @return the wrapped function
     */
    static <T, R, E extends Exception> Function<T, R> wrapper(FunctionWrapper<T, R, E> wrapper) {
        return t -> {
            try {
                return wrapper.accept(t);
            } catch (Exception e) {
                // TODO 按项目实际情况处理异常
                throw new RuntimeException(e);
            }
        };
    }
}
