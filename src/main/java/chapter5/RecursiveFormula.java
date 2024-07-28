package chapter5;

public class RecursiveFormula {
    public static void main(String[] args) {
        new RecursiveFormula().run();
    }

    public void run() {
        System.out.println(gcd(48, 72));
        System.out.println(gcd(182, 154));

        System.out.println(fib(11));
        System.out.println(fibIterative(11));
    }

    public int gcd(int x, int y) {
        if (y == 0) {
            return x;
        } else {
            return gcd(y, x % y);
        }
    }

    public int fib(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    public int fibIterative(int n) {
        int[] arr = new int[15];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i < 15; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[11];
    }
}
