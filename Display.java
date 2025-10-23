public abstract class Display {
    protected String address;
    protected int capacity;
    protected String[] allowedTypes;
    
    public Display(String address, int capacity, String[] allowedTypes) {
        this.address = address;
        this.capacity = capacity;
        this.allowedTypes = allowedTypes;
    }
    
    public abstract boolean addProduct(Product product, int slot);
    public abstract Product removeProduct(int slot);
    public abstract Product getProduct(int slot);
    public abstract int getAvailableSlots();
    public abstract boolean isFull();
    
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
