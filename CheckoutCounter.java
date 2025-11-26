import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

/*
  Checkout counter service where shoppers can pay for their products.
  Generates receipts and applies senior citizen discounts.
 */

public class CheckoutCounter extends Service {
    public CheckoutCounter(int x, int y, String floor) {
        super("CHECKOUT", x, y, floor);
    }

    @Override
    public String interact(Shopper shopper) {
        // Check if shopper has any products to checkout
        if (shopper.getTotalProductCount() == 0) {
            return "You have no products to checkout!";
        }

        // Build receipt
        StringBuilder receipt = new StringBuilder();
        receipt.append("=== RECEIPT ===\n");
        receipt.append("Customer: ").append(shopper.getName()).append("\n");
        receipt.append("Age: ").append(shopper.getAge()).append("\n\n");

        double total = 0;
        double discountedTotal = 0;
        boolean hasDiscount = false;

        // Collect all products from shopper (hand-carried and equipment)
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(shopper.getHandCarried());
        if (shopper.getEquipment() != null) {
            for (Product p : shopper.getEquipment().getProducts()) {
                allProducts.add(p);
            }
        }

        // Calculate prices and discounts for each product
        for (Product product : allProducts) {
            double price = product.getPrice();
            double discountRate = shopper.getSeniorDiscount(product);
            double discount = discountRate * price;
            double finalPrice = price - discount;

            // Add product line to receipt
            receipt.append(String.format("%s (SN: %s) - PHP %.2f",
                    product.getName(), product.getSerialNumber(), price));

            // Add discount information if applicable
            if (discount > 0) {
                receipt.append(String.format(" - Discount: PHP %.2f (%.0f%% off)",
                        discount, discountRate * 100));
                hasDiscount = true;
            }
            receipt.append("\n");

            total += price;
            discountedTotal += finalPrice;
        }

        // Add totals to receipt
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

        // Mark shopper as checked out and clear their products/equipment
        shopper.setCheckedOut(true);
        return receipt.toString();
    }
}
