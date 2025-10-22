// Chilled counter class makes that it could contain meats and seafood, with a capacity of 3.
public class ChilledCounter extends Display {
    private String address;
    private int capacity;
    private String[] allowedTypes;
    private Product[] products;

    // Main constructor
    public ChilledCounter(String address) {
        super(address, 3, new String[]{"CHICKEN", "BEEF", "SEAFOOD"}); // Same thing, same format to use.
        this.address = address;
        this.capacity = 3;
        this.allowedTypes = new String[]{"CHICKEN", "BEEF", "SEAFOOD"};
        this.products = new Product[3];
    }

    // Getter
    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    public Product[] getProducts() {
        return products;
    }

    // Add product to slot
    public boolean addProduct(Product product, int slot) {
        if (slot < 0 || slot >= capacity) {
            return false;
        }
        if (products[slot] != null) {
            return false;
        }
        if (canHoldProduct(product) == false) {
            return false;
        }
        products[slot] = product;
        return true;
    }

    // Remove product from slot
    public Product removeProduct(int slot) {
        if (slot < 0 || slot >= capacity) {
            return null;
        }
        Product product = products[slot];
        products[slot] = null;
        return product;
    }

    // Get product at slot
    public Product getProduct(int slot) {
        if (slot < 0 || slot >= capacity) {
            return null;
        }
        return products[slot];
    }

    // Check if product type is allowed
    public boolean canHoldProduct(Product product) {
        for (int i = 0; i < allowedTypes.length; i++) {
            if (product.getType().equals(allowedTypes[i])) {
                return true;
            }
        }
        return false;
    }

    // Count empty slots
    public int getAvailableSlots() {
        int count = 0;
        for (int i = 0; i < products.length; i++) {
            if (products[i] == null) {
                count++;
            }
        }
        return count;
    }

    // Check if full
    public boolean isFull() {
        if (getAvailableSlots() == 0) {
            return true;
        }
        return false;
    }
}
