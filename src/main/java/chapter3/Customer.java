package chapter3;

import java.util.function.Function;

public class Customer {
    private String emailAddress;

    public Customer(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public static void main(String[] args) {
        Customer customer = new Customer("bestcustoemr@thebestcustomer.com");
        Salesman salesman = new Salesman(customer, null);
        System.out.println(salesman.getBestCustomer()
                .getEmailAddress());

        Function<Customer, String> customerToEmailAddress =
                Customer::getEmailAddress;
        Function<Salesman, Customer> salesmanToBestCustomer =
                Salesman::getBestCustomer;
        Function<Salesman, String> toEmailAddress =
                salesmanToBestCustomer.andThen(
                        customerToEmailAddress);
        System.out.println(toEmailAddress.apply(salesman));

        Function<Manager, String> managerToEmailAddress =
                Manager::getEmailAddress;
        Function<Salesman, Manager> salesmanToManager =
                Salesman::getManager;
        Function<Salesman, String> toManagerEmailAddress =
                salesmanToManager.andThen(managerToEmailAddress);

        Manager manager = new Manager("manager@thecompany.com");
        Salesman salesman2 = new Salesman(customer, manager);
        System.out.println(toManagerEmailAddress.apply(salesman2));
        System.out.println(salesman2.getManager().getEmailAddress());

        salesman.processEmailAddress(toEmailAddress);
        salesman2.processEmailAddress(toManagerEmailAddress);
    }
}

class Salesman {
    private Customer bestCustomer;
    private Manager manager;

    Salesman(Customer bestCustomer,
             Manager manager) {
        this.bestCustomer = bestCustomer;
        this.manager = manager;
    }

    public Customer getBestCustomer() {
        return bestCustomer;
    }

    public Manager getManager() {
        return manager;
    }

    public void processEmailAddress(
            Function<Salesman, String> toEmailAddress
    ) {
        System.out.println(toEmailAddress.apply(this));
    }
}

class Manager {
    private String emailAddress;

    public Manager(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}

