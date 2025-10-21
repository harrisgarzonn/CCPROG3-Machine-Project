// Creates a chilled counter that will contain 3 products
public class ChilledCounter extends Display {

    // Creates a chilled counter to the display
    public ChilledCounter(String floor, String grouping, int number) {
        super(floor, grouping, number, "Chilled Counter", 3);
        this.allowedProductTypes = new String[]{"Chicken", "Beef", "Seafood"};
    }
}