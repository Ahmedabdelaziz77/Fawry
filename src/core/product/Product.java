package core.product;

public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean Available(int requiredQuantity) {
        return requiredQuantity <= this.quantity;
    }

    public void buyProduct(int amount) {
        if (!Available(amount)) {
            throw new IllegalArgumentException("Insufficient stock to fulfill purchase request.");
        }
        this.quantity -= amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) return false;
        Product other = (Product) obj;
        return this.name.equals(other.name)
                && this.price == other.price
                && this.quantity == other.quantity;
    }
}
