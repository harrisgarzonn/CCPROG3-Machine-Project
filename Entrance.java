public class Entrance extends Service {
    public Entrance(int x, int y) {
        super("ENTRANCE", x, y);
    }
    
    @Override
    public String interact(Shopper shopper) {
        return "You are at the entrance. Welcome to the supermarket!";
    }
}
