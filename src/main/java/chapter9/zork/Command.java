package chapter9.zork;

import java.util.ArrayList;
import java.util.List;

public class Command {
    public List<String> arguments = new ArrayList<String>();
    public String command = "";

    public Command arguments(List<String> arguments) {
        this.arguments = arguments;
        return this;
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    public void addArgument(String argument) {
        arguments.add(argument);
    }

    public Command command(String command) {
        this.command = command;
        return this;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void clear() {
        arguments.clear();
        command = "";
    }
}
