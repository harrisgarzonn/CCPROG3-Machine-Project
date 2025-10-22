// Chilled counter class makes that it could contain meats and seafood, with a capacity of 3.
public class ChilledCounter extends Display {
  // Constructor
  public ChilledCounter(String address) {
    super(address, 3, new String[]{"CHICKEN", "BEEF", "SEAFOOD"});
    // I used super so that whatever code in your Display this will get it (ykwim)
    // Just use this: (address, numberOfHowManyCanItHandle, ProductType)
  }
}
