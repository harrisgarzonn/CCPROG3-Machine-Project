/**
 * Abstract base class for all services in the supermarket.
 * Services include checkout counters, basket stations, product search, etc.
 * Each service has a type, location, and floor designation.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.1
 */
public abstract class Service {
    /** The type of service (e.g., "CHECKOUT", "PRODUCT_SEARCH") */
    protected String type;

    /** The x-coordinate of this service on the map */
    protected int x;

    /** The y-coordinate of this service on the map */
    protected int y;

    /** The floor where this service is located ("GF" or "2F") */
    protected String floor;

    /**
     * Constructs a Service with the specified type, location, and floor.
     *
     * @param type the type identifier for this service
     * @param x the x-coordinate on the supermarket map
     * @param y the y-coordinate on the supermarket map
     * @param floor the floor designation ("GF" or "2F")
     */
    public Service(String type, int x, int y, String floor) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.floor = floor;
    }

    /**
     * Defines the interaction behavior when a shopper uses this service.
     * Must be implemented by all concrete service classes.
     *
     * @param shopper the shopper interacting with this service
     * @return a string message describing the result of the interaction
     */
    public abstract String interact(Shopper shopper);

    /**
     * Gets the type of this service.
     *
     * @return the service type
     */
    public String getType() { return type; }

    /**
     * Gets the x-coordinate of this service.
     *
     * @return the x-coordinate
     */
    public int getX() { return x; }

    /**
     * Gets the y-coordinate of this service.
     *
     * @return the y-coordinate
     */
    public int getY() { return y; }

    /**
     * Gets the floor where this service is located.
     *
     * @return the floor designation ("GF" or "2F")
     */
    public String getFloor() { return floor; }
}
