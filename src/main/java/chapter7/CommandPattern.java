package chapter7;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CommandPattern {
    public static void main(String[] args) {
        Character character = new Character();
        Commands commands = new Commands();

        commands.addCommand(new WalkCommand(character));
        commands.addCommand(new RunCommand(character));
        commands.addCommand(new JumpCommand(character));
        commands.executeCommand();

        // Functional solution
        Character character1 = new Character();
        FunctionalCommands fc = new FunctionalCommands();
        fc.addCommand(character1::walk);
        fc.addCommand(character1::run);
        fc.addCommand(character1::jump);
        fc.executeCommand();
    }
}

class FunctionalCommands {
    private final List<Supplier<Boolean>> commands =
            new ArrayList<>();

    public void addCommand(Supplier<Boolean> action) {
        commands.add(action);
    }

    public void executeCommand() {
        commands.forEach(Supplier::get);
    }
}

interface Command {
    boolean execute();
}

interface Move {
    boolean walk();
    boolean run();
    boolean jump();
}

class WalkCommand implements Command {
    private final Move move;

    public WalkCommand(Move move) {
        this.move = move;
    }

    @Override
    public boolean execute() {
        return move.walk();
    }
}

class RunCommand implements Command {
    private final Move move;

    public RunCommand(Move move) {
        this.move = move;
    }

    @Override
    public boolean execute() {
        return move.run();
    }
}

class JumpCommand implements Command {
    private final Move move;

    public JumpCommand(Move move) {
        this.move = move;
    }

    @Override
    public boolean execute() {
        return move.jump();
    }
}

class Character implements Move {
    @Override
    public boolean walk() {
        System.out.println("Walking");
        return true;
    }

    @Override
    public boolean run() {
        System.out.println("Running");
        return true;
    }

    @Override
    public boolean jump() {
        System.out.println("Jumping");
        return true;
    }
}

class Commands {
    private final List<Command> commands = new ArrayList<>();

    public void addCommand(Command action) {
        commands.add(action);
    }

    public void executeCommand() {
        commands.forEach(Command::execute);
    }
}
