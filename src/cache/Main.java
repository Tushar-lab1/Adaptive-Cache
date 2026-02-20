package cache;

public class Main {
    public static void main(String[] args) {
        
        Cache cache = new Cache(3, new LRUStrategy());

        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);

        cache.get(1);
        cache.get(1);
        cache.get(2);

        System.out.println("\n--- Switching to LFU ---");
        cache.switchStrategy(new LFUStrategy());

        cache.put(4, 400);  // Should now use LFU eviction
    }
}
