package chapter2;

import java.util.function.Function;

public class ReferentialTransparency {
    public void run() {
        Function<Double, Double> pureFunction = t -> 3 * t;
        int num = 5;
        num = 6;
    }
}
