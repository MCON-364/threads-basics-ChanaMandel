# In-Class Exercises: Java Streams

**Time:** ~40 minutes  
**File:** `StreamExercise.java`

## Overview

In this exercise, you will work with a gradebook represented as a `Map<String, List<Integer>>` where:
- **Key:** Student name
- **Value:** List of grades (integers 0-100)

You'll apply various stream operations to analyze and transform this data.

## Setup

The starter code provides:
- A pre-populated `gradebook` map with sample student data
- Method stubs with `// TODO` comments
- Expected output comments for verification

## Tasks

### Part 1: Basic Queries (10 minutes)

| Task | Method | Description |
|------|--------|-------------|
| 1.1 | `getAllStudentNames()` | Return a sorted list of all student names |
| 1.2 | `countStudents()` | Count the total number of students |
| 1.3 | `getStudentGrades(name)` | Get grades for a specific student (return empty list if not found) |

### Part 2: Grade Analysis (15 minutes)

| Task | Method | Description |
|------|--------|-------------|
| 2.1 | `calculateAverage(name)` | Calculate average grade for a student |
| 2.2 | `getAllGradesFlattened()` | Flatten all grades into a single sorted list |
| 2.3 | `findHighestGrade()` | Find the highest grade across all students |
| 2.4 | `findLowestGrade()` | Find the lowest grade across all students |
| 2.5 | `getTotalGradeCount()` | Count total number of grades across all students |

### Part 3: Filtering and Grouping (15 minutes)

| Task | Method | Description |
|------|--------|-------------|
| 3.1 | `getPassingStudents(threshold)` | Get names of students with average >= threshold |
| 3.2 | `getFailingStudents(threshold)` | Get names of students with average < threshold |
| 3.3 | `groupByPerformance()` | Group students into "A" (90+), "B" (80+), "C" (70+), "D" (60+), "F" (<60) |
| 3.4 | `getStudentAverages()` | Return Map of student name → average grade |
| 3.5 | `findTopPerformer()` | Find student with highest average |

## Hints

### Useful Stream Operations
- `map.entrySet().stream()` - Stream over map entries
- `map.keySet().stream()` - Stream over keys only
- `map.values().stream()` - Stream over values only
- `flatMap()` - Flatten nested collections
- `mapToInt()` / `mapToDouble()` - Convert to primitive streams
- `Collectors.groupingBy()` - Group elements
- `Collectors.averagingInt()` - Calculate average

### Working with Map Entries
```java
// Get key from entry
entry.getKey()

// Get value from entry
entry.getValue()

// Filter entries, then collect keys
map.entrySet().stream()
   .filter(e -> someCondition(e.getValue()))
   .map(Map.Entry::getKey)
   .toList();
```

### Calculating Averages
```java
// Using IntStream
list.stream()
    .mapToInt(Integer::intValue)
    .average()
    .orElse(0.0);

// Using Collectors
list.stream()
    .collect(Collectors.averagingInt(Integer::intValue));
```

## Testing Your Solutions

Run the `main()` method in `StreamExercise.java` to test your implementations. Expected outputs are shown in comments.

You can also run individual tests:
```bash
mvn test -Dtest=StreamExerciseTest
```

## Common Mistakes to Avoid

1. **Forgetting terminal operations** - Your stream won't execute without one!
2. **Using wrong average type** - `averagingInt` vs `averagingDouble`
3. **Not handling empty streams** - Use `orElse()` or `orElseGet()`
4. **Modifying the source map** - Stream operations should be pure

## Grading Criteria

- [ ] All methods compile without errors
- [ ] All methods return correct results
- [ ] Code uses stream operations (no explicit loops)
- [ ] Code is readable and well-formatted

## Bonus Challenges (if time permits)

1. Find all students who have at least one perfect score (100)
2. Calculate the class average (average of all grades)
3. Find the student with the most consistent grades (lowest standard deviation)
