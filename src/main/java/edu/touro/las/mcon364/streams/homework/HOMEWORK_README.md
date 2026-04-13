# Homework: E-Commerce Order Analytics

**File:** `StreamHomework.java`

## Overview

You are building an analytics module for an e-commerce platform. The system processes customer orders and generates various reports using Java Streams.

You'll work with three record types:
- **Product** - id, name, category, price
- **OrderItem** - product, quantity
- **Order** - id, customerId, items, orderDate, status

## Learning Objectives

- Apply complex stream pipelines to real-world data
- Use `flatMap()` for nested data structures
- Master `Collectors.groupingBy()` with downstream collectors
- Combine multiple stream operations efficiently
- Handle Optional values properly

## Data Model

```java
record Product(String id, String name, String category, double price) {}
record OrderItem(Product product, int quantity) {}
record Order(String id, String customerId, List<OrderItem> items, 
             LocalDate orderDate, OrderStatus status) {}

enum OrderStatus { PENDING, SHIPPED, DELIVERED, CANCELLED }
```

## Tasks

### Part 1: Basic Analytics (30 minutes)

| Task | Method | Points |
|------|--------|--------|
| 1.1 | `getTotalRevenue()` | Calculate total revenue from all DELIVERED orders |
| 1.2 | `getOrderCount(status)` | Count orders by status |
| 1.3 | `getUniqueProducts()` | Get all unique products that have been ordered |
| 1.4 | `getAverageOrderValue()` | Calculate average value of delivered orders |

### Part 2: Customer Analytics (30 minutes)

| Task | Method | Points |
|------|--------|--------|
| 2.1 | `getRevenueByCustomer()` | Map of customerId → total spent (delivered orders) |
| 2.2 | `getTopCustomers(n)` | Top n customers by total spending |
| 2.3 | `getCustomerOrderCounts()` | Map of customerId → number of orders |
| 2.4 | `getCustomersWithMultipleOrders()` | List customers with more than one order |

### Part 3: Product Analytics (30 minutes)

| Task | Method | Points |
|------|--------|--------|
| 3.1 | `getRevenueByCategory()` | Map of category → total revenue |
| 3.2 | `getTopSellingProducts(n)` | Top n products by quantity sold |
| 3.3 | `getProductQuantitySold()` | Map of productId → total quantity sold |
| 3.4 | `getCategorySummary()` | Map of category → {totalRevenue, totalQuantity} |

### Part 4: Time-Based Analytics (30 minutes)

| Task | Method | Points |
|------|--------|--------|
| 4.1 | `getOrdersByMonth()` | Map of YearMonth → list of orders |
| 4.2 | `getMonthlyRevenue()` | Map of YearMonth → total revenue |
| 4.3 | `getOrdersInDateRange(start, end)` | Orders between two dates (inclusive) |
| 4.4 | `getDailyOrderCounts()` | Map of LocalDate → order count |

## Detailed Specifications

### 1.1 getTotalRevenue()
- Only include DELIVERED orders
- Revenue = Σ (item.quantity × item.product.price) for all items in all orders
- Return 0.0 if no delivered orders

### 2.2 getTopCustomers(n)
- Rank by total amount spent on DELIVERED orders
- Return list of customer IDs
- If fewer than n customers exist, return all of them

### 3.2 getTopSellingProducts(n)
- Rank by total quantity sold across all orders (any status)
- Return list of Product objects
- Include products from cancelled orders

### 3.4 getCategorySummary()
- Return a map where each category maps to a summary record
- Summary includes: total revenue and total quantity sold
- Only include delivered orders for revenue calculation

## Implementation Tips

### Calculating Order Total
```java
private double calculateOrderTotal(Order order) {
    return order.items().stream()
        .mapToDouble(item -> item.quantity() * item.product().price())
        .sum();
}
```

### Working with Nested Collections
```java
// Get all items from all orders
orders.stream()
    .flatMap(order -> order.items().stream())
    ...
```

### Grouping with Custom Downstream Collectors
```java
orders.stream()
    .collect(Collectors.groupingBy(
        Order::customerId,
        Collectors.summingDouble(this::calculateOrderTotal)
    ));
```

### Working with Dates
```java
// Group by YearMonth
orders.stream()
    .collect(Collectors.groupingBy(
        order -> YearMonth.from(order.orderDate())
    ));

// Filter by date range
orders.stream()
    .filter(o -> !o.orderDate().isBefore(start) && !o.orderDate().isAfter(end))
    ...
```

## Testing

Run the main method to test your implementations:
```bash
mvn exec:java -Dexec.mainClass="edu.touro.las.mcon364.streams.homework.StreamHomework"
```

Or run unit tests:
```bash
mvn test -Dtest=StreamHomeworkTest
```

## Grading Rubric

| Criteria | Points |
|----------|--------|
| Part 1 compiles and passes tests | 25 |
| Part 2 compiles and passes tests | 25 |
| Part 3 compiles and passes tests | 25 |
| Part 4 compiles and passes tests | 25 |
| **Total** | **100** |

### Bonus Points (up to 10 extra)
- Implement additional analytics methods
- Add comprehensive error handling
- Write additional unit tests

## Submission Requirements

1. Complete all TODO methods in `StreamHomework.java`
2. Ensure all tests pass
3. Code must use stream operations (no explicit loops in the methods)
4. Commit and push to your repository

## Common Mistakes to Avoid

1. **Not filtering by order status** - Many calculations should only include DELIVERED orders
2. **Using wrong data type for money** - Use double, not int
3. **Forgetting to handle empty streams** - Use orElse() for Optional results
4. **Modifying records** - Records are immutable; create new ones if needed
5. **Not using flatMap for nested collections** - Remember items are nested inside orders

## Resources

- [Java Stream API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html)
- [Collectors Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Collectors.html)
- [LocalDate API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/LocalDate.html)

---
*Due: Check Canvas for deadline*
