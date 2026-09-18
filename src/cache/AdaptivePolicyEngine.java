package cache;

import java.util.Map;

public class AdaptivePolicyEngine {
    private Cache cache;

    public AdaptivePolicyEngine(Cache cache) {
        this.cache = cache;
    }

    public void evaluateAndSwitch() {
        Map<String, Integer> freq = cache.getMetrics().getAccessFrequency();
        if(freq.isEmpty()) return;

        int maxFreq = 0;
        int totalAccess = 0;

        for(int f : freq.values()) {
            maxFreq = Math.max(maxFreq , f);
            totalAccess += f;
        }

        double dominanceRatio = (double) maxFreq/totalAccess;

        if (dominanceRatio > 0.4 &&
                !(cache.getStrategy() instanceof LFUStrategy)) {

            System.out.println("Adaptive Engine: Switching to LFU");
            cache.switchStrategy(new LFUStrategy());

        } else if (dominanceRatio <= 0.4 &&
                !(cache.getStrategy() instanceof LRUStrategy)) {

            System.out.println("Adaptive Engine: Switching to LRU");
            cache.switchStrategy(new LRUStrategy());
        }

        cache.getMetrics().resetFrequency();
    }
}
