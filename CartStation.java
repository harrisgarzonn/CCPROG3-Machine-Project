/*
  Service where shoppers can get or return carts.
  Located at specific positions in the supermarket.
 */
public class CartStation extends Service {
    public CartStation(int x, int y, String floor) {
        super("CART_STATION", x, y, floor);
    }

    @Override
    public String interact(Shopper shopper) {
        // Shopper can get a cart if:
        // 1. They have no equipment
        // 2. They have no hand-carried products
        // 3. They haven't checked out yet
        if (shopper.getEquipment() == null && shopper.getHandCarried().isEmpty() && !shopper.hasCheckedOut()) {
            shopper.setEquipment(new Cart());
            return "You picked up a cart!";
            // Shopper can return a cart if:
            // 1. They have a cart
            // 2. The cart is empty
        } else if (shopper.getEquipment() instanceof Cart && shopper.getEquipment().isEmpty()) {
            shopper.removeEquipment();
            return "You returned the cart!";
        }
        return "Cannot get or return cart at this time.";
    }
}
