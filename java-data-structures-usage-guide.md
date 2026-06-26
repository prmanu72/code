# Java Data Structures Usage Guide

This file expands on [java-data-structures.md](E:\code\java-data-structures.md) with practical guidance for each major JDK data structure:

- mostly used functions
- when to use
- when not to use
- thread safety
- common conversions to other structures

## How to Read This File

- "Thread Safe" means safe for concurrent use by multiple threads without external synchronization.
- "Common conversions" lists the most common ways developers convert or wrap that structure in practice.
- Interfaces are included first because they define how most code should be designed.
- Concrete classes follow after that.

## 1. Core Interfaces

| Structure | Mostly Used Functions | When to Use | When Not to Use | Thread Safe | Common Conversions |
| --- | --- | --- | --- | --- | --- |
| `Iterable<E>` | `iterator()`, enhanced `for` loop | When an API only needs traversal | When you need indexed access or mutation operations | Depends on implementation | `Collection`, stream via `StreamSupport.stream(...)` |
| `Collection<E>` | `add()`, `remove()`, `contains()`, `size()`, `isEmpty()`, `iterator()`, `toArray()` | Generic APIs over groups of elements | When ordering, uniqueness, or key-value mapping matters | Depends on implementation | `new ArrayList<>(c)`, `new HashSet<>(c)`, `c.stream()` |
| `List<E>` | `get()`, `add()`, `add(index, e)`, `remove()`, `set()`, `contains()`, `subList()` | Ordered data with duplicates and index-based access | When uniqueness or key-based lookup matters more | Depends on implementation | `new ArrayList<>(list)`, `new LinkedList<>(list)`, `list.stream()` |
| `Set<E>` | `add()`, `remove()`, `contains()`, `size()` | Unique elements only | When duplicates or positional access are required | Depends on implementation | `new HashSet<>(c)`, `new LinkedHashSet<>(c)`, `new TreeSet<>(c)` |
| `SortedSet<E>` | `first()`, `last()`, `headSet()`, `tailSet()`, `subSet()` | Sorted unique elements | When sort maintenance costs are not worth it | Depends on implementation | Usually `TreeSet`, sometimes to `NavigableSet` |
| `NavigableSet<E>` | `ceiling()`, `floor()`, `higher()`, `lower()`, `pollFirst()`, `pollLast()` | Sorted sets with nearest-match lookups | When only plain uniqueness is needed | Depends on implementation | Usually `TreeSet`, `ConcurrentSkipListSet` |
| `Queue<E>` | `offer()`, `poll()`, `peek()`, `add()`, `remove()`, `element()` | FIFO or producer-consumer style APIs | When random access is needed | Depends on implementation | `ArrayDeque`, `LinkedList`, blocking queues |
| `Deque<E>` | `addFirst()`, `addLast()`, `offerFirst()`, `offerLast()`, `pollFirst()`, `pollLast()`, `peekFirst()`, `peekLast()` | Stack or double-ended queue behavior | When only indexed list operations matter | Depends on implementation | `ArrayDeque`, `LinkedList`, `ConcurrentLinkedDeque` |
| `Map<K,V>` | `put()`, `get()`, `remove()`, `containsKey()`, `containsValue()`, `keySet()`, `values()`, `entrySet()`, `getOrDefault()` | Key-value storage | When only plain collection behavior is needed | Depends on implementation | `new HashMap<>(m)`, `new TreeMap<>(m)`, `new LinkedHashMap<>(m)` |
| `SortedMap<K,V>` | `firstKey()`, `lastKey()`, `headMap()`, `tailMap()`, `subMap()` | Sorted key-value lookup | When sort order gives no benefit | Depends on implementation | Usually `TreeMap` |
| `NavigableMap<K,V>` | `ceilingEntry()`, `floorEntry()`, `higherEntry()`, `lowerEntry()`, `pollFirstEntry()`, `pollLastEntry()` | Sorted maps with nearest-key operations | When hash lookup is enough | Depends on implementation | `TreeMap`, `ConcurrentSkipListMap` |
| `ConcurrentMap<K,V>` | `putIfAbsent()`, `replace()`, `remove(key, value)`, `computeIfAbsent()`, `compute()`, `merge()` | Thread-safe shared maps with atomic operations | When single-threaded code is enough | Yes by contract | `ConcurrentHashMap`, `ConcurrentSkipListMap` |
| `ConcurrentNavigableMap<K,V>` | all `ConcurrentMap` plus navigable methods | Concurrent sorted map access | When sorted order is unnecessary | Yes by contract | `ConcurrentSkipListMap` |
| `BlockingQueue<E>` | `put()`, `take()`, `offer()`, `poll()`, `peek()`, `remainingCapacity()` | Producer-consumer pipelines | When non-blocking behavior is required | Yes by contract | `ArrayBlockingQueue`, `LinkedBlockingQueue`, `PriorityBlockingQueue` |
| `BlockingDeque<E>` | `putFirst()`, `putLast()`, `takeFirst()`, `takeLast()`, `offerFirst()`, `offerLast()` | Blocking work queues at both ends | When simple queue semantics are enough | Yes by contract | `LinkedBlockingDeque` |
| `TransferQueue<E>` | `transfer()`, `tryTransfer()`, `put()`, `take()` | Direct producer-to-consumer handoff | When standard blocking queue semantics are enough | Yes by contract | `LinkedTransferQueue` |
| `SequencedCollection<E>` | `addFirst()`, `addLast()`, `getFirst()`, `getLast()`, `removeFirst()`, `removeLast()`, `reversed()` | Ordered collections in newer Java APIs | When targeting older Java versions or unordered collections | Depends on implementation | Usually via `List`, `Deque`, ordered sets |
| `SequencedSet<E>` | sequence operations plus set uniqueness | Ordered unique collections | When order does not matter | Depends on implementation | `LinkedHashSet`, ordered set views |
| `SequencedMap<K,V>` | `firstEntry()`, `lastEntry()`, `putFirst()`, `putLast()`, `reversed()` | Ordered maps in newer Java APIs | When plain hash or sorted map semantics are enough | Depends on implementation | `LinkedHashMap`, ordered map views |

## 2. Arrays and Basic Built-In Structures

| Structure | Mostly Used Functions | When to Use | When Not to Use | Thread Safe | Common Conversions |
| --- | --- | --- | --- | --- | --- |
| `T[]` | index access `arr[i]`, `length`, `Arrays.sort()`, `Arrays.copyOf()`, `System.arraycopy()` | Fixed-size fast indexed storage | When size changes often | No | `Arrays.asList(arr)`, `new ArrayList<>(Arrays.asList(arr))`, streams via `Arrays.stream(arr)` |
| Primitive arrays like `int[]` | `arr[i]`, `length`, `Arrays.sort()`, `Arrays.copyOf()` | Performance-sensitive numeric data | When object-style collection APIs are needed | No | `Arrays.stream(int[])`, manual boxing to `List<Integer>` |
| `Object[]` | index access, `length`, `Arrays.copyOf()` | Low-level APIs and internal storage | When generics-friendly collections are better | No | `Arrays.asList(array)`, `Collection.toArray()` |
| `int[][]` and similar | nested indexing, `length` | Matrix-like or jagged data | When graph/tree relationships fit better | No | manual conversion to nested lists |
| `String` | `length()`, `charAt()`, `substring()`, `split()`, `toCharArray()` | Immutable text or token storage | When mutable character-heavy operations dominate | Yes because immutable | `toCharArray()`, `chars()`, `codePoints()`, `List<String>` via `split()` |

## 3. List Implementations

| Structure | Mostly Used Functions | When to Use | When Not to Use | Thread Safe | Common Conversions |
| --- | --- | --- | --- | --- | --- |
| `ArrayList<E>` | `add()`, `get()`, `set()`, `remove()`, `contains()`, `size()`, `sort()` | General-purpose list, fast random access, append-heavy usage | When frequent insert/delete in the middle or thread-safe access is needed | No | `new LinkedList<>(list)`, `new HashSet<>(list)`, `list.toArray(new T[0])` |
| `LinkedList<E>` | `addFirst()`, `addLast()`, `removeFirst()`, `removeLast()`, `offer()`, `poll()`, `peek()` | Queue/deque behavior with frequent end operations | When random indexed access is important | No | `new ArrayList<>(list)`, use directly as `Deque` or `Queue` |
| `Vector<E>` | `add()`, `get()`, `remove()`, `size()` | Legacy synchronized list compatibility | In new code where `ArrayList` or `CopyOnWriteArrayList` is better | Yes | `new ArrayList<>(vector)`, `Collections.list(...)` in older APIs |
| `Stack<E>` | `push()`, `pop()`, `peek()`, `empty()` | Legacy stack API compatibility | In new code; prefer `ArrayDeque` | Yes | replace with `Deque<E> stack = new ArrayDeque<>()` |
| `CopyOnWriteArrayList<E>` | `add()`, `remove()`, `get()`, `contains()`, iteration | Read-heavy concurrent data with infrequent writes | Write-heavy workloads or large frequently updated lists | Yes | `new ArrayList<>(cowList)`, often exposed as `List<E>` |

## 4. Set Implementations

| Structure | Mostly Used Functions | When to Use | When Not to Use | Thread Safe | Common Conversions |
| --- | --- | --- | --- | --- | --- |
| `HashSet<E>` | `add()`, `remove()`, `contains()`, `size()` | Fast uniqueness checks and de-duplication | When sorted or insertion order must be preserved | No | `new ArrayList<>(set)`, `new LinkedHashSet<>(set)`, `new TreeSet<>(set)` |
| `LinkedHashSet<E>` | `add()`, `remove()`, `contains()`, iteration | Unique values with insertion-order preservation | When order is irrelevant and minimal overhead matters | No | `new ArrayList<>(set)`, `new HashSet<>(set)` |
| `TreeSet<E>` | `add()`, `remove()`, `contains()`, `first()`, `last()`, `ceiling()`, `floor()` | Sorted unique data and range queries | When hash-based lookups are enough or elements are not comparable | No | `new HashSet<>(set)`, `new ArrayList<>(set)` |
| `EnumSet<E extends Enum<E>>` | `of()`, `allOf()`, `noneOf()`, `add()`, `contains()`, `remove()` | Compact fast sets of enum constants | When elements are not enums | No | `EnumSet.copyOf(...)`, `new HashSet<>(enumSet)` |
| `CopyOnWriteArraySet<E>` | `add()`, `remove()`, `contains()`, iteration | Read-heavy concurrent unique collections | Frequent mutations or large sets | Yes | `new HashSet<>(set)`, often backed into `List` for export |
| `ConcurrentSkipListSet<E>` | `add()`, `remove()`, `contains()`, `first()`, `last()`, `ceiling()`, `floor()` | Concurrent sorted set access | When sorted order is not needed | Yes | `new TreeSet<>(set)`, `new ArrayList<>(set)` |
| `Collections.newSetFromMap(...)` | map-backed set operations | Custom set behavior using a chosen map | When a regular set implementation already fits | Depends on backing map | common backing maps: `ConcurrentHashMap`, `IdentityHashMap`, `WeakHashMap` |
| `ConcurrentHashMap.newKeySet()` | `add()`, `remove()`, `contains()` | Concurrent unique key set without custom values | When sorted order is needed | Yes | `new HashSet<>(set)`, `new ArrayList<>(set)` |
| `Map.keySet()` views | `contains()`, iteration, remove in some cases | When working directly with map keys | When an independent set copy is needed | Depends on backing map | `new HashSet<>(map.keySet())` |

## 5. Queue and Deque Implementations

| Structure | Mostly Used Functions | When to Use | When Not to Use | Thread Safe | Common Conversions |
| --- | --- | --- | --- | --- | --- |
| `ArrayDeque<E>` | `push()`, `pop()`, `peek()`, `offer()`, `poll()`, `addFirst()`, `addLast()` | Best default stack or queue in single-threaded code | When null elements are required or concurrent access is needed | No | `new LinkedList<>(deque)`, `new ArrayList<>(deque)` |
| `PriorityQueue<E>` | `offer()`, `poll()`, `peek()`, `add()` | Priority scheduling, top-k, heap problems | When full sorted iteration or thread safety is required | No | `new ArrayList<>(pq)` then sort, `new PriorityBlockingQueue<>(pq)` |
| `ConcurrentLinkedQueue<E>` | `offer()`, `poll()`, `peek()` | High-throughput non-blocking concurrent FIFO queue | When blocking semantics are needed | Yes | `new ArrayList<>(queue)`, sometimes to `LinkedBlockingQueue` |
| `ConcurrentLinkedDeque<E>` | `offerFirst()`, `offerLast()`, `pollFirst()`, `pollLast()`, `peekFirst()`, `peekLast()` | Non-blocking concurrent deque/stack | When you need blocking behavior | Yes | `new ArrayDeque<>(deque)`, `new LinkedList<>(deque)` |

## 6. Blocking Queue and Deque Implementations

| Structure | Mostly Used Functions | When to Use | When Not to Use | Thread Safe | Common Conversions |
| --- | --- | --- | --- | --- | --- |
| `ArrayBlockingQueue<E>` | `put()`, `take()`, `offer()`, `poll()`, `peek()` | Fixed-capacity producer-consumer pipelines | When you need unbounded growth or priority ordering | Yes | replace with `LinkedBlockingQueue` if capacity needs flexibility |
| `LinkedBlockingQueue<E>` | `put()`, `take()`, `offer()`, `poll()`, `peek()` | Standard blocking queue, often easiest producer-consumer choice | When bounded array-based behavior or direct handoff is required | Yes | `new ArrayList<>(queue)`, migrate to `ArrayBlockingQueue` for fixed capacity |
| `LinkedBlockingDeque<E>` | `putFirst()`, `putLast()`, `takeFirst()`, `takeLast()`, `offerFirst()`, `offerLast()` | Blocking double-ended work queues | When FIFO-only behavior is enough | Yes | `new LinkedList<>(deque)`, use as `BlockingQueue` if only one end matters |
| `PriorityBlockingQueue<E>` | `put()`, `take()`, `offer()`, `poll()`, `peek()` | Concurrent priority processing | When FIFO order or bounded capacity is needed | Yes | `new PriorityQueue<>(queue)` |
| `DelayQueue<E extends Delayed>` | `put()`, `take()`, `offer()`, `poll()` | Delayed task scheduling or expiry-based processing | When elements do not naturally model delays | Yes | often replaced conceptually by `PriorityBlockingQueue` plus timestamps |
| `SynchronousQueue<E>` | `put()`, `take()`, `offer()`, `poll()` | Direct handoff between producer and consumer threads | When buffering is required | Yes | usually swapped with `ArrayBlockingQueue` or `LinkedBlockingQueue` |
| `LinkedTransferQueue<E>` | `transfer()`, `tryTransfer()`, `put()`, `take()`, `offer()` | High-throughput concurrent exchange with optional direct transfer | When simpler queue semantics are enough | Yes | `new LinkedBlockingQueue<>(queue)` for simpler blocking semantics |

## 7. Map Implementations

| Structure | Mostly Used Functions | When to Use | When Not to Use | Thread Safe | Common Conversions |
| --- | --- | --- | --- | --- | --- |
| `HashMap<K,V>` | `put()`, `get()`, `remove()`, `containsKey()`, `getOrDefault()`, `computeIfAbsent()`, `entrySet()` | General-purpose key-value storage | When order, sorting, or concurrency is required | No | `new LinkedHashMap<>(map)`, `new TreeMap<>(map)`, `new ConcurrentHashMap<>(map)` |
| `LinkedHashMap<K,V>` | `put()`, `get()`, `remove()`, `entrySet()` | Predictable iteration order, LRU-style cache support with access-order | When neither order nor cache semantics matter | No | `new HashMap<>(map)`, `new TreeMap<>(map)` |
| `TreeMap<K,V>` | `put()`, `get()`, `remove()`, `firstKey()`, `lastKey()`, `ceilingEntry()`, `floorEntry()`, `subMap()` | Sorted keys, range queries, nearest-key lookups | When hash-based speed is enough or keys are not comparable | No | `new HashMap<>(map)`, `new LinkedHashMap<>(map)` |
| `Hashtable<K,V>` | `put()`, `get()`, `remove()`, `containsKey()` | Legacy synchronized map compatibility | In new code; prefer `ConcurrentHashMap` or synchronized wrappers | Yes | `new HashMap<>(table)`, `new ConcurrentHashMap<>(table)` |
| `WeakHashMap<K,V>` | `put()`, `get()`, `remove()`, `containsKey()` | Caches/metadata tied to object lifetime | When strong references are required for correctness | No | `new HashMap<>(map)` for stable copy |
| `IdentityHashMap<K,V>` | `put()`, `get()`, `remove()`, `containsKey()` | Reference-identity based keys, object graph traversal, proxies | When normal logical equality is required | No | `new HashMap<>(map)` if equality semantics should change |
| `EnumMap<K extends Enum<K>,V>` | `put()`, `get()`, `remove()`, `containsKey()`, `entrySet()` | Fast compact maps keyed by enum constants | When keys are not enums | No | `new HashMap<>(enumMap)`, `new TreeMap<>(enumMap)` |
| `ConcurrentHashMap<K,V>` | `put()`, `get()`, `remove()`, `putIfAbsent()`, `computeIfAbsent()`, `compute()`, `merge()`, `forEach()` | High-throughput shared map in concurrent code | When sorted order is needed or null keys/values matter | Yes | `new HashMap<>(map)`, `map.newKeySet()` |
| `ConcurrentSkipListMap<K,V>` | `put()`, `get()`, `remove()`, `ceilingEntry()`, `floorEntry()`, `subMap()` | Concurrent sorted map and range queries | When sort order is unnecessary | Yes | `new TreeMap<>(map)`, `new ConcurrentHashMap<>(map)` |
| `Properties` | `getProperty()`, `setProperty()`, `load()`, `store()` | Configuration files and string-based settings | When strong typing or modern config binding is needed | Yes | `Map<String,String>`, `new Properties()` from file data |

## 8. Specialized Structures

| Structure | Mostly Used Functions | When to Use | When Not to Use | Thread Safe | Common Conversions |
| --- | --- | --- | --- | --- | --- |
| `BitSet` | `set()`, `clear()`, `get()`, `and()`, `or()`, `xor()`, `cardinality()`, `nextSetBit()` | Compact boolean flags, bit operations, sieve-like algorithms | When simple `boolean[]` is clearer or thread-safe access is required | No | `toLongArray()`, `toByteArray()`, manual conversion to `boolean[]` |
| `Dictionary<K,V>` | `put()`, `get()`, `remove()`, `keys()`, `elements()` | Only for understanding very old APIs | In all new code; use `Map` | Abstract legacy type | replace with `Map<K,V>` |

## 9. Common View and Wrapper Conversions

These are not separate storage classes, but they are frequently used conversions around the structures above.

| Conversion / Wrapper | Typical Use |
| --- | --- |
| `Arrays.asList(array)` | array to fixed-size `List` view |
| `List.of(...)`, `Set.of(...)`, `Map.of(...)` | immutable small collections |
| `new ArrayList<>(collection)` | make mutable ordered copy |
| `new HashSet<>(collection)` | remove duplicates |
| `new LinkedHashSet<>(collection)` | remove duplicates and preserve encounter order |
| `new TreeSet<>(collection)` | unique sorted copy |
| `new HashMap<>(map)` | mutable hash-based copy |
| `new LinkedHashMap<>(map)` | mutable insertion-ordered copy |
| `new TreeMap<>(map)` | sorted map copy |
| `Collections.synchronizedList(...)` | synchronized wrapper around a list |
| `Collections.synchronizedSet(...)` | synchronized wrapper around a set |
| `Collections.synchronizedMap(...)` | synchronized wrapper around a map |
| `Collections.unmodifiableList(...)` | read-only list view |
| `Collections.unmodifiableSet(...)` | read-only set view |
| `Collections.unmodifiableMap(...)` | read-only map view |
| `collection.stream()` | collection to stream pipeline |
| `stream.collect(...)` | stream to list, set, or map |

## 10. Quick Rules of Thumb

- Use `ArrayList` by default for lists.
- Use `HashSet` by default for uniqueness.
- Use `HashMap` by default for key-value lookup.
- Use `ArrayDeque` instead of `Stack`.
- Use `ConcurrentHashMap` for shared mutable maps.
- Use `CopyOnWriteArrayList` and `CopyOnWriteArraySet` only for read-heavy and write-rare cases.
- Use `TreeMap` or `TreeSet` only when sorted order or range queries are actually needed.
- Use blocking queues only when producers or consumers should wait.
- Avoid legacy classes like `Vector`, `Stack`, `Hashtable`, and `Dictionary` in new code.
