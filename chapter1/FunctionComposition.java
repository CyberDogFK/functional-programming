package chapter1;

import java.util.function.Function;

public class FunctionComposition {
    public static void main() {
        Function<Integer, Integer> negateThenAbs =
                ((Function<Integer, Integer>)Math::negateExact)
                        .andThen(Math::abs);
        System.out.println(negateThenAbs.apply(-25));
        System.out.println(negateThenAbs.apply(25));
    }
}
