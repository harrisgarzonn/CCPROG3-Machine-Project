import java.util.*;

public class SupermarketSimulator {
    private Supermarket supermarket;
    private Scanner scanner;

    public SupermarketSimulator() {
        this.scanner = new Scanner(System.in);
        this.supermarket = new Supermarket();
    }

    public void start() {
        boolean restart = true;

        while (restart) {
            System.out.println("=== SUPERMARKET SIMULATOR ===");

            System.out.print("Enter shopper name: ");
            String name = scanner.nextLine();

            System.out.print("Enter shopper age: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            Shopper shopper = new Shopper(name, age);
            supermarket.setShopper(shopper);

            System.out.println("\nWelcome to the supermarket, " + name + "!");

            supermarket.displaySimpleMap();

            boolean running = true;
            while (running) {
                displayControls();
                System.out.print("\nEnter command: ");

                String input = scanner.nextLine().toUpperCase();

                switch (input) {
                    case "W":
                        moveShopper("NORTH");
                        break;
                    case "A":
                        moveShopper("WEST");
                        break;
                    case "S":
                        moveShopper("SOUTH");
                        break;
                    case "D":
                        moveShopper("EAST");
                        break;
                    case "I":
                        changeDirection("NORTH");
                        break;
                    case "J":
                        changeDirection("WEST");
                        break;
                    case "K":
                        changeDirection("SOUTH");
                        break;
                    case "L":
                        changeDirection("EAST");
                        break;
                    case " ":
                        interact();
                        break;
                    case "V":
                        viewProducts();
                        break;
                    case "Q":
                        running = false;
                        System.out.println("Exiting simulation...");
                        break;
                    default:
                        System.out.println("Invalid command. Please try again.");
                        supermarket.displaySimpleMap();
                }

                // Check if shopper has left the supermarket
                if (shopper.hasLeft()) {
                    running = false;
                }
            }

            // Ask for restart
            System.out.print("\nDo you want to restart the simulation? (Y/N): ");
            String restartChoice = scanner.nextLine().toUpperCase();

            if (!restartChoice.equals("Y")) {
                restart = false;
                System.out.println("Thank you for using the Supermarket Simulator!");
            } else {
                // Reset the supermarket for a new simulation
                supermarket = new Supermarket();
            }
        }

        scanner.close();
    }

    private void displayControls() {
        System.out.println("\n=============================");
        System.out.println("Movement: W (North), A (West), S (South), D (East)");
        System.out.println("Vision: I (North), J (West), K (South), L (East)");
        System.out.println("Interact: SPACE");
        System.out.println("View Products: V");
        System.out.println("Quit: Q");
        System.out.println("=============================");
    }

    private void moveShopper(String direction) {
        Shopper shopper = supermarket.getShopper();
        int x = shopper.getX();
        int y = shopper.getY();
        int newX = x, newY = y;

        switch (direction) {
            case "NORTH": newY = y - 1; break;
            case "SOUTH": newY = y + 1; break;
            case "WEST": newX = x - 1; break;
            case "EAST": newX = x + 1; break;
        }

        if (supermarket.canMoveTo(newX, newY)) {
            shopper.setPosition(newX, newY);
            System.out.println("Moved " + direction);
            supermarket.displaySimpleMap();
        } else {
            System.out.println("Cannot move " + direction + " - blocked!");
            supermarket.displaySimpleMap();
        }
    }

    private void changeDirection(String direction) {
        supermarket.getShopper().setDirection(direction);
        System.out.println("Now facing " + direction);
        supermarket.displaySimpleMap();
    }

    private void interact() {
        Shopper shopper = supermarket.getShopper();
        int x = shopper.getX();
        int y = shopper.getY();
        int frontX = x, frontY = y;

        switch (shopper.getDirection()) {
            case "NORTH": frontY--; break;
            case "SOUTH": frontY++; break;
            case "WEST": frontX--; break;
            case "EAST": frontX++; break;
        }

        Service service = supermarket.getServiceAt(frontX, frontY);
        if (service != null) {
            // Special handling for ProductSearch
            if (service instanceof ProductSearch) {
                System.out.print("Enter product name to search: ");
                String productName = scanner.nextLine();
                String result = ((ProductSearch) service).searchProduct(productName);
                System.out.println(result);
            } else if (service instanceof Stairs) {
                // Handle stairs - switch floors
                supermarket.switchFloor();
                String newFloor = supermarket.getCurrentFloor();
                System.out.println("You took the stairs to the " +
                        (newFloor.equals("GF") ? "Ground Floor" : "Second Floor") + "!");
            } else {
                String result = service.interact(shopper);
                System.out.println(result);
            }

            // Special handling for exit - don't show map if shopper has left
            if (service instanceof Exit && shopper.hasLeft()) {
                return;
            }

            supermarket.displaySimpleMap();
            return;
        }

        Display display = supermarket.getDisplayAt(frontX, frontY);
        if (display != null) {
            interactWithDisplay(display);
            return;
        }

        System.out.println("Nothing to interact with.");
        supermarket.displaySimpleMap();
    }

    private void interactWithDisplay(Display display) {
        Shopper shopper = supermarket.getShopper();

        System.out.println("\n=== DISPLAY INTERACTION ===");
        System.out.println("Display: " + display.getAddress());
        System.out.println("1. Pick up a product");
        System.out.println("2. Return a product");
        System.out.println("3. Cancel");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                pickProductFromDisplay(display);
                break;
            case "2":
                returnProductToDisplay(display);
                break;
            case "3":
                System.out.println("Cancelled interaction.");
                break;
            default:
                System.out.println("Invalid choice.");
        }

        supermarket.displaySimpleMap();
    }

    private void pickProductFromDisplay(Display display) {
        Shopper shopper = supermarket.getShopper();

        // Show available products
        System.out.println("\nAvailable products:");
        List<Product> availableProducts = new ArrayList<>();
        List<Integer> productSlots = new ArrayList<>();

        for (int i = 0; i < display.getCapacity(); i++) {
            Product product = display.getProduct(i);
            if (product != null) {
                availableProducts.add(product);
                productSlots.add(i);
                System.out.println((availableProducts.size()) + ". " + product.getName() +
                        " - PHP " + product.getPrice() + " (Type: " + product.getType() + ")");
            }
        }

        if (availableProducts.isEmpty()) {
            System.out.println("Display is empty.");
            return;
        }

        System.out.print("Select product number (0 to cancel): ");
        int productChoice = scanner.nextInt();
        scanner.nextLine();

        if (productChoice <= 0 || productChoice > availableProducts.size()) {
            System.out.println("Cancelled.");
            return;
        }

        Product selectedProduct = availableProducts.get(productChoice - 1);
        int slotToRemove = productSlots.get(productChoice - 1);

        // Check age restrictions BEFORE picking up
        if (selectedProduct.getType().equals("ALCOHOL") && !shopper.canGetAlcohol()) {
            System.out.println("You must be 18 or older to purchase alcohol!");
            System.out.println("You are not allowed to take this product.");
            return;
        }

        if (selectedProduct.getType().equals("CLEANING_AGENTS") && !shopper.canGetCleaningAgents()) {
            System.out.println("You must be 18 or older to purchase cleaning agents!");
            System.out.println("You are not allowed to take this product.");
            return;
        }

        if (shopper.pickProduct(selectedProduct)) {
            display.removeProduct(slotToRemove);
            System.out.println("Picked up: " + selectedProduct.getName() + " - PHP " + selectedProduct.getPrice());
        } else {
            System.out.println("No space to carry items! Consider getting a basket or cart.");
        }
    }

    private void returnProductToDisplay(Display display) {
        Shopper shopper = supermarket.getShopper();

        if (shopper.getTotalProductCount() == 0) {
            System.out.println("You have no products to return.");
            return;
        }

        // Show shopper's products
        System.out.println("\nYour products:");
        List<Product> shopperProducts = new ArrayList<>();
        shopperProducts.addAll(shopper.getHandCarried());

        if (shopper.getEquipment() != null) {
            for (Product p : shopper.getEquipment().getProducts()) {
                shopperProducts.add(p);
            }
        }

        for (int i = 0; i < shopperProducts.size(); i++) {
            Product p = shopperProducts.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " - PHP " + p.getPrice() +
                    " (Type: " + p.getType() + ")");
        }

        System.out.print("Select product number to return (0 to cancel): ");
        int productChoice = scanner.nextInt();
        scanner.nextLine();

        if (productChoice <= 0 || productChoice > shopperProducts.size()) {
            System.out.println("Cancelled.");
            return;
        }

        Product selectedProduct = shopperProducts.get(productChoice - 1);

        // Check if display can hold this product type
        if (!display.canHoldProduct(selectedProduct)) {
            System.out.println("This product cannot be placed in this display!");
            System.out.println(selectedProduct.getName() + " (Type: " + selectedProduct.getType() +
                    ") can only be placed in a " + selectedProduct.getDisplayType() + ".");
            return;
        }

        if (display.isFull()) {
            System.out.println("Display is full! Cannot return product here.");
            return;
        }

        // Find an empty slot
        int emptySlot = -1;
        for (int i = 0; i < display.getCapacity(); i++) {
            if (display.getProduct(i) == null) {
                emptySlot = i;
                break;
            }
        }

        if (emptySlot != -1) {
            if (shopper.returnProduct(selectedProduct)) {
                display.addProduct(selectedProduct, emptySlot);
                System.out.println("Returned: " + selectedProduct.getName() + " to " + display.getAddress());
            } else {
                System.out.println("Failed to return product.");
            }
        } else {
            System.out.println("No empty slot found in display.");
        }
    }

    private void viewProducts() {
        Shopper shopper = supermarket.getShopper();
        if (shopper.getTotalProductCount() == 0) {
            System.out.println("You have no products.");
            return;
        }

        System.out.println("\n=== YOUR PRODUCTS ===");

        if (shopper.getEquipment() != null) {
            if (shopper.getEquipment() instanceof Basket) {
                System.out.println("Equipment: Basket");
            } else if (shopper.getEquipment() instanceof Cart) {
                System.out.println("Equipment: Cart");
            }
        } else {
            System.out.println("Equipment: Hand-carried");
        }

        System.out.println("====================");

        Map<String, Integer> productCounts = new HashMap<>();
        Map<String, Double> productPrices = new HashMap<>();

        for (Product product : shopper.getHandCarried()) {
            productCounts.put(product.getName(), productCounts.getOrDefault(product.getName(), 0) + 1);
            productPrices.put(product.getName(), product.getPrice());
        }

        if (shopper.getEquipment() != null) {
            for (Product product : shopper.getEquipment().getProducts()) {
                productCounts.put(product.getName(), productCounts.getOrDefault(product.getName(), 0) + 1);
                productPrices.put(product.getName(), product.getPrice());
            }
        }

        double total = 0;
        for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            double price = productPrices.get(productName);
            double totalPrice = price * quantity;

            System.out.println(productName + " x" + quantity + " - PHP " +
                    String.format("%.2f", totalPrice));
            total += totalPrice;
        }

        System.out.println("====================");
        System.out.println("Total: PHP " + String.format("%.2f", total));
    }

    public static void main(String[] args) {
        SupermarketSimulator simulator = new SupermarketSimulator();
        simulator.start();
    }
}