import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopper in the supermarket simulation.
 * A shopper can carry products, use equipment (basket or cart), move around the store,
 * and has age-based restrictions for certain products.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.2
 */
public class Shopper {
    /** The name of the shopper */
    private String name;

    /** The age of the shopper (used for age-restricted products) */
    private int age;

    /** The equipment being used (basket or cart), or null if hand-carrying */
    private Equipment equipment;

    /** List of products being hand-carried (max 2 without equipment) */
    private List<Product> handCarried;

    /** The current x-coordinate of the shopper on the map */
    private int x;

    /** The current y-coordinate of the shopper on the map */
    private int y;

    /** The direction the shopper is facing (NORTH, SOUTH, EAST, or WEST) */
    private String direction;

    /** Flag indicating whether the shopper has checked out */
    private boolean hasCheckedOut;

    /** Flag indicating whether the shopper has left the supermarket */
    private boolean hasLeft;

    /**
     * Constructs a new Shopper with the specified name and age.
     * Initializes the shopper facing SOUTH with no equipment.
     *
     * @param name the name of the shopper
     * @param age the age of the shopper
     */
    public Shopper(String name, int age) {
        this.name = name;
        this.age = age;
        this.handCarried = new ArrayList<>();
        this.direction = "SOUTH";
        this.hasCheckedOut = false;
        this.hasLeft = false;
    }

    /**
     * Attempts to pick up a product.
     * Products are added to equipment if available, otherwise to hand-carried (max 2).
     * Cannot pick up products after checking out.
     *
     * @param product the product to pick up
     * @return true if the product was successfully picked up, false otherwise
     */
    public boolean pickProduct(Product product) {
        if (hasCheckedOut) return false;

        if (equipment != null) {
            return equipment.addProduct(product);
        } else if (handCarried.size() < 2) {
            handCarried.add(product);
            return true;
        }
        return false;
    }

    /**
     * Attempts to return a product from the shopper's inventory.
     * Checks equipment first, then hand-carried items.
     * Cannot return products after checking out.
     *
     * @param product the product to return
     * @return true if the product was successfully returned, false otherwise
     */
    public boolean returnProduct(Product product) {
        if (hasCheckedOut) return false;

        if (equipment != null) {
            for (int i = 0; i < equipment.getProductCount(); i++) {
                if (equipment.getProducts()[i] == product) {
                    equipment.removeProduct(i);
                    return true;
                }
            }
        }

        return handCarried.remove(product);
    }

    /**
     * Sets the equipment for this shopper (basket or cart).
     * Can only set equipment if the shopper has no hand-carried items,
     * no existing equipment, and hasn't checked out yet.
     *
     * @param newEquipment the equipment to use
     * @return true if equipment was successfully set, false otherwise
     */
    public boolean setEquipment(Equipment newEquipment) {
        if (hasCheckedOut || !handCarried.isEmpty() || equipment != null) {
            return false;
        }
        this.equipment = newEquipment;
        return true;
    }

    /**
     * Removes and returns the shopper's current equipment.
     * Can only remove equipment if it is empty.
     *
     * @return the removed equipment, or null if no equipment or equipment is not empty
     */
    public Equipment removeEquipment() {
        if (equipment == null || !equipment.isEmpty()) {
            return null;
        }
        Equipment oldEquipment = equipment;
        equipment = null;
        return oldEquipment;
    }

    /**
     * Gets the shopper's name.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets the shopper's age.
     *
     * @return the age
     */
    public int getAge() { return age; }

    /**
     * Gets the shopper's current equipment.
     *
     * @return the equipment, or null if none
     */
    public Equipment getEquipment() { return equipment; }

    /**
     * Gets the list of hand-carried products.
     *
     * @return the list of hand-carried products
     */
    public List<Product> getHandCarried() { return handCarried; }

    /**
     * Gets the shopper's current x-coordinate.
     *
     * @return the x-coordinate
     */
    public int getX() { return x; }

    /**
     * Gets the shopper's current y-coordinate.
     *
     * @return the y-coordinate
     */
    public int getY() { return y; }

    /**
     * Gets the direction the shopper is facing.
     *
     * @return the direction (NORTH, SOUTH, EAST, or WEST)
     */
    public String getDirection() { return direction; }

    /**
     * Checks if the shopper has checked out.
     *
     * @return true if checked out, false otherwise
     */
    public boolean hasCheckedOut() { return hasCheckedOut; }

    /**
     * Checks if the shopper has left the supermarket.
     *
     * @return true if left, false otherwise
     */
    public boolean hasLeft() { return hasLeft; }

    /**
     * Sets the shopper's position on the map.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void setPosition(int x, int y) { this.x = x; this.y = y; }

    /**
     * Sets the direction the shopper is facing.
     *
     * @param direction the new direction (NORTH, SOUTH, EAST, or WEST)
     */
    public void setDirection(String direction) { this.direction = direction; }

    /**
     * Sets the checkout status of the shopper.
     * When checking out, equipment is removed and all items are cleared.
     *
     * @param checkedOut true to mark as checked out, false otherwise
     */
    public void setCheckedOut(boolean checkedOut) {
        this.hasCheckedOut = checkedOut;
        if (checkedOut) {
            this.equipment = null;
            this.handCarried.clear();
        }
    }

    /**
     * Sets whether the shopper has left the supermarket.
     *
     * @param left true if the shopper has left, false otherwise
     */
    public void setLeft(boolean left) { this.hasLeft = left; }

    /**
     * Gets the total number of products the shopper is carrying.
     * Includes both hand-carried items and items in equipment.
     *
     * @return the total product count
     */
    public int getTotalProductCount() {
        int count = handCarried.size();
        if (equipment != null) {
            count += equipment.getProductCount();
        }
        return count;
    }

    /**
     * Checks if the shopper is old enough to purchase alcohol.
     *
     * @return true if age is 18 or older, false otherwise
     */
    public boolean canGetAlcohol() {
        return age >= 18;
    }

    /**
     * Checks if the shopper is old enough to purchase cleaning agents.
     *
     * @return true if age is 18 or older, false otherwise
     */
    public boolean canGetCleaningAgents() {
        return age >= 18;
    }

    /**
     * Calculates the senior citizen discount for a product.
     * Seniors (60+) get 20% off food and 10% off beverages (excluding alcohol).
     *
     * @param product the product to calculate discount for
     * @return the discount as a decimal (0.20 for 20%, 0.10 for 10%, 0.0 for no discount)
     */
    public double getSeniorDiscount(Product product) {
        if (age >= 60 && product.isConsumable() && !product.getType().equals("ALCOHOL")) {
            if (product.isFood()) return 0.20;
            if (product.isBeverage()) return 0.10;
        }
        return 0.0;
    }
}
