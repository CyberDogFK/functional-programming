package first_test;

class FirstTest {
    final int a = 8;

    void someFunction() {
        System.out.println(a);
        final var sac = new SomeAnotherClass();
        sac.someAnotherFunction();
    }

    public static void main() {
        final var ft = new FirstTest();
        ft.someFunction();
    }
}

class SomeAnotherClass {
    final int b = 9;

    void someAnotherFunction() {
        System.out.println(b);
    }
}
