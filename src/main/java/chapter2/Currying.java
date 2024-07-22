package chapter2;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying implements Runnable {
    @Override
    public void run() {
        BiFunction<String, String, String> biFunctionConcat =
                (a, b) -> a + b;
        System.out.println(biFunctionConcat.apply("Cat", "Dog"));

        Function<String, Function<String, String>> curryConcat;
        curryConcat = (a) -> (b) -> biFunctionConcat.apply(a, b);
        Function<String, String> intermediateFunction;
        intermediateFunction = curryConcat.apply("Cat");
        System.out.println(intermediateFunction);
        System.out.println(curryConcat.apply("Cat"));

        System.out.println(intermediateFunction.apply("Dog"));

        System.out.println(curryConcat.apply("Cat").apply("Dog"));
        System.out.println(curryConcat.apply(
                "Flying ").apply("Monkeys"));
    }

    public static void main(String[] args) {
        new Currying().run();
    }
}
