package core.cart;

import core.product.Product;
import core.interfaces.Expirable;
import core.interfaces.Shippable;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (!product.Available(quantity)) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock: " + product.getQuantity());
        }
        if (product instanceof Expirable && ((Expirable) product).isExpired()) {
            throw new IllegalArgumentException("Product is expired and cannot be added to the cart.");
        }
        items.add(new CartItem(product, quantity));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getShippedPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getShippingFees();
        }
        return total;
    }

    public double getSubtotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double getTotalWeight() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getWeight();
        }
        return total;
    }

    public double getTotalAmount() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalAmount();
        }
        return total;
    }
}
