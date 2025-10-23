public class ProductSearch extends Service {
    private Supermarket supermarket;
    
    public ProductSearch(int x, int y, Supermarket supermarket) {
        super("PRODUCT_SEARCH", x, y);
        this.supermarket = supermarket;
    }
    
    @Override
    public String interact(Shopper shopper) {
        // This would need console input - for now return placeholder
        return "Product search service - enter product name to search";
    }
    
    public String searchProduct(String productName) {
        return supermarket.findProductLocations(productName);
    }
}
