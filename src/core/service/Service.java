package core.service;

import core.customer.Customer;
import core.cart.Cart;
import core.cart.CartItem;
import core.interfaces.Shippable;
import core.interfaces.Expirable;

public class Service {

    public static void printCheckout(Customer customer, Cart cart) {
        verifyValidityOfPurchases(customer, cart);
        printShipmentNotice(cart);
        printCheckoutReceipt(customer, cart);
    }

    private static void printShipmentNotice(Cart cart) {
        System.out.println("\nShipment Notice");
        for (CartItem item : cart.getItems()) {
            if (item.getProduct() instanceof Shippable) {
                System.out.printf("%dx %s - %.0fg%n",
                        item.getQuantity(),
                        item.getProduct().getName(),
                        item.getWeight() * 1000);
            }
        }
        System.out.printf("Total Package Weight: %.1f kg%n%n", cart.getTotalWeight());
    }

    private static void printCheckoutReceipt(Customer customer, Cart cart) {
        System.out.println("Checkout Receipt");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s\t%.2f EGP%n",
                    item.getQuantity(),
                    item.getProduct().getName(),
                    item.getSubtotal());
        }
        System.out.println("----------------------------------------");
        System.out.printf("Subtotal:         %.2f EGP%n", cart.getSubtotal());
        System.out.printf("Shipping Fees:    %.2f EGP%n", cart.getShippedPrice());
        System.out.printf("Total Amount:     %.2f EGP%n", cart.getTotalAmount());
        System.out.printf("Remaining Balance: %.2f EGP%n", customer.getBalance() - cart.getTotalAmount());
        System.out.println();
    }

    public static void paymentOfPurchases(Customer customer, Cart cart) {
        verifyValidityOfPurchases(customer, cart);
        customer.withdraw(cart.getTotalAmount());
        for (CartItem item : cart.getItems()) {
            item.getProduct().buyProduct(item.getQuantity());
        }
        System.out.println("Payment processed successfully.\n");
    }

    public static void verifyValidityOfPurchases(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("Error: Your cart is empty.");
        }

        for (CartItem item : cart.getItems()) {
            if (!item.getProduct().Available(item.getQuantity())) {
                throw new IllegalArgumentException("Not enough quantity in stock for: " + item.getProduct().getName());
            }

            if (item.getProduct() instanceof Expirable &&
                    ((Expirable) item.getProduct()).isExpired()) {
                throw new IllegalArgumentException("Cannot purchase expired product: " + item.getProduct().getName());
            }
        }

        if (customer.getBalance() < cart.getTotalAmount()) {
            throw new IllegalArgumentException("Insufficient balance. You need " +
                    String.format("%.2f", cart.getTotalAmount()) + " EGP but have only " +
                    String.format("%.2f", customer.getBalance()) + " EGP.");
        }
    }
}
