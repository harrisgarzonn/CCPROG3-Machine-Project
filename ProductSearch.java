import java.util.Scanner;

/**
 * Represents a product search service in the supermarket.
 * Allows shoppers to search for products by name and find their locations.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.0
 */
public class ProductSearch extends Service {
    /** The supermarket instance used for product searches */
    private Supermarket supermarket;

    /**
     * Constructs a ProductSearch service at the specified location.
     *
     * @param x the x-coordinate of the service location
     * @param y the y-coordinate of the service location
     * @param supermarket the supermarket instance to search products in
     * @param floor the floor where this service is located ("GF" or "2F")
     */
    public ProductSearch(int x, int y, Supermarket supermarket, String floor) {
        super("PRODUCT_SEARCH", x, y, floor);
        this.supermarket = supermarket;
    }

    /**
     * Interacts with a shopper to perform a product search.
     * Prompts the shopper to enter a product name and returns search results.
     *
     * @param shopper the shopper using this service
     * @return a string containing the search results
     */
    @Override
    public String interact(Shopper shopper) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product name to search: ");
        String productName = scanner.nextLine();

        return searchProduct(productName);
    }

    /**
     * Searches for products by name in the supermarket.
     *
     * @param productName the name of the product to search for
     * @return a string containing all matching product locations
     */
    public String searchProduct(String productName) {
        return supermarket.findProductLocations(productName);
    }
}
