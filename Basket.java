public class Basket extends Equipment {
    private static final int BASKET_CAPACITY = 15;
    
    public Basket() {
        super(BASKET_CAPACITY);
    }
    
    @Override
    public String toString() {
        return String.format("Basket [%d/%d items]", getProductCount(), BASKET_CAPACITY);
    }
}
