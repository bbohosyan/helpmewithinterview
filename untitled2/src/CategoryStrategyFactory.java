import java.util.HashMap;
import java.util.Map;

public class CategoryStrategyFactory {
    private static CategoryStrategyFactory instance;
    private final Map<Category, CategoryStrategy> strategyMap;

    // Private constructor
    private CategoryStrategyFactory() {
        strategyMap = new HashMap<>();
        strategyMap.put(Category.CLOTHING, new ClothingStrategy());
        strategyMap.put(Category.ELECTRONICS, new ElectronicsStrategy());
    }

    // Thread-safe singleton (Bill Pugh implementation)
    private static class Holder {
        private static final CategoryStrategyFactory INSTANCE = new CategoryStrategyFactory();
    }

    public static CategoryStrategyFactory getInstance() {
        return Holder.INSTANCE;
    }

    public CategoryStrategy getStrategy(Category category) {
        CategoryStrategy strategy = strategyMap.get(category);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy for category: " + category);
        }
        return strategy;
    }
}
