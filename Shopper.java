import java.util.ArrayList;
import java.util.List;

public class Shopper {
    private String name;
    private int age;
    private Equipment equipment;
    private List<Product> handCarried;
    private int x, y; 
    private String direction;
    private boolean hasCheckedOut;
    
    public Shopper(String name, int age) {
        this.name = name;
        this.age = age;
        this.handCarried = new ArrayList<>();
        this.direction = "SOUTH";
        this.hasCheckedOut = false;
    }
    
    public boolean pickProduct(Product product) {
        if (hasCheckedOut) return false;
        
        if (equipment != null) {
            return equipment.addProduct(product);
        } else if (handCarried.size() < 2) {
            handCarried.add(product);
            return true;
        }
        return false;
    }
    
    public boolean returnProduct(Product product) {
        if (hasCheckedOut) return false;
        
        if (equipment != null) {
            for (int i = 0; i < equipment.getProductCount(); i++) {
                if (equipment.getProducts()[i] == product) {
                    equipment.removeProduct(i);
                    return true;
                }
            }
        } else {
            return handCarried.remove(product);
        }
        return false;
    }
    
    public boolean setEquipment(Equipment newEquipment) {
        if (hasCheckedOut || !handCarried.isEmpty() || equipment != null) {
            return false;
        }
        this.equipment = newEquipment;
        return true;
    }
    
    public Equipment removeEquipment() {
        if (!equipment.isEmpty()) {
            return null;
        }
        Equipment oldEquipment = equipment;
        equipment = null;
        return oldEquipment;
    }
    
    public String getName() { return name; }
    public int getAge() { return age; }
    public Equipment getEquipment() { return equipment; }
    public List<Product> getHandCarried() { return handCarried; }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getDirection() { return direction; }
    public boolean hasCheckedOut() { return hasCheckedOut; }
    
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
    public void setDirection(String direction) { this.direction = direction; }
    public void setCheckedOut(boolean checkedOut) { 
        this.hasCheckedOut = checkedOut; 
        if (checkedOut) {
            this.equipment = null;
            this.handCarried.clear();
        }
    }
    
    public int getTotalProductCount() {
        int count = handCarried.size();
        if (equipment != null) {
            count += equipment.getProductCount();
        }
        return count;
    }
    
    public boolean canGetAlcohol() {
        return age >= 18;
    }
    
    public boolean canGetCleaningAgents() {
        return age >= 18;
    }
    
    public double getSeniorDiscount(Product product) {
        if (age >= 60 && product.isConsumable() && !product.getType().equals("ALCOHOL")) {
            if (product.isFood()) return 0.20; 
            if (product.isBeverage()) return 0.10;
        }
        return 0.0;
    }
}
