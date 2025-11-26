/**
 * Represents the entrance service of the supermarket.
 * This is where shoppers first arrive and are greeted.
 *
 * @author Bien Gabriel L. Manoos, Harris Martin Garzon
 * @version 2.0
 */
public class Entrance extends Service {
    /**
     * Constructs a new Entrance at the specified location.
     *
     * @param x the x-coordinate of the entrance
     * @param y the y-coordinate of the entrance
     * @param floor the floor level where the entrance is located
     */
    public Entrance(int x, int y, String floor) {
        super("ENTRANCE", x, y, floor);
    }

    /**
     * Handles the interaction when a shopper arrives at the entrance.
     * Provides a welcome message to the shopper.
     *
     * @param shopper the shopper entering the supermarket
     * @return a welcome message
     */
    @Override
    public String interact(Shopper shopper) {
        return "You are at the entrance. Welcome to the supermarket!";
    }
}
