import java.util.*;
import javafx.scene.control.Alert;

public class Supermarket {
    private Map<String, Display> displays;
    private List<Service> services;
    private Shopper shopper;
    private char[][] groundFloor;
    private char[][] secondFloor;
    private String currentFloor; // "GF" or "2F"

    public Supermarket() {
        this.displays = new HashMap<>();
        this.services = new ArrayList<>();
        this.groundFloor = new char[22][22];
        this.secondFloor = new char[22][22];
        this.currentFloor = "GF";
        initializeMap();
    }

    // Add these getter methods
    public char[][] getGroundFloor() {
        return groundFloor;
    }

    public char[][] getSecondFloor() {
        return secondFloor;
    }

    private void initializeMap() {
        // Initialize ground floor
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                groundFloor[i][j] = '.';
            }
        }

        // Initialize second floor
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                secondFloor[i][j] = '.';
            }
        }

        addWalls();
        addDisplays();
        addServices();
        stockInitialProducts();
    }

    private void addWalls() {
        // Ground floor walls
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

        // Second floor walls
        for (int i = 0; i < 22; i++) {
            secondFloor[0][i] = 'W';
            secondFloor[21][i] = 'W';
            secondFloor[i][0] = 'W';
            secondFloor[i][21] = 'W';
        }

        // Second floor specific walls (from the image)
        int[] wall2X = {4, 5, 16, 17};
        int[] wall2Y = {16, 16, 16, 16};

        for (int i = 0; i < wall2X.length; i++) {
            secondFloor[wall2Y[i]][wall2X[i]] = 'W';
        }
    }

    private void addDisplays() {
        // GROUND FLOOR DISPLAYS
        // Chilled counters - FIXED: Add all chilled counters
        int chilledCounterCount = 1;

        // First row: positions 1-6
        for (int x = 1; x <= 6; x++) {
            addDisplay("GF, Chilled Counter " + chilledCounterCount, new ChilledCounter("GF, Chilled Counter " + chilledCounterCount), x, 1, "GF");
            chilledCounterCount++;
        }

        // Second row: positions 8-9
        for (int x = 8; x <= 9; x++) {
            addDisplay("GF, Chilled Counter " + chilledCounterCount, new ChilledCounter("GF, Chilled Counter " + chilledCounterCount), x, 1, "GF");
            chilledCounterCount++;
        }

        // Third row: positions 10-13
        for (int x = 10; x <= 13; x++) {
            addDisplay("GF, Chilled Counter " + chilledCounterCount, new ChilledCounter("GF, Chilled Counter " + chilledCounterCount), x, 1, "GF");
            chilledCounterCount++;
        }

        // Fourth row: positions 15-20
        for (int x = 15; x <= 20; x++) {
            addDisplay("GF, Chilled Counter " + chilledCounterCount, new ChilledCounter("GF, Chilled Counter " + chilledCounterCount), x, 1, "GF");
            chilledCounterCount++;
        }

        // Shelves
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
                addDisplay("GF, Shelf " + shelfCount, new Shelf("GF, Shelf " + shelfCount), x, y, "GF");
                shelfCount++;
            }
        }

        // Tables
        int tableCount = 1;
        for (int x = 10; x <= 11; x++) {
            for (int y = 4; y <= 7; y++) {
                addDisplay("GF, Table " + tableCount, new Table("GF, Table " + tableCount), x, y, "GF");
                tableCount++;
            }
            for (int y = 10; y <= 13; y++) {
                addDisplay("GF, Table " + tableCount, new Table("GF, Table " + tableCount), x, y, "GF");
                tableCount++;
            }
        }

        // SECOND FLOOR DISPLAYS
        // Refrigerators at top - FIXED: Add refrigerators at correct positions
        int refrigeratorCount = 1;
        for (int x = 3; x <= 6; x++) {
            addDisplay("2F, Refrigerator " + refrigeratorCount, new Refrigerator("2F, Refrigerator " + refrigeratorCount), x, 1, "2F");
            refrigeratorCount++;
        }
        for (int x = 9; x <= 12; x++) {
            addDisplay("2F, Refrigerator " + refrigeratorCount, new Refrigerator("2F, Refrigerator " + refrigeratorCount), x, 1, "2F");
            refrigeratorCount++;
        }
        for (int x = 15; x <= 18; x++) {
            addDisplay("2F, Refrigerator " + refrigeratorCount, new Refrigerator("2F, Refrigerator " + refrigeratorCount), x, 1, "2F");
            refrigeratorCount++;
        }

        // Second floor shelves
        int[][] shelf2Groups = {
                {2, 4, 7}, {2, 10, 13}, {3, 4, 7}, {3, 10, 13},
                {6, 4, 7}, {6, 10, 13}, {7, 4, 7}, {7, 10, 13},
                {14, 4, 7}, {14, 10, 13}, {15, 4, 7}, {15, 10, 13},
                {18, 4, 7}, {18, 10, 13}, {19, 4, 7}, {19, 10, 13}
        };

        shelfCount = 1;
        for (int[] group : shelf2Groups) {
            int x = group[0];
            int startY = group[1];
            int endY = group[2];
            for (int y = startY; y <= endY; y++) {
                addDisplay("2F, Shelf " + shelfCount, new Shelf("2F, Shelf " + shelfCount), x, y, "2F");
                shelfCount++;
            }
        }

        // Second floor tables
        tableCount = 1;
        for (int x = 10; x <= 11; x++) {
            for (int y = 4; y <= 7; y++) {
                addDisplay("2F, Table " + tableCount, new Table("2F, Table " + tableCount), x, y, "2F");
                tableCount++;
            }
            for (int y = 10; y <= 13; y++) {
                addDisplay("2F, Table " + tableCount, new Table("2F, Table " + tableCount), x, y, "2F");
                tableCount++;
            }
        }

        // Bottom tables for bread and eggs
        for (int x = 3; x <= 7; x++) {
            addDisplay("2F, Table " + tableCount, new Table("2F, Table " + tableCount), x, 20, "2F");
            tableCount++;
        }
        for (int x = 9; x <= 13; x++) {
            addDisplay("2F, Table " + tableCount, new Table("2F, Table " + tableCount), x, 20, "2F");
            tableCount++;
        }
        for (int x = 15; x <= 18; x++) {
            addDisplay("2F, Table " + tableCount, new Table("2F, Table " + tableCount), x, 20, "2F");
            tableCount++;
        }
    }

    private void addDisplay(String address, Display display, int x, int y, String floor) {
        displays.put(address, display);
        char[][] targetFloor = floor.equals("GF") ? groundFloor : secondFloor;

        if (display instanceof Table) {
            targetFloor[y][x] = 'T';
        } else if (display instanceof ChilledCounter) {
            targetFloor[y][x] = 'C';
        } else if (display instanceof Shelf) {
            targetFloor[y][x] = 'S';
        } else if (display instanceof Refrigerator) {
            targetFloor[y][x] = 'F'; // F for refrigerator (Freezer)
        }
    }

    private void addServices() {
        // GROUND FLOOR SERVICES
        services.add(new Entrance(11, 21, "GF"));
        groundFloor[21][11] = 'E';

        services.add(new Exit(10, 21, "GF"));
        groundFloor[21][10] = 'X';

        int[] checkoutX = {2, 4, 6, 8, 13, 15, 17, 19};
        for (int x : checkoutX) {
            services.add(new CheckoutCounter(x, 18, "GF"));
            groundFloor[18][x] = 'K';
        }

        services.add(new ProductSearch(8, 15, this, "GF"));
        services.add(new ProductSearch(13, 15, this, "GF"));
        groundFloor[15][8] = '?';
        groundFloor[15][13] = '?';

        services.add(new BasketStation(20, 1, "GF"));
        groundFloor[1][20] = 'B';

        services.add(new CartStation(20, 20, "GF"));
        groundFloor[20][20] = 'R';

        services.add(new Stairs(1, 15, "GF"));
        services.add(new Stairs(20, 15, "GF"));
        groundFloor[15][1] = 'U';
        groundFloor[15][20] = 'U';

        // SECOND FLOOR SERVICES
        services.add(new BasketStation(1, 1, "2F"));
        secondFloor[1][1] = 'B';

        services.add(new CartStation(20, 1, "2F"));
        secondFloor[1][20] = 'R';

        services.add(new ProductSearch(7, 20, this, "2F"));
        services.add(new ProductSearch(14, 20, this, "2F"));
        secondFloor[20][7] = '?';
        secondFloor[20][14] = '?';

        services.add(new Stairs(1, 15, "2F"));
        services.add(new Stairs(20, 15, "2F"));
        secondFloor[15][1] = 'U';
        secondFloor[15][20] = 'U';
    }

    private void stockInitialProducts() {
        stockChilledCounterProducts();
        stockShelfProducts();
        stockTableProducts();
        stockRefrigeratorProducts();
        stock2FShelfProducts();
        stock2FTableProducts();
    }

    private void stockChilledCounterProducts() {
        int productCount = 1;
        for (Display display : displays.values()) {
            if (display instanceof ChilledCounter) {
                if (productCount % 3 == 1) {
                    display.addProduct(new Product(
                            String.format("CHK%05d", productCount),
                            "Chicken Breast", 180.0, "CHICKEN"), 0);
                    display.addProduct(new Product(
                            String.format("CHK%05d", productCount + 1),
                            "Chicken Thighs", 160.0, "CHICKEN"), 1);
                } else if (productCount % 3 == 2) {
                    display.addProduct(new Product(
                            String.format("BEF%05d", productCount),
                            "Beef Rib", 350.0, "BEEF"), 0);
                    display.addProduct(new Product(
                            String.format("BEF%05d", productCount + 1),
                            "Ground Beef", 280.0, "BEEF"), 1);
                } else {
                    display.addProduct(new Product(
                            String.format("SEA%05d", productCount),
                            "Tilapia", 120.0, "SEAFOOD"), 0);
                    display.addProduct(new Product(
                            String.format("SEA%05d", productCount + 1),
                            "Shrimp", 280.0, "SEAFOOD"), 1);
                }
                productCount += 2;
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

        int groupIndex = 0;
        for (Display display : displays.values()) {
            if (display instanceof Shelf && display.getAddress().startsWith("GF")) {
                if (groupIndex < shelfProductGroups.length) {
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
            if (display instanceof Table && display.getAddress().startsWith("GF") && groupIndex < fruitGroups.length) {
                String[] fruits = fruitGroups[groupIndex];
                for (int i = 0; i < fruits.length && i < display.getCapacity(); i++) {
                    int serialNum = (groupIndex * 3 + i + 1);
                    String serialNumber = String.format("FRU%05d", serialNum);

                    Product product = new Product(
                            serialNumber,
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

    private void stockRefrigeratorProducts() {
        String[][][] refProductGroups = {
                { // Milk products
                        {"MLK00001", "Fresh Milk", "65.0", "MILK"},
                        {"MLK00002", "Soy Milk", "55.0", "MILK"},
                        {"MLK00003", "Almond Milk", "70.0", "MILK"}
                },
                { // Frozen Food products
                        {"FRZ00001", "Hotdog", "120.0", "FROZEN_FOOD"},
                        {"FRZ00002", "Chicken Nuggets", "180.0", "FROZEN_FOOD"},
                        {"FRZ00003", "Tocino", "150.0", "FROZEN_FOOD"}
                },
                { // Cheese products
                        {"CHS00001", "Sliced Cheese", "85.0", "CHEESE"},
                        {"CHS00002", "Keso de Bola", "200.0", "CHEESE"},
                        {"CHS00003", "Mozzarella", "150.0", "CHEESE"}
                }
        };

        int groupIndex = 0;
        for (Display display : displays.values()) {
            if (display instanceof Refrigerator && groupIndex < refProductGroups.length) {
                String[][] productGroup = refProductGroups[groupIndex];
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

    private void stock2FShelfProducts() {
        String[][][] shelf2ProductGroups = {
                { // Pet Food
                        {"PET00001", "Cat Food", "120.0", "PET_FOOD"},
                        {"PET00002", "Dog Food", "150.0", "PET_FOOD"},
                        {"PET00003", "Bird Seed", "80.0", "PET_FOOD"}
                },
                { // Clothes
                        {"CLO00001", "T-Shirt", "250.0", "CLOTHES"},
                        {"CLO00002", "Shorts", "200.0", "CLOTHES"},
                        {"CLO00003", "Pants", "350.0", "CLOTHES"}
                },
                { // Cleaning Agents
                        {"CLE00001", "Detergent", "95.0", "CLEANING_AGENTS"},
                        {"CLE00002", "Bleach", "75.0", "CLEANING_AGENTS"},
                        {"CLE00003", "Dish Soap", "45.0", "CLEANING_AGENTS"}
                },
                { // Home Essentials
                        {"HOM00001", "Broom", "120.0", "HOME_ESSENTIALS"},
                        {"HOM00002", "Mop", "150.0", "HOME_ESSENTIALS"},
                        {"HOM00003", "Plunger", "80.0", "HOME_ESSENTIALS"}
                },
                { // Stationery
                        {"STN00001", "Paper", "50.0", "STATIONERY"},
                        {"STN00002", "Tape", "25.0", "STATIONERY"},
                        {"STN00003", "Pencils", "30.0", "STATIONERY"}
                },
                { // Dental Care
                        {"DEN00001", "Toothpaste", "85.0", "DENTAL_CARE"},
                        {"DEN00002", "Toothbrush", "55.0", "DENTAL_CARE"},
                        {"DEN00003", "Dental Floss", "65.0", "DENTAL_CARE"}
                },
                { // Hair Care
                        {"HAR00001", "Shampoo", "150.0", "HAIR_CARE"},
                        {"HAR00002", "Conditioner", "140.0", "HAIR_CARE"},
                        {"HAR00003", "Hair Wax", "120.0", "HAIR_CARE"}
                },
                { // Body Care
                        {"BOD00001", "Soap", "45.0", "BODY_CARE"},
                        {"BOD00002", "Body Wash", "120.0", "BODY_CARE"},
                        {"BOD00003", "Shower Gel", "110.0", "BODY_CARE"}
                }
        };

        int groupIndex = 0;
        for (Display display : displays.values()) {
            if (display instanceof Shelf && display.getAddress().startsWith("2F")) {
                if (groupIndex < shelf2ProductGroups.length) {
                    String[][] productGroup = shelf2ProductGroups[groupIndex];
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
                } else {
                    // Use last group if we run out
                    String[][] productGroup = shelf2ProductGroups[shelf2ProductGroups.length - 1];
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
                }
            }
        }
    }

    private void stock2FTableProducts() {
        String[][][] table2ProductGroups = {
                { // Vegetables
                        {"VEG00001", "Cabbage", "35.0", "VEGETABLE"},
                        {"VEG00002", "Lettuce", "30.0", "VEGETABLE"},
                        {"VEG00003", "Broccoli", "45.0", "VEGETABLE"}
                },
                { // Bread
                        {"BRD00001", "Baguette", "55.0", "BREAD"},
                        {"BRD00002", "Croissant", "35.0", "BREAD"},
                        {"BRD00003", "Bagel", "40.0", "BREAD"}
                },
                { // Eggs
                        {"EGG00001", "Brown Eggs", "80.0", "EGGS"},
                        {"EGG00002", "Quail Eggs", "65.0", "EGGS"},
                        {"EGG00003", "Free-Range Eggs", "120.0", "EGGS"}
                }
        };

        int groupIndex = 0;
        for (Display display : displays.values()) {
            if (display instanceof Table && display.getAddress().startsWith("2F")) {
                if (groupIndex < table2ProductGroups.length) {
                    String[][] productGroup = table2ProductGroups[groupIndex];
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
                } else {
                    // Use last group if we run out
                    String[][] productGroup = table2ProductGroups[table2ProductGroups.length - 1];
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
                }
            }
        }
    }

    public void displaySimpleMap() {
        Shopper shopper = getShopper();
        char[][] currentMap = currentFloor.equals("GF") ? groundFloor : secondFloor;

        System.out.println("\n=== SUPERMARKET MAP ===");
        System.out.println("Floor: " + currentFloor);
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
                    System.out.print(currentMap[i][j] + " ");
                }
            }
            System.out.println();
        }

        System.out.println("\nLegend: ^v<> = You, . = Free, W = Wall");
        System.out.println("        T = Table, C = Chilled Counter, S = Shelf");
        System.out.println("        F = Refrigerator, E = Entrance, X = Exit");
        System.out.println("        B = Basket, R = Cart, K = Checkout");
        System.out.println("        ? = Search, U = Stairs");

        displayWhatIsFront();
    }

    private void displayWhatIsFront() {
        Shopper shopper = getShopper();
        char[][] currentMap = currentFloor.equals("GF") ? groundFloor : secondFloor;
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
            char frontTile = currentMap[frontY][frontX];
            switch (frontTile) {
                case 'W': System.out.println("Wall"); break;
                case 'T': System.out.println("Table"); break;
                case 'C': System.out.println("Chilled Counter (Meats/Seafood)"); break;
                case 'S': System.out.println("Shelf (Various Products)"); break;
                case 'F': System.out.println("Refrigerator (Milk/Frozen/Cheese)"); break;
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
        char[][] currentMap = currentFloor.equals("GF") ? groundFloor : secondFloor;
        char tile = currentMap[y][x];
        return tile == '.' || tile == 'E' || tile == 'X' || tile == 'B' || tile == 'R' || tile == 'K' || tile == '?' || tile == 'U';
    }

    public Display getDisplayAt(int x, int y) {
        char[][] currentMap = currentFloor.equals("GF") ? groundFloor : secondFloor;
        char tile = currentMap[y][x];

        for (Map.Entry<String, Display> entry : displays.entrySet()) {
            String address = entry.getKey();
            Display display = entry.getValue();

            // Check if display is on current floor
            if (!address.startsWith(currentFloor)) {
                continue;
            }

            // Match by tile type and position
            if (tile == 'C' && display instanceof ChilledCounter) {
                return display;
            } else if (tile == 'S' && display instanceof Shelf) {
                return display;
            } else if (tile == 'T' && display instanceof Table) {
                return display;
            } else if (tile == 'F' && display instanceof Refrigerator) {
                return display;
            }
        }
        return null;
    }

    public Service getServiceAt(int x, int y) {
        for (Service service : services) {
            if (service.getX() == x && service.getY() == y && service.getFloor().equals(currentFloor)) {
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
        this.currentFloor = "GF";
    }

    public void switchFloor() {
        currentFloor = currentFloor.equals("GF") ? "2F" : "GF";
    }

    public String getCurrentFloor() {
        return currentFloor;
    }

    public Shopper getShopper() { return shopper; }
}

class Exit extends Service {
    public Exit(int x, int y, String floor) {
        super("EXIT", x, y, floor);
    }

    @Override
    public String interact(Shopper shopper) {
        // Check if shopper has equipment
        if (shopper.getEquipment() != null) {
            return "You cannot leave with equipment! Please return your " +
                    (shopper.getEquipment() instanceof Basket ? "basket" : "cart") +
                    " first.";
        }

        // Check if shopper has products but hasn't checked out
        if (shopper.getTotalProductCount() > 0 && !shopper.hasCheckedOut()) {
            return "You need to checkout first before leaving! Please go to a checkout counter.";
        }

        // Allow shopper to leave
        shopper.setLeft(true);

        // Show exit confirmation and close program
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exit Supermarket");
            alert.setHeaderText("Thank you for shopping!");
            alert.setContentText("You have left the supermarket. The program will now close.");
            alert.showAndWait().ifPresent(response -> {
                System.exit(0);
            });
        });

        return "You have left the supermarket. Thank you for shopping!";
    }
}
class Stairs extends Service {
    public Stairs(int x, int y, String floor) {
        super("STAIRS", x, y, floor);
    }

    @Override
    public String interact(Shopper shopper) {
        return "SWITCH_FLOOR"; // Special return value to trigger floor switch
    }
}
