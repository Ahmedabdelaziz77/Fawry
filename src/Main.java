import core.customer.Customer;
import core.cart.Cart;
import core.product.*;
import core.service.Service;

import java.time.LocalDate;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        ShippableProduct laptop = new ShippableProduct("Laptop", 1200, 5, 2.5);
        ShippableProduct headphones = new ShippableProduct("Headphones", 300, 4, 0.3);
        Product courseAccess = new Product("Online Course Access", 100, 50);
        ExpiredShippableProduct spoiledJuice = new ExpiredShippableProduct("Spoiled Juice", 60, 2, LocalDate.now().minusDays(5), 1.0);

        Customer ahmed = new Customer("Ahmed", 2200);
        Customer zoz = new Customer("Zoz", 200);

        System.out.println("Test Case 1: Successful Checkout");
        Cart cart1 = new Cart();
        cart1.addItem(laptop, 1);
        cart1.addItem(headphones, 1);
        cart1.addItem(courseAccess, 3);
        Service.printCheckout(ahmed, cart1);
        Service.paymentOfPurchases(ahmed, cart1);

        System.out.println("\nTest Case 2: Adding Expired Product");
        Cart cart2 = new Cart();
        try {
            cart2.addItem(spoiledJuice, 1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            Service.printCheckout(ahmed, cart2);
        } catch (IllegalArgumentException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }

        System.out.println("\nTest Case 3: Requesting More Than Available Stock");
        Cart cart3 = new Cart();
        try {
            cart3.addItem(headphones, 10);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nTest Case 4: Not Enough Balance to Pay");
        Cart cart4 = new Cart();
        cart4.addItem(laptop, 1);
        try {
            Service.printCheckout(zoz, cart4);
        } catch (IllegalArgumentException e) {
            System.out.println("Payment failed: " + e.getMessage());
        }
    }
}
