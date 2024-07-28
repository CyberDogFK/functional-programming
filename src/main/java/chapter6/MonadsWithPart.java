package chapter6;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MonadsWithPart {
    public static void main(String[] args) {
        Map<Integer, Part> parts = new HashMap<>();
        parts.put(123, new Part(123, "bolt"));
        parts.put(456, new Part(456, "nail"));
        parts.put(789, new Part(789, "wire"));

        Optional<Part> optPart = Optional.empty();
        try {
            optPart = Optional.of(parts.get(999));
            System.out.println(
                    optPart
                            .flatMap(x -> x.outOfStock(true))
                            .flatMap(x -> x.partName(
                                    x.getPartName() + "-Out-of-stock"
                            ))
                            .flatMap(Part::replicatePartMonad)
                            .orElseThrow(() -> new RuntimeException("NO parts with number: " + 999))
            );
            System.out.println(optPart);
        } catch (RuntimeException ex) {
            System.out.println("Exception: " + optPart);
            throw new RuntimeException(ex);
        }
    }
}
