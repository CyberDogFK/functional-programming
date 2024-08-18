package chapter9.zork;

public class NPC {
    private String name;
    private String description;

    public NPC name(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public NPC description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description;
    }
}
