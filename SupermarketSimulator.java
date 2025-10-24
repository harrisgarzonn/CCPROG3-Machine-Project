import java.util.*;

public class SupermarketSimulator {
    private Supermarket supermarket;
    private Scanner scanner;
    
    public SupermarketSimulator() {
        this.scanner = new Scanner(System.in);
        this.supermarket = new Supermarket();
    }
    
    public void start() {
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
                case "N": 
                moveShopper("NORTH");
                changeDirection("NORTH"); 
                break;
            case "W": 
                moveShopper("WEST");
                changeDirection("WEST"); 
                break;
            case "S": 
                moveShopper("SOUTH"); 
                changeDirection("SOUTH"); 
                break;
            case "E": 
                moveShopper("EAST");
                changeDirection("EAST"); 
                break;
            case " ": interact(); break;
            case "V": viewProducts(); break;
            case "Q": 
                running = false;
                System.out.println("Thank you for shopping!");
                break;
            default:
                System.out.println("Invalid command. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private void displayControls() {
    System.out.println("\n=============================");
    System.out.println("Movement: N (North), W (West), S (South), E (East)");
    System.out.println("Vision: N (North), W (West), S (South), E (East)");
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
            case "NORTH": newY = y-1; break;
            case "SOUTH": newY = y+1; break;
            case "WEST": newX = x-1; break;
            case "EAST": newX = x+1; break;
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
            System.out.println(service.interact(shopper));
            return;
        }
        
        Display display = supermarket.getDisplayAt(frontX, frontY);
        if (display != null) {
            System.out.println("Interacting with display...");
            for (int i = 0; i < display.getCapacity(); i++) {
                Product product = display.getProduct(i);
                if (product != null) {
                    if (shopper.pickProduct(product)) {
                        display.removeProduct(i);
                        System.out.println("Picked up: " + product.getName() + " - PHP " + product.getPrice());
                    } else {
                        System.out.println("No space to carry items!");
                    }
                    return;
                }
            }
            System.out.println("Display is empty.");
            return;
        }
        
        System.out.println("Nothing to interact with.");
    }
    
    private void viewProducts() {
        Shopper shopper = supermarket.getShopper();
        if (shopper.getTotalProductCount() == 0) {
            System.out.println("You have no products.");
            return;
        }
        
        System.out.println("\nYour Products:");
        System.out.println("==============");
        
        Map<String, Integer> productCounts = new HashMap<>();
        double total = 0;
        
        for (Product product : shopper.getHandCarried()) {
            productCounts.put(product.getName(), productCounts.getOrDefault(product.getName(), 0) + 1);
            total += product.getPrice();
        }
        
        if (shopper.getEquipment() != null) {
            for (Product product : shopper.getEquipment().getProducts()) {
                productCounts.put(product.getName(), productCounts.getOrDefault(product.getName(), 0) + 1);
                total += product.getPrice();
            }
        }
        
        for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
            System.out.println(entry.getKey() + " x" + entry.getValue());
        }
        System.out.println("Total: PHP " + total);
    }
    
    public static void main(String[] args) {
        SupermarketSimulator simulator = new SupermarketSimulator();
        simulator.start();
    }
}
