public class EmailService {
    public void sendConfirmationEmail(double total, Customer customer) {
        System.out.println("Sending email to: " + customer.getEmail());
        System.out.println("Order total: $" + total);
        System.out.println("Items:");
        for (Item item : customer.getCart()) {
            System.out.println("- " + item.getName() + " x" + item.getQuantity());
        }
    }

}
