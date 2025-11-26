/*
  Service where shoppers can get or return baskets.
  Located at specific positions in the supermarket.
 */
public class BasketStation extends Service {
    public BasketStation(int x, int y, String floor) {
        super("BASKET_STATION", x, y, floor);
    }

    @Override
    public String interact(Shopper shopper) {
        // Shopper can get a basket if:
        // 1. They have no equipment
        // 2. They have no hand-carried products
        // 3. They haven't checked out yet
        if (shopper.getEquipment() == null && shopper.getHandCarried().isEmpty() && !shopper.hasCheckedOut()) {
            shopper.setEquipment(new Basket());
            return "You picked up a basket!";
            // Shopper can return a basket if:
            // 1. They have a basket
            // 2. The basket is empty
        } else if (shopper.getEquipment() instanceof Basket && shopper.getEquipment().isEmpty()) {
            shopper.removeEquipment();
            return "You returned the basket!";
        }
        return "Cannot get or return basket at this time.";
    }
}
