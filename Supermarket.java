import java.util.*;

public class Supermarket {
    private Map<String, Display> displays;
    private List<Service> services;
    private Shopper shopper;
    private char[][] groundFloor;
    
    public Supermarket() {
        this.displays = new HashMap<>();
        this.services = new ArrayList<>();
        this.groundFloor = new char[22][22];
        initializeMap();
    }
    
    private void initializeMap() {

        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                groundFloor[i][j] = '.'; 
            }
        }
        
        addWalls();
        addDisplays();
        addServices();
        stockInitialProducts();
    }
    
    private void addWalls() {
        for (int i = 0; i < 22; i++) {
            groundFloor[0][i] = 'W'; 
            groundFloor[21][i] = 'W'; 
            groundFloor[i][0] = 'W'; 
            groundFloor[i][21] = 'W'; 
        }
        
        int[] wallX = {1, 3, 5, 7, 10, 11, 14, 16, 18, 20, 10, 11};
        int[] wallY = {18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 17, 17};
        
        for (int i = 0; i < wallX.length; i++) {
            groundFloor[wallY[i]][wallX[i]] = 'W';
        }
    }
    
    private void addDisplays() {
        for (int x = 1; x <= 6; x++) {
            addDisplay("GF, Chilled Counter " + x, new ChilledCounter("GF, Chilled Counter " + x), x, 1);
        }
        for (int x = 8; x <= 9; x++) {
            addDisplay("GF, Chilled Counter " + x, new ChilledCounter("GF, Chilled Counter " + x), x, 1);
        }
        for (int x = 15; x <= 20; x++) {
            addDisplay("GF, Chilled Counter " + x, new ChilledCounter("GF, Chilled Counter " + x), x, 1);
        }
        
        int[][] shelfGroups = {
            {2, 4, 7}, {2, 10, 13}, {3, 4, 7}, {3, 10, 13},
            {6, 4, 7}, {6, 10, 13}, {7, 4, 7}, {7, 10, 13},
            {14, 4, 7}, {14, 10, 13}, {15, 4, 7}, {15, 10, 13},
            {18, 4, 7}, {18, 10, 13}, {19, 4, 7}, {19, 10, 13}
        };
        
        int shelfCount = 1;
        for (int[] group : shelfGroups) {
            int x = group[0];
            int startY = group[1];
            int endY = group[2];
            for (int y = startY; y <= endY; y++) {
                addDisplay("GF, Shelf " + shelfCount, new Shelf("GF, Shelf " + shelfCount), x, y);
                shelfCount++;
            }
        }
        
        int tableCount = 1;
        for (int x = 10; x <= 11; x++) {
            for (int y = 4; y <= 7; y++) {
                addDisplay("GF, Table " + tableCount, new Table("GF, Table " + tableCount), x, y);
                tableCount++;
            }
            for (int y = 10; y <= 13; y++) {
                addDisplay("GF, Table " + tableCount, new Table("GF, Table " + tableCount), x, y);
                tableCount++;
            }
        }
    }
    
    private void addDisplay(String address, Display display, int x, int y) {
        displays.put(address, display);
        if (display instanceof Table) {
            groundFloor[y][x] = 'T'; 
        } else if (display instanceof ChilledCounter) {
            groundFloor[y][x] = 'C'; 
        } else if (display instanceof Shelf) {
            groundFloor[y][x] = 'S'; 
        }
    }
    
    private void addServices() {
        services.add(new Entrance(11, 21));
        groundFloor[21][11] = 'E';
        
        services.add(new Exit(10, 21));
        groundFloor[21][10] = 'X'; 
        
        int[] checkoutX = {2, 4, 6, 8, 13, 15, 17, 19};
        for (int x : checkoutX) {
            services.add(new CheckoutCounter(x, 18));
            groundFloor[18][x] = 'K'; 
        }
        
        services.add(new ProductSearch(8, 15, this));
        services.add(new ProductSearch(13, 15, this));
        groundFloor[15][8] = '?';
        groundFloor[15][13] = '?';
        
        services.add(new BasketStation(1, 20));
        groundFloor[20][1] = 'B';
        
        services.add(new CartStation(20, 20));
        groundFloor[20][20] = 'R';
        
        services.add(new Stairs(1, 15));
        services.add(new Stairs(20, 15));
        groundFloor[15][1] = 'U';
        groundFloor[15][20] = 'U';
    }
    
    private void stockInitialProducts() {
        stockChilledCounterProducts();
        stockShelfProducts();
        stockTableProducts();
    }
    
private void stockChilledCounterProducts() {
    int productCount = 1;
    for (Display display : displays.values()) {
        if (display instanceof ChilledCounter) {
            if (productCount % 3 == 1) {
                display.addProduct(new Product("CHK00" + String.format("%03d", productCount), "Chicken Breast", 180.0, "CHICKEN"), 0);
                display.addProduct(new Product("CHK00" + String.format("%03d", productCount+1), "Chicken Thighs", 160.0, "CHICKEN"), 1);
            } else if (productCount % 3 == 2) {
                display.addProduct(new Product("BEF00" + String.format("%03d", productCount), "Beef Rib", 350.0, "BEEF"), 0);
                display.addProduct(new Product("BEF00" + String.format("%03d", productCount+1), "Ground Beef", 280.0, "BEEF"), 1);
            } else {
                display.addProduct(new Product("SEA00" + String.format("%03d", productCount), "Tilapia", 120.0, "SEAFOOD"), 0);
                display.addProduct(new Product("SEA00" + String.format("%03d", productCount+1), "Shrimp", 280.0, "SEAFOOD"), 1);
            }
            productCount++;
        }
    }
}
    
    private void stockShelfProducts() {
    String[][][] shelfProductGroups = {
        { 
            {"CER001", "Java Cereal", "120.0", "CEREAL"},
            {"CER002", "OOPsie Oaties", "150.0", "CEREAL"},
            {"CER003", "Barley Bytes", "110.0", "CEREAL"}
        },
        {  
            {"NDL001", "Instant Noodles", "15.0", "NOODLES"},
            {"NDL002", "Ramen Pack", "25.0", "NOODLES"},
            {"NDL003", "Pasta", "45.0", "NOODLES"}
        },
        { 
            {"SNK001", "Chocolate Cookies", "45.0", "SNACKS"},
            {"SNK002", "Potato Chips", "35.0", "SNACKS"},
            {"SNK003", "Candy Bars", "20.0", "SNACKS"}
        },
        {
            {"CAN001", "Canned Tuna", "35.0", "CANNED_GOODS"},
            {"CAN002", "Canned Sardines", "25.0", "CANNED_GOODS"},
            {"CAN003", "Canned Corn", "30.0", "CANNED_GOODS"}
        },
        { 
            {"CON001", "Table Salt", "12.0", "CONDIMENTS"},
            {"CON002", "Black Pepper", "15.0", "CONDIMENTS"},
            {"CON003", "Soy Sauce", "25.0", "CONDIMENTS"}
        },
        { 
            {"SFT001", "Cola Drink", "25.0", "SOFT_DRINK"},
            {"SFT002", "Lemon Soda", "20.0", "SOFT_DRINK"},
            {"SFT003", "Sparkling Water", "15.0", "SOFT_DRINK"}
        },
        {
            {"JUC001", "Orange Juice", "40.0", "JUICE"},
            {"JUC002", "Apple Juice", "45.0", "JUICE"},
            {"JUC003", "Pineapple Juice", "35.0", "JUICE"}
        },
        { 
            {"ALC001", "Beer", "80.0", "ALCOHOL"},
            {"ALC002", "Wine", "250.0", "ALCOHOL"},
            {"ALC003", "Soju", "120.0", "ALCOHOL"}
        }
    };

    Map<Integer, List<Display>> shelvesByX = new HashMap<>();
    for (Display display : displays.values()) {
        if (display instanceof Shelf) {
        }
    }

    int groupIndex = 0;
    for (Display display : displays.values()) {
        if (display instanceof Shelf && groupIndex < shelfProductGroups.length) {
            String[][] productGroup = shelfProductGroups[groupIndex];
            for (int i = 0; i < productGroup.length && i < display.getCapacity(); i++) {
                String[] productData = productGroup[i];
                Product product = new Product(
                    productData[0], 
                    productData[1], 
                    Double.parseDouble(productData[2]), 
                    productData[3]
                );
                display.addProduct(product, i);
            }
            groupIndex++;
        }
    }
}
    
    private void stockTableProducts() {
    String[][] fruitGroups = {
        {"Apple", "Banana", "Orange"},
        {"Grapes", "Mango", "Pineapple"},
        {"Watermelon", "Strawberry", "Kiwi"},
        {"Peach", "Pear", "Plum"}
    };

    int groupIndex = 0;
    for (Display display : displays.values()) {
        if (display instanceof Table && groupIndex < fruitGroups.length) {
            String[] fruits = fruitGroups[groupIndex];
            for (int i = 0; i < fruits.length && i < display.getCapacity(); i++) {
                Product product = new Product(
                    "FRU00" + String.format("%03d", (groupIndex * 3 + i + 1)),
                    fruits[i],
                    20 + ((groupIndex * 3 + i) * 5),
                    "FRUIT"
                );
                display.addProduct(product, i);
            }
            groupIndex++;
        }
    }
}
    
    public void displaySimpleMap() {
        Shopper shopper = getShopper();
        System.out.println("\n=== SUPERMARKET MAP ===");
        System.out.println("Your position: (" + shopper.getX() + ", " + shopper.getY() + ")");
        System.out.println("Facing: " + shopper.getDirection());
        System.out.println();
        
        int centerX = shopper.getX();
        int centerY = shopper.getY();
        
        for (int i = centerY - 2; i <= centerY + 2; i++) {
            for (int j = centerX - 2; j <= centerX + 2; j++) {
                if (i < 0 || i >= 22 || j < 0 || j >= 22) {
                    System.out.print("  "); 
                } else if (i == centerY && j == centerX) {
                    switch (shopper.getDirection()) {
                        case "NORTH": System.out.print("^ "); break;
                        case "SOUTH": System.out.print("v "); break;
                        case "WEST": System.out.print("< "); break;
                        case "EAST": System.out.print("> "); break;
                    }
                } else {
                    System.out.print(groundFloor[i][j] + " ");
                }
            }
            System.out.println(); 
        }
        
        System.out.println("\nLegend: ^v<> = You, . = Free, W = Wall");
        System.out.println("        T = Table, C = Chilled Counter, S = Shelf");
        System.out.println("        E = Entrance, X = Exit, B = Basket, R = Cart");
        System.out.println("        K = Checkout, ? = Search, U = Stairs");
        
        displayWhatIsFront();
    }
    
    private void displayWhatIsFront() {
        Shopper shopper = getShopper();
        int x = shopper.getX();
        int y = shopper.getY();
        int frontX = x, frontY = y;
        
        switch (shopper.getDirection()) {
            case "NORTH": frontY--; break;
            case "SOUTH": frontY++; break;
            case "WEST": frontX--; break;
            case "EAST": frontX++; break;
        }
        
        System.out.print("\nIn front of you: ");
        if (frontX >= 0 && frontX < 22 && frontY >= 0 && frontY < 22) {
            char frontTile = groundFloor[frontY][frontX];
            switch (frontTile) {
                case 'W': System.out.println("Wall"); break;
                case 'T': System.out.println("Table (Fruits)"); break;
                case 'C': System.out.println("Chilled Counter (Meats/Seafood)"); break;
                case 'S': System.out.println("Shelf (Various Products)"); break;
                case 'E': System.out.println("Entrance"); break;
                case 'X': System.out.println("Exit"); break;
                case 'B': System.out.println("Basket Station"); break;
                case 'R': System.out.println("Cart Station"); break;
                case 'K': System.out.println("Checkout Counter"); break;
                case '?': System.out.println("Product Search"); break;
                case 'U': System.out.println("Stairs"); break;
                case '.': System.out.println("Empty space"); break;
                default: System.out.println("Unknown"); break;
            }
        } else {
            System.out.println("Out of bounds");
        }
    }
    
    public boolean canMoveTo(int x, int y) {
        if (x < 0 || x >= 22 || y < 0 || y >= 22) {
            return false;
        }
        char tile = groundFloor[y][x];
        return tile == '.' || tile == 'E' || tile == 'X' || tile == 'B' || tile == 'R' || tile == 'K' || tile == '?' || tile == 'U';
    }
    
    public Display getDisplayAt(int x, int y) {
        for (Map.Entry<String, Display> entry : displays.entrySet()) {
            String address = entry.getKey();
            if (address.contains("Chilled Counter") && groundFloor[y][x] == 'C') {
                return entry.getValue();
            } else if (address.contains("Shelf") && groundFloor[y][x] == 'S') {
                return entry.getValue();
            } else if (address.contains("Table") && groundFloor[y][x] == 'T') {
                return entry.getValue();
            }
        }
        return null;
    }
    
    public Service getServiceAt(int x, int y) {
        for (Service service : services) {
            if (service.getX() == x && service.getY() == y) {
                return service;
            }
        }
        return null;
    }
    
    public String findProductLocations(String productName) {
        StringBuilder result = new StringBuilder();
        result.append("Search results for: ").append(productName).append("\n");
        
        boolean found = false;
        for (Display display : displays.values()) {
            for (int i = 0; i < display.getCapacity(); i++) {
                Product product = display.getProduct(i);
                if (product != null && product.getName().toLowerCase().contains(productName.toLowerCase())) {
                    result.append("- ").append(product.getName()).append(" at ").append(display.getAddress()).append("\n");
                    found = true;
                }
            }
        }
        
        if (!found) {
            result.append("No products found.");
        }
        return result.toString();
    }
    
    public void setShopper(Shopper shopper) {
        this.shopper = shopper;
        this.shopper.setPosition(11, 21);
    }
    
    public Shopper getShopper() { return shopper; }
}

class Exit extends Service {
    public Exit(int x, int y) {
        super("EXIT", x, y);
    }
    
    @Override
    public String interact(Shopper shopper) {
        if (shopper.hasCheckedOut() || shopper.getTotalProductCount() == 0) {
            return "You have left the supermarket. Thank you for shopping!";
        } else {
            return "You need to checkout first before leaving!";
        }
    }
}

class Stairs extends Service {
    public Stairs(int x, int y) {
        super("STAIRS", x, y);
    }
    
    @Override
    public String interact(Shopper shopper) {
        return "Stairs to second floor (MCO2 feature)";
    }
}
