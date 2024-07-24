package chapter3;

public class ExtendedFluent {
    public static void main(String[] args) {
        SailBoat sailBoat = new SailBoat()
                .named("Endeavour")
                .country("United Kingdom")
                .sails(3)
                .tonnage(15)
                .hulls(2);
        System.out.println(sailBoat);

        sailBoat = new SailBoat()
                .tonnage(15)
                .hulls(2)
                .country("United Kingdom")
                .named("Endeavour")
                .sails(3);
        System.out.println(sailBoat);
    }
}

class BaseBoat<DERIVED extends BaseBoat<DERIVED>> {
    private String name;
    private String country;
    private int tonnage;
    private int draft;

    public String getName() {
        return name;
    }

    public DERIVED named(String name) {
        this.name = name;
        return (DERIVED) this;
    }

    public String getCountry() {
        return country;
    }

    public DERIVED country(String country) {
        this.country = country;
        return (DERIVED) this;
    }

    public int getTonnage() {
        return tonnage;
    }

    public DERIVED tonnage(int tonnage) {
        this.tonnage = tonnage;
        return (DERIVED) this;
    }

    public int getDraft() {
        return draft;
    }

    public DERIVED draft(int draft) {
        this.draft = draft;
        return (DERIVED) this;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + " Country: " + this.country
                + " Tonnage: " + this.tonnage + " Draft: " + this.draft;
    }
}

class SailBoat extends BaseBoat<SailBoat> {
    private int numberOfSails;
    private int numberOfHulls;

    public int getSails() {
        return this.numberOfSails;
    }

    public SailBoat sails(int numberOfSails) {
        this.numberOfSails = numberOfSails;
        return this;
    }

    public int getNumberOfHulls() {
        return this.numberOfHulls;
    }

    public SailBoat  hulls(int numberOfHulls) {
        this.numberOfHulls = numberOfHulls;
        return this;
    }

    @Override
    public String toString() {
        return super.toString()
                + " Number of sails: " + this.numberOfSails
                + " Number of hulls: " + this.numberOfHulls;
    }
}
