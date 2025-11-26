/**
 * Abstract base class for all product display types in the supermarket.
 * Displays hold products in specific slots and enforce product type restrictions.
 * Concrete implementations include shelves, tables, chilled counters, and refrigerators.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.1
 */
public abstract class Display {
    /** The location address of this display */
    protected String address;

    /** The maximum number of products this display can hold */
    protected int capacity;

    /** The types of products allowed in this display */
    protected String[] allowedTypes;

    /**
     * Constructs a new Display with the specified parameters.
     *
     * @param address the location address of the display
     * @param capacity the maximum number of products the display can hold
     * @param allowedTypes the array of product types allowed in this display
     */
    public Display(String address, int capacity, String[] allowedTypes) {
        this.address = address;
        this.capacity = capacity;
        this.allowedTypes = allowedTypes;
    }

    /**
     * Adds a product to a specific slot in the display.
     *
     * @param product the product to add
     * @param slot the slot index where the product should be placed
     * @return true if the product was successfully added, false otherwise
     */
    public abstract boolean addProduct(Product product, int slot);

    /**
     * Removes and returns a product from a specific slot in the display.
     *
     * @param slot the slot index from which to remove the product
     * @return the removed product, or null if the slot is invalid or empty
     */
    public abstract Product removeProduct(int slot);

    /**
     * Gets the product at a specific slot without removing it.
     *
     * @param slot the slot index to retrieve the product from
     * @return the product at the specified slot, or null if the slot is invalid or empty
     */
    public abstract Product getProduct(int slot);

    /**
     * Gets the number of empty slots currently available in the display.
     *
     * @return the number of available slots
     */
    public abstract int getAvailableSlots();

    /**
     * Checks if the display is full (no empty slots).
     *
     * @return true if all slots are occupied, false otherwise
     */
    public abstract boolean isFull();

    /**
     * Checks if a product can be held in this display based on its type.
     *
     * @param product the product to check
     * @return true if the product type is in the allowedTypes array, false otherwise
     */
    public boolean canHoldProduct(Product product) {
        for (String type : allowedTypes) {
            if (product.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the address of this display.
     *
     * @return the address string
     */
    public String getAddress() { return address; }

    /**
     * Gets the maximum capacity of this display.
     *
     * @return the maximum number of products that can be stored
     */
    public int getCapacity() { return capacity; }

    /**
     * Gets the array of allowed product types for this display.
     *
     * @return the array of allowed type strings
     */
    public String[] getAllowedTypes() { return allowedTypes; }
}
