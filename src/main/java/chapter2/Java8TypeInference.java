package chapter2;

interface StringConcatenation {
    String concatenate(String s1, String s2);
}

interface IntegerConcatenation {
    String concatenate(Integer n1, Integer n2);
}

interface DoubleConcatenation {
    String concatenate(Double n1, Double n2);
}

interface Concatenation<T> {
    public String concatenate(T u, T v);
}

public class Java8TypeInference {
    public static void main(String[] args) {
        StringConcatenation sc = (s, t) -> s + ":" + t;
        IntegerConcatenation ic = (m, n) -> m + ":" + n;
        DoubleConcatenation dc = (m, n) -> m + ":" + n;
        System.out.println(sc.concatenate("Cat", "Dog"));
        System.out.println(ic.concatenate(23, 45));
        System.out.println(dc.concatenate(23.12, 45.12));

        Concatenation<String> stringConcatenation = (s, t) -> s + ":" + t;
        Concatenation<Integer> integerConcatenation = (s, t) -> s + ":" + t;
        System.out.println(
                stringConcatenation.concatenate("Cat", "Dog")
        );
        System.out.println(integerConcatenation.concatenate(23, 45));
    }
}
