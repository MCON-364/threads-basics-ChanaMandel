package edu.touro.las.mcon364.streams.demo;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

/**
 * Comprehensive demonstration of Java Streams.
 * 
 * This class contains instance methods demonstrating all major stream operations
 * covered in the course. Run the main method to see all demonstrations in action.
 * 
 * Topics covered:
 * 1. Stream Creation
 * 2. External vs Internal Iteration
 * 3. Intermediate Operations (stateless and stateful)
 * 4. Terminal Operations
 * 5. Lazy Evaluation
 * 6. Collecting Results
 * 7. Grouping and Partitioning
 * 8. Reductions
 * 9. Primitive Streams
 */
public class DemoStreams {
    
    // Sample data used throughout demonstrations
    private final List<String> names = List.of("Anna", "Michael", "Jonathan", "Eva", "Bob", "Alexandra");
    private final List<Integer> numbers = List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
    
    public static void main(String[] args) {
        DemoStreams demo = new DemoStreams();
        demo.runAllDemos();
    }
    
    /**
     * Runs all demonstration methods in sequence.
     */
    public void runAllDemos() {
        System.out.println("=".repeat(60));
        System.out.println("JAVA STREAMS DEMONSTRATION");
        System.out.println("=".repeat(60));
        
        demoStreamCreation();
        demoExternalVsInternalIteration();
        demoIntermediateOperations();
        demoStatefulOperations();
        demoTerminalOperations();
        demoLazyEvaluation();
        demoCollectingToList();
        demoCollectingToSet();
        demoCollectingToMap();
        demoGroupingBy();
        demoPartitioningBy();
        demoDownstreamCollectors();
        demoReduceBasics();
        demoReduceVariants();
        demoBuiltInReductions();
        demoPrimitiveStreams();
        demoSummaryStatistics();
        demoFlatMap();
        demoStreamReuse();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("END OF DEMONSTRATIONS");
        System.out.println("=".repeat(60));
    }
    
    // =========================================================================
    // 1. STREAM CREATION
    // =========================================================================
    
    /**
     * Demonstrates various ways to create streams.
     */
    public void demoStreamCreation() {
        printHeader("Stream Creation");
        
        // From a Collection
        Stream<String> fromCollection = names.stream();
        System.out.println("From Collection: " + fromCollection.toList());
        //print using forEach and method reference
        names.stream().forEach(System.out::println);
        
        // From an array
        String[] array = {"One", "Two", "Three"};
        Stream<String> fromArray = Arrays.stream(array);
        System.out.println("From Array: " + fromArray.toList());
        
        // Using Stream.of()
        Stream<String> fromOf = Stream.of("Alpha", "Beta", "Gamma");
        System.out.println("From Stream.of(): " + fromOf.toList());
        
        // Empty stream
        Stream<String> empty = Stream.empty();
        System.out.println("Empty stream count: " + empty.count());
        
        // Using Stream.generate() - infinite stream, must limit!
        Stream<Double> randoms = Stream.generate(Math::random).limit(5);
        System.out.println("Generated randoms: " + randoms.toList());
        
        // Using Stream.iterate()
        Stream<Integer> iterated = Stream.iterate(1, n -> n * 2).limit(8);
        System.out.println("Iterated (powers of 2): " + iterated.toList());
        
        // Using Stream.iterate() with predicate (Java 9+)
        Stream<Integer> bounded = Stream.iterate(1, n -> n < 100, n -> n * 2);
        System.out.println("Bounded iterate: " + bounded.toList());
        
        // IntStream range - can be used for primitive streams
        IntStream range = IntStream.range(1, 6);  // 1 to 5
        //Note the use of boxed() to convert IntStream to Stream<Integer> for toList()
        //Primitive streams produce int, long, double directly without boxing, but to collect to List<Integer> we need to box them
        System.out.println("IntStream.range(1,6): " + range.boxed().toList());
        
        // IntStream rangeClosed
        IntStream rangeClosed = IntStream.rangeClosed(1, 5);  // 1 to 5 inclusive
        System.out.println("IntStream.rangeClosed(1,5): " + rangeClosed.boxed().toList());
        //another way of printing using forEach and method reference
        IntStream.rangeClosed(1, 5).forEach(System.out::println);
    }
    
    // =========================================================================
    // 2. EXTERNAL VS INTERNAL ITERATION
    // =========================================================================
    
    /**
     * Contrasts traditional loops with stream pipelines.
     */
    public void demoExternalVsInternalIteration() {
        printHeader("External vs Internal Iteration");
        
        // EXTERNAL ITERATION (traditional loop)
        // You control the iteration mechanics
        List<String> longNamesExternal = new ArrayList<>();
        for (String name : names) {
            if (name.length() > 5) {
                longNamesExternal.add(name.toUpperCase());
            }
        }
        System.out.println("External iteration result: " + longNamesExternal);
        
        // INTERNAL ITERATION (stream)
        // Stream controls iteration; you describe transformations
        List<String> longNamesInternal = names.stream()
                .filter(n -> n.length() > 5)
                .map(String::toUpperCase)
                .toList();
        System.out.println("Internal iteration result: " + longNamesInternal);
        
        System.out.println("\nKey difference:");
        System.out.println("- External: HOW to iterate (imperative)");
        System.out.println("- Internal: WHAT transformation to apply (declarative)");
    }
    
    // =========================================================================
    // 3. INTERMEDIATE OPERATIONS (Stateless)
    // =========================================================================
    
    /**
     * Demonstrates stateless intermediate operations.
     */
    public void demoIntermediateOperations() {
        printHeader("Intermediate Operations (Stateless)");
        
        // filter() - keep elements matching predicate
        List<String> filtered = names.stream()
                .filter(n -> n.startsWith("A"))
                .toList();
        System.out.println("filter(starts with 'A'): " + filtered);
        
        // map() - transform each element
        // map() with method reference
        List<Integer> lengths = names.stream()
                .map(String::length)
                .toList();
        System.out.println("map(to length): " + lengths);

        //let's use peek to show what intermediate operations are doing -
        // peek() is a stateless operation that allows us to see the elements as they pass through the stream
        names.stream()
                .peek(n -> System.out.println("Original: " + n))
                .map(String::toLowerCase)
                .peek(s -> System.out.println("After toLower: " + s))
                .map(String::toUpperCase)
                .peek(s -> System.out.println("After map: " + s))
                .forEach(s -> System.out.println("Final element: " + s));


        // map() with lambda
        List<String> uppercased = names.stream()
                .map(n -> n.toUpperCase())
                .toList();
        System.out.println("map(toUpperCase): " + uppercased);
        
        // peek() - perform action without modifying (useful for debugging)
        //remember - that is what names collection looks like:
        //private final List<String> names = List.of("Anna", "Michael", "Jonathan", "Eva", "Bob", "Alexandra")
        System.out.print("peek() demonstration: ");
        long count = names.stream()
                .peek(n -> System.out.print(n + " "))
                .filter(n -> n.length() > 3)
                .count();
        System.out.println("\n  -> Count after filter: " + count);
        
        // limit() - truncate stream to n elements
        List<String> limited = names.stream()
                .limit(3)
                .toList();
        System.out.println("limit(3): " + limited);
        
        // skip() - skip first n elements
        List<String> skipped = names.stream()
                .skip(2)
                .toList();
        System.out.println("skip(2): " + skipped);
        
        // Combining limit and skip for pagination
        List<String> page2 = names.stream()
                .skip(2)
                .limit(2)
                .toList();
        System.out.println("skip(2).limit(2) [page 2]: " + page2);
        
        // takeWhile() - take elements while predicate is true (Java 9+)
        List<Integer> taken = List.of(1, 2, 3, 10, 4, 5).stream()
                .takeWhile(n -> n < 5)
                .toList();
        System.out.println("takeWhile(< 5) on [1,2,3,10,4,5]: " + taken);
        
        // dropWhile() - drop elements while predicate is true (Java 9+)
        List<Integer> dropped = List.of(1, 2, 3, 10, 4, 5).stream()
                .dropWhile(n -> n < 5)
                .toList();
        System.out.println("dropWhile(< 5) on [1,2,3,10,4,5]: " + dropped);
    }
    
    // =========================================================================
    // 4. STATEFUL INTERMEDIATE OPERATIONS
    // =========================================================================
    
    /**
     * Demonstrates stateful intermediate operations.
     */
    public void demoStatefulOperations() {
        printHeader("Stateful Intermediate Operations");
        
        System.out.println("Original numbers: " + numbers);
        
        // distinct() - removes duplicates (must remember seen elements)
        List<Integer> distinct = numbers.stream()
                .distinct()
                .toList();
        System.out.println("distinct(): " + distinct);
        
        // sorted() - natural order (must see all elements)
        List<Integer> sorted = numbers.stream()
                .sorted()
                .toList();
        System.out.println("sorted(): " + sorted);
        
        // sorted() with comparator - custom order
        List<String> sortedByLength = names.stream()
                .sorted(Comparator.comparingInt(String::length))
                .toList();
        System.out.println("sorted(by length): " + sortedByLength);
        
        // sorted() reversed
        List<String> sortedDescending = names.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
        System.out.println("sorted(reversed): " + sortedDescending);
        
        // Chaining: sorted then distinct
        List<Integer> sortedDistinct = numbers.stream()
                .sorted()
                .distinct()
                .toList();
        System.out.println("sorted().distinct(): " + sortedDistinct);
        
        System.out.println("\nNote: Stateful operations may buffer elements,");
        System.out.println("which can impact performance on large/infinite streams.");
    }
    
    // =========================================================================
    // 5. TERMINAL OPERATIONS
    // =========================================================================
    
    /**
     * Demonstrates various terminal operations.
     */
    public void demoTerminalOperations() {
        printHeader("Terminal Operations");
        
        // forEach() - perform action on each element
        System.out.print("forEach(): ");
        names.stream()
                .limit(3)
                .forEach(n -> System.out.print(n + " "));
        
        // count() - count elements
        long count = names.stream().count();
        System.out.println("count(): " + count);

        // Note these two methods - toList and toArray - are also terminal operations that collect results into a collection or array
        // toList() - collect to unmodifiable list (Java 16+)
        List<String> list = names.stream()
                .filter(n -> n.length() > 3)
                .toList();
        System.out.println("toList(): " + list);
        
        // toArray() - collect to array (need to provide generator for type)
        String[] array = names.stream()
                .filter(n -> n.length() > 4)
                .toArray(String[]::new);  //<-- method reference to create array of correct type
        System.out.println("toArray(): " + Arrays.toString(array));
        
        // findFirst() - get first element (respects order)
        // Note: findFirst() is short-circuiting - it will stop processing once it finds a match
        // It returns an Optional because there may not be a matching element, so you need to handle the case where it's empty
        Optional<String> first = names.stream()
                .filter(n -> n.length() > 5)
                .findFirst();
        System.out.println("findFirst(length > 5): " + first.orElse("none"));
        
        // findAny() - get any element (better for parallel)
        // Note  -it also returns an Optional for the same reason as findFirst() - there may not be a matching element, so you need to handle the empty case
        Optional<String> any = names.stream()
                .filter(n -> n.startsWith("A"))
                .findAny();
        System.out.println("findAny(starts with 'A'): " + any.orElse("none"));
        
        // anyMatch() - true if any element matches
        // Note: anyMatch() is short-circuiting - it will stop processing once it finds a match, which can improve performance on large streams
        // It returns a boolean because it's just checking for the presence of a match, so it doesn't need to return the element itself
        boolean hasLongName = names.stream()
                .anyMatch(n -> n.length() > 7);
        System.out.println("anyMatch(length > 7): " + hasLongName);
        
        // allMatch() - true if all elements match
        // as before it returns a boolean because it's just checking if all elements satisfy the condition,
        // so it doesn't need to return the elements themselves
        boolean allNonEmpty = names.stream()
                .allMatch(n -> !n.isEmpty());
        System.out.println("allMatch(non-empty): " + allNonEmpty);
        
        // noneMatch() - true if no elements match
        boolean noneStartWithZ = names.stream()
                .noneMatch(n -> n.startsWith("Z"));
        System.out.println("noneMatch(starts with 'Z'): " + noneStartWithZ);
        
        // min() and max()
        // These return Optional because there may not be any elements in the stream, so you need to handle the case where it's empty
        Optional<String> shortest = names.stream()
                .min(Comparator.comparingInt(String::length));
        Optional<String> longest = names.stream()
                .max(Comparator.comparingInt(String::length));
        System.out.println("min(by length): " + shortest.orElse("none"));
        System.out.println("max(by length): " + longest.orElse("none"));
    }
    
    // =========================================================================
    // 6. LAZY EVALUATION
    // =========================================================================
    
    /**
     * Demonstrates that streams are lazy - nothing executes until terminal operation.
     */
    public void demoLazyEvaluation() {
        printHeader("Lazy Evaluation");
        
        System.out.println("Creating pipeline WITHOUT terminal operation:");
        Stream<String> pipeline = names.stream()
                .peek(n -> System.out.println("  Processing: " + n))
                .filter(n -> n.length() > 3)
                .map(String::toUpperCase);
        System.out.println("Pipeline created. Nothing printed above = lazy!");
        
        System.out.println("\nNow calling terminal operation (count()):");
        long result = pipeline.count();
        System.out.println("Result: " + result);
        
        System.out.println("\n--- Short-circuiting demonstration ---");
        System.out.println("Using findFirst() - stops after first match:");
        Optional<String> found = names.stream()
                .peek(n -> System.out.println("  Checking: " + n))
                .filter(n -> n.length() > 5)
                .findFirst();
        System.out.println("Found: " + found.orElse("none"));
        System.out.println("Notice: Not all elements were processed!");
    }
    
    // =========================================================================
    // 7. COLLECTING TO LIST AND SET
    // =========================================================================
    
    /**
     * Demonstrates collecting to List.
     */
    public void demoCollectingToList() {
        printHeader("Collecting to List");
        
        // toList() - unmodifiable (Java 16+)
        List<String> unmodifiable = names.stream()
                .filter(n -> n.length() > 3)
                .toList();
        System.out.println("toList() [unmodifiable]: " + unmodifiable);
        
        // Collectors.toList() - mutable ArrayList
        List<String> mutable = names.stream()
                .filter(n -> n.length() > 3)
                .collect(Collectors.toList());
        mutable.add("Added");
        System.out.println("Collectors.toList() [mutable, added element]: " + mutable);
        
        // Collectors.toCollection() - specific type
        LinkedList<String> linked = names.stream()
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println("toCollection(LinkedList): " + linked.getClass().getSimpleName() + " " + linked);
    }
    
    /**
     * Demonstrates collecting to Set.
     */
    public void demoCollectingToSet() {
        printHeader("Collecting to Set");
        
        System.out.println("Original numbers (with duplicates): " + numbers);
        
        // Collectors.toSet() - HashSet, removes duplicates
        Set<Integer> set = numbers.stream().filter(n -> n % 2 == 1)
                .collect(Collectors.toSet());
        System.out.println("toSet(): " + set);
        
        // TreeSet for sorted unique elements
        TreeSet<Integer> treeSet = numbers.stream().filter(n -> n % 2 == 1)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("toCollection(TreeSet): " + treeSet);
    }
    
    // =========================================================================
    // 8. COLLECTING TO MAP
    // =========================================================================
    
    /**
     * Demonstrates collecting to Map with various strategies.
     */
    public void demoCollectingToMap() {
        printHeader("Collecting to Map");
        
        // Basic toMap: name -> length
        Map<String, Integer> nameLengths = names.stream()
                .collect(Collectors.toMap(
                        name -> name,           // key mapper
                         String::length// value mapper
                ));
        System.out.println("toMap(name -> length): " + nameLengths);
        
        // Using Function.identity()
        // Function.identity() is a built-in function that returns its input argument, so it's a convenient way to specify that the key should be the element itself
        Map<String, Integer> withIdentity = names.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        String::length
                ));
        System.out.println("Using Function.identity(): " + withIdentity);
        
        // Handling duplicates with merge function
        List<String> words = List.of("apple", "apricot", "banana", "blueberry", "avocado");
        Map<Character, String> firstLetterMap = words.stream()
                .collect(Collectors.toMap(
                        word -> word.charAt(0),           // key: first letter
                        word -> word,                      // value: the word
                        (existing, replacement) -> existing // merge: keep first
                ));
        System.out.println("toMap with merge (keep first): " + firstLetterMap);
        
        // Merge function: concatenate values
        Map<Character, String> concatenated = words.stream()
                .collect(Collectors.toMap(
                        word -> word.charAt(0),
                        word -> word,
                        (existing, replacement) -> existing + ", " + replacement
                ));
        System.out.println("toMap with merge (concatenate): " + concatenated);
        
        // Specifying map type (TreeMap for sorted keys)
        TreeMap<Character, String> sortedMap = words.stream()
                .collect(Collectors.toMap(
                        word -> word.charAt(0),
                        word -> word,
                        (a, b) -> a,
                        TreeMap::new
                ));
        System.out.println("toMap into TreeMap: " + sortedMap);
    }
    
    // =========================================================================
    // 9. GROUPING BY
    // =========================================================================
    
    /**
     * Demonstrates groupingBy collector.
     */
    public void demoGroupingBy() {
        printHeader("Grouping By");
        
        // Group names by length
        Map<Integer, List<String>> byLength = names.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("groupingBy(length): " + byLength);
        
        // Group by first letter
        Map<Character, List<String>> byFirstLetter = names.stream()
                .collect(Collectors.groupingBy(n -> n.charAt(0)));
        System.out.println("groupingBy(first letter): " + byFirstLetter);
        
        // Group with custom classifier
        Map<String, List<String>> byLengthCategory = names.stream()
                .collect(Collectors.groupingBy(n -> {
                    if (n.length() <= 3) return "short";
                    if (n.length() <= 5) return "medium";
                    return "long";
                }));
        System.out.println("groupingBy(length category): " + byLengthCategory);
    }
    
    // =========================================================================
    // 10. PARTITIONING BY
    // =========================================================================
    
    /**
     * Demonstrates partitioningBy collector.
     */
    public void demoPartitioningBy() {
        printHeader("Partitioning By");
        
        // Partition into two groups based on predicate
        Map<Boolean, List<String>> partitioned = names.stream()
                .collect(Collectors.partitioningBy(n -> n.length() > 4));
        
        System.out.println("partitioningBy(length > 4):");
        System.out.println("  true (long names):  " + partitioned.get(true));
        System.out.println("  false (short names): " + partitioned.get(false));
        
        // Partition numbers into even/odd
        Map<Boolean, List<Integer>> evenOdd = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("partitioningBy(even): " + evenOdd);
    }
    
    // =========================================================================
    // 11. DOWNSTREAM COLLECTORS
    // =========================================================================
    
    /**
     * Demonstrates combining groupingBy with downstream collectors.
     */
    public void demoDownstreamCollectors() {
        printHeader("Downstream Collectors");
        
        // Count elements in each group
        Map<Integer, Long> countByLength = names.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.counting()
                ));
        System.out.println("groupingBy + counting(): " + countByLength);
        
        // Join elements in each group
        Map<Integer, String> joinedByLength = names.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.joining(", ")
                ));
        System.out.println("groupingBy + joining(): " + joinedByLength);
        
        // Collect to Set instead of List
        Map<Integer, Set<String>> setsByLength = names.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.toSet()
                ));
        System.out.println("groupingBy + toSet(): " + setsByLength);
        
        // Map values in groups
        Map<Integer, List<String>> upperByLength = names.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.mapping(String::toUpperCase, Collectors.toList())
                ));
        System.out.println("groupingBy + mapping(toUpperCase): " + upperByLength);
        
        // Summarizing
        Map<Boolean, IntSummaryStatistics> statsByPartition = names.stream()
                .collect(Collectors.partitioningBy(
                        n -> n.length() > 4,
                        Collectors.summarizingInt(String::length)
                ));
        System.out.println("partitioningBy + summarizingInt:");
        System.out.println("  Long names stats:  " + statsByPartition.get(true));
        System.out.println("  Short names stats: " + statsByPartition.get(false));
    }
    
    // =========================================================================
    // 12. REDUCE BASICS
    // =========================================================================
    
    /**
     * Demonstrates basic reduce() operations.
     */
    public void demoReduceBasics() {
        printHeader("Reduce Basics");
        
        System.out.println("Numbers: " + numbers);
        
        // Sum using reduce
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("reduce(0, (a,b) -> a+b): " + sum);
        
        // Sum using method reference
        int sumMethodRef = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("reduce(0, Integer::sum): " + sumMethodRef);
        
        // Product
        int product = numbers.stream()
                .reduce(1, (a, b) -> a * b);
        System.out.println("reduce(1, (a,b) -> a*b): " + product);
        
        // String concatenation
        String concatenated = names.stream()
                .reduce("", (a, b) -> a + b);
        System.out.println("reduce(\"\", concat): " + concatenated);
        
        // Better string joining (uses StringBuilder internally)
        String joined = names.stream()
                .collect(Collectors.joining(", "));
        System.out.println("Collectors.joining(\", \"): " + joined);
        
        // Find longest name
        Optional<String> longest = names.stream()
                .reduce((a, b) -> a.length() >= b.length() ? a : b);
        System.out.println("reduce(longest): " + longest.orElse("none"));
    }
    
    // =========================================================================
    // 13. REDUCE VARIANTS
    // =========================================================================
    
    /**
     * Demonstrates different reduce() overloads.
     */
    public void demoReduceVariants() {
        printHeader("Reduce Variants");
        
        // Two-argument reduce: always returns a value
        int sum = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("Two-arg reduce (identity + accumulator): " + sum);
        
        // One-argument reduce: returns Optional (handles empty stream)
        Optional<Integer> optSum = numbers.stream()
                .reduce(Integer::sum);
        System.out.println("One-arg reduce (Optional): " + optSum.orElse(0));
        
        // Empty stream with one-arg reduce
        Optional<Integer> emptyResult = Stream.<Integer>empty()
                .reduce(Integer::sum);
        System.out.println("One-arg reduce on empty stream: " + emptyResult);
        
        // Three-argument reduce (for parallel with type change)
        // identity + accumulator + combiner
        int totalLength = names.stream()
                .reduce(
                        0,                                    // identity
                        (acc, name) -> acc + name.length(),   // accumulator
                        Integer::sum                          // combiner (for parallel)
                );
        System.out.println("Three-arg reduce (names to total length): " + totalLength);
        
        System.out.println("\nWhen to use each:");
        System.out.println("- Two-arg: When you have a default value");
        System.out.println("- One-arg: When empty stream should return empty");
        System.out.println("- Three-arg: When result type differs from element type");
    }
    
    // =========================================================================
    // 14. BUILT-IN REDUCTIONS
    // =========================================================================
    
    /**
     * Demonstrates built-in reduction methods.
     */
    public void demoBuiltInReductions() {
        printHeader("Built-in Reductions");
        
        // count()
        long count = names.stream().count();
        System.out.println("count(): " + count);
        
        // min() with comparator
        Optional<String> minName = names.stream()
                .min(Comparator.naturalOrder());
        System.out.println("min(natural order): " + minName.orElse("none"));
        
        // max() with comparator
        Optional<String> maxName = names.stream()
                .max(Comparator.comparingInt(String::length));
        System.out.println("max(by length): " + maxName.orElse("none"));
        
        // Matching operations
        boolean anyStartsWithA = names.stream().anyMatch(n -> n.startsWith("A"));
        boolean allNonEmpty = names.stream().allMatch(n -> !n.isEmpty());
        boolean noneStartsWithZ = names.stream().noneMatch(n -> n.startsWith("Z"));
        
        System.out.println("anyMatch(starts with A): " + anyStartsWithA);
        System.out.println("allMatch(non-empty): " + allNonEmpty);
        System.out.println("noneMatch(starts with Z): " + noneStartsWithZ);
        
        // findFirst() and findAny()
        Optional<String> first = names.stream()
                .filter(n -> n.length() > 4)
                .findFirst();
        System.out.println("findFirst(length > 4): " + first.orElse("none"));
    }
    
    // =========================================================================
    // 15. PRIMITIVE STREAMS
    // =========================================================================
    
    /**
     * Demonstrates primitive streams (IntStream, LongStream, DoubleStream).
     */
    public void demoPrimitiveStreams() {
        printHeader("Primitive Streams");
        
        // Creating IntStream
        IntStream intRange = IntStream.rangeClosed(1, 5);
        System.out.println("IntStream.rangeClosed(1, 5): " + intRange.boxed().toList());
        
        // mapToInt - convert Stream to IntStream
        int totalLength = names.stream()
                .mapToInt(String::length)
                .sum();
        System.out.println("mapToInt(length).sum(): " + totalLength);
        
        // IntStream operations
        System.out.println("\nIntStream specialized operations:");
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
        IntStream intStream = Arrays.stream(arr);
        System.out.println("  Original: " + Arrays.toString(arr));
        
        System.out.println("  sum(): " + Arrays.stream(arr).sum());
        System.out.println("  average(): " + Arrays.stream(arr).average().orElse(0));
        System.out.println("  min(): " + Arrays.stream(arr).min().orElse(0));
        System.out.println("  max(): " + Arrays.stream(arr).max().orElse(0));
        
        // boxed() - convert IntStream back to Stream<Integer>
        List<Integer> boxed = Arrays.stream(arr)
                .boxed()
                .toList();
        System.out.println("  boxed().toList(): " + boxed);
        
        // DoubleStream
        double average = names.stream()
                .mapToDouble(String::length)
                .average()
                .orElse(0.0);
        System.out.println("\nmapToDouble(length).average(): " + average);
    }
    
    // =========================================================================
    // 16. SUMMARY STATISTICS
    // =========================================================================
    
    /**
     * Demonstrates IntSummaryStatistics for efficient multi-stat computation.
     */
    public void demoSummaryStatistics() {
        printHeader("Summary Statistics");
        
        IntSummaryStatistics stats = names.stream()
                .mapToInt(String::length)
                .summaryStatistics();
        
        System.out.println("Name length statistics:");
        System.out.println("  Count:   " + stats.getCount());
        System.out.println("  Sum:     " + stats.getSum());
        System.out.println("  Min:     " + stats.getMin());
        System.out.println("  Max:     " + stats.getMax());
        System.out.println("  Average: " + stats.getAverage());
        
        // Using Collectors.summarizingInt
        IntSummaryStatistics numberStats = numbers.stream()
                .collect(Collectors.summarizingInt(Integer::intValue));
        System.out.println("\nNumber statistics via Collectors.summarizingInt:");
        System.out.println("  " + numberStats);
    }
    
    // =========================================================================
    // 17. FLATMAP
    // =========================================================================
    
    /**
     * Demonstrates flatMap for flattening nested structures.
     */
    public void demoFlatMap() {
        printHeader("FlatMap");
        
        // List of lists
        List<List<Integer>> listOfLists = List.of(
                List.of(1, 2, 3),
                List.of(4, 5),
                List.of(6, 7, 8, 9)
        );
        System.out.println("Original: " + listOfLists);
        
        // Flatten to single list
        List<Integer> flattened = listOfLists.stream()
                .flatMap(List::stream)
                .toList();
        System.out.println("flatMap(List::stream): " + flattened);
        
        // Split strings and flatten
        List<String> sentences = List.of("Hello World", "Java Streams", "Are Awesome");
        List<String> words = sentences.stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .toList();
        System.out.println("\nSentences: " + sentences);
        System.out.println("flatMap(split by space): " + words);
        
        // Get all characters from names
        List<Character> allChars = names.stream()
                .flatMap(name -> name.chars().mapToObj(c -> (char) c))
                .limit(20)  // just show first 20
                .toList();
        System.out.println("\nFirst 20 chars from all names: " + allChars);
    }
    
    // =========================================================================
    // 18. STREAM REUSE (Anti-pattern)
    // =========================================================================
    
    /**
     * Demonstrates that streams are single-use.
     */
    public void demoStreamReuse() {
        printHeader("Stream Single-Use (Anti-pattern)");
        
        Stream<String> stream = names.stream().filter(n -> n.length() > 3);
        
        // First use - works fine
        long count = stream.count();
        System.out.println("First use (count): " + count);
        
        // Second use - throws exception!
        try {
            stream.forEach(System.out::println);
        } catch (IllegalStateException e) {
            System.out.println("Second use threw: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        }
        
        System.out.println("\nSolution: Create a new stream each time, or use a Supplier:");
        java.util.function.Supplier<Stream<String>> streamSupplier = 
                () -> names.stream().filter(n -> n.length() > 3);
        System.out.println("  Count: " + streamSupplier.get().count());
        System.out.println("  List: " + streamSupplier.get().toList());
    }
    
    // =========================================================================
    // HELPER METHODS
    // =========================================================================
    
    private void printHeader(String title) {
        System.out.println("\n" + "-".repeat(60));
        System.out.println(">>> " + title);
        System.out.println("-".repeat(60));
    }
}
