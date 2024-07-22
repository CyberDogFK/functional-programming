package chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class HigherOrderFunctions {
    public static void main(String[] args) {
        final var hof = new HigherOrderFunctions();
        hof.run2();
    }

    public void run(String[] args) {
        var list = List.of("AAA", "BBB", "CCC");
        for (String element : list) {
            System.out.println(element.toLowerCase());
        }
        list.forEach(s -> System.out.println(
                processString(String::toLowerCase, s)
        ));
        list.forEach(s -> System.out.println(
                processString(String::toUpperCase, s)
        ));
    }

    public void run2() {
        List<String> numberString = Arrays.asList("12", "34", "82");
        List<Integer> numbers = new ArrayList<>();
        List<Integer> doubleNumbers = new ArrayList<>();
        for (String num : numberString) {
            numbers.add(Integer.parseInt(num));
        }
        numbers.clear();
        numberString
                .forEach(s -> numbers.add(Integer.parseInt(s)));

        Function<List<String>, List<Integer>> singleFunction = s -> {
            s.forEach(t -> numbers.add(Integer.parseInt(t)));
            return numbers;
        };
        Function<List<String>, List<Integer>> doubleFunction = s -> {
            s.forEach(t -> doubleNumbers.add(
                    Integer.parseInt(t) * 2
            ));
            return doubleNumbers;
        };
        numbers.clear();
        System.out.println(singleFunction.apply(numberString));
        System.out.println(doubleFunction.apply(numberString));
        Stream.of(numberString)
                .map(doubleFunction)
                .forEach(System.out::println);
    }

    public String processString(Function<String, String> operation, String target) {
        return operation.apply(target);
    }
}
