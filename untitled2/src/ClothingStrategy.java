public class ClothingStrategy implements CategoryStrategy {
    @Override
    public double calculateTotalPriceOfItems(Item item) {
        return item.price * item.quantity * 0.9;
    }
}
