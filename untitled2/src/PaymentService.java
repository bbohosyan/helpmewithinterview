import java.util.Date;

public class PaymentService {

    private EmailService emailService;
    private InventoryService inventoryService;

    public boolean processPayment(Payment paymentType, String cardNumber, double total, Customer customer) {
        boolean paymentSuccess = false;
        if (paymentType.equals(Payment.CREDIT_CARD)) {
            if (cardNumber != null && cardNumber.length() == 16) {
                System.out.println("Processing credit card payment: " + total);
                return true;
            }
        } else if (paymentType.equals(Payment.PAYPAL)) {
            if (customer.getEmail() != null) {
                System.out.println("Processing PayPal payment: " + total);
                return true;
            }
        } else if (paymentType.equals(Payment.BITCOIN)) {
            System.out.println("Processing Bitcoin payment: " + total);
            return true;
        }
        return false;
    }

    void afterPaymentProcesses(double total, Customer customer) {
        // Send confirmation email
        emailService.sendConfirmationEmail(total, customer);

        // Update inventory
        inventoryService.updateInventory(customer);

        // Log order
        System.out.println("LOG: Order placed by " + customer.getCustomerName() + " at " + new Date());

        customer.getCart().clear();
    }

}
