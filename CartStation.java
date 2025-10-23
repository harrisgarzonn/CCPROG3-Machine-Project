public class CartStation extends Service {
    public CartStation(int x, int y) {
        super("CART_STATION", x, y);
    }
    
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
