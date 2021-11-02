package top.felixu.common.func;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 包装Function函数式接口可能出现的受检异常
 *
 * @author felixu
 * @date 2018.12.27
 */
@FunctionalInterface
public interface FunctionWrapper<T, R, E extends RuntimeException> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws Throwable the exception
     */
    R accept(T t) throws Throwable;

    /**
     * wrapper checked exception
     *
     * @param wrapper the function wrapper
     * @param <T> the type of the input to the function
     * @param <R> the type of the result of the function
     * @param <E> the exception to the thrown of the function
     * @param exceptionSupplier The supplier which will return the exception to be thrown
     * @return the wrapped function
     */
    static <T, R, E extends RuntimeException> Function<T, R> wrapper(FunctionWrapper<T, R, E> wrapper, Supplier<? extends E> exceptionSupplier) {
        return t -> {
            try {
                return wrapper.accept(t);
            } catch (Throwable ex) {
                throw exceptionSupplier.get();
            }
        };
    }
}
