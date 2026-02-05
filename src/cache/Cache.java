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
        return null;
    }

    public void put(int key , int value) {

    }

    public void switchStrategy(EvictionStrategy newStrategy) {
        this.strategy = newStrategy;
    }
}