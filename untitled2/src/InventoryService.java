public class InventoryService {

    public void updateInventory(Customer customer) {
        for (Item item : customer.getCart()) {
            // Reduce stock
            System.out.println("Reducing stock for: " + item.getName());
        }
    }

}
