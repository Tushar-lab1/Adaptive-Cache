package cache;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    
    private final int capacity; // Cache Storage Capacity
    private final Map<String, String> storage; // Cache Table storage

    private EvictionStrategy strategy; // Eviction Strategy Interface
    private MetricsTracker metrics; // Metrics tracker
    private AdaptivePolicyEngine engine; // Policy Engine

    private int operationCount = 0; // Count of all the operations
    private static final int Evaluation_Threshold = 100;

    // Cache Constructor
    public Cache(int capacity , EvictionStrategy strategy) {
        this.capacity = capacity;
        this.strategy = strategy;
        this.storage = new HashMap<>();
        this.metrics = new MetricsTracker();
        this.engine = new AdaptivePolicyEngine(this);
        // System.out.println(this);
    }

    // This method returns the value of the node whenever the key is given as an input.
    // If the cache contains the key it records it as a hit else a miss. It also updates the eviction strategy that the key was accessed.
    // The overall operation count is increased by 1. We then check whether we should switch to different strategy and then return the value from the storage/Cache.
    public synchronized String get(String key) {

        if(storage.containsKey(key)) {
            metrics.recordHit(key);
            strategy.keyAccessed(key);
            operationCount++;
            checkAdaptiveSwitch();
            return storage.get(key);
        }

        metrics.recordMiss(key);
        operationCount++;
        checkAdaptiveSwitch();
        return null;
    }

    // This method is used to put a value inside the cache system.
    // If the key is already present if inform the eviction strategy that the key was accessed and also record it as a hit.
    // We check if we should switch to different eviction strategy.
    // If the key is not present record it as miss. If the storage is full then remove a key-value pair from the storage based on the eviction strategy. 
    public synchronized void put(String key , String value) {

        if(storage.containsKey(key)) {
            storage.put(key , value);
            strategy.keyAccessed(key);
            metrics.recordHit(key);
            operationCount++;
            checkAdaptiveSwitch();
            return;
        }

        metrics.recordMiss(key);

        if(storage.size() == capacity) {
            String key_to_evict = strategy.evictKey();
            storage.remove(key_to_evict);
            strategy.removeKey(key_to_evict);
        }

        storage.put(key , value);
        strategy.keyAccessed(key);
        operationCount++;
        checkAdaptiveSwitch();
    }

    public synchronized void remove(String key) {
        if(storage.containsKey(key)) {
            storage.remove(key);
            strategy.removeKey(key);
        }
    }

    // This method is used to check if the operation count is greater the the evaluation threshold then the eviction strategy should be switched and the operation count is once again zero.
    private void checkAdaptiveSwitch() {
        if(operationCount >= Evaluation_Threshold) {
            engine.evaluateAndSwitch();
            operationCount = 0;
        }
    }

    // This method is used to switch the strategy to another we will store the keys present inside the cache to the new eviction strategy.
    public synchronized void switchStrategy(EvictionStrategy newStrategy) {
        newStrategy.rebuild(storage.keySet());
        this.strategy = newStrategy;

        System.out.println("Strategy switched to : " + newStrategy.getClass().getSimpleName());
    }

    public MetricsTracker getMetrics() {
        return metrics;
    }

    public EvictionStrategy getStrategy() {
        return strategy;
    }

    public synchronized void printCacheState() {
        System.out.println("Current Cache Contents:");
        for (String key : storage.keySet()) {
            System.out.println("Key: " + key + " Value: " + storage.get(key));
        }

        System.out.println("Active Strategy: " + strategy.getClass().getSimpleName());
    }
}