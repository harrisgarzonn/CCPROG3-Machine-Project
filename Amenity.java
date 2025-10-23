public abstract class Amenity {
    protected int xCoord;
    protected int yCoord;
    protected String type;
    
    public Amenity(int xCoord, int yCoord, String type) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.type = type;
    }
    
    // Getters
    public int getX() { return xCoord; }
    public int getY() { return yCoord; }
    public String getType() { return type; }
    
    public abstract void interact(Shopper shopper);
    
    @Override
    public String toString() {
        return String.format("%s at (%d, %d)", type, xCoord, yCoord);
    }
}
