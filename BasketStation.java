/**
 * Represents a basket station service where shoppers can pick up or return baskets.
 * Baskets can only be picked up if the shopper has no equipment, no hand-carried items,
 * and has not checked out. Baskets can only be returned if they are empty.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.1
 */
public class BasketStation extends Service {
    /**
     * Constructs a new BasketStation at the specified location.
     *
     * @param x the x-coordinate of the basket station
     * @param y the y-coordinate of the basket station
     * @param floor the floor level where the basket station is located
     */
    public BasketStation(int x, int y, String floor) {
        super("BASKET_STATION", x, y, floor);
    }

    /**
     * Handles the interaction between a shopper and the basket station.
     * Allows shoppers to pick up a basket if they meet the requirements
     * (no equipment, no hand-carried items, and haven't checked out),
     * or return an empty basket.
     *
     * @param shopper the shopper interacting with the basket station
     * @return a message describing the result of the interaction
     */
    @Override
    public String interact(Shopper shopper) {
        if (shopper.getEquipment() == null && shopper.getHandCarried().isEmpty() && !shopper.hasCheckedOut()) {
            shopper.setEquipment(new Basket());
            return "You picked up a basket!";
        } else if (shopper.getEquipment() instanceof Basket && shopper.getEquipment().isEmpty()) {
            shopper.removeEquipment();
            return "You returned the basket!";
        }
        return "Cannot get or return basket at this time.";
    }
}
