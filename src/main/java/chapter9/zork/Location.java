package chapter9.zork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Location {
    private String name;
    private String description;
    private final List<String> items = new ArrayList<>();
    private final List<String> npcs = new ArrayList<>();
    private final Map<String, Direction> directions = new HashMap<>();

    public Location name(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Optional<String> getLocation(String direction) {
        if (this.directions.containsKey(direction)) {
            return Optional.of(this.directions.get(direction).getLocation());
        } else {
            return Optional.empty();
        }
    }

    public Location description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public List<String> getItems() {
        return this.items;
    }

    public void addItem(String item) {
        this.items.add(item);
    }

    public void displayItems() {
        if (items.isEmpty()) {

        } else {
            System.out.println("On the ground you see:");
            items.stream()
                    .forEach(item -> {
                        System.out.print(" " + item);
                    });
            System.out.println();
        }
    }

    public void addNPC(String npc) {
        this.npcs.add(npc);
    }

    public List<String> getNPCs() {
        return npcs;
    }

    public void displayNPCs() {
        if (npcs.isEmpty()) {

        } else {
            npcs.forEach(System.out::println);
        }
    }

    public void addDirection(Direction direction) {
        directions.put(direction.getDirection(), direction);
    }

    public void addDirection(String direction, String location) {
        Direction newDirection = new Direction(direction, location);
        directions.put(direction, newDirection);
    }

    public void displayPaths() {
        directions.forEach((way, direction) -> {
            System.out.print("If you go " + way);
            System.out.println(" you can get to " + direction.getLocation());
        });
    }
}
