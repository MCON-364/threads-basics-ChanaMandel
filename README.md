# MCON364 - Java Streams Assignment

This repository contains demonstrations, exercises, and homework for learning Java Streams.

## Prerequisites

- Java 21 or higher
- Maven 3.8+
- Understanding of functional interfaces and lambda expressions

## Repository Structure

```
streams/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
├── src/
│   ├── main/java/edu/touro/las/mcon364/streams/
│   │   ├── demo/
│   │   │   └── DemoStreams.java     # Comprehensive stream demonstrations
│   │   ├── exercises/
│   │   │   ├── EXERCISES_README.md  # In-class exercise instructions
│   │   │   └── StreamExercise.java  # Starter code for exercises (~40 min)
│   │   └── homework/
│   │       ├── HOMEWORK_README.md   # Homework project instructions
│   │       └── StreamHomework.java  # Starter code for homework (~2 hrs)
│   └── test/java/...                # Unit tests
```

## Getting Started

### Clone the Repository
```bash
git clone <your-repo-url>
cd streams
```

### Build the Project
```bash
mvn compile
```

### Run Demonstrations
```bash
mvn exec:java -Dexec.mainClass="edu.touro.las.mcon364.streams.demo.DemoStreams"
```

### Run Tests
```bash
mvn test
```

## Package Descriptions

### 1. Demo (`demo/`)
Contains `DemoStreams.java` with comprehensive examples of all stream operations covered in class:
- Stream creation and sources
- Intermediate operations (filter, map, flatMap, sorted, distinct, etc.)
- Terminal operations (collect, reduce, forEach, count, etc.)
- Lazy evaluation demonstrations
- Collecting to various data structures
- Grouping and partitioning
- Reductions and primitive streams

**Usage:** Run the demo class and study each method. Modify the examples to experiment.

### 2. Exercises (`exercises/`)
Contains `StreamExercise.java` with in-class practice problems.
- **Time:** ~40 minutes
- **Focus:** Applying stream operations to real scenarios
- **Instructions:** See `EXERCISES_README.md` in the exercises folder

### 3. Homework (`homework/`)
Contains `StreamHomework.java` with a mini-project assignment.
- **Time:** ~2 hours
- **Focus:** Building a complete data processing application using streams
- **Instructions:** See `HOMEWORK_README.md` in the homework folder

## Learning Objectives

After completing this assignment, you will be able to:

1. Create streams from various data sources
2. Chain intermediate operations to build processing pipelines
3. Use terminal operations to produce results
4. Understand and leverage lazy evaluation
5. Collect stream results into Lists, Sets, and Maps
6. Use `groupingBy()` and `partitioningBy()` for data organization
7. Perform reductions with `reduce()` and specialized methods
8. Work with primitive streams for numeric operations

## Key Concepts

| Concept | Description |
|---------|-------------|
| **Stream** | A pipeline of operations over data (not a data structure) |
| **Lazy Evaluation** | Intermediate operations don't execute until a terminal operation is called |
| **Single-Use** | Streams can only be consumed once |
| **Internal Iteration** | The stream controls iteration, not you |

## Common Mistakes to Avoid

- Forgetting to call a terminal operation (nothing executes!)
- Trying to reuse a consumed stream
- Mutating the source collection during stream processing
- Using `toMap()` without handling duplicate keys

## Resources

- [Java Stream API Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html)
- [Collectors Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Collectors.html)
- Course Canvas page on Streams

## Submission

1. Complete all TODO items in `StreamExercise.java` (in-class)
2. Complete all TODO items in `StreamHomework.java` (homework)
3. Ensure all tests pass: `mvn test`
4. Commit and push your changes

---
*MCON364 - Data Structures II*
