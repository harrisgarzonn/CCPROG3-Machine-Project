/*
  Abstract base class for shopping equipment (baskets and carts).
  Provides common functionality for storing products while shopping.
 */
public abstract class Equipment {
    protected int maxCapacity; // Maximum number of products equipment can hold
    protected Product[] products; // Array to store products
    protected int productCount; // Current number of products in equipment
    
    public Equipment(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.products = new Product[maxCapacity]; // Initialize empty product array
        this.productCount = 0; // Start with no products
    }
    
    public boolean addProduct(Product product) {
        if (productCount >= maxCapacity) {
            return false;
        }
        products[productCount] = product;
        productCount++;
        return true;
    }
    
    public Product removeProduct(int index) {
        if (index < 0 || index >= productCount) {
            return null; // invalid index
        }
        Product product = products[index];
        // Shift products to fill the gap
        for (int i = index; i < productCount - 1; i++) {
            products[i] = products[i + 1];
        }
        products[productCount - 1] = null; //Clear last position
        productCount--;
        return product;
    }

    // Gets all products currently in the equipment
    public Product[] getProducts() {
        Product[] result = new Product[productCount];
        System.arraycopy(products, 0, result, 0, productCount);
        return result;
    }

    // Getters
    public int getProductCount() { return productCount; }
    public int getMaxCapacity() { return maxCapacity; }
    public boolean isFull() { return productCount >= maxCapacity; }
    public boolean isEmpty() { return productCount == 0; }
}

