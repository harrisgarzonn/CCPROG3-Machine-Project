/*
 * This class makes / identify a product or creates a product according to its serialNumber, name, price, type (type of product)
*/
public class Product {
    private String serialNumber;
    private String name;
    private double price;
    private String type;

    // Main Constructor
    public Product(String serialNumber, String name, double price, String type) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
        this.type = type;
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

    public String getType() {
        return type;
    }

    // Check if product is food
    public boolean isFood() {
        if (type.equals("FRUIT") || type.equals("CHICKEN") || type.equals("BEEF") || 
            type.equals("SEAFOOD") || type.equals("CEREAL") || type.equals("NOODLES") || 
            type.equals("SNACKS") || type.equals("CANNED_GOODS") || type.equals("CONDIMENTS")) {
            return true;
        }
        return false;
    }

    // Check if product is beverage
    public boolean isBeverage() {
        if (type.equals("SOFT_DRINK") || type.equals("JUICE") || type.equals("ALCOHOL")) {
            return true;
        }
        return false;
    }

    // Check if product can be eaten or drunk
    public boolean isConsumable() {
        if (isFood() == true || isBeverage() == true) {
            return true;
        }
        return false;
    }

    // Get what type of display this product needs
    public String getDisplayType() {
        if (type.equals("FRUIT")) {
            return "TABLE";
        } else if (type.equals("CHICKEN") || type.equals("BEEF") || type.equals("SEAFOOD")) {
            return "CHILLED_COUNTER";
        } else {
            return "SHELF";
        }
    }
}
