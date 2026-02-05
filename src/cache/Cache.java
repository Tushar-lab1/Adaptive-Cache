package cache;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private final int capacity;
    private final Map<Integer , Integer> storage;
    private EvictionStrategy strategy;
    private MetricsTracker metrics;

    public Cache(int capacity , EvictionStrategy strategy) {
        this.capacity = capacity;
        this.storage = new HashMap<>();
        this.strategy = strategy;
        this.metrics = new MetricsTracker();
    }

    public Integer get(int key) {
        if(storage.containsKey(key)) {
            metrics.recordHit();
            strategy.keyAccessed(key);
            return storage.get(key);
        }
        metrics.recordMiss();
        return null;
    }

    public void put(int key , int value) {
        if(storage.containsKey(key)) {
            storage.put(key , value);
            strategy.keyAccessed(key);
            metrics.recordHit();
            return;
        }
        metrics.recordMiss();
        if(storage.size() == capacity) {
            int key_to_evict = strategy.evictKey();
            storage.remove(key_to_evict);
            strategy.removeKey(key_to_evict);
        }

        storage.put(key , value);
        strategy.keyAccessed(key);
    }

    public void switchStrategy(EvictionStrategy newStrategy) {
        this.strategy = newStrategy;
    }
}