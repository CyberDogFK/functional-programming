package chapter1;

public class Recursion {
    public static void main() {
        int result = 1;
        for (int i = 5; i >= 1; i--) {
            result = result * i;
        }
        System.out.println(result);

        int num = 5;
        System.out.println(factorial(num));
    }

    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
