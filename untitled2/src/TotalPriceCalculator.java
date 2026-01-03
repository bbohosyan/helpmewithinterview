public class TotalPriceCalculator {

    public double calculateTotalPriceOfItems(double total, Customer customer) {
        for (Item item : customer.getCart()) {
            CategoryStrategy categoryStrategy = CategoryStrategyFactory.getInstance().getStrategy(item.getCategory());
            total += categoryStrategy.calculateTotalPriceOfItems(item);
        }
        return total;
    }

    public double applyLoyaltyDiscount(double total, Customer customer) {
        if (customer.isLoyaltyMember()) {
            total = total * 0.95;
            customer.setLoyaltyPoints(customer.getLoyaltyPoints() + total * 0.1);
        }
        return total;
    }

    public double calculateShipping(double total, Destination destination, Customer customer) throws Exception {
        double totalWeight = 0;
        for (Item item : customer.getCart()) {
            totalWeight += item.getWeight() * item.getQuantity();
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


    public double applyOrderDiscount(double total) {
        if (total > 100) {
            total = total * 0.9;
        }
        return total;
    }

    public double calculateTotal(Destination destination, Customer customer) throws Exception {
        double total = 0;

        // Calculate total
        total = calculateTotalPriceOfItems(total, customer);

        // Apply loyalty discount
        total = applyLoyaltyDiscount(total, customer);

        // Apply order discount
        total = applyOrderDiscount(total);

        // Calculate shipping
        total += calculateShipping(total, destination, customer);

        return total;
    }
}
