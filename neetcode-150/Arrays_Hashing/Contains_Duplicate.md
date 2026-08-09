For an interview, **before jumping to `Set`**, you should clarify the requirements and constraints that influence the approach.

### Questions to ask the interviewer

1. **Do we only need to know whether a duplicate exists, or do we need to return the duplicate value?**

   * Here: only `true/false`.

2. **Can the input array be modified?**

   * If yes, sorting becomes an option.
   * If no, avoid modifying it.

3. **Are there any constraints on extra space?**

   * This is the key question for choosing `Set`.
   * `Set` → **O(n) extra space**
   * Sorting → potentially **O(1) extra space** depending on the sorting algorithm/implementation.

4. **Do we need to preserve the original order of the array?**

   * Relevant if considering sorting.

5. **Are the values bounded to a small range?**

   * Here values range from `-10^9` to `10^9`, so a boolean array/frequency array isn't practical.

6. **What's the expected time complexity?**

   * Given `n` can be `10^5`, an **O(n²)** brute-force solution is undesirable.
   * We should target **O(n)** average time.

### Then I'd summarize my interpretation

> "So, we need to determine whether any element occurs more than once, without modifying the array, and there's no restriction against using extra space. Since `n` can be up to `10^5`, I'll aim for O(n) time. A `HashSet` is a natural fit because it lets us check whether we've already seen an element in O(1) average time."

Then discuss the approach:

```text
seen = empty Set

for each num in nums:
    if num is already in seen:
        return true
    add num to seen

return false
```

### Interview thought process

I'd structure it like this:

**Brute force → Sorting → HashSet**

| Approach              |         Time |   Space | Comment          |
| --------------------- | -----------: | ------: | ---------------- |
| Compare every pair    |        O(n²) |    O(1) | Too slow for 10⁵ |
| Sort + adjacent check |   O(n log n) | Depends | Good alternative |
| HashSet               | O(n) average |    O(n) | **Best default** |

One important interview habit: **don't ask every possible question mechanically**. Ask the questions that can actually change your solution.

For this problem, the most important one is:

> **"Are there any restrictions on extra space or modifying the input?"**

That immediately determines whether `HashSet` is an appropriate solution.


For an **SDE3/Senior Software Engineer interview**, I would make the problem less about knowing `Set` and more about **requirements clarification, trade-offs, and designing for constraints**.

### A good modified version

> **Given an integer array `nums`, determine whether the array contains any duplicate values. The array can contain up to 10⁸ elements and values can range from -10⁹ to 10⁹.**
>
> **Your solution should process the data efficiently, but you should assume that the entire array may not fit comfortably in memory. Discuss the trade-offs of your approach and how you would handle the problem if the data were coming as a stream rather than as an in-memory array.**

Then I would expect the candidate to ask questions like:

### 1. Clarify the requirements

**Candidate:**

> "Do we need the answer only after consuming the entire input, or can we return as soon as we detect a duplicate?"

This opens the door to early termination.

**Candidate:**

> "Can I use additional memory? If yes, is there a memory limit?"

This determines whether a `HashSet` is viable.

**Candidate:**

> "Is the input guaranteed to fit in memory, or should I treat it as a stream?"

This changes the architecture significantly.

### 2. Clarify correctness requirements

> "Do we need exact duplicate detection, or would a probabilistic answer be acceptable?"

This is a **senior-level question**.

If exact → `HashSet`, sorting, external sorting, etc.

If probabilistic is acceptable → Bloom filter becomes an option.

You can then discuss:

```text
HashSet
    Exact
    O(n) expected time
    O(n) memory

Bloom Filter
    Probabilistic
    Much lower memory
    False positives possible
```

### 3. Push the interviewer further

For SDE3, I'd proactively explore scale:

> "If we're dealing with 10⁸–10⁹ values and the dataset doesn't fit in memory, are we allowed to use disk?"

If yes:

**External sorting**

```text
Input
  ↓
Partition / write to disk
  ↓
Sort partitions
  ↓
Merge sorted partitions
  ↓
Check adjacent values
```

Or:

> "Is this running on a single machine, or can I distribute the processing?"

Now you can discuss partitioning by hash:

```text
hash(value) % N
        ↓
 ┌──────┼──────┐
 ↓      ↓      ↓
Node 1 Node 2 Node 3
```

All occurrences of the same value go to the same partition, so duplicate detection remains correct.

---

## What makes this SDE3-level?

The original question tests:

> **"Do you know how to use a Set?"**

The senior version tests:

> **"Can you identify the constraints that determine the architecture?"**

You want to demonstrate this progression:

```text
Simple constraints
      ↓
HashSet
O(n) time, O(n) space
      ↓
Memory constrained
      ↓
Sorting / external sorting
      ↓
Massive distributed data
      ↓
Partition by hash
      ↓
Approximate answer acceptable?
      ↓
Bloom Filter
```

### The key interview habit

For SDE3, don't immediately say:

> "I'll use a HashSet."

Instead say:

> **"Before choosing the data structure, I'd like to understand the input size, memory constraints, whether the input can be modified, whether exact detection is required, and whether the data is available in memory or arrives as a stream."**

That demonstrates **engineering judgment**, rather than just DSA knowledge.
