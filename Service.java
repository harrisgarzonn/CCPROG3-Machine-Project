public abstract class Service {
    protected String type;
    protected int x, y;
    protected String floor;

    public Service(String type, int x, int y, String floor) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.floor = floor;
    }

    public abstract String interact(Shopper shopper);

    public String getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getFloor() { return floor; }
}