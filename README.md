# Adaptive Distributed Cache Engine (Java)

A **Redis-inspired distributed adaptive caching system** implemented in Java. What started as a local cache dynamically switching between **LRU (Least Recently Used)** and **LFU (Least Frequently Used)** eviction strategies based on runtime access patterns, is now being extended into a fully distributed caching cluster.

The project demonstrates **system design concepts, distributed systems, consistent hashing, design patterns, and adaptive algorithms** commonly used in real-world caching systems like Redis and Memcached.

---

# 🚀 Overview

Caches improve performance by storing frequently accessed data in fast memory.
However, in modern applications, a single node is often not enough. 

This project implements a **distributed, self-optimizing cache cluster** that features:

* **Distributed Nodes**: Multiple cache nodes communicating over a network.
* **Consistent Hashing**: Scalable and balanced key distribution across nodes.
* **Replication**: Primary/replica node architecture for fault tolerance.
* **Cache Invalidation**: Cross-node cache invalidation mechanisms.
* **Adaptive Eviction Engine**: Local nodes track access patterns and dynamically switch between **LRU** and **LFU** eviction strategies.
* **Interactive CLI**: A simple command-line interface to interact with the cache cluster.

---

# 🧠 Key Concepts Demonstrated

* **Distributed Systems & Networking (Sockets)**
* **Consistent Hashing Algorithm (Hash Ring)**
* **Data Replication (Primary-Replica)**
* **Strategy Design Pattern**
* **Adaptive Algorithms**
* **Cache Eviction Policies (LRU, LFU)**
* **Metrics-driven optimization**

---

# 🏗️ System Architecture

## Distributed Architecture

```text
                  ┌───────────────────────┐
                  │    Interactive CLI    │
                  └───────────┬──────────┘
                              │ (Network Socket)
                              ▼
                  ┌───────────────────────┐
                  │   Cluster Manager     │
                  │ (Consistent Hashing)  │
                  └───────────┬──────────┘
                              │
          ┌───────────────────┼───────────────────┐
          ▼                   ▼                   ▼
  ┌───────────────┐   ┌───────────────┐   ┌───────────────┐
  │ Primary Node 1│   │ Primary Node 2│   │ Primary Node 3│
  └───────┬───────┘   └───────┬───────┘   └───────┬───────┘
          │                   │                   │
  ┌───────▼───────┐   ┌───────▼───────┐   ┌───────▼───────┐
  │ Replica Node A│   │ Replica Node B│   │ Replica Node C│
  └───────────────┘   └───────────────┘   └───────────────┘
```

## Local Node Architecture

Inside every `Primary Node` and `Replica Node`, an adaptive cache engine operates:

```text
                  ┌───────────────────────┐
                  │ CacheNodeServer (Net) │
                  └───────────┬──────────┘
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

---

# 📂 Project Structure

```text
src/
├── cache/ (Core Engine)
│   ├── Cache.java
│   ├── EvictionStrategy.java
│   ├── AdaptivePolicyEngine.java
│   ├── MetricsTracker.java
│   ├── LRUStrategy.java
│   ├── LFUStrategy.java
│   └── Node.java
│
└── distributed/ (Distributed Layer)
    ├── CacheNodeServer.java    (Server Node)
    ├── ConsistentHashing.java  (Hash Ring)
    ├── ClusterManager.java     (Routing logic)
    ├── ReplicationHandler.java (Syncing)
    └── CLI.java                (User Client)
```

---

# 🔄 Current Development Status

The system is currently undergoing a major upgrade from a single-node memory cache to a distributed network cache. 

**Completed:**
- Refactored `Cache` core to support string-based keys and values (Redis-like).
- Introduced thread-safety (`synchronized`) in core cache operations for concurrent socket handling.
- Cache invalidation (`remove`) logic.

**In Progress:**
- Implementing the `distributed` package.
- Consistent hashing and cluster manager.
- Socket-based node servers and replication.

---

# 📜 License

This project is open-source and free to use for educational purposes.
