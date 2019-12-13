package top.felixu.common.func;

import java.util.function.Consumer;

/**
 * 包装Consumer函数式接口可能出现的受检异常
 *
 * @author felixu
 * @date 2019.07.04
 */
@FunctionalInterface
public interface ConsumerWrapper<T, E extends Exception> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);

    /**
     * wrapper checked exception
     *
     * @param wrapper the operation wrapper
     * @param <T> the type of the input to the operation
     * @param <E> the exception of the thrown of the operation
     * @return the wrapped operation
     */
    static <T, E extends Exception> Consumer<T> wrapper(ConsumerWrapper<T, E> wrapper) {
        return t -> {
            try {
                wrapper.accept(t);
            } catch (Exception e) {
                // TODO 按项目实际情况处理异常
                throw new RuntimeException(e);
            }
        };
    }
}
