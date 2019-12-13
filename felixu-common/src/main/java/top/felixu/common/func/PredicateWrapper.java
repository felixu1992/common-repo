package top.felixu.common.func;

import java.util.function.Predicate;

/**
 * 包装Predicate函数式接口可能出现的受检异常
 *
 * @author felixu
 * @date 2019.07.04
 */
@FunctionalInterface
public interface PredicateWrapper<T, E extends Exception> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     * @throws E possible errors
     */
    boolean test(T t) throws E;

    /**
     * wrapper checked exception
     *
     * @param wrapper the predicate wrapper
     * @param <T> the type of the input to the predicate
     * @param <E> the exception of the thrown of the predicate
     * @return the wrapped predicate
     */
    static <T, E extends Exception> Predicate<T> wrapper(PredicateWrapper<T, E> wrapper) {
        return t -> {
            try {
                return wrapper.test(t);
            } catch (Exception e) {
                // TODO 按项目实际情况处理异常
                throw new RuntimeException(e);
            }
        };
    }
}
