/**
 * Represents a refrigerator display unit in the supermarket.
 * A refrigerator has 3 tiers, with each tier containing 3 products (9 total capacity).
 * Can only hold products of types: MILK, FROZEN_FOOD, and CHEESE.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.0
 */
public class Refrigerator extends Display {
    /** The address/location identifier of this refrigerator */
    private String address;

    /** The total capacity of this refrigerator (9 products) */
    private int capacity;

    /** Array of product types allowed in this refrigerator */
    private String[] allowedTypes;

    /** Products stored in tier 1 (top tier) */
    private Product[] tier1Products;

    /** Products stored in tier 2 (middle tier) */
    private Product[] tier2Products;

    /** Products stored in tier 3 (bottom tier) */
    private Product[] tier3Products;

    /**
     * Constructs a Refrigerator with the specified address.
     * Initializes all three tiers with capacity for 3 products each.
     *
     * @param address the address/location identifier for this refrigerator
     */
    public Refrigerator(String address) {
        super(address, 9, new String[]{"MILK", "FROZEN_FOOD", "CHEESE"});
        this.address = address;
        this.capacity = 9;
        this.allowedTypes = new String[]{"MILK", "FROZEN_FOOD", "CHEESE"};
        this.tier1Products = new Product[3];
        this.tier2Products = new Product[3];
        this.tier3Products = new Product[3];
    }

    /**
     * Gets the address of this refrigerator.
     *
     * @return the address string
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the total capacity of this refrigerator.
     *
     * @return the capacity (9 products)
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets all products from all three tiers combined into a single array.
     * The array contains products in order: tier1, tier2, tier3.
     *
     * @return an array of all 9 product slots (may contain null values)
     */
    public Product[] getProducts() {
        Product[] allProducts = new Product[9];
        for (int i = 0; i < 3; i++) {
            allProducts[i] = tier1Products[i];
            allProducts[i + 3] = tier2Products[i];
            allProducts[i + 6] = tier3Products[i];
        }
        return allProducts;
    }

    /**
     * Adds a product to a specific tier and slot.
     *
     * @param product the product to add
     * @param tier the tier number (1, 2, or 3)
     * @param slot the slot within the tier (0-2)
     * @return true if the product was successfully added, false otherwise
     */
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

    /**
     * Removes a product from a specific tier and slot.
     *
     * @param tier the tier number (1, 2, or 3)
     * @param slot the slot within the tier (0-2)
     * @return the removed product, or null if the slot was empty or invalid
     */
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

    /**
     * Gets a product from a specific tier and slot without removing it.
     *
     * @param tier the tier number (1, 2, or 3)
     * @param slot the slot within the tier (0-2)
     * @return the product at the specified location, or null if empty or invalid
     */
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

    /**
     * Gets all products in tier 1.
     *
     * @return array of tier 1 products
     */
    public Product[] getTier1Products() {
        return tier1Products;
    }

    /**
     * Gets all products in tier 2.
     *
     * @return array of tier 2 products
     */
    public Product[] getTier2Products() {
        return tier2Products;
    }

    /**
     * Gets all products in tier 3.
     *
     * @return array of tier 3 products
     */
    public Product[] getTier3Products() {
        return tier3Products;
    }

    /**
     * Checks if this refrigerator can hold the specified product type.
     * Only MILK, FROZEN_FOOD, and CHEESE types are allowed.
     *
     * @param product the product to check
     * @return true if the product type is allowed, false otherwise
     */
    public boolean canHoldProduct(Product product) {
        for (int i = 0; i < allowedTypes.length; i++) {
            if (product.getType().equals(allowedTypes[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the number of empty slots across all tiers.
     *
     * @return the number of available slots
     */
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

    /**
     * Checks if the refrigerator is completely full.
     *
     * @return true if all slots are occupied, false otherwise
     */
    public boolean isFull() {
        if (getAvailableSlots() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Adds a product to a general slot (0-8) without specifying tier.
     * Slots 0-2 are tier 1, slots 3-5 are tier 2, slots 6-8 are tier 3.
     *
     * @param product the product to add
     * @param slot the general slot number (0-8)
     * @return true if the product was successfully added, false otherwise
     */
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

    /**
     * Removes a product from a general slot (0-8) without specifying tier.
     *
     * @param slot the general slot number (0-8)
     * @return the removed product, or null if the slot was empty or invalid
     */
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

    /**
     * Gets a product from a general slot (0-8) without removing it.
     *
     * @param slot the general slot number (0-8)
     * @return the product at the specified slot, or null if empty or invalid
     */
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
