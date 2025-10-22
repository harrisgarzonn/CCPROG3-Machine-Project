// This shelf class have 2 tiers, because there are a lot and different types of products for each
// shelf. A shelf can contain 4 products (with different names / unique names (some producuts though))
public class Shelf extends Display {
    private Product[] tier1Products;
    private Product[] tier2Products;

    // Main Constructor
    public Shelf(String address) {
        super(address, 8, new String[]{
            "CEREAL", "NOODLES", "SNACKS", "CANNED_GOODS", "CONDIMENTS",
            "SOFT_DRINK", "JUICE", "ALCOHOL"});
        tier1Products = new Product[4];
        tier2Products = new Product[4];
    }

    // Add product to a tier
    public boolean addProductToTier(Product product, int tier, int slot) {
        // Check if product type is allowed
        if (canHoldProduct(product) == false) {
            return false;
        }

        if (tier == 1) {
            // Tier 1
            if (slot < 0 || slot >= 4) {
                return false;
            }
            if (tier1Products[slot] != null) {
                return false;
            }
            tier1Products[slot] = product;
            return true;
        } else if (tier == 2) {
            // Tier 2
            if (slot < 0 || slot >= 4) {
                return false;
            }
            if (tier2Products[slot] != null) {
                return false;
            }
            tier2Products[slot] = product;
            return true;
        }
        return false;
    }

    // Remove product from tier
    public Product removeProductFromTier(int tier, int slot) {
        if (tier == 1) {
            if (slot < 0 || slot >= 4) {
                return null;
            }
            Product product = tier1Products[slot];
            tier1Products[slot] = null;
            return product;
        } else if (tier == 2) {
            if (slot < 0 || slot >= 4) {
                return null;
            }
            Product product = tier2Products[slot];
            tier2Products[slot] = null;
            return product;
        }
        return null;
    }

    // Get product from tier
    public Product getProductFromTier(int tier, int slot) {
        if (tier == 1) {
            if (slot < 0 || slot >= 4) {
                return null;
            }
            return tier1Products[slot];
        } else if (tier == 2) {
            if (slot < 0 || slot >= 4) {
                return null;
            }
            return tier2Products[slot];
        }
        return null;
    }

    // Getters
    public Product[] getTier1Products() {
        return tier1Products;
    }

    public Product[] getTier2Products() {
        return tier2Products;
    }
}
