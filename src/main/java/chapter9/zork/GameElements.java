package chapter9.zork;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GameElements {
    public static Map<String, Location> locations = new HashMap<>();
    public static Map<String, Item> items = new HashMap<>();
    public static Map<String, NPC> npcs = new HashMap<>();
    public static final Map<String, Supplier<Boolean>> commands = new HashMap<>();
    public static Location currentLocation;

    public static void displayView(Location location) {
        System.out.println(location.getDescription());
        GameElements.currentLocation.displayItems();
        GameElements.currentLocation.displayNPCs();
    }
}
