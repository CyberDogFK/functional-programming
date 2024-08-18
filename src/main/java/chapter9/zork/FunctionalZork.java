package chapter9.zork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FunctionalZork {
    private final Scanner scanner;
    private Character character = null;
    private final FunctionalCommands fc;
    public static final Map<String, Supplier<Boolean>> commands = new HashMap<>();
    private final Command command = new Command();

    private Supplier<Boolean> dropCommand = () -> character.drop(command);
    private Supplier<Boolean> pickupCommand = () -> character.pickup(command);
    private Supplier<Boolean> walkCommand = () -> character.walk(command);
    private Supplier<Boolean> inventoryCommand = () -> character.inventory(command);
    private Supplier<Boolean> lookCommand = () -> {
        GameElements.displayView(GameElements.currentLocation);
        return true;
    };
    private Supplier<Boolean> directionsCommand = () -> {
        GameElements.currentLocation.displayPaths();
        return true;
    };
    private final Supplier<Boolean> quitCommand = () -> {
        System.out.println("Thank you for playing!");
        return true;
    };

    public FunctionalZork() {
        scanner = new Scanner(System.in);
        fc = new FunctionalCommands();
        initializeGame();
        character = new Character(GameElements.currentLocation);
    }

    public void initializeCommands() {
        commands.put("drop", dropCommand);
        commands.put("Drop", dropCommand);
        commands.put("pickup", pickupCommand);
        commands.put("Pickup", pickupCommand);
        commands.put("walk", walkCommand);
        commands.put("go", walkCommand);
        commands.put("inventory", inventoryCommand);
        commands.put("inv", inventoryCommand);
        commands.put("look", lookCommand);
        commands.put("directions", directionsCommand);
        commands.put("dir", directionsCommand);
        commands.put("quit", quitCommand);
    }

    public void initializeGame() {
        System.out.println("Welcome to Functional Zork!\n");
        File file = new File("data.txt");
        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line = br.readLine();
            while ("Location".equalsIgnoreCase(line)) {
                Location location = new Location()
                        .name(br.readLine())
                        .description(br.readLine());
                line = br.readLine();
                while ("Direction".equalsIgnoreCase(line)) {
                    // Add direction
                    location.addDirection(new Direction()
                            .direction(br.readLine())
                            .location(br.readLine()));
                    line = br.readLine();
                }
                while ("Item".equalsIgnoreCase(line)) {
                    // Add items
                    Item item = new Item()
                            .name(br.readLine())
                            .description(br.readLine());
                    location.addItem(item.getName());
                    GameElements.items.put(item.getName(), item);
                    line = br.readLine();
                }
                while("NPC".equalsIgnoreCase(line)) {
                    // Add NPC
                    NPC npc = new NPC()
                            .name(br.readLine())
                            .description(br.readLine());
                    location.addNPC(npc.getName());
                    GameElements.npcs.put(npc.getName(), npc);
                    line = br.readLine();
                }
                GameElements.locations.put(location.getName(),
                        location);
            }
            if ("StartingLocation".equalsIgnoreCase(line)) {
                GameElements.currentLocation = GameElements.locations.get(br.readLine());
                GameElements.displayView(GameElements.currentLocation);
            } else {
                System.out.println("Missing Starting Location");
            }
            initializeCommands();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stream<String> getCommandStream() {
        String commandLine = scanner.nextLine();

        // Stop words
        List<String> toRemove = Arrays.asList("a", "an", "the", "and");
        return Pattern
                .compile("\\s+")
                .splitAsStream(commandLine)
                //.map(s -> s.toLowerCase()
                .filter(s -> !toRemove.contains(s));
    }

    public void parseCommandStream(Stream<String> tokens) {
        command.clear();
        tokens.map(token -> {
            if (commands.containsKey(token)) {
                command.setCommand(token);
            } else {
                command.addArgument(token);
            }
            return token;
        })
                .allMatch(token -> !"quit".equalsIgnoreCase(token));
    }

    public String executeCommand() {
        Supplier<Boolean> nextCommand =
                commands.get(command.getCommand());
        if (nextCommand != null) {
            fc.addCommand(nextCommand);
            fc.executeCommand();
            return command.getCommand();
        } else {
            System.out.println("Unknown command");
            return "";
        }
    }

    // getCommandStream but imperative
    public List<String> processCommand(String commandLine) {
        List<String> toRemove = Arrays.asList("a", "an", "the", "and");

        List<String> tokens = new ArrayList<>();
        for (String token : commandLine.split("\\s+")) {
            if (!toRemove.contains(token)) {
                tokens.add(token);
                // tokens.add(token.toLowerCase());
            }
        }
        return tokens;
    }

    public static void main(String[] args) {
        String command = "";
        Stream<String> commandStream;
        FunctionalZork game = new FunctionalZork();
        while (!"Quit".equalsIgnoreCase(command)) {
            System.out.print(">> ");
            commandStream = game.getCommandStream();
            game.parceCommandStream(commandStream);
            command = game.executeCommand();
        }
    }
}
