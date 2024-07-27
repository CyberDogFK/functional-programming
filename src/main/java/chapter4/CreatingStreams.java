package chapter4;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CreatingStreams {
    public static void main(String[] args) {
        IntStream.iterate(0, n -> n + 1)
                .limit(10)
                .forEach(n -> System.out.print(n + " "));

        Stream.iterate("Going", m -> m + "...")
                .limit(5)
                .forEach(System.out::println);

        IntStream.iterate(0, n -> (n + 1) % 2)
                .distinct()
                .limit(10)
                .forEach(System.out::println);
    }
}

class Rectangle {
    private int x;
    private int y;
    private int height;
    private int width;

    public Rectangle(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Rectangle scale(double percent) {
        height = (int) (height * (1.0 + percent));
        width = (int) (width * (1.0 + percent));
        return this;
    }

    public int getArea() {
        return height * width;
    }

    public String toString() {
        return "X: " + x + " Y: " + y
                + " Height: " + height + " Width: " + width;
    }
}
