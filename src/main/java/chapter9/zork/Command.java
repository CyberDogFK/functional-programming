package chapter9.zork;

import java.util.ArrayList;
import java.util.List;

public class Command {
    public List<String> arguments = new ArrayList<String>();

    public Command arguments(List<String> arguments) {
        this.arguments = arguments;
        return this;
    }

    public List<String> getArguments() {
        return this.arguments;
    }
}
