// This shelf class have 2 tiers, because there are a lot and different types of products for each
// shelf. A shelf can contain 4 products (with different names / unique names (some producuts though))
public class Shelf extends Display {
    private String address;
    private int capacity;
    private String[] allowedTypes;
    private Product[] tier1Products;
    private Product[] tier2Products;

    // Constructor
    public Shelf(String address) {
        super(address, 8, new String[]{
            "CEREAL", "NOODLES", "SNACKS", "CANNED_GOODS", "CONDIMENTS",
            "SOFT_DRINK", "JUICE", "ALCOHOL"
        });
        this.address = address;
        this.capacity = 8;
        this.allowedTypes = new String[]{
            "CEREAL", "NOODLES", "SNACKS", "CANNED_GOODS", "CONDIMENTS",
            "SOFT_DRINK", "JUICE", "ALCOHOL"
        };
        this.tier1Products = new Product[4];
        this.tier2Products = new Product[4];
    }

    // Getters
    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    // Get all products (combined from both tiers)
    public Product[] getProducts() {
        Product[] allProducts = new Product[8];
        for (int i = 0; i < 4; i++) {
            allProducts[i] = tier1Products[i];
            allProducts[i + 4] = tier2Products[i];
        }
        return allProducts;
    }

    // Add product to a tier
    public boolean addProductToTier(Product product, int tier, int slot) {
        if (canHoldProduct(product) == false) {
            return false;
        }

        if (tier == 1) {
            if (slot < 0 || slot >= 4) {
                return false;
            }
            if (tier1Products[slot] != null) {
                return false;
            }
            tier1Products[slot] = product;
            return true;
        } else if (tier == 2) {
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

    // Get tier 1 products
    public Product[] getTier1Products() {
        return tier1Products;
    }

    // Get tier 2 products
    public Product[] getTier2Products() {
        return tier2Products;
    }

    // Check if product type is allowed
    public boolean canHoldProduct(Product product) {
        for (int i = 0; i < allowedTypes.length; i++) {
            if (product.getType().equals(allowedTypes[i])) {
                return true;
            }
        }
        return false;
    }

    // Count empty slots
    public int getAvailableSlots() {
        int count = 0;
        for (int i = 0; i < tier1Products.length; i++) {
            if (tier1Products[i] == null) {
                count++;
            }
        }
        for (int i = 0; i < tier2Products.length; i++) {
            if (tier2Products[i] == null) {
                count++;
            }
        }
        return count;
    }

    // Check if full
    public boolean isFull() {
        if (getAvailableSlots() == 0) {
            return true;
        }
        return false;
    }

    // Add product to general slot
    public boolean addProduct(Product product, int slot) {
        if (slot < 0 || slot >= 8) {
            return false;
        }
        if (slot < 4) {
            return addProductToTier(product, 1, slot);
        } else {
            return addProductToTier(product, 2, slot - 4);
        }
    }

    // Remove product from general slot
    public Product removeProduct(int slot) {
        if (slot < 0 || slot >= 8) {
            return null;
        }
        if (slot < 4) {
            return removeProductFromTier(1, slot);
        } else {
            return removeProductFromTier(2, slot - 4);
        }
    }

    // Get product from general slot
    public Product getProduct(int slot) {
        if (slot < 0 || slot >= 8) {
            return null;
        }
        if (slot < 4) {
            return getProductFromTier(1, slot);
        } else {
            return getProductFromTier(2, slot - 4);
        }
    }
}
