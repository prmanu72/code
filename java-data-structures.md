# Java Data Structures in the JDK

This document lists the major data structures available in standard Java/JDK, including core collection types, concurrent variants, specialized structures, and legacy classes.

## Notes on "Complete List"

There is no single finite universal list of all Java data structures if third-party libraries and custom implementations are included.

This file covers the practical complete list for:

- JDK-provided data structures
- Java Collections Framework interfaces
- Common specialized and modified variants such as `ConcurrentMap`, `BlockingQueue`, `CopyOnWriteArrayList`, and reference-based maps

## 1. Primitive and Built-In Structures

| Structure | Java Form | Notes |
| --- | --- | --- |
| Array | `T[]` | Fixed-size contiguous storage |
| Primitive array | `int[]`, `char[]`, `byte[]`, etc. | Efficient storage for primitive values |
| Object array | `Object[]` | Stores object references |
| Multidimensional / jagged array | `int[][]` | Array of arrays |
| String-backed character sequence | `String` | Not a collection, but often treated as sequence storage |

## 2. Core Collection Interfaces

### Base Interfaces

- `Iterable`
- `Collection`
- `Map`

### List Interfaces

- `List`
- `SequencedCollection`

### Set Interfaces

- `Set`
- `SortedSet`
- `NavigableSet`
- `SequencedSet`

### Queue Interfaces

- `Queue`
- `Deque`

### Map Interfaces

- `Map`
- `SortedMap`
- `NavigableMap`
- `ConcurrentMap`
- `ConcurrentNavigableMap`
- `SequencedMap`

### Blocking / Concurrent Interfaces

- `BlockingQueue`
- `BlockingDeque`
- `TransferQueue`

## 3. List Implementations

| Class | Type | Ordering | Thread Safe | Notes |
| --- | --- | --- | --- | --- |
| `ArrayList` | Dynamic array | Insertion order | No | Most common general-purpose list |
| `LinkedList` | Doubly linked list | Insertion order | No | Also implements `Deque` and `Queue` |
| `Vector` | Dynamic array | Insertion order | Yes | Legacy synchronized list |
| `Stack` | Stack over `Vector` | LIFO | Yes | Legacy; prefer `Deque` |
| `CopyOnWriteArrayList` | Copy-on-write list | Insertion order | Yes | Good for read-heavy concurrent use |

## 4. Set Implementations

| Class | Type | Ordering | Thread Safe | Notes |
| --- | --- | --- | --- | --- |
| `HashSet` | Hash table set | No guaranteed order | No | Fast general-purpose set |
| `LinkedHashSet` | Hash table + linked order | Insertion order | No | Preserves insertion order |
| `TreeSet` | Balanced tree set | Sorted | No | Implements `NavigableSet` |
| `EnumSet` | Bit-vector-like enum set | Natural enum order | No | Very efficient for enum values |
| `CopyOnWriteArraySet` | Copy-on-write set | Insertion order | Yes | Read-heavy concurrent usage |
| `ConcurrentSkipListSet` | Skip list set | Sorted | Yes | Concurrent sorted set |

### Set Views and Derived Sets

- `Collections.newSetFromMap(...)`
- `ConcurrentHashMap.newKeySet()`
- `Map.keySet()`
- `IdentityHashMap.keySet()`
- `WeakHashMap.keySet()`

## 5. Queue and Deque Implementations

| Class | Type | Ordering | Thread Safe | Notes |
| --- | --- | --- | --- | --- |
| `ArrayDeque` | Resizable array deque | FIFO / LIFO | No | Preferred over `Stack` for stack usage |
| `LinkedList` | Linked queue/deque | FIFO / LIFO | No | General queue/deque implementation |
| `PriorityQueue` | Heap | Priority order | No | Min-heap by default |
| `ConcurrentLinkedQueue` | Lock-free linked queue | FIFO | Yes | Non-blocking concurrent queue |
| `ConcurrentLinkedDeque` | Lock-free linked deque | FIFO / LIFO | Yes | Non-blocking concurrent deque |

## 6. Blocking Queue and Deque Implementations

| Class | Interface | Ordering | Thread Safe | Notes |
| --- | --- | --- | --- | --- |
| `ArrayBlockingQueue` | `BlockingQueue` | FIFO | Yes | Bounded blocking queue |
| `LinkedBlockingQueue` | `BlockingQueue` | FIFO | Yes | Optionally bounded |
| `LinkedBlockingDeque` | `BlockingDeque` | FIFO / LIFO | Yes | Blocking deque |
| `PriorityBlockingQueue` | `BlockingQueue` | Priority order | Yes | Unbounded priority queue |
| `DelayQueue` | `BlockingQueue` | Delay-based | Yes | Elements available after delay expires |
| `SynchronousQueue` | `BlockingQueue` | Direct handoff | Yes | No internal capacity |
| `LinkedTransferQueue` | `TransferQueue` | FIFO | Yes | Supports direct producer-to-consumer transfer |

## 7. Map Implementations

| Class | Type | Ordering | Thread Safe | Notes |
| --- | --- | --- | --- | --- |
| `HashMap` | Hash table map | No guaranteed order | No | Most common map |
| `LinkedHashMap` | Hash table + linked order | Insertion/access order | No | Supports predictable iteration order |
| `TreeMap` | Balanced tree map | Sorted | No | Implements `NavigableMap` |
| `Hashtable` | Hash table map | No guaranteed order | Yes | Legacy synchronized map |
| `WeakHashMap` | Hash table map | No guaranteed order | No | Entries removed when keys are weakly reachable |
| `IdentityHashMap` | Hash table map | No guaranteed order | No | Uses reference equality instead of `equals()` |
| `EnumMap` | Array-backed enum map | Natural enum order | No | Efficient for enum keys |
| `ConcurrentHashMap` | Concurrent hash map | No guaranteed order | Yes | High-performance concurrent map |
| `ConcurrentSkipListMap` | Skip list map | Sorted | Yes | Concurrent sorted map |
| `Properties` | String-oriented map | No guaranteed order | Yes | Legacy config map, extends `Hashtable` |

## 8. Specialized Structures

| Class | Type | Notes |
| --- | --- | --- |
| `BitSet` | Bit vector / bit array | Compact storage for bits and flags |
| `PriorityQueue` | Heap | Common priority-based data structure |
| `PriorityBlockingQueue` | Concurrent heap | Thread-safe priority queue |
| `EnumSet` | Specialized set | Efficient enum membership checks |
| `EnumMap` | Specialized map | Efficient enum-keyed lookup |
| `WeakHashMap` | Reference-based map | Useful for caches and metadata |
| `IdentityHashMap` | Identity-based map | Compares keys by reference |

## 9. Concurrent and Modified Variants

These are the common "modified" or specialized versions people usually mean when they ask for structures like `ConcurrentMap`.

### Concurrent Map Variants

- `ConcurrentMap` - interface for thread-safe maps with atomic operations
- `ConcurrentHashMap` - hash-based concurrent map
- `ConcurrentNavigableMap` - concurrent sorted-map interface
- `ConcurrentSkipListMap` - skip-list-based concurrent sorted map

### Concurrent Set Variants

- `ConcurrentSkipListSet`
- `ConcurrentHashMap.newKeySet()`
- `CopyOnWriteArraySet`

### Concurrent List Variants

- `CopyOnWriteArrayList`
- `Vector` (legacy synchronized)

### Concurrent Queue and Deque Variants

- `ConcurrentLinkedQueue`
- `ConcurrentLinkedDeque`
- `ArrayBlockingQueue`
- `LinkedBlockingQueue`
- `LinkedBlockingDeque`
- `PriorityBlockingQueue`
- `DelayQueue`
- `SynchronousQueue`
- `LinkedTransferQueue`

### Copy-On-Write Variants

- `CopyOnWriteArrayList`
- `CopyOnWriteArraySet`

### Sorted / Navigable Concurrent Variants

- `ConcurrentSkipListMap`
- `ConcurrentSkipListSet`

## 10. Ordering-Based Variants

### Insertion-Ordered

- `ArrayList`
- `LinkedList`
- `LinkedHashSet`
- `LinkedHashMap`
- `CopyOnWriteArrayList`
- `CopyOnWriteArraySet`
- `Vector`

### Access-Ordered

- `LinkedHashMap` with access-order mode enabled

### Sorted / Navigable

- `TreeSet`
- `TreeMap`
- `ConcurrentSkipListSet`
- `ConcurrentSkipListMap`

### Priority-Ordered

- `PriorityQueue`
- `PriorityBlockingQueue`

## 11. Reference-, Identity-, and Enum-Based Variants

| Variant | Class | Behavior |
| --- | --- | --- |
| Weak-reference map | `WeakHashMap` | Keys may be garbage collected |
| Identity-based map | `IdentityHashMap` | Uses `==` instead of `equals()` |
| Enum-keyed map | `EnumMap` | Keys must be enum constants |
| Enum-only set | `EnumSet` | Elements must be enum constants |
| Bit-oriented set-like storage | `BitSet` | Compact representation of boolean flags |

## 12. Legacy Structures

These are still in the JDK but generally avoided in new code unless required for compatibility.

- `Vector`
- `Stack`
- `Hashtable`
- `Dictionary`
- `Properties`

## 13. Abstract Data Structures Represented by Java Classes

Java also supports common computer science data structures through these implementations:

| Abstract Data Structure | Typical Java Representation |
| --- | --- |
| Dynamic array | `ArrayList`, `Vector` |
| Linked list | `LinkedList` |
| Stack | `ArrayDeque`, `Stack` |
| Queue | `ArrayDeque`, `LinkedList`, `ConcurrentLinkedQueue` |
| Deque | `ArrayDeque`, `LinkedList`, `ConcurrentLinkedDeque` |
| Hash table | `HashMap`, `Hashtable`, `ConcurrentHashMap` |
| Balanced binary search tree | `TreeMap`, `TreeSet` |
| Heap | `PriorityQueue`, `PriorityBlockingQueue` |
| Skip list | `ConcurrentSkipListMap`, `ConcurrentSkipListSet` |
| Bit vector | `BitSet` |

## 14. Practical Complete JDK Implementation List

This is the concise implementation-level list most people mean by "all Java data structures in the JDK":

- `ArrayList`
- `LinkedList`
- `Vector`
- `Stack`
- `CopyOnWriteArrayList`
- `HashSet`
- `LinkedHashSet`
- `TreeSet`
- `EnumSet`
- `CopyOnWriteArraySet`
- `ConcurrentSkipListSet`
- `ArrayDeque`
- `PriorityQueue`
- `ConcurrentLinkedQueue`
- `ConcurrentLinkedDeque`
- `ArrayBlockingQueue`
- `LinkedBlockingQueue`
- `LinkedBlockingDeque`
- `PriorityBlockingQueue`
- `DelayQueue`
- `SynchronousQueue`
- `LinkedTransferQueue`
- `HashMap`
- `LinkedHashMap`
- `TreeMap`
- `Hashtable`
- `WeakHashMap`
- `IdentityHashMap`
- `EnumMap`
- `ConcurrentHashMap`
- `ConcurrentSkipListMap`
- `Properties`
- `BitSet`

## 15. Quick Selection Guide

| Need | Use |
| --- | --- |
| General resizable list | `ArrayList` |
| Frequent insert/remove at ends | `ArrayDeque` or `LinkedList` |
| Unique unordered values | `HashSet` |
| Unique insertion-ordered values | `LinkedHashSet` |
| Unique sorted values | `TreeSet` |
| General key-value mapping | `HashMap` |
| Predictable map iteration order | `LinkedHashMap` |
| Sorted key-value mapping | `TreeMap` |
| High-throughput concurrent map | `ConcurrentHashMap` |
| Concurrent sorted map | `ConcurrentSkipListMap` |
| Priority processing | `PriorityQueue` |
| Producer-consumer blocking queue | `LinkedBlockingQueue` or `ArrayBlockingQueue` |
| Read-heavy concurrent list | `CopyOnWriteArrayList` |
| Enum keys or values | `EnumMap` or `EnumSet` |

## 16. Important Clarification

Some items above are:

- concrete classes such as `ArrayList`
- interfaces such as `Map` and `ConcurrentMap`
- specialized variants such as `WeakHashMap`
- legacy classes such as `Stack`

So if you need a strict "class-only list," use Section 14.
