package top.felixu.common.func;

import java.util.function.Supplier;

/**
 * @author felixu
 * @since 2021.11.02
 */
@FunctionalInterface
public interface SupplierWrapper<T, E extends RuntimeException> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws Throwable possible errors
     */
    T get() throws Throwable;

    /**
     * wrapper checked exception
     *
     * @param wrapper the operation wrapper
     * @param <T> the type of the input to the operation
     * @param <E> the exception to the thrown of the operation
     * @param exceptionSupplier The supplier which will return the exception to be thrown
     * @return the wrapped operation
     */
    static <T, E extends RuntimeException> T wrapper(SupplierWrapper<T, E> wrapper, Supplier<? extends E> exceptionSupplier) {
        try {
            return wrapper.get();
        } catch (Throwable e) {
            throw exceptionSupplier.get();
        }
    }
}
