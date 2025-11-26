import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a checkout counter service where shoppers can finalize their purchases.
 * Generates a detailed receipt showing all products, prices, applicable discounts,
 * and saves the receipt to a text file.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.0
 */
public class CheckoutCounter extends Service {
    /**
     * Constructs a new CheckoutCounter at the specified location.
     *
     * @param x the x-coordinate of the checkout counter
     * @param y the y-coordinate of the checkout counter
     * @param floor the floor level where the checkout counter is located
     */
    public CheckoutCounter(int x, int y, String floor) {
        super("CHECKOUT", x, y, floor);
    }

    /**
     * Processes the checkout for a shopper, generating a detailed receipt.
     * The receipt includes customer information, itemized product list with prices,
     * any applicable senior citizen discounts, total cost, and is saved to a file.
     * The shopper's checkout status is set to true after successful checkout.
     *
     * @param shopper the shopper checking out their products
     * @return a formatted receipt string showing all purchase details, or an error
     *         message if the shopper has no products to checkout
     */
    @Override
    public String interact(Shopper shopper) {
        if (shopper.getTotalProductCount() == 0) {
            return "You have no products to checkout!";
        }

        StringBuilder receipt = new StringBuilder();
        receipt.append("=== RECEIPT ===\n");
        receipt.append("Customer: ").append(shopper.getName()).append("\n");
        receipt.append("Age: ").append(shopper.getAge()).append("\n\n");

        double total = 0;
        double discountedTotal = 0;
        boolean hasDiscount = false;

        // Collect all products from hand-carried and equipment
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(shopper.getHandCarried());
        if (shopper.getEquipment() != null) {
            for (Product p : shopper.getEquipment().getProducts()) {
                allProducts.add(p);
            }
        }

        // Process each product and calculate totals
        for (Product product : allProducts) {
            double price = product.getPrice();
            double discountRate = shopper.getSeniorDiscount(product);
            double discount = discountRate * price;
            double finalPrice = price - discount;

            receipt.append(String.format("%s (SN: %s) - PHP %.2f",
                    product.getName(), product.getSerialNumber(), price));

            if (discount > 0) {
                receipt.append(String.format(" - Discount: PHP %.2f (%.0f%% off)",
                        discount, discountRate * 100));
                hasDiscount = true;
            }
            receipt.append("\n");

            total += price;
            discountedTotal += finalPrice;
        }

        receipt.append("\nTotal: PHP ").append(String.format("%.2f", total));

        if (hasDiscount) {
            receipt.append("\nDiscounted Total: PHP ").append(String.format("%.2f", discountedTotal));
            receipt.append("\n(Senior Citizen Discount Applied)");
        }

        // Save receipt to file
        try {
            String filename = "receipt_" + shopper.getName().replaceAll("\\s+", "_") +
                    "_" + System.currentTimeMillis() + ".txt";
            FileWriter writer = new FileWriter(filename);
            writer.write(receipt.toString());
            writer.close();
            receipt.append("\n\nReceipt saved to: ").append(filename);
        } catch (IOException e) {
            receipt.append("\n\nWarning: Could not save receipt to file.");
        }

        shopper.setCheckedOut(true);
        return receipt.toString();
    }
}
