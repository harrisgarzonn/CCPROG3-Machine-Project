// Creates a table that will contain 4 products
public class Table extends Display {

    // Creates a table adding to display
    public Table(String floor, String grouping, int number) {
        super(floor, grouping, number, "Table", 4);
        this.allowedProductTypes = new String[]{"Fruit"};
    }
}