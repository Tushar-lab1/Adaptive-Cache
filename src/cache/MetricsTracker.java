package cache;

public class MetricsTracker {
    private int hits = 0;
    private int miss = 0;
    private int totalRequests = 0;

    public void recordHit() {
        hits++;
        totalRequests++;
    }

    public void recordMiss() {
        miss++;
        totalRequests++;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public double hitRatio() {
        return totalRequests == 0 ? 0 : (double) hits/totalRequests;
    }

    public double missRatio() {
        return totalRequests == 0? 0 : (double) miss/totalRequests;
    }
}
