public abstract class Service {
    protected String type;
    protected int x, y;
    
    public Service(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
    
    public abstract String interact(Shopper shopper);
    
    public String getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
}
