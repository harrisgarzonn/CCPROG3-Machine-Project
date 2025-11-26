/**
 * Represents a table display unit in the supermarket.
 * A table has a single tier with capacity for 4 products.
 * Typically used for displaying fruits and other produce.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.0
 */
public class Table extends Display {
    /** The address/location identifier of this table */
    private String address;

    /** The total capacity of this table (4 products) */
    private int capacity;

    /** Array of product types allowed on this table */
    private String[] allowedTypes;

    /** Array of products stored on this table */
    private Product[] products;

    /**
     * Constructs a Table with the specified address.
     * Initializes with capacity for 4 products and allows FRUIT type by default.
     *
     * @param address the address/location identifier for this table
     */
    public Table(String address) {
        super(address, 4, new String[]{"FRUIT"});
        this.address = address;
        this.capacity = 4;
        this.allowedTypes = new String[]{"FRUIT"};
        this.products = new Product[4];
    }

    /**
     * Gets the address of this table.
     *
     * @return the address string
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the total capacity of this table.
     *
     * @return the capacity (4 products)
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets all products on this table.
     *
     * @return an array of all product slots (may contain null values)
     */
    public Product[] getProducts() {
        return products;
    }

    /**
     * Adds a product to a specific slot on the table.
     * The slot must be empty and the product type must be allowed.
     *
     * @param product the product to add
     * @param slot the slot number (0-3)
     * @return true if the product was successfully added, false otherwise
     */
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

    /**
     * Removes a product from a specific slot.
     *
     * @param slot the slot number (0-3)
     * @return the removed product, or null if the slot was empty or invalid
     */
    public Product removeProduct(int slot) {
        if (slot < 0 || slot >= capacity) {
            return null;
        }
        Product product = products[slot];
        products[slot] = null;
        return product;
    }

    /**
     * Gets a product from a specific slot without removing it.
     *
     * @param slot the slot number (0-3)
     * @return the product at the specified slot, or null if empty or invalid
     */
    public Product getProduct(int slot) {
        if (slot < 0 || slot >= capacity) {
            return null;
        }
        return products[slot];
    }

    /**
     * Checks if this table can hold the specified product type.
     * By default, only FRUIT type is allowed.
     *
     * @param product the product to check
     * @return true if the product type is allowed, false otherwise
     */
    public boolean canHoldProduct(Product product) {
        for (int i = 0; i < allowedTypes.length; i++) {
            if (product.getType().equals(allowedTypes[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the number of empty slots on the table.
     *
     * @return the number of available slots
     */
    public int getAvailableSlots() {
        int count = 0;
        for (int i = 0; i < products.length; i++) {
            if (products[i] == null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if the table is completely full.
     *
     * @return true if all slots are occupied, false otherwise
     */
    public boolean isFull() {
        if (getAvailableSlots() == 0) {
            return true;
        }
        return false;
    }
}
