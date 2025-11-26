/*
Abstract base class for all display types in the supermarket.
Defines common functionality for product displays.
*/
public abstract class Display {
    protected String address;
    protected int capacity;
    protected String[] allowedTypes;

    //Constructor for Display
    public Display(String address, int capacity, String[] allowedTypes) {
        this.address = address;
        this.capacity = capacity; // Maximum number of products display can hold
        this.allowedTypes = allowedTypes; //Array of product types this array can hold
    }

    // Abstract mmethods that must be implemented by subclasses
    public abstract boolean addProduct(Product product, int slot);
    public abstract Product removeProduct(int slot);
    public abstract Product getProduct(int slot);
    public abstract int getAvailableSlots();
    public abstract boolean isFull();

    // Checks if the display can hold a given product type
    // true if display can hold the product, otherwise false
    public boolean canHoldProduct(Product product) {
        for (String type : allowedTypes) {
            if (product.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    public String getAddress() { return address; }
    public int getCapacity() { return capacity; }
    public String[] getAllowedTypes() { return allowedTypes; }
}
