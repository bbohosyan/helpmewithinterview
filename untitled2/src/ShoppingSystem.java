import java.util.*;

public class ShoppingSystem {

    public static ShoppingSystem instance;
    private TotalPriceCalculator totalPriceCalculator;
    private PaymentService paymentService;
    private EmailService emailService;
    private InventoryService inventoryService;

    private ShoppingSystem(){
        this.totalPriceCalculator = new TotalPriceCalculator();
        this.paymentService = new PaymentService();
        this.emailService = new EmailService();
    }

    public static ShoppingSystem getInstance() {
        if (instance == null) {
            instance = new ShoppingSystem();
        }
        return instance;
    }

    public double checkout(Payment paymentType, String cardNumber, Destination destination, Customer customer) throws Exception {

        double total = totalPriceCalculator.calculateTotal(destination, customer);

        // Process payment
        boolean paymentSuccess = paymentService.processPayment(paymentType, cardNumber, total, customer);

        if (paymentSuccess) {
            paymentService.afterPaymentProcesses(total, customer);
            return total;
        } else {
            System.out.println("Payment failed!");
            return -1;
        }
    }
}