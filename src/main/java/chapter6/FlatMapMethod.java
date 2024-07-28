package chapter6;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Function;

public class FlatMapMethod {
    public static void main(String[] args) {
        Optional<Integer> one = Optional.of(1);
        Optional<String> ostring = Optional.of("go");

        Optional<Integer> plusOne = one.flatMap(
                n -> Optional.of(n + 1));
        System.out.println(plusOne.get());

        Function<? super Integer, Optional<Integer>> plusOneFunction =
                n -> Optional.of(n + 1);
        plusOne = one.flatMap(plusOneFunction);
        System.out.println(plusOne.get());
        one = Optional.empty();
        System.out.println("Assigned empty optional to one");

        System.out.println("Going to call flat map");
        plusOne = one.flatMap(x -> {
            System.out.println("Flat map one");
            return Optional.of(x + 1);
        });

        System.out.println("Going to call map on one");
        plusOne = one.map(x -> {
            System.out.println("Mapping one");
            return x + 1;
        });
        System.out.println(plusOne.get());
    }
}
