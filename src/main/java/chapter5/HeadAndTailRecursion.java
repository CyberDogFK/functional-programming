package chapter5;

public class HeadAndTailRecursion {
    public void head(String phrase) {
        if (phrase.isEmpty()) {
            return;
        }
        head(phrase.substring(1));
        System.out.print(phrase.charAt(0));
    }

    public void tail(String phrase) {
        if (phrase.isEmpty()) {
            return;
        }
        System.out.print(phrase.charAt(0));
        tail(phrase.substring(1));
    }

    public void run() {
        head("Recursion");
        System.out.println();
        tail("Recursion");
        System.out.println();
    }

    public static void main(String[] args) {
        new HeadAndTailRecursion().run();
    }
}
