public class ItemService {

    public void addItem(String name, double price, Category category, int quantity, double weight, Customer customer) {
        Item item = new Item();
        item.name = name;
        item.price = price;
        item.category = category;
        item.quantity = quantity;
        item.weight = weight;
        customer.getCart().add(item);
    }

}
