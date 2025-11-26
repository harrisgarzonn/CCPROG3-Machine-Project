/**
 * Represents a shelf display unit in the supermarket.
 * A shelf has 2 tiers, with each tier containing 4 products (8 total capacity).
 * Can hold various product types including cereals, noodles, snacks, canned goods,
 * condiments, soft drinks, juice, and alcohol.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.3
 */
public class Shelf extends Display {
    /** The address/location identifier of this shelf */
    private String address;

    /** The total capacity of this shelf (8 products) */
    private int capacity;

    /** Array of product types allowed on this shelf */
    private String[] allowedTypes;

    /** Products stored in tier 1 (top tier) */
    private Product[] tier1Products;

    /** Products stored in tier 2 (bottom tier) */
    private Product[] tier2Products;

    /**
     * Constructs a Shelf with the specified address.
     * Initializes both tiers with capacity for 4 products each.
     *
     * @param address the address/location identifier for this shelf
     */
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

    /**
     * Gets the address of this shelf.
     *
     * @return the address string
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the total capacity of this shelf.
     *
     * @return the capacity (8 products)
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets all products from both tiers combined into a single array.
     * The array contains products in order: tier1, tier2.
     *
     * @return an array of all 8 product slots (may contain null values)
     */
    public Product[] getProducts() {
        Product[] allProducts = new Product[8];
        for (int i = 0; i < 4; i++) {
            allProducts[i] = tier1Products[i];
            allProducts[i + 4] = tier2Products[i];
        }
        return allProducts;
    }

    /**
     * Adds a product to a specific tier and slot.
     *
     * @param product the product to add
     * @param tier the tier number (1 or 2)
     * @param slot the slot within the tier (0-3)
     * @return true if the product was successfully added, false otherwise
     */
    public boolean addProductToTier(Product product, int tier, int slot) {
        if (!canHoldProduct(product)) {
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

    /**
     * Removes a product from a specific tier and slot.
     *
     * @param tier the tier number (1 or 2)
     * @param slot the slot within the tier (0-3)
     * @return the removed product, or null if the slot was empty or invalid
     */
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

    /**
     * Gets a product from a specific tier and slot without removing it.
     *
     * @param tier the tier number (1 or 2)
     * @param slot the slot within the tier (0-3)
     * @return the product at the specified location, or null if empty or invalid
     */
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
     * Checks if this shelf can hold the specified product type.
     * Allowed types include: CEREAL, NOODLES, SNACKS, CANNED_GOODS,
     * CONDIMENTS, SOFT_DRINK, JUICE, and ALCOHOL.
     *
     * @param product the product to check
     * @return true if the product type is allowed, false otherwise
     */
    public boolean canHoldProduct(Product product) {
        for (String allowedType : allowedTypes) {
            if (product.getType().equals(allowedType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the number of empty slots across both tiers.
     *
     * @return the number of available slots
     */
    public int getAvailableSlots() {
        int count = 0;
        for (Product tier1Product : tier1Products) {
            if (tier1Product == null) {
                count++;
            }
        }
        for (Product tier2Product : tier2Products) {
            if (tier2Product == null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if the shelf is completely full.
     *
     * @return true if all slots are occupied, false otherwise
     */
    public boolean isFull() {
        return getAvailableSlots() == 0;
    }

    /**
     * Adds a product to a general slot (0-7) without specifying tier.
     * Slots 0-3 are tier 1, slots 4-7 are tier 2.
     *
     * @param product the product to add
     * @param slot the general slot number (0-7)
     * @return true if the product was successfully added, false otherwise
     */
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

    /**
     * Removes a product from a general slot (0-7) without specifying tier.
     *
     * @param slot the general slot number (0-7)
     * @return the removed product, or null if the slot was empty or invalid
     */
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

    /**
     * Gets a product from a general slot (0-7) without removing it.
     *
     * @param slot the general slot number (0-7)
     * @return the product at the specified slot, or null if empty or invalid
     */
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
