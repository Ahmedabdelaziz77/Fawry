package core.customer;

public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        if (balance <= 0) {
            throw new IllegalArgumentException("Initial balance must be greater than zero.");
        }
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance for this transaction.");
        }
        this.balance -= amount;
    }
}
