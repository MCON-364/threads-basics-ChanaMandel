package edu.touro.las.mcon364.streams.homework;

import java.time.*;
import java.util.*;

/**
 * Homework: E-Commerce Order Analytics
 * 
 * Time: ~2 hours
 * 
 * Build an analytics module for an e-commerce platform using Java Streams.
 * Complete all methods marked with TODO.
 * 
 * See HOMEWORK_README.md for detailed instructions.
 */
public class StreamHomework {
    
    // =========================================================================
    // DATA MODEL
    // =========================================================================
    
    /**
     * Represents a product in the catalog.
     */
    public record Product(String id, String name, String category, double price) {}
    
    /**
     * Represents a line item in an order.
     */
    public record OrderItem(Product product, int quantity) {
        public double getLineTotal() {
            return product.price() * quantity;
        }
    }
    
    /**
     * Represents a customer order.
     */
    public record CustomerOrder(
            String id,
            String customerId,
            List<OrderItem> items,
            LocalDate orderDate,
            OrderStatus status
    ) {
        public double getTotal() {
            return items.stream()
                    .mapToDouble(OrderItem::getLineTotal)
                    .sum();
        }
    }
    
    /**
     * Order status enumeration.
     */
    public enum OrderStatus {
        PENDING, SHIPPED, DELIVERED, CANCELLED
    }
    
    /**
     * Summary statistics for a category.
     */
    public record CategorySummary(double totalRevenue, int totalQuantity) {}
    
    // =========================================================================
    // SAMPLE DATA
    // =========================================================================
    
    private final List<Product> products;
    private final List<CustomerOrder> customerOrders;
    
    /**
     * Constructor initializes sample data for testing.
     */
    public StreamHomework() {
        // Create products
        products = List.of(
            new Product("P001", "Laptop", "Electronics", 999.99),
            new Product("P002", "Smartphone", "Electronics", 699.99),
            new Product("P003", "Headphones", "Electronics", 149.99),
            new Product("P004", "T-Shirt", "Clothing", 29.99),
            new Product("P005", "Jeans", "Clothing", 59.99),
            new Product("P006", "Sneakers", "Footwear", 89.99),
            new Product("P007", "Backpack", "Accessories", 49.99),
            new Product("P008", "Watch", "Accessories", 199.99),
            new Product("P009", "Tablet", "Electronics", 449.99),
            new Product("P010", "Jacket", "Clothing", 119.99)
        );
        
        // Create orders
        customerOrders = new ArrayList<>();
        
        // Customer C001 orders
        customerOrders.add(new CustomerOrder("O001", "C001",
            List.of(
                new OrderItem(products.get(0), 1),  // Laptop
                new OrderItem(products.get(2), 2)   // 2x Headphones
            ),
            LocalDate.of(2024, 1, 15), OrderStatus.DELIVERED));
        
        customerOrders.add(new CustomerOrder("O002", "C001",
            List.of(
                new OrderItem(products.get(3), 3),  // 3x T-Shirt
                new OrderItem(products.get(4), 2)   // 2x Jeans
            ),
            LocalDate.of(2024, 2, 20), OrderStatus.DELIVERED));
        
        // Customer C002 orders
        customerOrders.add(new CustomerOrder("O003", "C002",
            List.of(
                new OrderItem(products.get(1), 1),  // Smartphone
                new OrderItem(products.get(7), 1)   // Watch
            ),
            LocalDate.of(2024, 1, 22), OrderStatus.DELIVERED));
        
        customerOrders.add(new CustomerOrder("O004", "C002",
            List.of(
                new OrderItem(products.get(5), 2)   // 2x Sneakers
            ),
            LocalDate.of(2024, 3, 10), OrderStatus.SHIPPED));
        
        // Customer C003 orders
        customerOrders.add(new CustomerOrder("O005", "C003",
            List.of(
                new OrderItem(products.get(8), 1),  // Tablet
                new OrderItem(products.get(6), 1)   // Backpack
            ),
            LocalDate.of(2024, 2, 5), OrderStatus.DELIVERED));
        
        customerOrders.add(new CustomerOrder("O006", "C003",
            List.of(
                new OrderItem(products.get(9), 1),  // Jacket
                new OrderItem(products.get(3), 2)   // 2x T-Shirt
            ),
            LocalDate.of(2024, 3, 15), OrderStatus.CANCELLED));
        
        // Customer C004 orders
        customerOrders.add(new CustomerOrder("O007", "C004",
            List.of(
                new OrderItem(products.get(0), 2),  // 2x Laptop
                new OrderItem(products.get(1), 1)   // Smartphone
            ),
            LocalDate.of(2024, 1, 30), OrderStatus.DELIVERED));
        
        // Customer C005 orders (single order)
        customerOrders.add(new CustomerOrder("O008", "C005",
            List.of(
                new OrderItem(products.get(2), 1),  // Headphones
                new OrderItem(products.get(3), 1),  // T-Shirt
                new OrderItem(products.get(5), 1)   // Sneakers
            ),
            LocalDate.of(2024, 2, 28), OrderStatus.PENDING));
        
        // Customer C006 orders
        customerOrders.add(new CustomerOrder("O009", "C006",
            List.of(
                new OrderItem(products.get(7), 2),  // 2x Watch
                new OrderItem(products.get(6), 3)   // 3x Backpack
            ),
            LocalDate.of(2024, 3, 1), OrderStatus.DELIVERED));
        
        customerOrders.add(new CustomerOrder("O010", "C006",
            List.of(
                new OrderItem(products.get(4), 1),  // Jeans
                new OrderItem(products.get(9), 1)   // Jacket
            ),
            LocalDate.of(2024, 3, 20), OrderStatus.SHIPPED));
    }
    
    // =========================================================================
    // PART 1: Basic Analytics
    // =========================================================================
    
    /**
     * Task 1.1: Calculate total revenue from all DELIVERED orders.
     * 
     * Revenue = sum of (quantity × price) for all items in delivered orders.
     * 
     * Expected: ~5765.87
     */
    public double getTotalRevenue() {
        // TODO: Implement using streams
        // Hint: Filter by DELIVERED status, then sum order totals
        return 0.0;
    }
    
    /**
     * Task 1.2: Count orders by status.
     * 
     * Example: getOrderCount(DELIVERED) -> 5
     */
    public long getOrderCount(OrderStatus status) {
        // TODO: Implement using streams
        return 0;
    }
    
    /**
     * Task 1.3: Get all unique products that have been ordered.
     * 
     * Returns a Set of all products that appear in any order.
     */
    public Set<Product> getUniqueProducts() {
        // TODO: Implement using streams
        // Hint: Use flatMap to get all OrderItems, then map to Product
        return null;
    }
    
    /**
     * Task 1.4: Calculate average value of DELIVERED orders.
     * 
     * Expected: ~1153.17
     */
    public double getAverageOrderValue() {
        // TODO: Implement using streams
        // Hint: Filter delivered orders, map to total, get average
        return 0.0;
    }
    
    // =========================================================================
    // PART 2: Customer Analytics
    // =========================================================================
    
    /**
     * Task 2.1: Get total revenue by customer (DELIVERED orders only).
     * 
     * Returns: Map of customerId → total amount spent
     * 
     * Expected includes: {C001=1509.93, C002=899.97, ...}
     */
    public Map<String, Double> getRevenueByCustomer() {
        // TODO: Implement using streams
        // Hint: Filter delivered, group by customerId, sum totals
        return null;
    }
    
    /**
     * Task 2.2: Get top N customers by total spending (DELIVERED orders).
     * 
     * Returns: List of customer IDs sorted by spending (highest first)
     * 
     * Example: getTopCustomers(3) -> [C004, C001, C006] (or similar based on data)
     */
    public List<String> getTopCustomers(int n) {
        // TODO: Implement using streams
        // Hint: Use getRevenueByCustomer(), sort by value descending, limit
        return null;
    }
    
    /**
     * Task 2.3: Get order count by customer (all orders, any status).
     * 
     * Returns: Map of customerId → number of orders placed
     */
    public Map<String, Long> getCustomerOrderCounts() {
        // TODO: Implement using streams
        // Hint: Group by customerId, count
        return null;
    }
    
    /**
     * Task 2.4: Get customers who have placed more than one order.
     * 
     * Expected: [C001, C002, C003, C006]
     */
    public List<String> getCustomersWithMultipleOrders() {
        // TODO: Implement using streams
        // Hint: Use getCustomerOrderCounts(), filter count > 1
        return null;
    }
    
    // =========================================================================
        // PART 3: Product Analytics
    // =========================================================================
    
    /**
     * Task 3.1: Get total revenue by category (DELIVERED orders only).
     * 
     * Returns: Map of category → total revenue
     * 
     * Expected includes: {Electronics=4599.91, Clothing=209.95, ...}
     */
    public Map<String, Double> getRevenueByCategory() {
        // TODO: Implement using streams
        // Hint: Filter delivered, flatMap to items, group by category
        return null;
    }
    
    /**
     * Task 3.2: Get top N selling products by quantity (all orders, any status).
     * 
     * Returns: List of Product objects sorted by total quantity sold
     */
    public List<Product> getTopSellingProducts(int n) {
        // TODO: Implement using streams
        // Hint: flatMap to items, group by product, sum quantities, sort
        return null;
    }
    
    /**
     * Task 3.3: Get total quantity sold for each product (all orders).
     * 
     * Returns: Map of productId → total quantity sold
     */
    public Map<String, Integer> getProductQuantitySold() {
        // TODO: Implement using streams
        // Hint: flatMap, group by product id, sum quantity
        return null;
    }
    
    /**
     * Task 3.4: Get category summary with revenue and quantity (DELIVERED only).
     * 
     * Returns: Map of category → CategorySummary(totalRevenue, totalQuantity)
     */
    public Map<String, CategorySummary> getCategorySummary() {
        // TODO: Implement using streams
        // Hint: This is more complex - consider using Collectors.teeing() 
        // or computing in multiple steps
        return null;
    }
    
    // =========================================================================
    // PART 4: Time-Based Analytics (30 minutes)
    // =========================================================================
    
    /**
     * Task 4.1: Group orders by month.
     * 
     * Returns: Map of YearMonth → List of Orders
     */
    public Map<YearMonth, List<CustomerOrder>> getOrdersByMonth() {
        // TODO: Implement using streams
        // Hint: Use YearMonth.from(order.orderDate()) as classifier
        return null;
    }
    
    /**
     * Task 4.2: Get monthly revenue (DELIVERED orders only).
     * 
     * Returns: Map of YearMonth → total revenue for that month
     */
    public Map<YearMonth, Double> getMonthlyRevenue() {
        // TODO: Implement using streams
        // Hint: Filter delivered, group by month, sum totals
        return null;
    }
    
    /**
     * Task 4.3: Get orders within a date range (inclusive).
     * 
     * Returns: List of orders where start <= orderDate <= end
     */
    public List<CustomerOrder> getOrdersInDateRange(LocalDate start, LocalDate end) {
        // TODO: Implement using streams
        // Hint: Filter using !isBefore(start) && !isAfter(end)
        return null;
    }
    
    /**
     * Task 4.4: Get order count by date.
     * 
     * Returns: Map of LocalDate → number of orders on that date
     */
    public Map<LocalDate, Long> getDailyOrderCounts() {
        // TODO: Implement using streams
        // Hint: Group by orderDate, count
        return null;
    }
    
    // =========================================================================
    // BONUS CHALLENGES
    // =========================================================================
    
    /**
     * Bonus 1: Find products that have never been ordered.
     */
    public List<Product> getNeverOrderedProducts() {
        // TODO: Implement if time permits
        return null;
    }
    
    /**
     * Bonus 2: Get the most popular product in each category (by quantity).
     */
    public Map<String, Product> getMostPopularByCategory() {
        // TODO: Implement if time permits
        return null;
    }
    
    /**
     * Bonus 3: Calculate month-over-month revenue growth rate.
     * 
     * Returns: Map of YearMonth → growth rate (as decimal, e.g., 0.15 for 15%)
     * First month should have growth rate of 0.0
     */
    public Map<YearMonth, Double> getMonthlyGrowthRate() {
        // TODO: Implement if time permits
        return null;
    }
    
    // =========================================================================
    // MAIN METHOD - Test your implementations
    // =========================================================================
    
    public static void main(String[] args) {
        StreamHomework hw = new StreamHomework();
        
        System.out.println("=".repeat(70));
        System.out.println("E-COMMERCE ORDER ANALYTICS - Testing Your Implementations");
        System.out.println("=".repeat(70));
        
        // Part 1: Basic Analytics
        System.out.println("\n--- PART 1: Basic Analytics ---");
        System.out.printf("1.1 Total Revenue (DELIVERED): $%.2f%n", hw.getTotalRevenue());
        // Expected: ~$5765.87
        
        System.out.println("1.2 Order counts by status:");
        for (OrderStatus status : OrderStatus.values()) {
            System.out.printf("     %s: %d%n", status, hw.getOrderCount(status));
        }
        // Expected: PENDING=1, SHIPPED=2, DELIVERED=5, CANCELLED=1
        
        System.out.println("1.3 Unique products ordered: " + 
            (hw.getUniqueProducts() != null ? hw.getUniqueProducts().size() : "null"));
        // Expected: 10 (all products have been ordered)
        
        System.out.printf("1.4 Average order value (DELIVERED): $%.2f%n", hw.getAverageOrderValue());
        // Expected: ~$1153.17
        
        // Part 2: Customer Analytics
        System.out.println("\n--- PART 2: Customer Analytics ---");
        System.out.println("2.1 Revenue by customer: " + hw.getRevenueByCustomer());
        
        System.out.println("2.2 Top 3 customers: " + hw.getTopCustomers(3));
        
        System.out.println("2.3 Customer order counts: " + hw.getCustomerOrderCounts());
        
        System.out.println("2.4 Customers with multiple orders: " + hw.getCustomersWithMultipleOrders());
        // Expected: [C001, C002, C003, C006]
        
        // Part 3: Product Analytics
        System.out.println("\n--- PART 3: Product Analytics ---");
        System.out.println("3.1 Revenue by category: " + hw.getRevenueByCategory());
        
        System.out.print("3.2 Top 5 selling products: ");
        List<Product> topProducts = hw.getTopSellingProducts(5);
        if (topProducts != null) {
            topProducts.forEach(p -> System.out.print(p.name() + " "));
        }
        System.out.println();
        
        System.out.println("3.3 Product quantity sold: " + hw.getProductQuantitySold());
        
        System.out.println("3.4 Category summary: " + hw.getCategorySummary());
        
        // Part 4: Time-Based Analytics
        System.out.println("\n--- PART 4: Time-Based Analytics ---");
        System.out.println("4.1 Orders by month:");
        Map<YearMonth, List<CustomerOrder>> byMonth = hw.getOrdersByMonth();
        if (byMonth != null) {
            byMonth.forEach((month, orderList) -> 
                System.out.printf("     %s: %d orders%n", month, orderList.size()));
        }
        
        System.out.println("4.2 Monthly revenue: " + hw.getMonthlyRevenue());
        
        LocalDate start = LocalDate.of(2024, 2, 1);
        LocalDate end = LocalDate.of(2024, 2, 28);
        List<CustomerOrder> febCustomerOrders = hw.getOrdersInDateRange(start, end);
        System.out.printf("4.3 Orders in Feb 2024: %d%n", 
            febCustomerOrders != null ? febCustomerOrders.size() : 0);
        // Expected: 3 orders
        
        System.out.println("4.4 Daily order counts: " + hw.getDailyOrderCounts());
        
        // Bonus
        System.out.println("\n--- BONUS CHALLENGES ---");
        System.out.println("Bonus 1 - Never ordered products: " + hw.getNeverOrderedProducts());
        System.out.println("Bonus 2 - Most popular by category: " + hw.getMostPopularByCategory());
        System.out.println("Bonus 3 - Monthly growth rate: " + hw.getMonthlyGrowthRate());
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Review your outputs against the expected values in HOMEWORK_README.md");
        System.out.println("=".repeat(70));
    }
}
