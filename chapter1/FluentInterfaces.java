package chapter1;

import java.time.LocalDate;

public class FluentInterfaces {
    public static void main() {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusYears(2);
        futureDate = futureDate.minusMonths(1);
        futureDate = futureDate.plusDays(3);
        System.out.println(today);
        System.out.println(futureDate);
        System.out.println();

        futureDate = LocalDate.now()
                .plusYears(2)
                .minusMonths(1)
                .plusDays(3);
        System.out.println(futureDate);
    }
}
