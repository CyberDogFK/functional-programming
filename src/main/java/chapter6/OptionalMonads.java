package chapter5;

import java.util.Optional;
import java.util.function.Function;

public class OptionalMonads {

    public static void main(String[] args) {
        Function<? super String, Optional<String>> toConcatString =
                x -> Optional.of(x + "ing");
        Function<? super String, Optional<String>> toUpperString =
                x -> Optional.of(x.toUpperCase());

        var ostring = Optional.of("go");
        var result = ostring
                .flatMap(toConcatString)
                .flatMap(toUpperString);
        System.out.println(ostring.get());
        System.out.println(result.get());
    }
}
