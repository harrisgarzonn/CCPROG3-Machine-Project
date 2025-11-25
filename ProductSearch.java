import java.util.Scanner;

public class ProductSearch extends Service {
    private Supermarket supermarket;

    public ProductSearch(int x, int y, Supermarket supermarket, String floor) {
        super("PRODUCT_SEARCH", x, y, floor);
        this.supermarket = supermarket;
    }

    @Override
    public String interact(Shopper shopper) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product name to search: ");
        String productName = scanner.nextLine();

        return searchProduct(productName);
    }

    public String searchProduct(String productName) {
        return supermarket.findProductLocations(productName);
    }
}