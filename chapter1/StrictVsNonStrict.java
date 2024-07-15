package chapter1;

import java.util.Random;

public class StrictVsNonStrict {
    public static void main() {
        Random random = new Random();
        random.ints()
                .limit(5)
                .sorted()
                .forEach(System.out::println);
    }
}
