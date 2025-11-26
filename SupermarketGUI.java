import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

/*
  JavaFX GUI application for the Supermarket Simulator.
  Provides a graphical interface for navigating the supermarket,
  interacting with displays and services, and managing shopping activities.
 */
public class SupermarketGUI extends Application {
    private Supermarket supermarket; // Main supermarket model
    private Shopper shopper; // Current shopper
    private Rectangle[][] mapTiles; // 2D array to store tile references
    private GridPane mapGrid; // Grid layout for map display
    private TextArea infoArea; // Text area for information display
    private VBox controlPanel; // Panel for control buttons
    private Map<Character, String> tileDescriptions; // Map tile descriptions
    private Label statusLabel; // Status display label
    private Label floorLabel; // Current floor display
    private Label positionLabel;  // Shopper position display
    private Label equipmentLabel; // Equipment status display


    /*
      Main entry point for JavaFX application
      @param primaryStage The primary stage for this application
     */
        
    @Override
    public void start(Stage primaryStage) {
        initializeSupermarket();
        setupTileDescriptions();

        primaryStage.setTitle("Supermarket Simulator");

        // Main layout using BorderPane for organized sections
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #f0f0f0;");

        // Create UI components
        createMapDisplay();
        createInfoPanel();
        createControlPanel();
        createStatusPanel();

        // Arrange components
        mainLayout.setCenter(createMapContainer());
        mainLayout.setRight(createRightPanel());
        mainLayout.setBottom(createStatusPanel());

        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        updateDisplay();
    }

    private void initializeSupermarket() {
        supermarket = new Supermarket();

        // Create shopper through dialog
        TextInputDialog nameDialog = new TextInputDialog("John");
        nameDialog.setTitle("Shopper Information");
        nameDialog.setHeaderText("Enter Shopper Details");
        nameDialog.setContentText("Name:");

        TextInputDialog ageDialog = new TextInputDialog("25");
        ageDialog.setTitle("Shopper Information");
        ageDialog.setHeaderText("Enter Shopper Details");
        ageDialog.setContentText("Age:");

        String name = nameDialog.showAndWait().orElse("John");
        int age = Integer.parseInt(ageDialog.showAndWait().orElse("25"));

        shopper = new Shopper(name, age);
        supermarket.setShopper(shopper);
    }

     /*
      Sets up descriptions for each map tile character
      Used for tooltips and status display
     */
    private void setupTileDescriptions() {
        tileDescriptions = new HashMap<>();
        tileDescriptions.put('W', "Wall");
        tileDescriptions.put('T', "Table (Fruits/Vegetables/Bread/Eggs)");
        tileDescriptions.put('C', "Chilled Counter (Meats/Seafood)");
        tileDescriptions.put('S', "Shelf (Various Products)");
        tileDescriptions.put('F', "Refrigerator (Milk/Frozen/Cheese)");
        tileDescriptions.put('E', "Entrance");
        tileDescriptions.put('X', "Exit");
        tileDescriptions.put('B', "Basket Station");
        tileDescriptions.put('R', "Cart Station");
        tileDescriptions.put('K', "Checkout Counter");
        tileDescriptions.put('?', "Product Search");
        tileDescriptions.put('U', "Stairs");
        tileDescriptions.put('.', "Empty Space");
    }

    /*
      Creates container for the map display with floor label
      @return VBox containing floor label and map grid
     */
    private VBox createMapContainer() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);

        floorLabel = new Label("Floor: " + supermarket.getCurrentFloor());
        floorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        container.getChildren().addAll(floorLabel, mapGrid);
        return container;
    }

    private void createMapDisplay() {
        mapGrid = new GridPane();
        mapGrid.setGridLinesVisible(true);
        mapGrid.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 2;");

        // Initialize the 2D array
        mapTiles = new Rectangle[22][22];

        // Create 22x22 grid
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                Rectangle tile = new Rectangle(25, 25);
                tile.setStroke(Color.LIGHTGRAY);
                tile.setFill(Color.WHITE);

                // Store reference in the array
                mapTiles[i][j] = tile;

                // Add to grid with explicit row/column indices
                mapGrid.add(tile, j, i);
            }
        }
    }

    private void createInfoPanel() {
        infoArea = new TextArea();
        infoArea.setEditable(false);
        infoArea.setWrapText(true);
        infoArea.setPrefSize(300, 400);
        infoArea.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 12;");
        infoArea.setText("Welcome to the Supermarket Simulator!\n\nUse the controls to move around and interact with amenities.");
    }

    // Creates the information text area for displaying messages and actions
    private void createControlPanel() {
        controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setStyle("-fx-background-color: #e8e8e8; -fx-border-color: #ccc; -fx-border-width: 1;");

        Label controlsLabel = new Label("CONTROLS");
        controlsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Movement buttons
        Label movementLabel = new Label("Movement:");
        Button btnNorth = new Button("↑ North");
        Button btnSouth = new Button("↓ South");
        Button btnWest = new Button("← West");
        Button btnEast = new Button("→ East");

        HBox movementRow1 = new HBox(5, btnNorth);
        movementRow1.setAlignment(Pos.CENTER);
        HBox movementRow2 = new HBox(5, btnWest, btnEast);
        movementRow2.setAlignment(Pos.CENTER);
        HBox movementRow3 = new HBox(5, btnSouth);
        movementRow3.setAlignment(Pos.CENTER);

        // Vision buttons
        Label visionLabel = new Label("Change Vision:");
        Button btnVisionN = new Button("Look North");
        Button btnVisionS = new Button("Look South");
        Button btnVisionW = new Button("Look West");
        Button btnVisionE = new Button("Look East");

        HBox visionBox = new HBox(5, btnVisionN, btnVisionS, btnVisionW, btnVisionE);
        visionBox.setAlignment(Pos.CENTER);

        // Action buttons
        Label actionLabel = new Label("Actions:");
        Button btnInteract = new Button("Interact (Space)");
        Button btnViewProducts = new Button("View Products (V)");
        Button btnSearch = new Button("Product Search");

        btnInteract.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnViewProducts.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnSearch.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");

        // Set button actions
        btnNorth.setOnAction(e -> moveShopper("NORTH"));
        btnSouth.setOnAction(e -> moveShopper("SOUTH"));
        btnWest.setOnAction(e -> moveShopper("WEST"));
        btnEast.setOnAction(e -> moveShopper("EAST"));

        btnVisionN.setOnAction(e -> changeDirection("NORTH"));
        btnVisionS.setOnAction(e -> changeDirection("SOUTH"));
        btnVisionW.setOnAction(e -> changeDirection("WEST"));
        btnVisionE.setOnAction(e -> changeDirection("EAST"));

        btnInteract.setOnAction(e -> interact());
        btnViewProducts.setOnAction(e -> viewProducts());
        btnSearch.setOnAction(e -> openSearchDialog());

        controlPanel.getChildren().addAll(
                controlsLabel, movementLabel, movementRow1, movementRow2, movementRow3,
                new Separator(), visionLabel, visionBox,
                new Separator(), actionLabel, btnInteract, btnViewProducts, btnSearch
        );
    }

    // Creates the status panel at the bottom of the screen
    private VBox createStatusPanel() {
        VBox statusPanel = new VBox(5);
        statusPanel.setPadding(new Insets(10));
        statusPanel.setStyle("-fx-background-color: #d4edda; -fx-border-color: #c3e6cb; -fx-border-width: 1;");

        statusLabel = new Label("Ready to explore!");
        positionLabel = new Label("Position: (0, 0)");
        equipmentLabel = new Label("Equipment: None");

        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        statusPanel.getChildren().addAll(statusLabel, positionLabel, equipmentLabel);
        return statusPanel;
    }

    // Creates the right-side panel containing shopper info, messages, and controls
    private VBox createRightPanel() {
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setPrefWidth(350);

        // Shopper info
        VBox shopperInfo = new VBox(5);
        shopperInfo.setStyle("-fx-background-color: #e3f2fd; -fx-border-color: #bbdefb; -fx-padding: 10;");

        Label shopperLabel = new Label("SHOPPER INFO");
        shopperLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label nameLabel = new Label("Name: " + shopper.getName());
        Label ageLabel = new Label("Age: " + shopper.getAge());
        Label directionLabel = new Label("Facing: " + shopper.getDirection());

        shopperInfo.getChildren().addAll(shopperLabel, nameLabel, ageLabel, directionLabel);

        rightPanel.getChildren().addAll(shopperInfo, new Separator(), infoArea, new Separator(), controlPanel);
        return rightPanel;
    }

    // Updates the entire display including map, shopper position, and status
    private void updateDisplay() {
        // Clear map
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                Rectangle tile = mapTiles[i][j];
                if (tile != null) {
                    tile.setFill(Color.WHITE);
                    tile.setStroke(Color.LIGHTGRAY);
                    tile.setStrokeWidth(1);
                }
            }
        }

        // Get current floor map
        char[][] currentMap = supermarket.getCurrentFloor().equals("GF") ?
                supermarket.getGroundFloor() : supermarket.getSecondFloor();

        // Draw amenities and tiles
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                Rectangle tile = mapTiles[i][j];
                if (tile != null) {
                    char tileChar = currentMap[i][j];
                    tile.setFill(getTileColor(tileChar));

                    // Add tooltip
                    Tooltip tooltip = new Tooltip(tileDescriptions.getOrDefault(tileChar, "Unknown"));
                    Tooltip.install(tile, tooltip);

                    // Make tiles clickable for interaction
                    final int x = j, y = i;
                    tile.setOnMouseClicked(e -> handleTileClick(x, y));
                }
            }
        }

        // Draw shopper
        int shopperX = shopper.getX();
        int shopperY = shopper.getY();
        Rectangle shopperTile = mapTiles[shopperY][shopperX];
        if (shopperTile != null) {
            shopperTile.setFill(getShopperColor(shopper.getDirection()));

            // Highlight front tile
            int frontX = shopperX, frontY = shopperY;
            switch (shopper.getDirection()) {
                case "NORTH": frontY--; break;
                case "SOUTH": frontY++; break;
                case "WEST": frontX--; break;
                case "EAST": frontX++; break;
            }

            if (frontX >= 0 && frontX < 22 && frontY >= 0 && frontY < 22) {
                Rectangle frontTile = mapTiles[frontY][frontX];
                if (frontTile != null) {
                    frontTile.setStroke(Color.RED);
                    frontTile.setStrokeWidth(2);
                }
            }
        }

        // Update status
        updateStatus();
    }

    // Gets the color for a specific map tile character
    private Color getTileColor(char tileChar) {
        switch (tileChar) {
            case 'W': return Color.DARKGRAY;
            case 'T': return Color.LIGHTGREEN;
            case 'C': return Color.LIGHTBLUE;
            case 'S': return Color.LIGHTYELLOW;
            case 'F': return Color.CYAN;
            case 'E': return Color.GREEN;
            case 'X': return Color.RED;
            case 'B': return Color.ORANGE;
            case 'R': return Color.ORANGERED;
            case 'K': return Color.PURPLE;
            case '?': return Color.GOLD;
            case 'U': return Color.BROWN;
            case '.': return Color.WHITE;
            default: return Color.WHITE;
        }
    }

    private Color getShopperColor(String direction) {
        switch (direction) {
            case "NORTH": return Color.RED;
            case "SOUTH": return Color.DARKRED;
            case "WEST": return Color.CRIMSON;
            case "EAST": return Color.FIREBRICK;
            default: return Color.RED;
        }
    }

    private void handleTileClick(int x, int y) {
        // Only allow interaction with front tile
        int shopperX = shopper.getX();
        int shopperY = shopper.getY();
        int frontX = shopperX, frontY = shopperY;

        switch (shopper.getDirection()) {
            case "NORTH": frontY--; break;
            case "SOUTH": frontY++; break;
            case "WEST": frontX--; break;
            case "EAST": frontX++; break;
        }

        if (x == frontX && y == frontY) {
            interact();
        } else {
            infoArea.appendText("\nYou can only interact with what's directly in front of you!");
        }
    }

    // Moves the shopper in the specified direction if possible
    private void moveShopper(String direction) {
        int x = shopper.getX();
        int y = shopper.getY();
        int newX = x, newY = y;

        // Calculate new position based on direction
        switch (direction) {
            case "NORTH": newY = y - 1; break;
            case "SOUTH": newY = y + 1; break;
            case "WEST": newX = x - 1; break;
            case "EAST": newX = x + 1; break;
        }

        // Check if movement is possible and update position
        if (supermarket.canMoveTo(newX, newY)) {
            shopper.setPosition(newX, newY);
            infoArea.appendText("\nMoved " + direction);
            updateDisplay();
        } else {
            infoArea.appendText("\nCannot move " + direction + " - blocked!");
        }
    }

    // Changes the shopper's facing direction
    private void changeDirection(String direction) {
        shopper.setDirection(direction);
        infoArea.appendText("\nNow facing " + direction);
        updateDisplay();
    }

    // Handles interaction with whatever is in front of the shopper
    public void interact() {
    int x = shopper.getX();
    int y = shopper.getY();
    int frontX = x, frontY = y;

    switch (shopper.getDirection()) {
        case "NORTH": frontY--; break;
        case "SOUTH": frontY++; break;
        case "WEST": frontX--; break;
        case "EAST": frontX++; break;
    }

        // // Check for service first
    Service service = supermarket.getServiceAt(frontX, frontY);
    if (service != null) {
        if (service instanceof ProductSearch) {
            openSearchDialog();
        } else if (service instanceof Stairs) {
            supermarket.switchFloor();
            String newFloor = supermarket.getCurrentFloor();
            infoArea.appendText("\nYou took the stairs to the " + 
                (newFloor.equals("GF") ? "Ground Floor" : "Second Floor") + "!");
            floorLabel.setText("Floor: " + newFloor);
            updateDisplay();
        } else if (service instanceof Exit) {
            String result = service.interact(shopper);
            infoArea.appendText("\n" + result);
            
            // Show restart confirmation dialog
            showRestartDialog();
        } else {
            String result = service.interact(shopper);
            infoArea.appendText("\n" + result);
            updateDisplay();
        }
        return;
    }

        // Check for display if no service found
    Display display = supermarket.getDisplayAt(frontX, frontY);
    if (display != null) {
        openDisplayInteraction(display);
        return;
    }

    infoArea.appendText("\nNothing to interact with.");
}

    //Opens a search dialog for finding products by name
    private void openSearchDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Product Search");
        dialog.setHeaderText("Search for Products");
        dialog.setContentText("Enter product name:");

        // // Process search when dialog is completed
        dialog.showAndWait().ifPresent(productName -> {
            String result = supermarket.findProductLocations(productName);
            infoArea.appendText("\n" + result);
        });
    }

    //  Opens interaction dialog for a display (pick up or return products)
    private void openDisplayInteraction(Display display) {
        Stage interactionStage = new Stage();
        interactionStage.setTitle("Display Interaction - " + display.getAddress());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Interacting with: " + display.getAddress());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Action buttons for display interaction
        Button pickButton = new Button("Pick Up Product");
        Button returnButton = new Button("Return Product");
        Button cancelButton = new Button("Cancel");

        // Style buttons
        pickButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        returnButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        // Set button actions
        pickButton.setOnAction(e -> {
            interactionStage.close();
            openPickProductDialog(display);
        });

        returnButton.setOnAction(e -> {
            interactionStage.close();
            openReturnProductDialog(display);
        });

        cancelButton.setOnAction(e -> interactionStage.close());

        layout.getChildren().addAll(titleLabel, pickButton, returnButton, cancelButton);

        Scene scene = new Scene(layout, 300, 200);
        interactionStage.setScene(scene);
        interactionStage.show();
    }

    // Opens dialog for picking up products from a display
    private void openPickProductDialog(Display display) {
        Stage pickStage = new Stage();
        pickStage.setTitle("Pick Up Product");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label titleLabel = new Label("Available products in " + display.getAddress());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        ListView<String> productList = new ListView<>();

        // Populate list with available products
        for (int i = 0; i < display.getCapacity(); i++) {
            Product product = display.getProduct(i);
            if (product != null) {
                productList.getItems().add((i + 1) + ". " + product.getName() +
                        " - PHP " + product.getPrice() + " (Type: " + product.getType() + ")");
            }
        }

        // Handle empty display
        if (productList.getItems().isEmpty()) {
            productList.getItems().add("Display is empty");
        }

        HBox buttonBox = new HBox(10);
        Button pickButton = new Button("Pick Selected");
        Button cancelButton = new Button("Cancel");

        pickButton.setOnAction(e -> {
            int selectedIndex = productList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0 && !productList.getItems().get(selectedIndex).equals("Display is empty")) {
                // Find the actual product slot
                int productCount = 0;
                int slotToRemove = -1;
                Product selectedProduct = null;

                // Locate the selected product in the display
                for (int i = 0; i < display.getCapacity(); i++) {
                    Product product = display.getProduct(i);
                    if (product != null) {
                        if (productCount == selectedIndex) {
                            selectedProduct = product;
                            slotToRemove = i;
                            break;
                        }
                        productCount++;
                    }
                }

                if (selectedProduct != null) {
                    // Check age restrictions
                    if (selectedProduct.getType().equals("ALCOHOL") && !shopper.canGetAlcohol()) {
                        infoArea.appendText("\nYou must be 18 or older to purchase alcohol!");
                    } else if (selectedProduct.getType().equals("CLEANING_AGENTS") && !shopper.canGetCleaningAgents()) {
                        infoArea.appendText("\nYou must be 18 or older to purchase cleaning agents!");
                    } else if (shopper.pickProduct(selectedProduct)) {
                        display.removeProduct(slotToRemove);
                        infoArea.appendText("\nPicked up: " + selectedProduct.getName());
                        updateDisplay();
                    } else {
                        infoArea.appendText("\nNo space to carry items! Consider getting equipment.");
                    }
                }
                pickStage.close();
            }
        });

        cancelButton.setOnAction(e -> pickStage.close());

        buttonBox.getChildren().addAll(pickButton, cancelButton);
        layout.getChildren().addAll(titleLabel, productList, buttonBox);

        Scene scene = new Scene(layout, 400, 300);
        pickStage.setScene(scene);
        pickStage.show();
    }

    // Opens dialog for returning products to a display
    private void openReturnProductDialog(Display display) {
        Stage returnStage = new Stage();
        returnStage.setTitle("Return Product");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label titleLabel = new Label("Your products to return to " + display.getAddress());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        ListView<String> productList = new ListView<>();

        // Populate with shopper's products
        int productIndex = 0;
        for (Product product : shopper.getHandCarried()) {
            productList.getItems().add((productIndex + 1) + ". " + product.getName() +
                    " - PHP " + product.getPrice() + " (Hand)");
            productIndex++;
        }

        if (shopper.getEquipment() != null) {
            for (Product product : shopper.getEquipment().getProducts()) {
                productList.getItems().add((productIndex + 1) + ". " + product.getName() +
                        " - PHP " + product.getPrice() + " (Equipment)");
                productIndex++;
            }
        }

        if (productList.getItems().isEmpty()) {
            productList.getItems().add("You have no products to return");
        }

        HBox buttonBox = new HBox(10);
        Button returnButton = new Button("Return Selected");
        Button cancelButton = new Button("Cancel");

        returnButton.setOnAction(e -> {
            int selectedIndex = productList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0 && !productList.getItems().get(selectedIndex).equals("You have no products to return")) {
                // Find the actual product
                int currentIndex = 0;
                Product selectedProduct = null;

                for (Product product : shopper.getHandCarried()) {
                    if (currentIndex == selectedIndex) {
                        selectedProduct = product;
                        break;
                    }
                    currentIndex++;
                }

                // Search in equipment if not found in hand-carried
                if (selectedProduct == null && shopper.getEquipment() != null) {
                    for (Product product : shopper.getEquipment().getProducts()) {
                        if (currentIndex == selectedIndex) {
                            selectedProduct = product;
                            break;
                        }
                        currentIndex++;
                    }
                }

                if (selectedProduct != null) {
                    // Validate return conditions
                    if (!display.canHoldProduct(selectedProduct)) {
                        infoArea.appendText("\nThis product cannot be placed in this display!");
                    } else if (display.isFull()) {
                        infoArea.appendText("\nDisplay is full! Cannot return product.");
                    } else {
                        // Find empty slot and return product
                        int emptySlot = -1;
                        for (int i = 0; i < display.getCapacity(); i++) {
                            if (display.getProduct(i) == null) {
                                emptySlot = i;
                                break;
                            }
                        }

                        if (emptySlot != -1 && shopper.returnProduct(selectedProduct)) {
                            display.addProduct(selectedProduct, emptySlot);
                            infoArea.appendText("\nReturned: " + selectedProduct.getName());
                            updateDisplay();
                        }
                    }
                }
                returnStage.close();
            }
        });

        cancelButton.setOnAction(e -> returnStage.close());

        buttonBox.getChildren().addAll(returnButton, cancelButton);
        layout.getChildren().addAll(titleLabel, productList, buttonBox);

        Scene scene = new Scene(layout, 400, 300);
        returnStage.setScene(scene);
        returnStage.show();
    }

    private void viewProducts() {
        Stage productsStage = new Stage();
        productsStage.setTitle("Your Products");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label titleLabel = new Label("Products in Your Possession");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextArea productsArea = new TextArea();
        productsArea.setEditable(false);
        productsArea.setPrefSize(400, 300);

        // Generate products summary
        StringBuilder summary = new StringBuilder();

         // Show equipment status
        if (shopper.getEquipment() != null) {
            if (shopper.getEquipment() instanceof Basket) {
                summary.append("Equipment: Basket\n");
            } else if (shopper.getEquipment() instanceof Cart) {
                summary.append("Equipment: Cart\n");
            }
        } else {
            summary.append("Equipment: Hand-carried\n");
        }

        summary.append("====================\n\n");

        // Count products and calculate totals
        Map<String, Integer> productCounts = new HashMap<>();
        Map<String, Double> productPrices = new HashMap<>();

        // Count hand-carried products
        for (Product product : shopper.getHandCarried()) {
            productCounts.put(product.getName(), productCounts.getOrDefault(product.getName(), 0) + 1);
            productPrices.put(product.getName(), product.getPrice());
        }

        // Count equipment products
        if (shopper.getEquipment() != null) {
            for (Product product : shopper.getEquipment().getProducts()) {
                productCounts.put(product.getName(), productCounts.getOrDefault(product.getName(), 0) + 1);
                productPrices.put(product.getName(), product.getPrice());
            }
        }

        // Build product list with quantities and prices
        double total = 0;
        for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            double price = productPrices.get(productName);
            double totalPrice = price * quantity;

            summary.append(productName).append(" x").append(quantity)
                    .append(" - PHP ").append(String.format("%.2f", totalPrice)).append("\n");
            total += totalPrice;
        }

        summary.append("\n====================\n");
        summary.append("Total: PHP ").append(String.format("%.2f", total));

        productsArea.setText(summary.toString());

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> productsStage.close());

        layout.getChildren().addAll(titleLabel, productsArea, closeButton);

        Scene scene = new Scene(layout, 450, 400);
        productsStage.setScene(scene);
        productsStage.show();
    }

    // Shows restart dialog when exiting the supermarket
    private void showRestartDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Supermarket");
        alert.setHeaderText("You have left the supermarket!");
        alert.setContentText("Do you want to restart the simulation or exit the program?");

        // Custom button options
        ButtonType restartButton = new ButtonType("Restart");
        ButtonType exitButton = new ButtonType("Exit");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(restartButton, exitButton, cancelButton);

        // Handle user choice
        alert.showAndWait().ifPresent(response -> {
            if (response == restartButton) {
                // Restart the simulation
                restartSimulation();
            } else if (response == exitButton) {
                // Exit the program
                System.exit(0);
            }
            // If cancel, do nothing and stay in the simulation
        });
    }

    private void restartSimulation() {
        // Reset the supermarket
        supermarket = new Supermarket();

        // Get new shopper information
        TextInputDialog nameDialog = new TextInputDialog("John");
        nameDialog.setTitle("Shopper Information");
        nameDialog.setHeaderText("Enter Shopper Details");
        nameDialog.setContentText("Name:");

        TextInputDialog ageDialog = new TextInputDialog("25");
        ageDialog.setTitle("Shopper Information");
        ageDialog.setHeaderText("Enter Shopper Details");
        ageDialog.setContentText("Age:");

        String name = nameDialog.showAndWait().orElse("John");
        int age = Integer.parseInt(ageDialog.showAndWait().orElse("25"));

        shopper = new Shopper(name, age);
        supermarket.setShopper(shopper);

        // Clear the info area
        infoArea.setText("Welcome to the Supermarket Simulator!\n\nUse the controls to move around and interact with amenities.");

        // Update the display
        updateDisplay();

        infoArea.appendText("\n\n=== NEW SIMULATION STARTED ===");
        infoArea.appendText("\nWelcome, " + name + "! Age: " + age);
    }

    private void updateStatus() {
        positionLabel.setText("Position: (" + shopper.getX() + ", " + shopper.getY() + ")");

        String equipmentText;
        if (shopper.getEquipment() == null) {
            equipmentText = "Equipment: None (Hand-carried: " + shopper.getHandCarried().size() + "/2)";
        } else if (shopper.getEquipment() instanceof Basket) {
            equipmentText = "Equipment: Basket (" + shopper.getEquipment().getProductCount() + "/15)";
        } else {
            equipmentText = "Equipment: Cart (" + shopper.getEquipment().getProductCount() + "/30)";
        }
        equipmentLabel.setText(equipmentText);

        // Update front tile description
        int x = shopper.getX();
        int y = shopper.getY();
        int frontX = x, frontY = y;

        switch (shopper.getDirection()) {
            case "NORTH": frontY--; break;
            case "SOUTH": frontY++; break;
            case "WEST": frontX--; break;
            case "EAST": frontX++; break;
        }

        if (frontX >= 0 && frontX < 22 && frontY >= 0 && frontY < 22) {
            char[][] currentMap = supermarket.getCurrentFloor().equals("GF") ?
                    supermarket.getGroundFloor() : supermarket.getSecondFloor();
            char frontTile = currentMap[frontY][frontX];
            statusLabel.setText("In front: " + tileDescriptions.getOrDefault(frontTile, "Unknown"));
        } else {
            statusLabel.setText("In front: Out of bounds");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
