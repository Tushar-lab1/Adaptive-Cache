package cache;

public class Main {

    public static void main(String[] args) {

        // Creating a cache object by passing the parameters 1. Cache size 2. Eviction strategy object
        Cache cache = new Cache(3, new LRUStrategy());
        // Created a Policy Engine by passing this cache
        AdaptivePolicyEngine engine = new AdaptivePolicyEngine(cache);

        cache.put("1", "10");
        cache.put("2", "20");
        cache.put("3", "30");

        cache.get("1");
        cache.get("1");
        cache.get("1");

        cache.get("1");
        cache.get("2");
        cache.get("3");
        
        cache.get("1");
        cache.get("2");
        cache.get("3");
        
        cache.get("1");
        cache.get("2");
        cache.get("3");
        
        cache.get("1");
        cache.get("2");
        cache.get("3");
        
        cache.get("1");
        cache.get("2");
        cache.get("3");

        cache.printCacheState();
    }
}