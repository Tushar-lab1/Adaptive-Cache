package cache;

public class Main {
    public static void main(String[] args) {
        Cache cache = new Cache(3, new LRUStrategy());
        System.out.println("---- PUT OPERATIONS ----");
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);

        System.out.println("\n---- GET OPERATIONS ----");
        System.out.println("Get 1: " + cache.get(1)); // HIT
        System.out.println("Get 2: " + cache.get(2)); // HIT

        System.out.println("\n---- ADDING NEW ITEM (Triggers Eviction) ----");
        cache.put(4, 400); // Evicts LRU

        System.out.println("\n---- FINAL CHECK ----");
        System.out.println("Get 3 (should be null if evicted): " + cache.get(3));
        System.out.println("Get 4: " + cache.get(4));
        System.out.println("Get 1: " + cache.get(1));
        System.out.println("Get 2: " + cache.get(2));
    }
}
