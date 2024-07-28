package chapter4;

import java.util.Scanner;
import java.util.stream.Stream;

public class InfinityStream {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stream.iterate(scanner.next(), s -> scanner.next())
                .forEach(System.out::println);

    }
}
