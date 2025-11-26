/*
  Entrance service where shoppers spawn when starting the simulation.
  Provides welcome message but no interactive functionality.
 */
public class Entrance extends Service {
    public Entrance(int x, int y, String floor) {
        super("ENTRANCE", x, y, floor);
    }

    @Override
    public String interact(Shopper shopper) {
        return "You are at the entrance. Welcome to the supermarket!";
    }
}
