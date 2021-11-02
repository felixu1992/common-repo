package top.felixu.common.func;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 包装 Consumer 函数式接口可能出现的受检异常
 *
 * @author felixu
 * @date 2019.07.04
 */
@FunctionalInterface
public interface ConsumerWrapper<T, E extends RuntimeException> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws Throwable the exception
     */
    void accept(T t) throws Throwable;

    /**
     * wrapper checked exception
     *
     * @param wrapper the operation wrapper
     * @param <T> the type of the input to the operation
     * @param <E> the exception to the thrown of the operation
     * @param exceptionSupplier The supplier which will return the exception to be thrown
     * @return the wrapped operation
     */
    static <T, E extends RuntimeException> Consumer<T> wrapper(ConsumerWrapper<T, E> wrapper, Supplier<? extends E> exceptionSupplier) {
        return t -> {
            try {
                wrapper.accept(t);
            } catch (Throwable e) {
                throw exceptionSupplier.get();
            }
        };
    }
}
