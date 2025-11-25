import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class CheckoutCounter extends Service {
    public CheckoutCounter(int x, int y, String floor) {
        super("CHECKOUT", x, y, floor);
    }

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

        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(shopper.getHandCarried());
        if (shopper.getEquipment() != null) {
            for (Product p : shopper.getEquipment().getProducts()) {
                allProducts.add(p);
            }
        }

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