package chapter7;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

public class ExecuteAround {
    public static void main(String[] args) {
        new ExecuteAround().run();
    }

    public void run() {
        System.out.println(executeImperativeSquareSolution(5));
        System.out.println(executeImperativeCubeSolution(5));
        Function<Integer, Integer> computeSquare = x -> x * x;
        Function<Integer, Integer> computeCube = x -> x * x * x;
        System.out.println(executeDuration(computeSquare, 5));
        System.out.println(executeDuration(computeCube, 5));

        System.out.println(executeWithLog(x -> x * x, 5));
        System.out.println(executeWithLog(computeSquare, 5));
        System.out.println(withLog(5));

        int result = executeDuration(x -> x * x, 5);
        System.out.println(withLog(result));

        System.out.println(withLog(executeDuration(x -> x * x, 5)));
        System.out.println(executeDuration(x -> x * x, withLog(5)));

        System.out.println(
                executeBefore(ExecuteAround::withLog, computeSquare, 5)
        );
        System.out.println(
                executeAfter(computeSquare, ExecuteAround::withLog, 5)
        );

        Function<Integer, Integer> computeDuration =
                x -> executeDuration(computeSquare, x);
        Integer[] arr = {1, 2, 3, 4, 5};
        Stream<Integer> myStream = Arrays.stream(arr);
        myStream
                .map(computeDuration)
                .forEach(System.out::println);
    }

    public int executeBefore(
            Function<Integer, Integer> beforeFunction,
            Function<Integer, Integer> function,
            Integer value
    ) {
        beforeFunction.apply(value);
        return function.apply(value);
    }

    public int executeAfter(
            Function<Integer, Integer> function,
            Function<Integer, Integer> afterFunction,
            Integer value
    ) {
        int result = function.apply(value);
        afterFunction.apply(result);
        return result;
    }

    private static int withLog(int value) {
        System.out.print("Operation logged for " + value + " - ");
        return value;
    }

    private static int executeWithLog(
            Function<Integer, Integer> consumer, int value
    ) {
        System.out.print("Operation logged for " + value + " - ");
        return consumer.apply(value);
    }

    public int executeImperativeSquareSolution(int value) {
        long start = System.currentTimeMillis();
        int result = 0;
        try {
            // Perform computation
            result = value * value;
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.print("Duration: " + duration + " - ");
        return result;
    }

    public int executeImperativeCubeSolution(int value) {
        long start = System.currentTimeMillis();
        int result = 0;
        try {
            // Perform computation
            result = value * value * value;
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.print("Duration: " + duration + " - ");
        return result;
    }

    public int executeDuration(
            Function<Integer, Integer> computation, int value) {
        long start = System.currentTimeMillis();
        int result = 0;
        try {
            result = computation.apply(value);
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.print("Duration: " + duration + " - ");
        return result;
    }
}
