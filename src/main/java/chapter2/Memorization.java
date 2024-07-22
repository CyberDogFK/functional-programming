package chapter2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


class Memoizer<T, U> {
    private final Map<T, U> memoizationCache = new ConcurrentHashMap<>();

    private Function<T, U> doMemoize(final Function<T, U> function) {
        return input -> memoizationCache.computeIfAbsent(input, function);
    }

    public static <T, U> Function<T, U> memoize(final Function<T, U> function) {
        return new Memoizer<T, U>().doMemoize(function);
    }
}

public class Memorization {
    private final Map<Integer, Integer> memoizationCache = new HashMap<>();

    public void run() {
        System.out.println(computeExpensiveSquare(4));
        System.out.println(computeExpensiveSquare(4));
        System.out.println();

        Function<Integer, Integer> squareFunction = x -> {
            System.out.println("In  function");
            return x * x;
        };

        Function<Integer, Integer> memoizationFunction = Memoizer.memoize(squareFunction);
        System.out.println(memoizationFunction.apply(2));
        System.out.println(memoizationFunction.apply(2));
        System.out.println(memoizationFunction.apply(2));
        System.out.println();

        Function<Double, Double> memoizationFunction2 = Memoizer.memoize(x -> x * x);
        System.out.println(memoizationFunction2.apply(4.0));
    }

    public Integer computeExpensiveSquare(Integer input) {
        if (!memoizationCache.containsKey(input)) {
            memoizationCache.put(input,
                    doComputeExpensiveSquare(input));
        }
        return memoizationCache.get(input);
    }

    private Integer doComputeExpensiveSquare(Integer input) {
        System.out.println("Computing square");
        return input * input;
    }

    public static void main(String[] args) {
        final var mem = new Memorization();
        mem.run();
    }
}
