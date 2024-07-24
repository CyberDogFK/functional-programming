package chapter3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluentInterfaces {
    public void run() {
    }

    public void examples() {
    int[] hoursWorked = {8, 12, 8, 6, 6, 5, 6, 0};
    int totalHoursWorked = Arrays.stream(hoursWorked)
            .filter(n -> n > 6)
            .sum();
        System.out.println(totalHoursWorked);

    LocalDateTime timeInstance = LocalDateTime.now()
            .plusDays(3)
            .minusHours(4)
            .plusWeeks(1)
            .plusYears(2);
        System.out.println(timeInstance);

    String animal = "Cat";
    String concat = animal.concat("Dog");
    String lower = concat.toLowerCase();
    int length = lower.length();
        System.out.println(lower);
        System.out.println(length);

    animal = "Cat";
        System.out.println(animal
                .concat("Dog")
                .toLowerCase());
        System.out.println(animal
                .concat("Dog")
                .toLowerCase()
                .length());
}

    public static void main(String[] args) {
        FluentInterfaces fi = new FluentInterfaces();
        fi.examples();
        Boat boat = new Boat();
        boat.named("Albatross")
                .country("Panama")
                .tonnage(12000)
                .draft(25);

        Port port = new Port();
        Boat newBoat = port.add("Cloud");
        port.add(boat);
        port.add(new Boat() {
            {
                named("Albatross");
                country("Panama");
                tonnage(1500);
                draft(35);
            }
        });
    }
}

class Number {
    public Number chainedMethod(int num) {
        Number newTest = new Number();
        // use num
        return newTest;
    }

    public Number cascadedMethod(int num) {
        // use num
        return this;
    }
}

class Boat {
    private String name;
    private String country;
    private int tonnage;
    private int draft;

    public String getName() {
        return name;
    }

    public Boat named(String name) {
        this.name = name;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Boat country(String country) {
        this.country = country;
        return this;
    }

    public int getTonnage() {
        return tonnage;
    }

    public Boat tonnage(int tonnage) {
        this.tonnage = tonnage;
        return this;
    }

    public int getDraft() {
        return draft;
    }

    public Boat draft(int draft) {
        this.draft = draft;
        return this;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + " Country: " + this.country
                + " Tonnage: " + this.tonnage + " Draft: " +
                this.draft;
    }
}

class Port {
    private List<Boat> boats = new ArrayList<>();

    public Boat add(String name) {
        Boat boat = new Boat().named(name);
        boats.add(boat);
        return boat;
    }

    public Boat add(Boat boat) {
        boats.add(boat);
        return boat;
    }
}
