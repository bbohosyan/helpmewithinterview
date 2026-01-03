import java.util.*;

public class ShoppingSystem {

    public static ShoppingSystem instance;
    private Customer customer;

    public static ShoppingSystem getInstance() {
        if (instance == null) {
            instance = new ShoppingSystem();
        }
        return instance;
    }

    public double checkout(Payment paymentType, String cardNumber, Destination destination) throws Exception {

        double total = calculateTotal(destination);

        // Process payment
        boolean paymentSuccess = processPayment(paymentType, cardNumber, total);

        if (paymentSuccess) {
            afterPaymentProcesses(total);
            return total;
        } else {
            System.out.println("Payment failed!");
            return -1;
        }
    }

    private void afterPaymentProcesses(double total) {
        // Send confirmation email
        sendConfirmationEmail(total);

        // Update inventory
        updateInventory();

        // Log order
        System.out.println("LOG: Order placed by " + customer.getCustomerName() + " at " + new Date());

        customer.getCart().clear();
    }

    private double calculateTotal(Destination destination) throws Exception {
        double total = 0;

        // Calculate total
        total = calculateTotalPriceOfItems(total);

        // Apply loyalty discount
        total = applyLoyaltyDiscount(total);

        // Apply order discount
        total = applyOrderDiscount(total);

        // Calculate shipping
        total += calculateShipping(total, destination);

        return total;
    }

    private void updateInventory() {
        for (Item item : customer.getCart()) {
            // Reduce stock
            System.out.println("Reducing stock for: " + item.name);
        }
    }

    private void sendConfirmationEmail(double total) {
        System.out.println("Sending email to: " + customer.getEmail());
        System.out.println("Order total: $" + total);
        System.out.println("Items:");
        for (Item item : customer.getCart()) {
            System.out.println("- " + item.name + " x" + item.quantity);
        }
    }

    private boolean processPayment(Payment paymentType, String cardNumber, double total) {
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

    private double calculateShipping(double total, Destination destination) throws Exception {
        double totalWeight = 0;
        for (Item item : customer.getCart()) {
            totalWeight += item.weight * item.quantity;
        }

        double shipping = 0;
        if (destination.equals(Destination.USA)) {
            if (totalWeight < 5) {
                return 10;
            } else if (totalWeight < 20) {
                return 20;
            } else {
                return 50;
            }
        } else if (destination.equals(Destination.EUROPE)) {
            if (totalWeight < 5) {
                return 25;
            } else if (totalWeight < 20) {
                return 40;
            } else {
                return 80;
            }
        }
        throw new Exception();
    }

    private double applyOrderDiscount(double total) {
        if (total > 100) {
            total = total * 0.9;
        }
        return total;
    }

    private double applyLoyaltyDiscount(double total) {
        if (customer.isLoyaltyMember()) {
            total = total * 0.95;
            customer.setLoyaltyPoints(customer.getLoyaltyPoints() + total * 0.1);
        }
        return total;
    }

    private double calculateTotalPriceOfItems(double total) {
        for (Item item : customer.getCart()) {
            CategoryStrategy categoryStrategy = CategoryStrategyFactory.getInstance().getStrategy(item.category);
            total += categoryStrategy.calculateTotalPriceOfItems(item);
        }
        return total;
    }
}