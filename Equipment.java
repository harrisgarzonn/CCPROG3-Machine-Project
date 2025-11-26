/**
 * Abstract base class for shopping equipment that can carry products.
 * Equipment includes items like baskets and carts that shoppers use
 * to transport products around the supermarket.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.2
 */
public abstract class Equipment {
    /** The maximum number of products this equipment can hold */
    protected int maxCapacity;

    /** Array storing the products in this equipment */
    protected Product[] products;

    /** The current number of products in this equipment */
    protected int productCount;

    /**
     * Constructs new Equipment with the specified maximum capacity.
     *
     * @param maxCapacity the maximum number of products this equipment can hold
     */
    public Equipment(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.products = new Product[maxCapacity];
        this.productCount = 0;
    }

    /**
     * Adds a product to this equipment.
     *
     * @param product the product to add
     * @return true if the product was successfully added, false if the equipment is full
     */
    public boolean addProduct(Product product) {
        if (productCount >= maxCapacity) {
            return false;
        }
        products[productCount] = product;
        productCount++;
        return true;
    }

    /**
     * Removes and returns a product at the specified index.
     * Products after the removed product are shifted down to fill the gap.
     *
     * @param index the index of the product to remove
     * @return the removed product, or null if the index is invalid
     */
    public Product removeProduct(int index) {
        if (index < 0 || index >= productCount) {
            return null;
        }
        Product product = products[index];
        // Shift products down to fill the gap
        for (int i = index; i < productCount - 1; i++) {
            products[i] = products[i + 1];
        }
        products[productCount - 1] = null;
        productCount--;
        return product;
    }

    /**
     * Gets a copy of all products currently in this equipment.
     *
     * @return an array containing all products in this equipment
     */
    public Product[] getProducts() {
        Product[] result = new Product[productCount];
        System.arraycopy(products, 0, result, 0, productCount);
        return result;
    }

    /**
     * Gets the current number of products in this equipment.
     *
     * @return the number of products currently held
     */
    public int getProductCount() { return productCount; }

    /**
     * Gets the maximum capacity of this equipment.
     *
     * @return the maximum number of products this equipment can hold
     */
    public int getMaxCapacity() { return maxCapacity; }

    /**
     * Checks if this equipment is full.
     *
     * @return true if the equipment has reached maximum capacity, false otherwise
     */
    public boolean isFull() { return productCount >= maxCapacity; }

    /**
     * Checks if this equipment is empty.
     *
     * @return true if the equipment contains no products, false otherwise
     */
    public boolean isEmpty() { return productCount == 0; }
}
