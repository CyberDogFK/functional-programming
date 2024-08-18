package chapter9.zork;

public class Item {
    private String name;
    private String description;

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Item description(String description) {
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
