// Refrigerator class with 3 tiers, each tier can contain 3 products (9 total capacity)
public class Refrigerator extends Display {
    private String address;
    private int capacity;
    private String[] allowedTypes;
    private Product[] tier1Products;
    private Product[] tier2Products;
    private Product[] tier3Products;

    // Constructor
    public Refrigerator(String address) {
        super(address, 9, new String[]{"MILK", "FROZEN_FOOD", "CHEESE"});
        this.address = address;
        this.capacity = 9;
        this.allowedTypes = new String[]{"MILK", "FROZEN_FOOD", "CHEESE"};
        this.tier1Products = new Product[3];
        this.tier2Products = new Product[3];
        this.tier3Products = new Product[3];
    }

    // Getters
    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    // Get all products (combined from all three tiers)
    public Product[] getProducts() {
        Product[] allProducts = new Product[9];
        for (int i = 0; i < 3; i++) {
            allProducts[i] = tier1Products[i];
            allProducts[i + 3] = tier2Products[i];
            allProducts[i + 6] = tier3Products[i];
        }
        return allProducts;
    }

    // Add product to a specific tier
    public boolean addProductToTier(Product product, int tier, int slot) {
        if (canHoldProduct(product) == false) {
            return false;
        }

        if (tier == 1) {
            if (slot < 0 || slot >= 3) {
                return false;
            }
            if (tier1Products[slot] != null) {
                return false;
            }
            tier1Products[slot] = product;
            return true;
        } else if (tier == 2) {
            if (slot < 0 || slot >= 3) {
                return false;
            }
            if (tier2Products[slot] != null) {
                return false;
            }
            tier2Products[slot] = product;
            return true;
        } else if (tier == 3) {
            if (slot < 0 || slot >= 3) {
                return false;
            }
            if (tier3Products[slot] != null) {
                return false;
            }
            tier3Products[slot] = product;
            return true;
        }
        return false;
    }

    // Remove product from tier
    public Product removeProductFromTier(int tier, int slot) {
        if (tier == 1) {
            if (slot < 0 || slot >= 3) {
                return null;
            }
            Product product = tier1Products[slot];
            tier1Products[slot] = null;
            return product;
        } else if (tier == 2) {
            if (slot < 0 || slot >= 3) {
                return null;
            }
            Product product = tier2Products[slot];
            tier2Products[slot] = null;
            return product;
        } else if (tier == 3) {
            if (slot < 0 || slot >= 3) {
                return null;
            }
            Product product = tier3Products[slot];
            tier3Products[slot] = null;
            return product;
        }
        return null;
    }

    // Get product from tier
    public Product getProductFromTier(int tier, int slot) {
        if (tier == 1) {
            if (slot < 0 || slot >= 3) {
                return null;
            }
            return tier1Products[slot];
        } else if (tier == 2) {
            if (slot < 0 || slot >= 3) {
                return null;
            }
            return tier2Products[slot];
        } else if (tier == 3) {
            if (slot < 0 || slot >= 3) {
                return null;
            }
            return tier3Products[slot];
        }
        return null;
    }

    // Get tier products
    public Product[] getTier1Products() {
        return tier1Products;
    }

    public Product[] getTier2Products() {
        return tier2Products;
    }

    public Product[] getTier3Products() {
        return tier3Products;
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
        for (int i = 0; i < tier3Products.length; i++) {
            if (tier3Products[i] == null) {
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

    // Add product to general slot (0-8)
    public boolean addProduct(Product product, int slot) {
        if (slot < 0 || slot >= 9) {
            return false;
        }
        if (slot < 3) {
            return addProductToTier(product, 1, slot);
        } else if (slot < 6) {
            return addProductToTier(product, 2, slot - 3);
        } else {
            return addProductToTier(product, 3, slot - 6);
        }
    }

    // Remove product from general slot
    public Product removeProduct(int slot) {
        if (slot < 0 || slot >= 9) {
            return null;
        }
        if (slot < 3) {
            return removeProductFromTier(1, slot);
        } else if (slot < 6) {
            return removeProductFromTier(2, slot - 3);
        } else {
            return removeProductFromTier(3, slot - 6);
        }
    }

    // Get product from general slot
    public Product getProduct(int slot) {
        if (slot < 0 || slot >= 9) {
            return null;
        }
        if (slot < 3) {
            return getProductFromTier(1, slot);
        } else if (slot < 6) {
            return getProductFromTier(2, slot - 3);
        } else {
            return getProductFromTier(3, slot - 6);
        }
    }
}
