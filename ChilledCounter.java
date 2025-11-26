/**
 * Represents a chilled counter display for storing temperature-sensitive products.
 * This counter can hold chicken, beef, and seafood products with a capacity of 3 items.
 * Products are stored in specific slots within the counter.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.0
 */
public class ChilledCounter extends Display {
    private String address;
    private int capacity;
    private String[] allowedTypes;
    private Product[] products;

    /**
     * Constructs a new ChilledCounter with a specified address.
     * The counter is initialized with a capacity of 3 slots and can hold
     * CHICKEN, BEEF, and SEAFOOD product types.
     *
     * @param address the location address of the chilled counter
     */
    public ChilledCounter(String address) {
        super(address, 3, new String[]{"CHICKEN", "BEEF", "SEAFOOD"});
        this.address = address;
        this.capacity = 3;
        this.allowedTypes = new String[]{"CHICKEN", "BEEF", "SEAFOOD"};
        this.products = new Product[3];
    }

    /**
     * Gets the address of this chilled counter.
     *
     * @return the address string
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the maximum capacity of this chilled counter.
     *
     * @return the maximum number of products that can be stored
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the array of products currently in the chilled counter.
     *
     * @return the products array
     */
    public Product[] getProducts() {
        return products;
    }

    /**
     * Adds a product to a specific slot in the chilled counter.
     * The product can only be added if the slot is valid, empty, and the
     * product type is allowed (CHICKEN, BEEF, or SEAFOOD).
     *
     * @param product the product to add
     * @param slot the slot index (0-2) where the product should be placed
     * @return true if the product was successfully added, false otherwise
     */
    public boolean addProduct(Product product, int slot) {
        if (slot < 0 || slot >= capacity) {
            return false;
        }
        if (products[slot] != null) {
            return false;
        }
        if (!canHoldProduct(product)) {
            return false;
        }
        products[slot] = product;
        return true;
    }

    /**
     * Removes and returns a product from a specific slot in the chilled counter.
     *
     * @param slot the slot index (0-2) from which to remove the product
     * @return the removed product, or null if the slot is invalid or empty
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
     * Gets the product at a specific slot without removing it.
     *
     * @param slot the slot index (0-2) to retrieve the product from
     * @return the product at the specified slot, or null if the slot is invalid or empty
     */
    public Product getProduct(int slot) {
        if (slot < 0 || slot >= capacity) {
            return null;
        }
        return products[slot];
    }

    /**
     * Checks if a product can be held in this chilled counter based on its type.
     * Only CHICKEN, BEEF, and SEAFOOD types are allowed.
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
     * Counts the number of empty slots in the chilled counter.
     *
     * @return the number of available (empty) slots
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
     * Checks if the chilled counter is full (no empty slots).
     *
     * @return true if all slots are occupied, false otherwise
     */
    public boolean isFull() {
        return getAvailableSlots() == 0;
    }
}
