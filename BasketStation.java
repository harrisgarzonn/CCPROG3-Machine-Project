public class BasketStation extends Service {
    public BasketStation(int x, int y, String floor) {
        super("BASKET_STATION", x, y, floor);
    }

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