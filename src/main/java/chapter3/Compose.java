package chapter3;

import java.util.Objects;
import java.util.function.Function;

public class Compose {
    public interface CompositionFunction<T, R> {
        R call(T x);
    }

    public static <T, U, R> CompositionFunction<T, R> compose(
            final CompositionFunction<U, R> f,
            final CompositionFunction<T, U> g) {
        return x -> f.call(g.call(x));
    }

    public static void main(String[] args) {
        CompositionFunction<Double, Double> doubleNumber=
                x -> 2 * x;
        CompositionFunction<Double, Double> negateNumber =
                x -> -x;
        CompositionFunction<Double, Double> doubleThenNegate =
                Compose.compose(doubleNumber, negateNumber);
        System.out.println(doubleThenNegate.call(5.0));

        Function<Double, Double> doubleFunction = x -> 2 * x;
        Function<Double, Double> second = doubleFunction.compose(x -> -x);
        System.out.println(second.apply(5.0));
    }

    @FunctionalInterface
    public interface Function<T, R> {
        R apply(T t);

        default <V> Function<V, R> compose(
                Function<? super V, ? extends T> before) {
            Objects.requireNonNull(before);
            return (V v) -> apply(before.apply(v));
        }

        default <V> Function<T, V> andThen(
                Function<? super R, ? extends V> after
        ) {
            Objects.requireNonNull(after);
            return (T t) -> after.apply(apply(t));
        }

        static <T> Function<T, T> identity() {
            return t -> t;
        }
    }
}
