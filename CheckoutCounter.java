import java.util.ArrayList;
import java.util.List;


public class CheckoutCounter extends Service {
    public CheckoutCounter(int x, int y) {
        super("CHECKOUT", x, y);
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
        
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(shopper.getHandCarried());
        if (shopper.getEquipment() != null) {
            for (Product p : shopper.getEquipment().getProducts()) {
                allProducts.add(p);
            }
        }
        for (Product product : allProducts) {
            double price = product.getPrice();
            double discount = shopper.getSeniorDiscount(product) * price;
            double finalPrice = price - discount;
            
            receipt.append(String.format("%s (SN: %s) - PHP %.2f", 
                product.getName(), product.getSerialNumber(), price));
            if (discount > 0) {
                receipt.append(String.format(" - Discount: PHP %.2f", discount));
            }
            receipt.append("\n");
            
            total += price;
            discountedTotal += finalPrice;
        }
        
        receipt.append("\nTotal: PHP ").append(String.format("%.2f", total));
        if (total != discountedTotal) {
            receipt.append("\nDiscounted Total: PHP ").append(String.format("%.2f", discountedTotal));
            receipt.append("\n(Senior Citizen Discount Applied)");
        }
        
        shopper.setCheckedOut(true);
        return receipt.toString();
    }
}
