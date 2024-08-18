package chapter9.zork;

public class Direction {
    private String direction;
    private String location;

    public Direction() {
        this.direction = "";
        this.location = "";
    }

    public Direction(String direction, String location) {
        this.direction = direction;
        this.location = location;
    }

    public Direction direction(String direction) {
        this.direction = direction;
        return this;
    }

    public Direction location(String location) {
        this.location = location;
        return this;
    }

    public String getDirection() {
        return this.direction;
    }

    public String getLocation() {
        return this.location;
    }
}
