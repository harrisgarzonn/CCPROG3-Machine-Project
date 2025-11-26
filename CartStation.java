/**
 * Represents a cart station service where shoppers can pick up or return shopping carts.
 * Carts can only be picked up if the shopper has no equipment, no hand-carried items,
 * and has not checked out. Carts can only be returned if they are empty.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.0
 */
public class CartStation extends Service {
    /**
     * Constructs a new CartStation at the specified location.
     *
     * @param x the x-coordinate of the cart station
     * @param y the y-coordinate of the cart station
     * @param floor the floor level where the cart station is located
     */
    public CartStation(int x, int y, String floor) {
        super("CART_STATION", x, y, floor);
    }

    /**
     * Handles the interaction between a shopper and the cart station.
     * Allows shoppers to pick up a cart if they meet the requirements
     * (no equipment, no hand-carried items, and haven't checked out),
     * or return an empty cart.
     *
     * @param shopper the shopper interacting with the cart station
     * @return a message describing the result of the interaction
     */
    @Override
    public String interact(Shopper shopper) {
        if (shopper.getEquipment() == null && shopper.getHandCarried().isEmpty() && !shopper.hasCheckedOut()) {
            shopper.setEquipment(new Cart());
            return "You picked up a cart!";
        } else if (shopper.getEquipment() instanceof Cart && shopper.getEquipment().isEmpty()) {
            shopper.removeEquipment();
            return "You returned the cart!";
        }
        return "Cannot get or return cart at this time.";
    }
}
