package chapter2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Lambda {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Huey", "Duey", "Luey");
        Consumer<String> consumer = System.out::println;
        list.forEach(consumer);
    }
}
