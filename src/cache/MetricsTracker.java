package cache;

import java.util.HashMap;
import java.util.Map;

public class MetricsTracker {
    private int hits = 0;
    private int miss = 0;
    private int totalRequests = 0;
    private Map<String, Integer> accessFrequency = new HashMap<>();

    public void recordHit(String key) {
        hits++;
        totalRequests++;
        recordAccess(key);
    }

    public void recordMiss(String key) {
        miss++;
        totalRequests++;
        recordAccess(key);
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    private void recordAccess(String key) {
        accessFrequency.put(key , accessFrequency.getOrDefault(key , 0) + 1);
    }
    public int getHits() {
        return hits;
    }
    public int getMisses() {
        return miss;
    }

    public Map<String, Integer> getAccessFrequency() {
        return accessFrequency;
    }

    public void resetFrequency() {
        accessFrequency.clear();
    }

    public double hitRatio() {
        return totalRequests == 0 ? 0 : (double) hits/totalRequests;
    }

    public double missRatio() {
        return totalRequests == 0? 0 : (double) miss/totalRequests;
    }
}
