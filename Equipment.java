import java.util.ArrayList;

public class Equipment {
    protected int maxCapacity;
    protected ArrayList<Product> products;
    
    public Equipment(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.products = new ArrayList<>();
    }
    
    public boolean addProduct(Product product) {
        if (isFull()) {
            return false;
        }
        products.add(product);
        return true;
    }
    
    public Product removeProduct(Product product) {
        if (products.remove(product)) {
            return product;
        }
        return null;
    }
    
    public Product removeProduct(int index) {
        if (index < 0 || index >= products.size()) {
            return null;
        }
        return products.remove(index);
    }
    
    public int getProductCount() {
        return products.size();
    }
    
    public boolean isFull() {
        return products.size() >= maxCapacity;
    }
    
    public boolean isEmpty() {
        return products.isEmpty();
    }
    
    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products);
    }
    
    public void clear() {
        products.clear();
    }
    
    public int getMaxCapacity() {
        return maxCapacity;
    }
}
