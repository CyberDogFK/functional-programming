package chapter2;

import java.util.function.BiFunction;
import java.util.function.Function;

public class EliminatingDependencies {
    public void run() {
        BiFunction<Integer, Double, Double> computeHourly =
                (hours, rate) -> hours * rate;
        Function<Double, Double> computeSalary = rate -> rate * 40.0;
        BiFunction<Double, Double, Double> computeSales =
                (rate, commision) -> rate * 40.0 + commision;

        double hourlyPay = computeHourly.apply(35, 12.75);
        double salaryPay = computeSalary.apply(25.35);
        double salesPay = computeSales.apply(8.75, 2500.0);

        System.out.println(computeHourly.apply(35, 12.75)
         + computeSalary.apply(25.35)
         + computeSales.apply(8.75, 2500.00));

        double total = 0.0;
        boolean hourly = false;
        if (hourly) {
            total = hourlyPay;
        } else {
            total = salaryPay + salesPay;
        }
        System.out.println(total);
    }

    public static void main(String[] args) {

    }
}
