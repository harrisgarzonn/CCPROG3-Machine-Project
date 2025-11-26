/**
 * Represents a product in the supermarket with identifying information,
 * pricing, and categorization. Products are classified by type and can
 * be categorized as food, beverages, or other consumables.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.1
 */
public class Product {
    /** The unique serial number of this product */
    private String serialNumber;

    /** The name of this product */
    private String name;

    /** The price of this product in PHP */
    private double price;

    /** The type/category of this product (e.g., FRUIT, VEGETABLE, CHICKEN) */
    private String type;

    /**
     * Constructs a new Product with the specified attributes.
     *
     * @param serialNumber the unique serial number for this product
     * @param name the name of the product
     * @param price the price of the product in PHP
     * @param type the type/category of the product
     */
    public Product(String serialNumber, String name, double price, String type) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    /**
     * Gets the serial number of this product.
     *
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Gets the name of this product.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of this product.
     *
     * @return the price in PHP
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the type/category of this product.
     *
     * @return the product type
     */
    public String getType() {
        return type;
    }

    /**
     * Checks if this product is a food item.
     * Food items include fruits, vegetables, meats, seafood, bread, cereals,
     * noodles, snacks, canned goods, condiments, eggs, cheese, and frozen food.
     *
     * @return true if the product is food, false otherwise
     */
    public boolean isFood() {
        return type.equals("FRUIT") || type.equals("VEGETABLE") || type.equals("CHICKEN") ||
                type.equals("BEEF") || type.equals("SEAFOOD") || type.equals("BREAD") ||
                type.equals("CEREAL") || type.equals("NOODLES") || type.equals("SNACKS") ||
                type.equals("CANNED_GOODS") || type.equals("CONDIMENTS") || type.equals("EGGS") ||
                type.equals("CHEESE") || type.equals("FROZEN_FOOD");
    }

    /**
     * Checks if this product is a beverage.
     * Beverages include soft drinks, juice, alcohol, and milk.
     *
     * @return true if the product is a beverage, false otherwise
     */
    public boolean isBeverage() {
        return type.equals("SOFT_DRINK") || type.equals("JUICE") || type.equals("ALCOHOL") ||
                type.equals("MILK");
    }

    /**
     * Checks if this product is consumable (can be eaten or drunk).
     *
     * @return true if the product is food or beverage, false otherwise
     */
    public boolean isConsumable() {
        return isFood() || isBeverage();
    }

    /**
     * Determines the type of display needed for this product.
     * Different product types require different storage displays:
     * - TABLE: fruits, vegetables, bread, eggs
     * - CHILLED_COUNTER: chicken, beef, seafood
     * - REFRIGERATOR: milk, frozen food, cheese
     * - SHELF: all other products
     *
     * @return the display type as a String (TABLE, CHILLED_COUNTER, REFRIGERATOR, or SHELF)
     */
    public String getDisplayType() {
        if (type.equals("FRUIT")) {
            return "TABLE";
        } else if (type.equals("VEGETABLE")) {
            return "TABLE";
        } else if (type.equals("BREAD")) {
            return "TABLE";
        } else if (type.equals("EGGS")) {
            return "TABLE";
        } else if (type.equals("CHICKEN") || type.equals("BEEF") || type.equals("SEAFOOD")) {
            return "CHILLED_COUNTER";
        } else if (type.equals("MILK") || type.equals("FROZEN_FOOD") || type.equals("CHEESE")) {
            return "REFRIGERATOR";
        } else {
            return "SHELF";
        }
    }
}
