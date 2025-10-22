// Table class consists of 4 products.
public class Table extends Display {
    private String address;
    private int capacity;
    private String[] allowedTypes;
    private Product[] products;

    // Main constructor
    public Table(String address) {
        super(address, 4, new String[]{"FRUIT"}); // Same as the last file this uses the same format
        this.address = address;
        this.capacity = 4;
        this.allowedTypes = new String[]{"FRUIT"};
        this.products = new Product[4];
    }

    // Getters
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
