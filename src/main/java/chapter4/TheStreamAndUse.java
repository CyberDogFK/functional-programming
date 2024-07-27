package chapter4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class TheStreamAndUse implements Runnable {
    public void run() {
        int[] numbers = { 3, 6, 8, 8, 4, 6, 3, 3, 5, 6, 9, 4, 3, 6};
        Set<Integer> numberSet = new HashSet<>();
        for (int number : numbers) {
            numberSet.add(number);
        }
        int total = 0;
        for (int number : numberSet) {
            total += number;
        }

        total = Arrays.stream(numbers)
                .distinct()
                .sum();

        IntStream stream = Arrays.stream(numbers);
        total = stream
                .distinct()
                .sum();
        total = stream.sum();
    }

    public static void main(String[] args) {

    }
}
