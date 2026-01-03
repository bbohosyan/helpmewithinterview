public class ItemService {

    public void addItem(String name, double price, Category category, int quantity, double weight, Customer customer) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setCategory(category);
        item.setQuantity(quantity);
        item.setWeight(weight);
        customer.getCart().add(item);
    }

}
