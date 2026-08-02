# Adaptive In-Memory Cache Engine (Java)

A **Redis-inspired adaptive caching system** implemented in Java that dynamically switches between **LRU (Least Recently Used)** and **LFU (Least Frequently Used)** eviction strategies based on runtime access patterns.

The project demonstrates **system design concepts, design patterns, and adaptive algorithms** commonly used in real-world caching systems.

---

# 🚀 Overview

Caches improve performance by storing frequently accessed data in fast memory.
However, the **choice of eviction policy** can significantly affect cache performance depending on the workload.

This project implements a **self-optimizing cache** that:

* Tracks access patterns in real time
* Measures frequency distribution of keys
* Dynamically switches between **LRU** and **LFU** eviction strategies

This makes the cache capable of adapting to **different workloads automatically**.

---

# 🧠 Key Concepts Demonstrated

* **Strategy Design Pattern**
* **Adaptive Algorithms**
* **Cache Eviction Policies**
* **HashMap + Doubly Linked List (LRU)**
* **Frequency Buckets (LFU)**
* **Metrics-driven optimization**
* **Dynamic strategy switching**

---

# 🏗️ System Architecture

```
                 ┌───────────────────────┐
                 │        Main           │
                 │  (Program Entry)     │
                 └───────────┬──────────┘
                             │
                             ▼
                 ┌───────────────────────┐
                 │        Cache          │
                 │   Core Cache Engine   │
                 └───────┬───────────────┘
                         │
        ┌────────────────┼──────────────────┐
        ▼                ▼                  ▼
 MetricsTracker   EvictionStrategy   AdaptivePolicyEngine
                         │
              ┌──────────┴───────────┐
              ▼                      ▼
         LRUStrategy            LFUStrategy
```

The **Cache** acts as the central component responsible for storage and eviction decisions.

---

# 📂 Project Structure

```
cache/
│
├── Main.java
├── Cache.java
├── EvictionStrategy.java
├── AdaptivePolicyEngine.java
├── MetricsTracker.java
│
├── LRUStrategy.java
├── LFUStrategy.java
│
└── Node.java
```

---

# 📄 File Explanations

## Main.java

Entry point of the program.

Simulates cache operations such as:

* Insertions (`put`)
* Access operations (`get`)

Used to test how the cache behaves under different workloads.

---

## Cache.java

Core component that manages:

* Cache storage
* Get/Put operations
* Eviction handling
* Metrics tracking
* Adaptive strategy switching

Responsibilities include:

```
Store key-value pairs
Track operations
Invoke eviction policy
Trigger adaptive evaluation
```

---

## EvictionStrategy.java

Interface defining the contract for all eviction policies.

```
keyAccessed(int key)
evictKey()
removeKey(int key)
rebuild(Iterable<Integer> keys)
```

This allows the cache to support **plug-and-play eviction algorithms**.

---

## LRUStrategy.java

Implements the **Least Recently Used (LRU)** eviction policy.

### Data Structures Used

* **HashMap** → O(1) node lookup
* **Doubly Linked List** → maintain access order

Structure:

```
Head <-> Most Recent ... Least Recent <-> Tail
```

When a key is accessed:

```
Move node to front
```

Eviction removes:

```
Tail.previous (Least Recently Used)
```

Time Complexity:

```
Access  → O(1)
Insert  → O(1)
Evict   → O(1)
```

---

## LFUStrategy.java

Implements the **Least Frequently Used (LFU)** eviction policy.

### Data Structures Used

```
keyToFreq   : Map<Key, Frequency>
freqToKeys  : Map<Frequency, Ordered Set of Keys>
```

Example:

```
keyToFreq

1 → 5
2 → 2
3 → 1
```

```
freqToKeys

1 → [3]
2 → [2]
5 → [1]
```

Eviction removes keys with the **lowest frequency**.

Time Complexity:

```
Access → O(1)
Insert → O(1)
Evict  → O(1)
```

---

## AdaptivePolicyEngine.java

This component enables **dynamic eviction strategy switching**.

Every fixed number of operations:

1. Collect access frequency data
2. Compute workload distribution
3. Decide whether **LRU or LFU** is more suitable

### Decision Logic

```
dominanceRatio = maxFrequency / totalAccess
```

If:

```
dominanceRatio > 0.4
```

Then:

```
Switch to LFU
```

Otherwise:

```
Switch to LRU
```

This allows the cache to adapt to **hot-key workloads** vs **uniform workloads**.

---

## MetricsTracker.java

Tracks cache performance statistics:

```
hits
misses
totalRequests
accessFrequency
```

Metrics collected are used by the **Adaptive Policy Engine** to optimize eviction strategy.

---

## Node.java

Represents a node used in the **LRU doubly linked list**.

```
key
prev
next
```

---

# 🔄 Cache Operation Flow

### GET Operation

```
Client
  │
  ▼
Cache.get(key)
  │
  ├─ Key Exists → HIT
  │     │
  │     ├─ MetricsTracker.recordHit()
  │     └─ EvictionStrategy.keyAccessed()
  │
  └─ Key Missing → MISS
        │
        └─ MetricsTracker.recordMiss()

operationCount++
Adaptive engine may evaluate strategy
```

---

### PUT Operation

```
Client
  │
  ▼
Cache.put(key,value)

if key exists
    update value
else
    if cache full
        strategy.evictKey()

insert key
strategy.keyAccessed()
```

---

# 🧪 Example Execution

Initial Cache Size

```
3
```

Operations:

```
PUT 1
PUT 2
PUT 3

GET 1
GET 1
GET 1
```

Access Frequency:

```
1 → 3
2 → 0
3 → 0
```

Adaptive Engine detects **hot key behavior**.

Eviction strategy switches:

```
LRU → LFU
```

---

# ⚡ Features

* O(1) cache operations
* Pluggable eviction policies
* Runtime adaptive strategy switching
* Metrics-driven optimization
* Clean modular architecture

---

# 🛠 Technologies Used

* **Java**
* HashMap
* Doubly Linked List
* Adaptive Algorithms
* Strategy Design Pattern

---

# 📈 Potential Improvements

Future extensions for the system:

### TTL Expiration

```
put(key,value,ttl)
```

### Thread-Safe Cache

```
ConcurrentHashMap
ReentrantLock
```

### Benchmarking Framework

Simulate workloads such as:

```
Hotspot Access
Uniform Random Access
Sequential Access
```

### Additional Eviction Policies

```
MRU
FIFO
ARC
```

---

# 🎯 Learning Outcomes

Through this project you will learn:

* How real caching systems work
* How eviction algorithms operate internally
* How to design extensible systems
* How to build adaptive runtime optimizations

---

# 📜 License

This project is open-source and free to use for educational purposes.
