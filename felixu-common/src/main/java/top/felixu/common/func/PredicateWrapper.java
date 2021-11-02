package top.felixu.common.func;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 包装Predicate函数式接口可能出现的受检异常
 *
 * @author felixu
 * @date 2019.07.04
 */
@FunctionalInterface
public interface PredicateWrapper<T, E extends RuntimeException> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     * @throws Throwable possible errors
     */
    boolean test(T t) throws Throwable;

    /**
     * wrapper checked exception
     *
     * @param wrapper the predicate wrapper
     * @param <T> the type of the input to the predicate
     * @param <E> the exception to the thrown of the predicate
     * @return the wrapped predicate
     */
    static <T, E extends RuntimeException> Predicate<T> wrapper(PredicateWrapper<T, E> wrapper, Supplier<? extends E> exceptionSupplier) {
        return t -> {
            try {
                return wrapper.test(t);
            } catch (Throwable ex) {
                throw exceptionSupplier.get();
            }
        };
    }
}
