public class ElectronicsStrategy implements CategoryStrategy {
    @Override
    public double calculateTotalPriceOfItems(Item item) {
        double total = 0;
        if (item.quantity >= 2) {
            total += item.price * (item.quantity - 1); // Buy 2 Get 1 Free
        } else {
            total += item.price * item.quantity;
        }
    }
}
