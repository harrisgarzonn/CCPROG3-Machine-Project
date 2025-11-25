public abstract class Equipment {
    protected int maxCapacity;
    protected Product[] products;
    protected int productCount;
    
    public Equipment(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.products = new Product[maxCapacity];
        this.productCount = 0;
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
            return null;
        }
        Product product = products[index];
        // Shift products
        for (int i = index; i < productCount - 1; i++) {
            products[i] = products[i + 1];
        }
        products[productCount - 1] = null;
        productCount--;
        return product;
    }
    
    public Product[] getProducts() {
        Product[] result = new Product[productCount];
        System.arraycopy(products, 0, result, 0, productCount);
        return result;
    }
    
    public int getProductCount() { return productCount; }
    public int getMaxCapacity() { return maxCapacity; }
    public boolean isFull() { return productCount >= maxCapacity; }
    public boolean isEmpty() { return productCount == 0; }
}

