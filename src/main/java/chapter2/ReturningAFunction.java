package chapter2;

import java.util.Arrays;
import java.util.function.BiFunction;

public class ReturningAFunction {
    public static void main(String[] args) {
        final var raf = new ReturningAFunction();
        raf.run_imperative();
    }

    public enum EmployeeType {
        Hourly, Salary, Sales
    }

    public void run_imperative() {
        int[] hoursWorked = {8, 12, 8, 6, 6, 5, 6, 0};
        int totalHoursWorked = 0;
        for (int hour : hoursWorked) {
            totalHoursWorked += hour;
        }
        totalHoursWorked = Arrays.stream(hoursWorked).sum();

        System.out.println(
                calculatePay(totalHoursWorked, 15.75f,
                        EmployeeType.Hourly)
        );

        System.out.println(
                calculatePayFunction(EmployeeType.Hourly)
                        .apply(totalHoursWorked, 15.75f)
        );
    }

    public void run_functional() {

    }

    public BiFunction<Integer, Float, Float> calculatePayFunction(EmployeeType type) {
        return switch (type) {
            case Hourly -> (hours, payRate) -> hours * payRate;
            case Salary -> (hours, payRate) -> 40 * payRate;
            case Sales -> (hours, payRate) -> 500f + 0.15f * payRate;
        };
    }

    public float calculatePay(int hoursWorked,
                              float payRate, EmployeeType type) {
        switch (type) {
            case Hourly:
                return hoursWorked * payRate;
            case Salary:
                return 40 * payRate;
            case Sales:
                return 500.0f + 0.15f * payRate;
            default:
                return 0.0f;
        }
    }


}
