package chapter6;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Part {
    private int partNumber;
    private String partName;
    private boolean outOfStock;

    public Part(int partNumber, String partName) {
        this.partNumber = partNumber;
        this.partName = partName;
    }

    public boolean isOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public Optional<Part> outOfStock(boolean outOfStock) {
        this.outOfStock = outOfStock;
        return Optional.of(this);
    }

    public Part setPartName(String partName) {
        this.partName = partName;
        return this;
    }

    public Optional<Part> partName(String partName) {
        this.partName = partName;
        return Optional.of(this);
    }

    public int getPartNumber() {
        return partNumber;
    }

    public String getPartName() {
        return partName;
    }

    public Optional<Part> replicatePartMonad() {
        // Replicate part
        System.out.println("Part replicated: " + this);
        return Optional.of(this);
    }

    @Override
    public String toString() {
        return "Part{" + "partNumber=" + partNumber + "," +
                "partName=" + partName + ", outOfStock=" + outOfStock
                + "}";
    }

    private static void replicatePart(Part part) {
        // Replicate part
        System.out.println("Part replicated: " + part);
    }

    public static void main(String[] args) {
        Map<Integer, Part> parts = new HashMap<>();
        parts.put(123, new Part(123, "bolt"));
        parts.put(456, new Part(123, "nail"));
        parts.put(789, new Part(123, "wire"));

        int partId = 123;
        Part part = parts.get(partId);
        part.setOutOfStock(true);
        part.setPartName(part.getPartName() + "-Out-Of-Stock");
        replicatePart(part);

        Optional<Part> optPart = Optional.of(parts.get(456));
        System.out.println(
                optPart
                        .flatMap(x -> x.outOfStock(true))
                        .flatMap(x -> x.partName(x.getPartName() +
                                "-Out-of-stock"))
                        .flatMap(Part::replicatePartMonad)
                        .orElseThrow(RuntimeException::new)
        );
    }
}
