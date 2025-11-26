/*
  Abstract base class for all service amenities in the supermarket.
  Services are interactive facilities that shoppers can use.
 */
public abstract class Service {
    protected String type; // Type of service (e.g., "ENTRANCE", "CHECKOUT")
    protected int x, y; // Position coordinates on the map
    protected String floor; // Which floor the service is on ("GF" or "2F")

    public Service(String type, int x, int y, String floor) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.floor = floor;
    }

    // Abstract method for service interaction
    public abstract String interact(Shopper shopper);

    // Getters
    public String getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getFloor() { return floor; }
}
