/**
 * Represents a product in the supermarket with serial number, name, and price.
 */
public class Product {
    private String serialNumber;
    private String name;
    private double price;
    private String productType;
    
    // Main constructor
    public Product(String serialNumber, String name, double price, String productType) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
        this.productType = productType;
    }
    
    // Getters
    public String getSerialNumber() {
        return serialNumber;
    }
    
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getProductType() {
        return productType;
    }
    
    // This checks if the product is consumable returns true or false
    public boolean isConsumable() {
        String[] consumableTypes = {
            "Fruit", "Chicken", "Beef", "Seafood", "Cereal", 
            "Noodles", "Snacks", "Canned Goods", "Condiments", 
            "Soft drink", "Juice", "Alcohol"
        };
        
        for (String type : consumableTypes) {
            if (productType.equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    // This check if it is a food excluding drinks, returns true or false
    public boolean isFood() {
        String[] foodTypes = {
            "Fruit", "Chicken", "Beef", "Seafood", "Cereal", 
            "Noodles", "Snacks", "Canned Goods", "Condiments"
        };
        
        for (String type : foodTypes) {
            if (productType.equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    // This one checks if the product is a beverage
    public boolean isBeverage() {
        return productType.equals("Soft drink") || 
               productType.equals("Juice") || 
               productType.equals("Alcohol");
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s (₱%.2f)", serialNumber, name, price);
    }
}