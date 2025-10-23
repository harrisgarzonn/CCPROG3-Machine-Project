public class Cart extends Equipment {
    private static final int CART_CAPACITY = 30;
    
    public Cart() {
        super(CART_CAPACITY);
    }
    
    @Override
    public String toString() {
        return String.format("Cart [%d/%d items]", getProductCount(), CART_CAPACITY);
    }
}
