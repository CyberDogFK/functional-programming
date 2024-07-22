package chapter2;

import java.util.function.Function;

public class ClosureInJava {
    int instanceLength;

    public Function<String, String> getStringOperation() {
        final String separator = ":";

        return target -> {
            int localLength = target.length();
            instanceLength = target.length();
            return target.toLowerCase()
                    + separator + instanceLength + separator
                    + localLength;
        };
    }

    public static void main(String[] args) {
        ClosureInJava ce = new ClosureInJava();
        final Function<String, String> functino = ce.getStringOperation();
        System.out.println(functino.apply("Closure"));
    }
}
